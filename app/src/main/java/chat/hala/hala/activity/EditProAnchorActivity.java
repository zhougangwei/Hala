package chat.hala.hala.activity;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.EditHeadAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.AnchorTagBean;
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
import chat.hala.hala.wight.country.CountryActivity;
import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditProAnchorActivity extends BaseActivity {


    private static final String TAG = "EditProAnchorActivity";
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
    TextView etHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.et_weight)
    TextView etWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.et_zodiac)
    TextView etZodiac;
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
    TextView etTags;
    @BindView(R.id.ll_tags)
    LinearLayout llTags;
    @BindView(R.id.et_bio)
    TextView etBio;
    @BindView(R.id.ll_bio)
    LinearLayout llBio;
    @BindView(R.id.et_certified)
    EditText etCertified;
    @BindView(R.id.ll_certified)
    LinearLayout llCertified;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.rv_pic)
    RecyclerView recyclerView;

    @BindView(R.id.tv_country)
    TextView tvCountry;

    private List<EditHeadAdapter.UserHead> mList;
    private List<String> uriList = new ArrayList<>();
    List<Integer> tagsList = new ArrayList<>();
    List<ApplyAnchorBean.CoversBean> covers = new ArrayList<>();
    private String bio;     //个人经历
    private static final int REQUEST_BIO = 222;
    private static final int REQUEST_TAG = 223;
    private static final int REQUEST_CHOOSE_COUNTRY = 666;

    EditHeadAdapter mAdapter;

    private final static String[] constellationThArr = new String[]{"ราศีมังกร",
            "ราศีกุมภ์", "ราศีมีน", "ราศีเมษ", "ราศีพฤษภ", "ราศีเมถุน", "ราศีกรกฎ", "ราศีสิงห์", "ราศีกันย์", "ราศีตุล",
            "ราศีพิจิก", "ราศีธนู", "ราศีมังกร"};

    private final static String[] constellationEnArr = new String[]{"Capricornus",
            "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra",
            "Scorpio", "Sagittarius", "Capricornus"};


    private final static String[] constellationARArr = new String[] { "برج الجدي",
            "برج الدلو", "برج الحوت", "برج الحمل", "برج الثور", "برج الجوزاء", "برج السرطان", "برج الاسد", "برج العذراء", "برج الميزان",
            "برج العقرب", "برج القوس", "برج الجدي" };

    private String userName;
    private String phoneNum;
    private String height;
    private String weight;
    private String zodiac;
    private String city;
    private String country;
    private String intro;
    private String certify;
    private DoublePicker weightPicker;
    private DoublePicker heightPicker;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_pro;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
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
                    ChoosePicManager.choosePic(EditProAnchorActivity.this, 3);
                }
            }
        });


    }

    @OnClick({R.id.ll_zodiac, R.id.ll_country, R.id.ll_city, R.id.ll_introction, R.id.ll_tags, R.id.ll_bio, R.id.ll_certified, R.id.tv_save, R.id.iv_back, R.id.ll_height, R.id.ll_weight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_zodiac:
                setZodiac();
                break;
            case R.id.ll_country:
                Intent intent1 = new Intent(this, CountryActivity.class);
                intent1.putExtra("type", CountryActivity.FROM_EDIT_PRO);
                startActivityForResult(intent1, REQUEST_CHOOSE_COUNTRY);
                break;
            case R.id.ll_tags:
                Intent tagIntent = new Intent(this, TagActivity.class);
                startActivityForResult(tagIntent, REQUEST_TAG);
                break;
            case R.id.ll_bio:
                Intent intent = new Intent(this, BioActivity.class);
                startActivityForResult(intent, REQUEST_BIO);
                break;
            case R.id.tv_save:
                if (!judgeEmpty()) {
                    return;
                } else {
                    upQiniu();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_height:
                chooseHeight();
                break;
            case R.id.ll_weight:
                chooseWeight();
                break;
        }
    }

    private void chooseWeight() {
        if (weightPicker == null) {
            final ArrayList<String> firstData = new ArrayList<>();
            firstData.add("0");
            firstData.add("1");
            firstData.add("2");
            final ArrayList<String> secondData = new ArrayList<>();
            for (int i = 0; i <= 99; i++) {
                if (i < 10) {
                    secondData.add("0" + i);
                } else {
                    secondData.add("" + i);
                }
            }
            weightPicker = new DoublePicker(this, firstData, secondData);
            weightPicker.setDividerVisible(true);
            weightPicker.setCycleDisable(true);
            weightPicker.setSelectedIndex(0, 50);
            weightPicker.setSecondLabel("", "KG");
            weightPicker.setTextSize(15);
            weightPicker.setContentPadding(15, 15);
            weightPicker.setOnPickListener(new DoublePicker.OnPickListener() {
                @Override
                public void onPicked(int selectedFirstIndex, int selectedSecondIndex) {
                    String height = ("0".equals(firstData.get(selectedFirstIndex)) ? "" : firstData.get(selectedFirstIndex)) + secondData.get(selectedSecondIndex);
                    etWeight.setText(height);
                }
            });
            weightPicker.show();
        } else {
            weightPicker.show();
        }
    }

    private void chooseHeight() {
        if(heightPicker==null){
            final ArrayList<String> firstData = new ArrayList<>();
            firstData.add("1");
            firstData.add("2");
            final ArrayList<String> secondData = new ArrayList<>();
            for (int i = 0; i <= 99; i++) {
                if (i < 10) {
                    secondData.add("0" + i);
                } else {
                    secondData.add("" + i);
                }
            }
            heightPicker = new DoublePicker(this, firstData, secondData);
            heightPicker.setDividerVisible(true);
            heightPicker.setCycleDisable(true);
            heightPicker.setSelectedIndex(0, 50);
            heightPicker.setSecondLabel("", "CM");
            heightPicker.setTextSize(15);
            heightPicker.setContentPadding(15, 15);
            heightPicker.setOnPickListener(new DoublePicker.OnPickListener() {
                @Override
                public void onPicked(int selectedFirstIndex, int selectedSecondIndex) {
                    String height = firstData.get(selectedFirstIndex) + secondData.get(selectedSecondIndex);
                    etHeight.setText(height);
                }
            });
            heightPicker.show();
        }else{
            heightPicker.show();
        }

    }

    private boolean judgeEmpty() {
        userName = etUsername.getText().toString();
        phoneNum = etPhoneNum.getText().toString();
        height = etHeight.getText().toString();
        weight = etWeight.getText().toString();
        zodiac = etZodiac.getText().toString();  //星座
        city = etCity.getText().toString();
        country = tvCountry.getText().toString();
        intro = etIntroction.getText().toString();
        certify = etCertified.getText().toString();

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
        if (TextUtils.isEmpty(zodiac)) {
            ToastUtils.showToast(this, "zodiac" + "不可以为空");
            return false;
        }
        if (TextUtils.isEmpty(country)) {
            ToastUtils.showToast(this, "country" + "不可以为空");
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
        if (TextUtils.isEmpty(intro)) {
            ToastUtils.showToast(this, "intro" + "不可以为空");
            return false;
        }
        if (TextUtils.isEmpty(certify)) {
            ToastUtils.showToast(this, "certify" + "不可以为空");
            return false;
        }

        if (tagsList == null || tagsList.size() == 0) {
            ToastUtils.showToast(this, "tagsList" + "不可以为空");
            return false;
        }
        if (uriList == null || uriList.size() == 0) {
            ToastUtils.showToast(this, "uriList" + "不可以为空");
            return false;
        }

        return true;

    }

    private void setZodiac() {
        List<String> data = Arrays.asList(constellationEnArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                etZodiac.setText(item);
            }
        });
        picker.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAG) {
                String tag = data.getStringExtra("tags");
                List<AnchorTagBean.DataBean> tagList = GsonUtil.parseJsonToList(tag,
                        new TypeToken<List<AnchorTagBean.DataBean>>() {
                        }.getType()
                );
                StringBuilder sb = new StringBuilder();
                if (tagList != null && tagList.size() > 0) {
                    tagsList.clear();
                    for (AnchorTagBean.DataBean dataBean : tagList) {
                        tagsList.add(dataBean.getTagId());
                        sb.append(dataBean.getContent()+" ");
                    }
                }
                etTags.setText(sb.toString());
            } else if (requestCode == REQUEST_BIO) {
                bio = data.getStringExtra("bio");
                etBio.setText(bio);
            } else if (requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
                List<String> strings = Matisse.obtainPathResult(data);
                if (uriList != null) {
                    uriList.clear();
                    uriList.addAll(strings);
                    for (String s : uriList) {
                        mList.add(new EditHeadAdapter.UserHead(s, false));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == REQUEST_CHOOSE_COUNTRY) {
                String countryName = data.getStringExtra("countryName");
                tvCountry.setText(countryName);
            }
        }
    }

    private void upQiniu() {
        QiNiuToken.DataBean.StarchatanchorBean starchatanchorBean = QiniuInfo.getmStarchatanchorBean();
        if (starchatanchorBean == null) {
            return;
        }
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
                LogUtils.e(TAG, "uploadFailure: 失败");
            }
        });
    }

    private void gotoSave(List<String> paths) {
        if (paths != null) {
            covers.clear();
            for (int i = 0; i < paths.size(); i++) {
                ApplyAnchorBean.CoversBean coversBean = new ApplyAnchorBean.CoversBean();
                coversBean.setCoverUrl(paths.get(i));
                coversBean.setSortby(i);
                covers.add(coversBean);
            }
        }

        ApplyAnchorBean applyAnchorBean = new ApplyAnchorBean();
        applyAnchorBean.setRealName(userName);
        applyAnchorBean.setMobileNumber(phoneNum);
        applyAnchorBean.setHeight(TextUtils.isEmpty(height) ? 0 : Integer.parseInt(height));
        applyAnchorBean.setWeight(TextUtils.isEmpty(weight) ? 0 : Integer.parseInt(weight));
        applyAnchorBean.setZodiac(zodiac);
        applyAnchorBean.setCity(city);
        applyAnchorBean.setIntroduction(intro);
        applyAnchorBean.setCountry(country);
        applyAnchorBean.setTagIds(tagsList);
        applyAnchorBean.setCertifyUrl(certify);
        applyAnchorBean.setBiography(bio);
        applyAnchorBean.setCovers(covers);
        applyAnchorBean.setCpm(20 + "");
        RetrofitFactory.getInstance()
                .applyAnchor(ProxyPostHttpRequest.getJsonInstance()
                        .applyAnchor(GsonUtil.parseObjectToJson(applyAnchorBean)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BeAnchorBean>() {
                    @Override
                    public void onNext(BeAnchorBean baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            ToastUtils.showToast(EditProAnchorActivity.this, "提交失败");
                            LogUtils.e("Edit", GsonUtil.parseObjectToJson(baseBean));
                            return;
                        }
                        if (Contact.REPONSE_CODE_APPLYANCHOR_FAIL_ALREADY_NAME_OR_PHONE != baseBean.getCode()) {
                            ToastUtils.showToast(EditProAnchorActivity.this, "主播用户名或手机号存在");
                            return;
                        }
                        int id = baseBean.getData().getMemberId();
                        AvchatInfo.setAnchorId(id);
                        ToastUtils.showToast(EditProAnchorActivity.this, "提交成功");
                        finish();
                    }
                });

    }


}
