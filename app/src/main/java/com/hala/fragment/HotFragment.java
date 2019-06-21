package com.hala.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.adapter.HotCallAdapter;
import com.hala.base.BaseFragment;
import com.hala.base.Contact;
import com.hala.bean.OneToOneListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HotFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    private boolean isLoadMore;



    List<OneToOneListBean.DataBean.ListBean> mHotOnetoOneList=new ArrayList<>();
    private HotCallAdapter hotCallAdapter;
    private int page;

    @Override
    protected void initView() {
        hotCallAdapter = new HotCallAdapter(R.layout.item_hot_list,mHotOnetoOneList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(hotCallAdapter);

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(true);
                        hotCallAdapter.notifyDataSetChanged();
                        swrl.setRefreshing(false);
                    }
                }, 200);
            }
        });
        hotCallAdapter.setPreLoadNumber(5);
        hotCallAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
               getData(false);
        }});
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_hot;
    }

    @Override
    protected void initData() {
        getData(false);
    }

    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            page =0;
        }else{
            page++;
        }
        RetrofitFactory.getInstance().getHotOneToOneList(0, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {
                    @Override
                    public void onNext(OneToOneListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            hotCallAdapter.loadMoreFail();
                            return;
                        }
                        if (oneToOneListBean.getData().getPageable().isNextPage()) {
                            hotCallAdapter.loadMoreEnd();
                        } else {

                        }
                        if (isRefresh) {
                            mHotOnetoOneList.clear();
                            List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getContent();
                            if (content != null && content.size() > 0) {
                                mHotOnetoOneList.addAll(content);
                            }
                            hotCallAdapter.notifyDataSetChanged();
                        } else {


                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }


}
