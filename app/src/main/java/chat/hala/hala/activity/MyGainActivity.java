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
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.CoinBriefBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.SPUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyGainActivity extends BaseActivity {


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


    @Override
    protected int getContentViewId() {
        return R.layout.activity_mygain;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mTvTitle.setText("收益");
        initData();
    }

    @SuppressLint("CheckResult")
    private void initData() {
        RetrofitFactory.getInstance().getCoinInComeList(0,20)
                .subscribeOn(Schedulers.io())
                .compose(this.<CoinListBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onGetData(CoinListBean coinBriefBean) {
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

    @OnClick({R.id.iv_back, R.id.ll_gain_money,  R.id.ll_income_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_gain_money:
                WebviewActivity2.startActivity(this, Contact.HOST+"/cashOut/cashOut.html?token="+SPUtil.getInstance(this).getString(Contact.TOKEN, ""),"提现");
                break;
            case R.id.ll_income_detail:
                startActivity(new Intent(this,MyIncomeActivity.class));
                break;
        }
    }
}
