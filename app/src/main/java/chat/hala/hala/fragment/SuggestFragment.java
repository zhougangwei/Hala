package chat.hala.hala.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chat.hala.hala.R;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.adapter.SuggestAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.wight.banner.Banner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SuggestFragment extends BaseFragment {


    public static String TAG = "SuggestFragment";
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    private boolean isLoadMore = true;


    List<OneToOneListBean.DataBean.ListBean> mSuggestList = new ArrayList<>();
    private SuggestAdapter suggestAdapter;
    private int page;

    @Override
    protected void initView() {
        suggestAdapter = new SuggestAdapter(R.layout.item_suggest_list, mSuggestList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(suggestAdapter);


        suggestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(), mSuggestList.get(position).getAnchorId(), mSuggestList.get(position).getMemberId());
            }
        });

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = true;
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(true);
                        swrl.setRefreshing(false);
                    }
                }, 500);
            }
        });
        suggestAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, rv);
        suggestAdapter.setPreLoadNumber(5);
        
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_suggest;
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean isRefresh) {

        if (!isLoadMore) {
            return;
        }
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        LogUtils.e(TAG, "aaa" + page);

        RetrofitFactory.getInstance().getHotOneToOneList(0, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        suggestAdapter.loadMoreFail();
                    }

                    @Override
                    public void onGetData(OneToOneListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            suggestAdapter.loadMoreFail();
                            return;
                        }
                        LogUtils.e(TAG, "onGetData: " + GsonUtil.parseObjectToJson(oneToOneListBean));

                        if (oneToOneListBean.getData().getPageable().isNextPage()) {
                            suggestAdapter.loadMoreComplete();
                        } else {
                            suggestAdapter.loadMoreEnd();
                            isLoadMore = false;
                        }
                        if (isRefresh) {
                            mSuggestList.clear();
                        }
                        LogUtils.e(TAG, "aaaListBean");

                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mSuggestList.addAll(content);
                        }
                        suggestAdapter.notifyDataSetChanged();
                        suggestAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
    }


}
