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
import com.blankj.utilcode.utils.TimeUtils;
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
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.AnchorTagBean;
import chat.hala.hala.bean.ApplyAnchorBean;
import chat.hala.hala.bean.EditUserBean;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.bean.RegistBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import chat.hala.hala.utils.ToastUtils;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditUserActivity extends BaseActivity {


    private static final String TAG = "EditUserActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;

    @BindView(R.id.et_gender)
    TextView etGender;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
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
    @BindView(R.id.et_autoGraph)
    EditText etAutoGraph;
    @BindView(R.id.ll_autoGraph)
    LinearLayout llAutoGraph;

    @BindView(R.id.et_bio)
    TextView etBio;
    @BindView(R.id.ll_bio)
    LinearLayout llBio;

    @BindView(R.id.et_tags)
    TextView etTags;
    @BindView(R.id.ll_tags)
    LinearLayout llTags;

    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.rv_pic)
    RecyclerView recyclerView;


    private List<EditHeadAdapter.UserHead> mList;
    private List<String> uriList = new ArrayList<>();
    List<Integer> tagsList = new ArrayList<>();


    EditHeadAdapter mAdapter;
    private String userName;

    private String height;
    private String weight;
    private String birth;
    private String city;
    private String autoGraph;
    private String introdution;

    private DoublePicker heightPicker;

    private int genderIndex;




    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_user;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

        tvTitle.setText("编辑资料");
        mList = new ArrayList<>();
        mList.add(new EditHeadAdapter.UserHead("", true));
        mAdapter = new EditHeadAdapter(mList);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        dataBackShow();
        if (!AvchatInfo.isAnchor()) {
            llBio.setVisibility(View.GONE);
            llHeightWeight.setVisibility(View.GONE);
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mList.get(position).isAdd()) {
                    ChoosePicManager.choosePic(EditUserActivity.this, 3);
                }
            }
        });


    }

    /*
     * 数据回显
     * */
    private void dataBackShow() {
        etUsername.setText(AvchatInfo.getName());
        etGender.setText(AvchatInfo.getGender()==1?"男":"女");
        tvBirth.setText(AvchatInfo.getBirthDate());
        etCity.setText(AvchatInfo.getResidentialPlace());
        etAutoGraph.setText(AvchatInfo.getAutoGraph());
        etBio.setText(AvchatInfo.getIntroduction());
    }

    @OnClick({R.id.ll_tags, R.id.ll_birth, R.id.ll_city, R.id.ll_autoGraph, R.id.ll_bio, R.id.tv_save, R.id.iv_back, R.id.ll_height_weight, R.id.ll_gender})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tags:
                Intent tagIntent = new Intent(this, TagActivity.class);
                startActivityForResult(tagIntent, Contact.REQUEST_TAG);
                break;
            case R.id.ll_birth:
                setBirth();
                break;
            case R.id.ll_bio:
                Intent intent = new Intent(this, BioActivity.class);
                startActivityForResult(intent, Contact.REQUEST_BIO);
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
            case R.id.ll_height_weight:
                chooseHeightAndWeight();
                break;
            case R.id.ll_gender:
                chooseGender();
                break;

        }
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
            heightPicker.setSelectedIndex(160, 50);
            heightPicker.setSecondLabel("", "KG");
            heightPicker.setTextSize(15);
            heightPicker.setContentPadding(15, 15);
            heightPicker.setOnPickListener(new DoublePicker.OnPickListener() {
                @Override
                public void onPicked(int selectedFirstIndex, int selectedSecondIndex) {
                    height = firstData.get(selectedFirstIndex);
                    weight = secondData.get(selectedSecondIndex);
                }
            });
            heightPicker.show();
        } else {
            heightPicker.show();
        }

    }

    private void chooseGender() {

        String[] sexArr = new String[]{getString(R.string.male), getString(R.string.female)};
        List<String> data = Arrays.asList(sexArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(17);
        picker.setTextPadding(10);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                genderIndex = index;
                etGender.setText(item);
            }
        });
        picker.show();
    }

    private boolean judgeEmpty() {
        userName = etUsername.getText().toString();

        birth = tvBirth.getText().toString();  //星座
        city = etCity.getText().toString();
        autoGraph = etAutoGraph.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            ToastUtils.showToast(this, "userName" + "不可以为空");
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
        if (TextUtils.isEmpty(autoGraph)) {
            ToastUtils.showToast(this, "autoGraph" + "不可以为空");
            return false;
        }


        if (AvchatInfo.isAnchor()) {

            if (TextUtils.isEmpty(height)) {
                ToastUtils.showToast(this, "height" + "不可以为空");
                return false;
            }
            if (TextUtils.isEmpty(weight)) {
                ToastUtils.showToast(this, "weight" + "不可以为空");
                return false;
            }
            if (TextUtils.isEmpty(introdution)) {
                ToastUtils.showToast(this, "introdution" + "不可以为空");
                return false;
            }
            if (tagsList == null || tagsList.size() == 0) {
                ToastUtils.showToast(this, "tagsList" + "不可以为空");
                return false;
            }
        }


        if (uriList == null || uriList.size() == 0) {
            ToastUtils.showToast(this, "uriList" + "不可以为空");
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
                introdution = data.getStringExtra("bio");
                etBio.setText(introdution);
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
            } else if (requestCode == Contact.REQUEST_TAG) {
                String tag = data.getStringExtra("tags");
                List<AnchorTagBean.DataBean> tagList = GsonUtil.parseJsonToList(tag,
                        new TypeToken<List<AnchorTagBean.DataBean>>() {
                        }.getType()
                );
                StringBuilder sb = new StringBuilder();
                if (tagList != null && tagList.size() > 0) {
                    tagsList.clear();
                    for (int i = 0; i < tagList.size(); i++) {
                       AnchorTagBean.DataBean dataBean=tagList.get(i);
                        tagsList.add(dataBean.getTagId());
                        if(i==tagList.size()-1){
                            sb.append(dataBean.getContent());
                        }else{
                            sb.append(dataBean.getContent() + ",");
                        }
                    }
                }
                etTags.setText(sb.toString());
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
        List<EditUserBean.AlbumBean> covers = new ArrayList<>();
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                EditUserBean.AlbumBean coversBean = new EditUserBean.AlbumBean();
                coversBean.setMediaUrl(paths.get(i));
                coversBean.setSortby(i + "");
                covers.add(coversBean);
            }
        }
        EditUserBean dataBean = new EditUserBean();
        dataBean.setAlbum(covers);
        dataBean.setNickname(userName);
        dataBean.setGender((genderIndex + 1) + "");
        dataBean.setAutograph(autoGraph);
        dataBean.setBirthDate(birth);
        dataBean.setIntroduction(introdution);
        dataBean.setResidentialPlace(city);
        dataBean.setTagIds(tagsList);
        dataBean.setHeight(height);
        dataBean.setWeight(weight);
        RetrofitFactory.getInstance()
                .changeUserInfo(ProxyPostHttpRequest.getJsonInstance().changeUserInfo(GsonUtil.parseObjectToJson(dataBean)),
                        AvchatInfo.isAnchor() ? "anchor" : "member"
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RegistBean>() {
                    @Override
                    public void onGetData(RegistBean registBean) {
                        if (ResultUtils.cheekSuccess(registBean)) {
                            AvchatInfo.saveBaseData(registBean.getData(), EditUserActivity.this,false);
                        }
                    }
                });
    }
}
