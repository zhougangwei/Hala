package chat.hala.hala.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.ToastUtils;

public class AuthenticationActivity extends BaseActivity {


    private static final String TAG = "AuthenticationActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.iv_front_card)
    ImageView ivFrontCard;
    @BindView(R.id.ll_front)
    LinearLayout llFront;
    @BindView(R.id.iv_back_card)
    ImageView ivBackCard;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.iv_hand_card)
    ImageView ivHandCard;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.ll_hand)
    LinearLayout llHand;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.textView4)
    TextView textView4;


    public static final int FROT_FRONT = 1;
    public static final int FROT_BACK = 2;
    public static final int FROT_HAND = 3;
    private int currentType;

    private String frontUrl;

    private String handUrl;


    @Override

    protected int getContentViewId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("实名认证");
    }


    @OnClick({R.id.iv_back, R.id.ll_front, R.id.ll_back, R.id.ll_hand, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_front:
                setChooseType(FROT_FRONT);
                ChoosePicManager.choosePic(AuthenticationActivity.this, 1);
                break;
            case R.id.ll_back:
                setChooseType(FROT_BACK);
                ChoosePicManager.choosePic(AuthenticationActivity.this, 1);
                break;
            case R.id.ll_hand:
                setChooseType(FROT_HAND);
                ChoosePicManager.choosePic(AuthenticationActivity.this, 1);
                break;
            case R.id.tv_submit:
                if (!judgeEmpty()) {
                    return;
                } else {
                    ToastUtils.showToast(this,"正在提交!");
                    upQiniu();
                }
                break;
        }
    }

    private void setChooseType(int type) {
        currentType = type;

    }

    private boolean judgeEmpty() {
        if (TextUtils.isEmpty(frontUrl)) {
            ToastUtils.showToast(this, "请输入正面照");
            return false;
        }
     /*   if (backUrl.isEmpty()) {
            ToastUtils.showToast(this, "请输入背面照");
            return false;
        }*/
        if (TextUtils.isEmpty(handUrl)) {
            ToastUtils.showToast(this, "请输入手持照");
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
            List<String> strings = Matisse.obtainPathResult(data);
            if (strings != null) {
                String picUrl = strings.get(0);
                switch (currentType) {
                    case FROT_FRONT:
                        frontUrl = picUrl;
                        Glide.with(this).load(picUrl)
                                .apply(RequestOptions.placeholderOf(ivFrontCard.getDrawable()))
                                .into(ivFrontCard);
                        break;

                    case FROT_HAND:
                        handUrl = picUrl;
                        Glide.with(this).load(picUrl)
                                .apply(RequestOptions.placeholderOf(ivHandCard.getDrawable()))
                                .into(ivHandCard);
                        break;
                }
            }
        }
    }

    private void upQiniu() {
        QiNiuToken.DataBean.StarchatanchorBean starchatanchorBean = QiniuInfo.getmStarchatanchorBean();
        if (starchatanchorBean == null) {
            QiniuInfo.initQiniu();
            ToastUtils.showToast(AuthenticationActivity.this, "图片上传失败,请过三秒重新提交!");
            return;
        }
        ArrayList uriList = new ArrayList();
        uriList.add(handUrl);
        uriList.add(frontUrl);

        new UploadPicManger().uploadImageArray(uriList, 0, starchatanchorBean.getToken(), starchatanchorBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra("frontCard", paths.get(1));
                    intent.putExtra("handCard", paths.get(0));
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    ToastUtils.showToast(AuthenticationActivity.this, "上传失败,请重新上传");
                }
            }

            @Override
            public void uploadFailure() {
                // TODO: 2019/6/25 0025 上传图片失败
                LogUtils.e(TAG, "uploadFailure: 失败");
            }
        });
    }
}
