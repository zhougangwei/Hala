package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.FamilyApplication;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.EditEmpty;
import chat.hala.hala.utils.EditUtils;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApplyFamilyActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.textView22)
    TextView textView22;
    @BindView(R.id.textView27)
    TextView textView27;
    @BindView(R.id.textView28)
    TextView textView28;
    @BindView(R.id.et_family_name)
    @EditEmpty("家族昵称")
    EditText etFamilyName;
    @BindView(R.id.et_family_anchor_num)
    EditText etFamilyAnchorNum;
    @EditEmpty("手机号")
    @BindView(R.id.et_family_phone_num)
    EditText etFamilyPhoneNum;
    @EditEmpty("申请人")
    @BindView(R.id.et_family_apply_name)
    EditText etFamilyApplyName;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private List<String> uriList = new ArrayList<>();
    private String mediaurl;
    private String avatarUrl;
    private String familyName;
    private String linkman;
    private String linkModel;
    private String num;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_apply_family;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("家族申请");
    }


    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                if (EditUtils.judeEmpty(this)) {
                    judgeEmpty();
                    upQiniu();
                }
                break;
            case R.id.iv_head:
                ChoosePicManager.choosePic(this, 1);
        }
    }

    private void judgeEmpty() {



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
            List<String> strings = Matisse.obtainPathResult(data);
            if (strings != null && strings.size() > 0) {
                uriList.clear();
                uriList.addAll(strings);
                mediaurl = uriList.get(0);
                Glide.with(this).load(mediaurl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
            }
        }


    }


    private void upQiniu() {
        QiNiuToken.DataBean.StarchatmemberBean starchatmemberBean = QiniuInfo.getmStarchatmemberBean();
        if (starchatmemberBean == null) {
            QiniuInfo.initQiniu();
            ToastUtils.showToast(ApplyFamilyActivity.this, "图片上传失败,请过三秒重新提交!");
            return;
        }
        new UploadPicManger().uploadImageArray(uriList, 0, starchatmemberBean.getToken(), starchatmemberBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                avatarUrl = paths.get(0);
                gotoApply();
            }

            @Override
            public void uploadFailure() {
                // TODO: 2019/6/25 0025 上传图片失败
                ToastUtils.showToast(ApplyFamilyActivity.this, "图片上传失败,请重新提交!");
            }
        });
    }

    private void gotoApply() {
        familyName = etFamilyName.getText().toString();
        linkman = etFamilyApplyName.getText().toString();
        linkModel = etFamilyPhoneNum.getText().toString();
        num = etFamilyAnchorNum.getText().toString();
        FamilyApplication bean = new FamilyApplication();
        bean.setFamilyName(familyName);
        bean.setLinkman(linkman);
        bean.setMediaUrl(avatarUrl);
        bean.setLinkmanMobel(linkModel);
        bean.setMemNumbers(num);

        RetrofitFactory.getInstance().applyFamily(ProxyPostHttpRequest.getJsonInstance().applyFamily(GsonUtil.parseObjectToJson(bean)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            ToastUtils.showToast(ApplyFamilyActivity.this, "申请成功!请等待审核");
                            finish();
                        }else{
                            ToastUtils.showToast(ApplyFamilyActivity.this, "申请失败!您已经有家族了或者您已申请过家族!");
                        }

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
