package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

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
    ImageView mIvBack;
    @BindView(R.id.tv_coin)
    TextView mTvCoin;
    @BindView(R.id.iv_detail)
    TextView mTvDetail;


    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.tv_charge)
    TextView mTvCharge;
    private int chargeType;
    private final static int CHARGE_WEXIN = 1;
    private final static int CHARGE_ALI = 2;


    private ChargeAdapter mChargeAdapter;

    List<RuleBean.DataBean.MainlandRechargeSettingBean> mdataList = new ArrayList<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_charge;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

        mChargeAdapter = new ChargeAdapter(R.layout.item_charge, mdataList);
        mRv.setAdapter(mChargeAdapter);
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        // mRv.addItemDecoration(new SpaceItemDecoration(SizeUtils.dp2px(this,13)));
        initData();
        mChargeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mdataList.size(); i++) {
                    mdataList.get(i).setClicked(false);
                }
                mdataList.get(position).setClicked(true);
                mChargeAdapter.notifyDataSetChanged();
            }
        });


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
                            List<RuleBean.DataBean.MainlandRechargeSettingBean> recharge_setting = ruleBean.getData().getMainlandRechargeSetting();
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


    /*
    * 明细
    * */
    @OnClick({R.id.iv_back, R.id.tv_charge,R.id.iv_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_charge:
                startCharge();
                break;
            case R.id.iv_detail:
                startChargeDetail();
                break;
        }
    }
    private void startChargeDetail() {

    }
    private void startCharge() {
        if(chargeType==0){
            return;
        }else if(chargeType == CHARGE_WEXIN){
            gotoChargeWeixin();
        }else if(chargeType ==CHARGE_ALI){
            gotoChargeAli();
        }
    }
    private void gotoChargeAli() {

    }

    private void gotoChargeWeixin() {

    }

}
