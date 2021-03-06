package chat.hala.hala.fragment;

import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;
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


    public static String TAG = "HotFragment";
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rv_random)
    RecyclerView rvRandom;

  @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    private boolean isLoadMore = true;
    private List<OneToOneListBean.DataBean.ListBean> mRanodmList = new ArrayList<>();
    List<OneToOneListBean.DataBean.ListBean> mFansList = new ArrayList<>();
    private FollowAdapter fansAdapter;
    private FollowAdapter randomAdapter;
    private int page;

    @Override
    protected void initView() {
        fansAdapter = new FollowAdapter(R.layout.item_hot_list, mFansList);
        randomAdapter = new FollowAdapter(R.layout.item_hot_list, mRanodmList);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        }
        ;
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(fansAdapter);
        fansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(), mFansList.get(position).getAnchorId(), mFansList.get(position).getMemberId());
            }
        });

        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvRandom.setLayoutManager(layoutManager2);
        rvRandom.setAdapter(randomAdapter);
        final int decowidth = SizeUtils.dp2px(getActivity(), 9);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childLayoutPosition = parent.getChildLayoutPosition(view);
                int mode = childLayoutPosition % 2;
                outRect.top = 0;
                outRect.bottom = 0;
                if (mode == 0) {
                    outRect.right = decowidth / 2;
                    outRect.left = decowidth;
                } else if (mode == 1) {
                    outRect.left = decowidth / 2;
                    outRect.right = decowidth;
                }
            }
        };
        rvRandom.addItemDecoration(itemDecoration);
        RecyclerView.ItemDecoration itemDecoration2 = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childLayoutPosition = parent.getChildLayoutPosition(view);
                int mode = childLayoutPosition % 2;
                outRect.top = 0;
                outRect.bottom = 0;
                if (mode == 0) {
                    outRect.right = decowidth / 2;
                    outRect.left = decowidth;
                } else if (mode == 1) {
                    outRect.left = decowidth / 2;
                    outRect.right = decowidth;
                }
            }
        };
        randomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(), mRanodmList.get(position).getAnchorId(), mRanodmList.get(position).getMemberId());
            }
        });

      rv.addItemDecoration(itemDecoration2);
      swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = true;
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData(true);
                        getRandomData(true);
                        swrl.setRefreshing(false);
                    }
                }, 500);
            }
        });

        randomAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getRandomData(false);
            }
        }, rv);

        fansAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, rv);
        fansAdapter.setEmptyView(R.layout.view_follow_empty);
        fansAdapter.setPreLoadNumber(5);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_follow;
    }

    @Override
    protected void initData() {
        getData(true);
        getRandomData(true);
    }

    private void getRandomData(final boolean isRefresh) {
        RetrofitFactory.getInstance().getRandOneToOneList(3)
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
                        if (isRefresh) {
                            mRanodmList.clear();
                        }
                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mRanodmList.addAll(content);
                        }
                        randomAdapter.notifyDataSetChanged();
                        randomAdapter.disableLoadMoreIfNotFullPage(rvRandom);
                    }
                });
    }

    private void getData(final boolean isRefresh) {
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        RetrofitFactory.getInstance()
                .getFansNum("following", page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new BaseCosumer<FansBean>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                fansAdapter.loadMoreFail();
                            }
                    @Override
                    public void onGetData(FansBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            fansAdapter.loadMoreFail();
                            return;
                        }
                        fansAdapter.loadMoreEnd();
                        isLoadMore = false;
                        if (isRefresh) {
                            mFansList.clear();
                        }
                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mFansList.addAll(content);
                        }
                        fansAdapter.notifyDataSetChanged();
                        fansAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
    }
}
