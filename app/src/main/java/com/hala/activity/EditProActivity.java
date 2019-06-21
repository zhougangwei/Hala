package com.hala.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hala.R;
import com.hala.avchat.AvchatInfo;
import com.hala.avchat.QiniuInfo;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.ApplyAnchorBean;
import com.hala.bean.BeAnchorBean;
import com.hala.bean.QiNiuToken;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;
import com.hala.http.UploadPicManger;
import com.hala.utils.GsonUtil;
import com.hala.utils.ToastUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditProActivity extends BaseActivity {



    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.et_zodiac)
    EditText etZodiac;
    @BindView(R.id.ll_zodiac)
    LinearLayout llZodiac;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.et_introction)
    EditText etIntroction;
    @BindView(R.id.ll_introction)
    LinearLayout llIntroction;
    @BindView(R.id.et_tags)
    EditText etTags;
    @BindView(R.id.ll_tags)
    LinearLayout llTags;
    @BindView(R.id.et_bio)
    EditText etBio;
    @BindView(R.id.ll_bio)
    LinearLayout llBio;
    @BindView(R.id.et_certified)
    EditText etCertified;
    @BindView(R.id.ll_certified)
    LinearLayout llCertified;
    @BindView(R.id.tv_save)
    TextView tvSave;

    List<ApplyAnchorBean.TagsBean> tagsList = new ArrayList();
    List<ApplyAnchorBean.CoversBean> covers = new ArrayList<>();
    private String bio;     //个人经历
    private static final int REQUEST_BIO = 222;
    private static final int REQUEST_TAG = 223;
    private static final int REQUEST_CODE_CHOOSE =224 ;
    private List<Uri> uriList;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_pro;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.iv_add_pic, R.id.ll_username, R.id.ll_phone_num, R.id.ll_height, R.id.ll_weight, R.id.ll_zodiac, R.id.ll_city, R.id.ll_introction, R.id.ll_tags, R.id.ll_bio, R.id.ll_certified, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_pic:
                gotoAddPic();
                break;
            case R.id.ll_username:
                break;
            case R.id.ll_phone_num:
                break;
            case R.id.ll_height:
                break;
            case R.id.ll_weight:
                break;
            case R.id.ll_zodiac:
                break;
            case R.id.ll_city:
                break;
            case R.id.ll_introction:
                break;
            case R.id.ll_tags:
                Intent tagIntent = new Intent(this, TagActivity.class);
                startActivityForResult(tagIntent, REQUEST_TAG);
                break;
            case R.id.ll_bio:
                Intent intent = new Intent(this, BioActivity.class);
                startActivityForResult(intent, REQUEST_BIO);
                break;
            case R.id.ll_certified:
                break;
            case R.id.tv_save:
                upQiniu();
                break;
        }
    }

    private void gotoAddPic() {
        Matisse.from(this)
                .choose(MimeType.allOf())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(5)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.example.xx.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(new GlideEngine())//图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE);//


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAG) {
                tagsList.clear();
                String tag = data.getStringExtra("tag");
                List<ApplyAnchorBean.TagsBean> tagList = GsonUtil.parseJsonToList(tag,
                        new TypeToken<List<ApplyAnchorBean.TagsBean>>() {
                        }.getType()
                );
                tagsList.addAll(tagList);
            } else if (requestCode == REQUEST_BIO) {
                bio = data.getStringExtra("bio");
            }else if(requestCode==REQUEST_CODE_CHOOSE){
                uriList = Matisse.obtainResult(data);



            }


        }


    }

    private void upQiniu() {
        QiNiuToken.DataBean.StarchatanchorBean starchatanchorBean = QiniuInfo.getmStarchatanchorBean();
        if (starchatanchorBean==null) {
            return;
        }
        new UploadPicManger().uploadImageArray(uriList, 0, starchatanchorBean.getToken(), starchatanchorBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                gotoSave(paths);
            }
            @Override
            public void uploadFailure() {
                gotoSave(null);
            }
        });
    }

    private void gotoSave(List<String> paths) {


        if (paths!=null) {
            covers.clear();
            for (int i = 0; i < paths.size(); i++) {
                ApplyAnchorBean.CoversBean coversBean = new ApplyAnchorBean.CoversBean();
                coversBean.setCoverUrl(paths.get(i));
                covers.add(coversBean);
            }
        }



        String userName = etUsername.getText().toString();
        String phoneNum = etPhoneNum.getText().toString();
        String height = etHeight.getText().toString();
        String weight = etWeight.getText().toString();
        String zodiac = etZodiac.getText().toString();  //星座
        String city = etCity.getText().toString();
        String intro = etIntroction.getText().toString();

        String certify = etCertified.getText().toString();
        ApplyAnchorBean applyAnchorBean = new ApplyAnchorBean();
        applyAnchorBean.setNickname(userName);
        applyAnchorBean.setMobileNumber(phoneNum);
        applyAnchorBean.setHeight(TextUtils.isEmpty(height) ? 0 : Integer.parseInt(height));
        applyAnchorBean.setWeight(TextUtils.isEmpty(weight) ? 0 : Integer.parseInt(weight));
        applyAnchorBean.setZodiac(zodiac);
        applyAnchorBean.setCity(city);
        applyAnchorBean.setIntroduction(intro);
        applyAnchorBean.setTags(tagsList);
        applyAnchorBean.setCertifyUrl(certify);
        applyAnchorBean.setBiography(bio);
        applyAnchorBean.setCovers(covers);

        RetrofitFactory.getInstance()
                .applyAnchor(ProxyPostHttpRequest.getJsonInstance()
                        .applyAnchor(GsonUtil.parseObjectToJson(applyAnchorBean)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BeAnchorBean>() {
                    @Override
                    public void onNext(BeAnchorBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            ToastUtils.showToast(EditProActivity.this, "提交失败");
                            return;
                        }
                        int id = baseBean.getData().getId();
                        AvchatInfo.setAnchorId(id);
                        ToastUtils.showToast(EditProActivity.this, "提交成功");
                    }
                });

    }


}
