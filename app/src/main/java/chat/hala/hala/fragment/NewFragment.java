package chat.hala.hala.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import chat.hala.hala.adapter.HotCallAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewFragment extends BaseFragment {


    public  static String TAG="HotFragment";
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    private boolean isLoadMore=true;



    List<OneToOneListBean.DataBean.ListBean> mHotOnetoOneList=new ArrayList<>();
    private HotCallAdapter hotCallAdapter;
    private int page;

    @Override
    protected void initView() {
        hotCallAdapter = new HotCallAdapter(R.layout.item_hot_list,mHotOnetoOneList);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(hotCallAdapter);

        hotCallAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(),mHotOnetoOneList.get(position).getAnchorId(),mHotOnetoOneList.get(position).getMemberId());
            }
        });

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = true;
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e(TAG,"wo222");
                        getData(true);
                        swrl.setRefreshing(false);
                    }
                }, 200);
            }
        });
        hotCallAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e(TAG,"wo ");
                getData(false);
            }},rv);
        hotCallAdapter.setPreLoadNumber(5);
        hotCallAdapter.disableLoadMoreIfNotFullPage(rv);
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

        if (!isLoadMore){
            return;
        }
        if (isRefresh) {
            page =0;
        }else{
            page++;
        }
        LogUtils.e(TAG,"aaa"+page);

        RetrofitFactory.getInstance().getHotOneToOneList(0, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hotCallAdapter.loadMoreFail();
                    }

                    @Override
                    public void onGetData(OneToOneListBean oneToOneListBean) {

                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            hotCallAdapter.loadMoreFail();
                            return;
                        }
                        if (oneToOneListBean.getData().getPageable().isNextPage()) {
                            hotCallAdapter.loadMoreEnd();
                            isLoadMore=false;
                        } else {
                            hotCallAdapter.loadMoreComplete();
                        }
                        if (isRefresh) {
                            mHotOnetoOneList.clear();
                        }
                        LogUtils.e(TAG,"aaaListBean");

                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mHotOnetoOneList.addAll(content);
                        }
                        hotCallAdapter.notifyDataSetChanged();
                    }
                });
    }
}
