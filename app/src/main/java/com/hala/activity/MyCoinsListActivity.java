package com.hala.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.adapter.CoinListAdapter;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.CallListBean;
import com.hala.bean.CoinListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCoinsListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    List<CoinListBean.DataBean.TransactionsBean.ListBean> callList = new ArrayList<>();
    private CoinListAdapter adapter;
    private int page = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_coins_list;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CoinListAdapter(R.layout.item_coin_list, callList);
        rv.setAdapter(adapter);

        getData();
    }

    private void getData() {
        RetrofitFactory.getInstance().getCoinList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onNext(CoinListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
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
