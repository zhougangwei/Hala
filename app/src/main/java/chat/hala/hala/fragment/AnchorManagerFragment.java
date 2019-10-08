package chat.hala.hala.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import chat.hala.hala.R;
import chat.hala.hala.activity.FamilyAnchorDetailActivity;
import chat.hala.hala.adapter.FamilyAnchorAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.FamilyAnchorBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AnchorManagerFragment extends BaseFragment {

    public static String TAG = "FamilyManagerFragment";

    List<OneToOneListBean.DataBean.ListBean> mAnchorList = new ArrayList<>();
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private FamilyAnchorAdapter familyAnchorAdapter;

    private int page;
    private boolean isLoadMore=true;

    @Override
    protected void initView() {
        familyAnchorAdapter = new FamilyAnchorAdapter(R.layout.item_family_anchor, mAnchorList);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(familyAnchorAdapter);
        familyAnchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), FamilyAnchorDetailActivity.class);
                intent.putExtra("memberId",mAnchorList.get(position).getMemberId());
                startActivity(intent);
            }
        });
        familyAnchorAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(false);
            }
        }, rv);
        familyAnchorAdapter.setPreLoadNumber(5);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_anchor;
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        LogUtils.e(TAG, "getData" + page);
        if (!isLoadMore) {
            return;
        }
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        RetrofitFactory.getInstance().getFamilyAnchor(page, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<FamilyAnchorBean>() {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        familyAnchorAdapter.loadMoreFail();
                    }

                    @Override
                    public void onGetData(FamilyAnchorBean oneToOneListBean) {
                        LogUtils.e(TAG, "ffff" + GsonUtil.parseObjectToJson(oneToOneListBean));
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            familyAnchorAdapter.loadMoreFail();
                            return;
                        }
                        if (oneToOneListBean.getData().getPageable().isNextPage()) {
                            familyAnchorAdapter.loadMoreComplete();
                        } else {
                            familyAnchorAdapter.loadMoreEnd();
                            isLoadMore = false;

                        }
                        if (isRefresh) {
                            mAnchorList.clear();
                        }
                        LogUtils.e(TAG, "aaaListBean");

                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mAnchorList.addAll(content);
                        }
                        familyAnchorAdapter.notifyDataSetChanged();
                        familyAnchorAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
    }





}
