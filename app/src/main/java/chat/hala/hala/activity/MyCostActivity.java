package chat.hala.hala.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.CoinIncomeAdapter;

import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCostActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView    mIvBack;
    @BindView(R.id.tv_title)
    TextView     mTvTitle;

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
        mTvTitle.setText("消费");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        adapter = new CoinIncomeAdapter(R.layout.item_coin_income, callList,CoinIncomeAdapter.COST);
        mRv.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e("onLoadMoreRequested");
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
        RetrofitFactory.getInstance().getCoinCostList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onGetData(CoinListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
                        if (callListBean.getData().getTransactions().getPageable().isNextPage()) {
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreEnd();
                            isLoadMore =false;
                        }

                        List<CoinListBean.DataBean.TransactionsBean.ListBean> list = callListBean.getData().getTransactions().getList();
                        if (list != null && list.size() > 0) {
                            callList.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                        adapter.disableLoadMoreIfNotFullPage(mRv);
                    }
                });

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
