package chat.hala.hala.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.EditHeadAdapter;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.ApplyAnchorBean;
import chat.hala.hala.bean.BeAnchorBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ToastUtils;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApplyAnchorActivity extends BaseActivity {


    private static final String TAG = "ApplyAnchorActivity";
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
    @BindView(R.id.tv_height_weight)
    TextView etHeightWeight;
    @BindView(R.id.ll_height_weight)
    LinearLayout llHeightWeight;

    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.ll_birth)
    LinearLayout llBirth;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.ll_city)
    LinearLayout llCity;


    @BindView(R.id.et_bio)
    TextView etBio;
    @BindView(R.id.ll_bio)
    LinearLayout llBio;


    @BindView(R.id.rv_pic)
    RecyclerView recyclerView;
    @BindView(R.id.tv_video_verity)
    TextView tvVideoVerity;
    @BindView(R.id.ll_video_verity)
    LinearLayout llVideoVerity;
    @BindView(R.id.tv_name_verity)
    TextView tvNameVerity;
    @BindView(R.id.ll_name_verity)
    LinearLayout llNameVerity;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;


    private List<EditHeadAdapter.UserHead> mList;
    private List<String> uriList = new ArrayList<>();

    List<ApplyAnchorBean.AnchorBean.AlbumBean> covers = new ArrayList<>();
    private String bio;     //个人经历
    EditHeadAdapter mAdapter;


    private String userName;
    private String phoneNum;
    private String height;
    private String weight;
    private String birth;
    private String city;

    private DoublePicker heightPicker;

    private String idCardFrontUrl;

    private String idCardHandledUrl;

    private String videoUrl;
    private boolean clickUp;
    private int chargePostion;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_prepare_be_anchor;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("认证提交");
        mList = new ArrayList<>();
        mList.add(new EditHeadAdapter.UserHead("", true));
        mAdapter = new EditHeadAdapter(mList);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).isAdd()) {
                    if (3 - (mList.size() - 1) == 0) {
                        return;
                    }
                    chargePostion = 0;
                    ChoosePicManager.choosePic(ApplyAnchorActivity.this, 3 - (mList.size() - 1));
                } else {
                    chargePostion = position;
                    ChoosePicManager.choosePic(ApplyAnchorActivity.this, 1);
                }

            }
        });


    }

    @OnClick({R.id.ll_birth, R.id.ll_city, R.id.ll_bio, R.id.iv_back, R.id.ll_height_weight, R.id.ll_video_verity, R.id.ll_name_verity, R.id.tv_submit})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.ll_birth:
                setBirth();
                break;
            case R.id.ll_bio:
                Intent intent = new Intent(this, BioActivity.class);
                startActivityForResult(intent, Contact.REQUEST_BIO);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_height_weight:
                chooseHeightAndWeight();
                break;
            case R.id.ll_video_verity:
                gotoVideoVerity();
                break;
            case R.id.ll_name_verity:
                gotoNameVerity();
                break;
            case R.id.tv_submit:
                if (!judgeEmpty()) {
                    return;
                } else {
                    upQiniu();
                    ToastUtils.showToast(this, "正在上传!");
                }
                break;
        }
    }

    /*
     * 实名认证
     * */
    private void gotoNameVerity() {
        startActivityForResult(new Intent(this, AuthenticationActivity.class), Contact.REQUEST_CHOOSE_CARD);
    }

    /*
    视频认证
    * */
    private void gotoVideoVerity() {
        startActivityForResult(new Intent(this, VideoVerityActivity.class), Contact.REQUEST_VIDEO_VERIFY);
    }


    private void chooseHeightAndWeight() {
        if (heightPicker == null) {
            final ArrayList<String> firstData = new ArrayList<>();
            for (int i = 100; i <= 200; i++) {
                firstData.add(i + "");
            }

            final ArrayList<String> secondData = new ArrayList<>();
            for (int i = 0; i <= 99; i++) {
                if (i < 10) {
                    secondData.add("0" + i);
                } else {
                    secondData.add("" + i);
                }
            }
            heightPicker = new DoublePicker(this, firstData, secondData);
            heightPicker.setFirstLabel("", "CM");
            heightPicker.setDividerVisible(true);
            heightPicker.setCycleDisable(true);
            heightPicker.setSelectedIndex(60, 50);
            heightPicker.setSecondLabel("", "KG");
            heightPicker.setTextSize(15);
            heightPicker.setContentPadding(15, 15);
            heightPicker.setOnPickListener(new DoublePicker.OnPickListener() {
                @Override
                public void onPicked(int selectedFirstIndex, int selectedSecondIndex) {
                    height = firstData.get(selectedFirstIndex);
                    weight = secondData.get(selectedSecondIndex);
                    etHeightWeight.setText(height+"/"+weight);
                }
            });
            heightPicker.show();
        } else {
            heightPicker.show();
        }

    }

    private boolean judgeEmpty() {
        userName = etUsername.getText().toString();
        phoneNum = etPhoneNum.getText().toString();
        birth = tvBirth.getText().toString();  //星座
        city = etCity.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showToast(this, "userName" + "不可以为空");
            return false;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.showToast(this, "phoneNum" + "不可以为空");
            return false;
        }
        phoneNum = "+86" + phoneNum;

        if (TextUtils.isEmpty(height)) {
            ToastUtils.showToast(this, "height" + "不可以为空");
            return false;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtils.showToast(this, "weight" + "不可以为空");
            return false;
        }
        if (TextUtils.isEmpty(birth)) {
            ToastUtils.showToast(this, "birth" + "不可以为空");
            return false;
        }


        if (TextUtils.isEmpty(city)) {
            ToastUtils.showToast(this, "city" + "不可以为空");
            return false;
        }

        if (uriList == null || uriList.size() == 0) {
            ToastUtils.showToast(this, "uriList" + "不可以为空");
            return false;
        }
        if (TextUtils.isEmpty(bio)) {
            ToastUtils.showToast(this, "bio)) {\n" +
                    "            ToastUtils.showToast(this" + "不可以为空");
            return false;
        }


        if (TextUtils.isEmpty(videoUrl)) {
            ToastUtils.showToast(this, "videoUrl" + "不可以为空");
            return false;
        }
        return true;
    }

    private void setBirth() {

        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2050, 1, 1);
        picker.setRangeStart(1900, 1, 1);
        picker.setSelectedItem(1990, 1, 1);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvBirth.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Contact.REQUEST_BIO) {
                bio = data.getStringExtra("bio");
                etBio.setText(bio);
            } else if (requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
                List<String> strings = Matisse.obtainPathResult(data);
                if (uriList != null) {
                    if (chargePostion == 0) {
                        uriList.addAll(strings);
                        for (String s : strings) {
                            mList.add(new EditHeadAdapter.UserHead(s, false));
                        }
                    }else{
                        uriList.set(chargePostion-1,strings.get(0));
                        mList.set(chargePostion,new EditHeadAdapter.UserHead(strings.get(0), false));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == Contact.REQUEST_CHOOSE_CARD) {
                idCardFrontUrl = data.getStringExtra("frontCard");

                idCardHandledUrl = data.getStringExtra("handCard");
                tvNameVerity.setText("已填充");
            } else if (requestCode == Contact.REQUEST_VIDEO_VERIFY) {
                videoUrl = data.getStringExtra("videoUrl");
                tvVideoVerity.setText("已填充");
            }

        }
    }

    private void upQiniu() {
        QiNiuToken.DataBean.StarchatanchorBean starchatanchorBean = QiniuInfo.getmStarchatanchorBean();
        if (starchatanchorBean == null) {
            return;
        }
        if (clickUp) {
            return;
        }
        clickUp = true;
        new UploadPicManger().uploadImageArray(uriList, 0, starchatanchorBean.getToken(), starchatanchorBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                for (int i = 0; i < paths.size(); i++) {
                    LogUtils.e(TAG, "uploadSuccess:" + paths.get(i));
                }
                gotoSave(paths);
            }

            @Override
            public void uploadFailure() {
                // TODO: 2019/6/25 0025 上传图片失败
                clickUp = false;
                LogUtils.e(TAG, "uploadFailure: 失败");
            }
        });
    }

    private void gotoSave(List<String> paths) {
        if (paths != null) {
            covers.clear();
            for (int i = 0; i < paths.size(); i++) {
                ApplyAnchorBean.AnchorBean.AlbumBean coversBean = new ApplyAnchorBean.AnchorBean.AlbumBean();
                coversBean.setMediaUrl(paths.get(i));
                coversBean.setSortby(i + "");
                covers.add(coversBean);
            }
        }
        ApplyAnchorBean applyAnchorBean = new ApplyAnchorBean();
        ApplyAnchorBean.AnchorBean anchorBean = new ApplyAnchorBean.AnchorBean();
        anchorBean.setHeight(TextUtils.isEmpty(height) ? 0 + "" : height);
        anchorBean.setWeight(TextUtils.isEmpty(weight) ? 0 + "" : weight);
        anchorBean.setResidentialPlace(city);
        anchorBean.setIntroduction(bio);
        anchorBean.setAlbum(covers);
        anchorBean.setBirthDate(birth);

        ApplyAnchorBean.ApplicationBean applicationBean = new ApplyAnchorBean.ApplicationBean();
        applicationBean.setRealName(userName);
        applicationBean.setMobileNumber(phoneNum);
        applicationBean.setIdCardFront(idCardFrontUrl);
        applicationBean.setIdCardHandled(idCardHandledUrl);
        applicationBean.setCertifyVideo(videoUrl);


        applyAnchorBean.setAnchor(anchorBean);
        applyAnchorBean.setApplication(applicationBean);

        RetrofitFactory.getInstance()
                .applyAnchor(ProxyPostHttpRequest.getJsonInstance()
                        .applyAnchor(GsonUtil.parseObjectToJson(applyAnchorBean)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BeAnchorBean>() {
                    @Override
                    public void onGetData(BeAnchorBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            ToastUtils.showToast(ApplyAnchorActivity.this, getString(R.string.submit_fail));
                            LogUtils.e("Edit", GsonUtil.parseObjectToJson(baseBean));
                            return;
                        }
                        if (Contact.REPONSE_CODE_APPLYANCHOR_FAIL_ALREADY_NAME_OR_PHONE == baseBean.getCode()) {
                            ToastUtils.showToast(ApplyAnchorActivity.this, "主播用户名或手机号存在");
                            return;
                        }
                        ToastUtils.showToast(ApplyAnchorActivity.this, getString(R.string.submit_success));
                        finish();
                    }
                });

    }


}
