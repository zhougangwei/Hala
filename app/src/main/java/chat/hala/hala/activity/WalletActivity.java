package chat.hala.hala.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.CoinBriefBean;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WalletActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView    mIvBack;
    @BindView(R.id.tv_title)
    TextView     mTvTitle;
    @BindView(R.id.tv_coin_num)
    TextView     mTvCoinNum;
    @BindView(R.id.iv1)
    ImageView    mIv1;
    @BindView(R.id.tv1)
    TextView     mTv1;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.ll_recharge)
    LinearLayout mLlRecharge;
    @BindView(R.id.ll_income)
    LinearLayout mLlIncome;
    @BindView(R.id.ll_cost)
    LinearLayout mLlCost;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mTvTitle.setText("Wallet");
        initData();
    }

    @SuppressLint("CheckResult")
    private void initData() {
        RetrofitFactory.getInstance().getCoinBrief()
                .subscribeOn(Schedulers.io())
                .compose(this.<CoinBriefBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CoinBriefBean>() {
                    @Override
                    public void accept(CoinBriefBean coinBriefBean) throws Exception {
                        if (ResultUtils.cheekSuccess(coinBriefBean)) {
                            mTvCoinNum.setText(coinBriefBean.getData().getTotal()+"");
                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_recharge, R.id.ll_income, R.id.ll_cost})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_recharge:
                startActivity(new Intent(this,ChargeActivity.class));
                break;
            case R.id.ll_income:
                startActivity(new Intent(this,MyIncomeActivity.class));
                break;
            case R.id.ll_cost:
                startActivity(new Intent(this,MyCostActivity.class));
                break;
        }
    }
}
