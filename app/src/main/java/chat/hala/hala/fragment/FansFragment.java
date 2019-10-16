package chat.hala.hala.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import chat.hala.hala.R;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.adapter.FansAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.FansBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wjy on 2019/10/12/012.
 */
public class FansFragment extends BaseFragment {

    List<OneToOneListBean.DataBean.ListBean> mFansList = new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    Unbinder unbinder;
    private FansAdapter fansAdapter;
    private boolean isLoadMore = true;
    private int page;
    public final static int FOLLOW = 0;
    public final static int FANS = 1;
    public final static int FRIENDS = 2;
    public int type = FOLLOW;


    public static FansFragment newInstance(int arg) {
        FansFragment fragment = new FansFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_hot;
    }

    @Override
    protected void initData() {
        type = getArguments().getInt("type", FOLLOW);
        fansAdapter = new FansAdapter(R.layout.item_fans_list, mFansList,type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(fansAdapter);
        fansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(), mFansList.get(position).getAnchorId(), mFansList.get(position).getMemberId());
            }
        });
        fansAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, rv);
        fansAdapter.setPreLoadNumber(5);
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
        fansAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(!mFansList.get(position).getFollowing()){
                    addFollow(mFansList.get(position).getMemberId());
                }
            }
        });
        getData(true);
    }


    private void getData(final boolean isRefresh) {
        if (!isLoadMore) {
            fansAdapter.loadMoreEnd();
            return;
        }
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        RetrofitFactory.getInstance()
                .getFansNum(FANS == type ? "fans" : type == FOLLOW ? "following" : "friends", page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FansBean>() {
                    @Override
                    public void onGetData(FansBean baseBean) {
                        if (!ResultUtils.cheekSuccess(baseBean)) {
                            fansAdapter.loadMoreFail();
                        }
                        if (baseBean.getData().getPageable().isNextPage()) {
                            fansAdapter.loadMoreComplete();
                        } else {
                            fansAdapter.loadMoreEnd();
                            isLoadMore = false;
                        }
                        if (isRefresh) {
                            mFansList.clear();
                        }
                        List<OneToOneListBean.DataBean.ListBean> content = baseBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mFansList.addAll(content);
                        }
                        fansAdapter.notifyDataSetChanged();
                        fansAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
    }


    private void addFollow(int memberId) {
        RetrofitFactory.getInstance().addFollow("follow", memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            ToastUtils.showToast(getActivity(), "关注成功!");
                            getData(true);
                        }
                    }
                });
    }



}
