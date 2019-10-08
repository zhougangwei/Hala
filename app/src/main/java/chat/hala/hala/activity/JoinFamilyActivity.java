package chat.hala.hala.activity;

import android.os.Bundle;
import android.support.constraint.Group;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.TimeUtil;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class JoinFamilyActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.gp_content)
    Group gpContent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_join_family;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("加入家族");
        if (AvchatInfo.hasFamily()) {
            tvSure.setEnabled(false);
            tvSure.setClickable(false);
            gpContent.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            LoginBean.DataBean.MemberBean.FamilyData familyData = AvchatInfo.getMemberBean().getFamilyData();
            tvTime.setText(TimeUtil.dealDateFormat3(familyData.getJoinTime()));
            tvName.setText(familyData.getFname());
            Glide.with(this).load(familyData.getMediaUrl()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivHead);
        }else{
            tvEmpty.setVisibility(View.VISIBLE);
            gpContent.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sure:
                joinFamily();
                break;
        }
    }

    private void joinFamily() {
        String code = editText.getText().toString();
        RetrofitFactory.getInstance().joinFamily(code).subscribeOn(
                Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            ToastUtils.showToast(JoinFamilyActivity.this, "加入成功!");
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
