package chat.hala.hala.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import chat.hala.hala.adapter.ChargeListAdapter;
import chat.hala.hala.adapter.CoinIncomeAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.ChargeListBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.location.RealTimeLocation;

/**
 * @author wjy on 2019/10/14/014.
 */
public class ChargeListActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private ChargeListAdapter adapter;
    private int page=0;
    private boolean isLoadMore=true;
    private List<ChargeListBean.DataBean.TransactionsBean.ListBean> callList= new ArrayList<>();
    @Override
    protected int getContentViewId() {
        return R.layout.activity_charge_list;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
       mTvTitle.setText("充值记录");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        adapter = new ChargeListAdapter(R.layout.item_charge_list, callList, CoinIncomeAdapter.INCOME);
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
            adapter.loadMoreEnd();
            return;
        }
        RetrofitFactory.getInstance().getMemberRechargeList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<ChargeListBean>() {
                    @Override
                    public void onGetData(ChargeListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
                        if (callListBean.getData().getTransactions().getPageable().isNextPage()) {
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreEnd();
                            isLoadMore =false;
                        }
                        List<ChargeListBean.DataBean.TransactionsBean.ListBean> list = callListBean.getData().getTransactions().getList();
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
