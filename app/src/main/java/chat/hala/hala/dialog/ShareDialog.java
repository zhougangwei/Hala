package chat.hala.hala.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.Contact;
import chat.hala.hala.utils.Util;

public class ShareDialog extends Dialog {


    private static final int THUMB_SIZE = 150;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.iv_weixin)
    TextView ivWeixin;
    @BindView(R.id.iv_weixin_quan)
    TextView ivWeixinQuan;
    @BindView(R.id.iv_qq)
    TextView ivQq;
    @BindView(R.id.iv_share_scan)
    TextView ivShareScan;
    @BindView(R.id.iv_share_copy)
    TextView ivShareCopy;
    private Activity mContext;
    private String mTitle, mMsg;
    private String mCancel;
    private String mConfirm;

    private OnClickListener mListener;
    private IWXAPI api;
    private Tencent mTencent;
    private static final String TAG="ShareDialog";


    public ShareDialog(@NonNull Activity context) {
        super(context);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTencent = Tencent.createInstance(Contact.QQ_APP_ID, mContext.getApplicationContext());
        api = WXAPIFactory.createWXAPI(mContext, Contact.WEIXIN_APP_ID, false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width =  ScreenUtils.getScreenWidth(mContext);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.getWindow().setAttributes(lp);


    }


    private void initViews() {

    }


    @OnClick({R.id.iv_weixin, R.id.iv_weixin_quan, R.id.iv_qq, R.id.iv_share_scan, R.id.iv_share_copy, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_weixin:
                shareToWeixin();
                break;
            case R.id.iv_weixin_quan:
                shareToWeiXinTimeLine();
                break;
            case R.id.iv_qq:
                shareToQQ();
                break;
            case R.id.iv_share_scan:
                break;
            case R.id.iv_share_copy:
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void shareToQQ() {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566448206051&di=0f5642d1e9182d2b91faca5a9fb47a8d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201712%2F22%2F20171222223729_d8HCB.jpeg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "Pa聊");
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "快来Pa聊");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "我我哦我我我我");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://www.qq.com");


        mTencent.shareToQQ(mContext, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i(TAG, "onComplete: " );
            }

            @Override
            public void onError(UiError uiError) {
                Log.i(TAG, "onError: " );
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: " );
            }
        });
    }

    private void shareToWeiXinTimeLine() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.qq.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.send_img);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);


    }

    private void shareToWeixin() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.qq.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.send_img);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }





    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
