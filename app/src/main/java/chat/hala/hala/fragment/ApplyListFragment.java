package chat.hala.hala.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chat.hala.hala.R;
import chat.hala.hala.adapter.ApplyAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.ApplyListBean;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.utils.ToastUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wjy on 2019/9/10/010.
 */
public class ApplyListFragment extends BaseFragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;

    private ApplyAdapter applyAdapter;
    List<ApplyListBean.DataBean.ListBean> dataList = new ArrayList();
    private boolean isLoadMore = true;
    private int page;

    @Override
    protected void initView() {
        applyAdapter = new ApplyAdapter(R.layout.item_apply_chat, dataList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(applyAdapter);

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


        applyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, rv);

        applyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
               startApply(dataList.get(position));
            }
        });
    }

    private void startApply(final ApplyListBean.DataBean.ListBean dataBean) {
        dataBean.setState(ApplyListBean.DataBean.ListBean.State.apply_wating);
        applyAdapter.notifyDataSetChanged();
        RetrofitFactory.getInstance().startApply(dataBean.getLootChat().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dataBean.setState(ApplyListBean.DataBean.ListBean.State.apply_failed);
                        applyAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            ToastUtils.showToast(getActivity(),"抢单成功!");
                            dataBean.setState(ApplyListBean.DataBean.ListBean.State.apply_successed);
                            VideoCallManager.gotoLoot(getActivity(),dataBean.getLootChat().getCategory(), AvchatInfo.getAnchorId(),
                                    dataBean.getMember().getMemberId(),dataBean.getLootChat().getId()
                                    );
                        }else{
                            ToastUtils.showToast(getActivity(),"抢单失败!");
                            dataBean.setState(ApplyListBean.DataBean.ListBean.State.apply_failed);
                        }
                        applyAdapter.notifyDataSetChanged();
                    }
                });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_apply_list;
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
        RetrofitFactory.getInstance().getApplyList(page, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<ApplyListBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        applyAdapter.loadMoreFail();
                    }

                    @Override
                    public void onGetData(ApplyListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            applyAdapter.loadMoreFail();
                            return;
                        }
                       if (oneToOneListBean.getData().getPageable().isNextPage()) {
                            applyAdapter.loadMoreComplete();
                        } else {
                            applyAdapter.loadMoreEnd();
                            isLoadMore = false;
                        }
                        applyAdapter.loadMoreEnd();
                        isLoadMore = false;
                        if (isRefresh) {
                            dataList.clear();
                        }
                        List<ApplyListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            for (int i = 0; i < content.size(); i++) {
                                content.get(i).setState(ApplyListBean.DataBean.ListBean.State.apply_waited);
                            }
                            dataList.addAll(content);
                        }
                        applyAdapter.notifyDataSetChanged();
                        applyAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
    }


}
