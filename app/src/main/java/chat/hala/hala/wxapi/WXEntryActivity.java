package chat.hala.hala.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessView;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessWebview;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import chat.hala.hala.activity.LoginActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.weixinqq.NetworkUtil;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;
    private MyHandler handler;

	private static class MyHandler extends Handler {
		private final WeakReference<WXEntryActivity> wxEntryActivityWeakReference;

		public MyHandler(WXEntryActivity wxEntryActivity){
			wxEntryActivityWeakReference = new WeakReference<WXEntryActivity>(wxEntryActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			int tag = msg.what;
			switch (tag) {
				case NetworkUtil.GET_TOKEN: {
					Bundle data = msg.getData();
					JSONObject json = null;
					try {
						json = new JSONObject(data.getString("result"));
						String openId, accessToken, refreshToken, scope;
						openId = json.getString("openid");
						accessToken = json.getString("access_token");
						refreshToken = json.getString("refresh_token");
						scope = json.getString("scope");

						Intent intent = new Intent(wxEntryActivityWeakReference.get(), LoginActivity.class);
						intent.putExtra("openId", openId);
						intent.putExtra("accessToken", accessToken);
						intent.putExtra("refreshToken", refreshToken);
						intent.putExtra("scope", scope);
						wxEntryActivityWeakReference.get().startActivity(intent);
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
				}
			}
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, Contact.WEIXIN_APP_ID, false);
		handler = new MyHandler(this);
		LogUtils.e("WXEntryActivity");
        try {
            Intent intent = getIntent();
        	api.handleIntent(intent, this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:

			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:

			break;
		default:
			break;
		}
        finish();
	}

	@Override
	public void onResp(BaseResp resp) {

		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			break;
		default:
			break;
		}
		
		if (resp.getType() == ConstantsAPI.COMMAND_SUBSCRIBE_MESSAGE) {
			SubscribeMessage.Resp subscribeMsgResp = (SubscribeMessage.Resp) resp;
			String text = String.format("openid=%s\ntemplate_id=%s\nscene=%d\naction=%s\nreserved=%s",
					subscribeMsgResp.openId, subscribeMsgResp.templateID, subscribeMsgResp.scene, subscribeMsgResp.action, subscribeMsgResp.reserved);

			Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		}

        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProgramResp = (WXLaunchMiniProgram.Resp) resp;
            String text = String.format("openid=%s\nextMsg=%s\nerrStr=%s",
                    launchMiniProgramResp.openId, launchMiniProgramResp.extMsg,launchMiniProgramResp.errStr);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_OPEN_BUSINESS_VIEW) {
            WXOpenBusinessView.Resp launchMiniProgramResp = (WXOpenBusinessView.Resp) resp;
            String text = String.format("openid=%s\nextMsg=%s\nerrStr=%s\nbusinessType=%s",
                    launchMiniProgramResp.openId, launchMiniProgramResp.extMsg,launchMiniProgramResp.errStr,launchMiniProgramResp.businessType);

            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        if (resp.getType() == ConstantsAPI.COMMAND_OPEN_BUSINESS_WEBVIEW) {
            WXOpenBusinessWebview.Resp response = (WXOpenBusinessWebview.Resp) resp;
            String text = String.format("businessType=%d\nresultInfo=%s\nret=%d",response.businessType,response.resultInfo,response.errCode);
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			SendAuth.Resp authResp = (SendAuth.Resp)resp;
			final String code = authResp.code;
			NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
							"appid=%s&secret=%s&code=%s&grant_type=authorization_code", Contact.WEIXIN_APP_ID,
					Contact.WEIXIN_SECRET, code), NetworkUtil.GET_TOKEN);
		}
        finish();
	}
	

	

}