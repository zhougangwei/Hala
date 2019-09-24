package chat.hala.hala.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.FansAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.FansBean;
import chat.hala.hala.bean.LoginBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FollowOrFansActivity extends BaseActivity {

    private static final String TAG = "FollowOrFansActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    List<OneToOneListBean.DataBean.ListBean> mFansList = new ArrayList<>();
    private FansAdapter fansAdapter;
    private boolean isLoadMore = true;
    private int page;

    public final static int FANS=1;
    public final static int FOLLOW=2;
    public final static int FRIENDS=3;
    private int type;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_follow_or_fans;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        switch (type) {
            case FANS:
                tvTitle.setText("我的粉丝");
                break;
            case FOLLOW:
                tvTitle.setText("我的关注");
                break;
            case FRIENDS:
                tvTitle.setText("我的朋友");
                break;
        }

        fansAdapter = new FansAdapter(R.layout.item_suggest_list, mFansList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(fansAdapter);
        fansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(FollowOrFansActivity.this, mFansList.get(position).getAnchorId(), mFansList.get(position).getMemberId());
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
        fansAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e(TAG, "wo ");
                getData(false);
            }
        }, rv);
        fansAdapter.setPreLoadNumber(5);
        initData();
    }
    private void initData() {
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
        RetrofitFactory.getInstance()
                .getFansNum(FANS==type?"fans":type==FOLLOW?"following":"friends",page,Contact.PAGE_SIZE)
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


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
