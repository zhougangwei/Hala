package chat.hala.hala.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.CoinIncomeAdapter;
import chat.hala.hala.adapter.CoinListAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyIncomeActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView    mIvBack;
    @BindView(R.id.tv_title)
    TextView     mTvTitle;
    @BindView(R.id.tv_coin)
    TextView     mTvCoin;
    @BindView(R.id.rv)
    RecyclerView mRv;
    private CoinIncomeAdapter adapter;
    private int page=0;
    List<CoinListBean.DataBean.TransactionsBean.ListBean> callList = new ArrayList<>();
    private boolean isLoadMore=true;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_income;
    }

    @Override
    protected void beforeInitView() {

    }



    @Override
    protected void initView() {
        mTvTitle.setText("My Income");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        adapter = new CoinIncomeAdapter(R.layout.item_coin_income, callList);
        mRv.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page=page+1;
                getData(page);
            }},mRv);
        adapter.setPreLoadNumber(5);
        getData(page);

    }

    private void getData(int page) {
        if (!isLoadMore){
            return;
        }
        RetrofitFactory.getInstance().getCoinInComeList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onNext(CoinListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
                        if (callListBean.getData().getTransactions().getPageable().isNextPage()) {
                            adapter.loadMoreEnd();
                            isLoadMore =false;
                        } else {
                            adapter.loadMoreComplete();
                        }
                        mTvCoin.setText(callListBean.getData().getTotal()+"");;
                        List<CoinListBean.DataBean.TransactionsBean.ListBean> list = callListBean.getData().getTransactions().getList();
                        if (list != null && list.size() > 0) {
                            callList.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
