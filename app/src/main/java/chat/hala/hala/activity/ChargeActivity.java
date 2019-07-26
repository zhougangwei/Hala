package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.ChargeAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.RuleBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChargeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView    mIvBack;
    @BindView(R.id.tv_title)
    TextView     mTvTitle;
    @BindView(R.id.tv_coin)
    TextView     mTvCoin;
    @BindView(R.id.tv1)
    TextView     mTv1;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.tv_contact)
    TextView     mTvContact;
    private ChargeAdapter mChargeAdapter;

    List<RuleBean.DataBean.RechargeSettingBean> mdataList = new ArrayList<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_charge;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mTvTitle.setText(R.string.recharge);
        mChargeAdapter = new ChargeAdapter(R.layout.item_charge, mdataList);
        mRv.setAdapter(mChargeAdapter);
        mRv.setLayoutManager(new GridLayoutManager(this,3));
       // mRv.addItemDecoration(new SpaceItemDecoration(SizeUtils.dp2px(this,13)));
        initData();
    }

    @SuppressLint("CheckResult")
    private void initData() {
        RetrofitFactory.getInstance().getRuleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RuleBean>() {
                    @Override
                    public void onGetData(RuleBean ruleBean) {
                        if (ResultUtils.cheekSuccess(ruleBean)) {
                            List<RuleBean.DataBean.RechargeSettingBean> recharge_setting = ruleBean.getData().getRecharge_setting();
                            /*if(recharge_setting==null){
                                return;
                            }*/
                            mdataList.clear();
                            mdataList.addAll(recharge_setting);
                            mChargeAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    @OnClick({R.id.iv_back, R.id.tv_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_contact:
                break;
        }
    }
}
