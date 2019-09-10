package chat.hala.hala.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chat.hala.hala.R;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.adapter.FollowAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.FansBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FollowFragment extends BaseFragment {


    public  static String TAG="HotFragment";
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rv_random)
    RecyclerView rvRandom;

    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    private boolean isLoadMore=true;
    private List<OneToOneListBean.DataBean.ListBean> mRanodmList = new ArrayList<>();
    List<OneToOneListBean.DataBean.ListBean> mFansList =new ArrayList<>();
    private FollowAdapter fansAdapter;
    private FollowAdapter randomAdapter;
    private int page;

    @Override
    protected void initView() {
        fansAdapter = new FollowAdapter(R.layout.item_hot_list, mFansList);
        randomAdapter = new FollowAdapter(R.layout.item_hot_list, mRanodmList);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(fansAdapter);
        fansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(), mFansList.get(position).getAnchorId(), mFansList.get(position).getMemberId());
            }
        });

        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 2);
        rvRandom.setLayoutManager(layoutManager2);
        rvRandom.setAdapter(randomAdapter);

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = true;
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e(TAG,"wo222");
                        getRandomData(true);
                        swrl.setRefreshing(false);
                    }
                }, 500);
            }
        });
        fansAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e(TAG,"wo ");
                getRandomData(false);
            }},rv);
        fansAdapter.setEmptyView(R.layout.view_follow_empty);
        fansAdapter.setPreLoadNumber(5);



    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_follow;
    }

    @Override
    protected void initData() {
        getData();
        getRandomData(true);

    }

    private void getRandomData(final boolean isRefresh) {
        LogUtils.e(TAG,"getData"+page);
        if (!isLoadMore){
            return;
        }
        if (isRefresh) {
            page =0;
        }else{
            page++;
        }
        LogUtils.e(TAG,"aaa"+page);
        RetrofitFactory.getInstance().getRandOneToOneList(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        randomAdapter.loadMoreFail();
                    }
                    @Override
                    public void onGetData(OneToOneListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            randomAdapter.loadMoreFail();
                            return;
                        }
                         randomAdapter.loadMoreEnd();
                         isLoadMore=false;
                        if (isRefresh) {
                            mRanodmList.clear();
                        }
                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mRanodmList.addAll(content);
                        }
                        randomAdapter.notifyDataSetChanged();
                        randomAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
    }

    private void getData() {



        RetrofitFactory.getInstance()
                .getFansNum("following",page,Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FansBean>() {

                    @Override
                    public void onGetData(FansBean oneToOneListBean) {

                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            return;
                        }
                        mFansList.clear();
                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mFansList.addAll(content);
                        }
                        fansAdapter.notifyDataSetChanged();
                    }
                });
    }
}
