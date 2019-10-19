package chat.hala.hala.fragment;

import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chat.hala.hala.R;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.activity.LoginActivityNew;
import chat.hala.hala.adapter.HotCallAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.AdBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.wight.EmptyLoadMoreView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HotFragment extends BaseFragment {


    public static String TAG = "HotFragment";
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;
    private boolean isLoadMore = true;

    List<OneToOneListBean.DataBean.ListBean> mHotOnetoOneList = new ArrayList<>();
    private HotCallAdapter hotCallAdapter;
    private int page;

    private List<AdBean.DataBean> bannerList =new ArrayList<>();

    @Override
    protected void initView() {
        hotCallAdapter = new HotCallAdapter(mHotOnetoOneList, bannerList,getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(hotCallAdapter);
        final int decowidth = SizeUtils.dp2px(getActivity(),9);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position>=mHotOnetoOneList.size()){
                    position=position-1;
                }
                if(mHotOnetoOneList.get(position).getDataType()==OneToOneListBean.DataBean.ListBean.BANNER){
                    return 2;
                }
                return 1;
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int childLayoutPosition = parent.getChildLayoutPosition(view);
                if(childLayoutPosition>=mHotOnetoOneList.size()){
                    childLayoutPosition=childLayoutPosition-1;
                }
                if(mHotOnetoOneList.get(childLayoutPosition).getDataType()==OneToOneListBean.DataBean.ListBean.BANNER){
                    outRect.left=decowidth;
                    outRect.right=decowidth;
                    return;
                }
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
        }};

        rv.addItemDecoration(itemDecoration);
        hotCallAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (AvchatInfo.isLogin()){
                    AnchorsActivity.startAnchorAc(getActivity(), mHotOnetoOneList.get(position).getAnchorId(), mHotOnetoOneList.get(position).getMemberId());
                }else{
                    LoginActivityNew.startLogin(getActivity());
                }

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


        hotCallAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtils.e(TAG, "wo ");
                getData(false);
            }
        }, rv);
        getBannerData();
        hotCallAdapter.setLoadMoreView(new EmptyLoadMoreView());
        hotCallAdapter.setPreLoadNumber(5);

    }

    private void getBannerData() {
        RetrofitFactory.getInstance().getAd().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AdBean>() {
                    @Override
                    public void onGetData(AdBean adBean) {
                        if (ResultUtils.cheekSuccess(adBean)) {
                            List<AdBean.DataBean> data = adBean.getData();
                            if (data!=null&&data.size()>0) {
                                bannerList.clear();
                                bannerList.addAll(data);
                                hotCallAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }




    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_hot;
    }

    @Override
    protected void initData() {
        Log.e(TAG, "initData: " );
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        Log.e(TAG, "getData: " );
        if (!isLoadMore) {
            hotCallAdapter.loadMoreEnd();
            return;
        }
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        LogUtils.e(TAG, "aaa " + hashCode());


        RetrofitFactory.getInstance().getHotOneToOneList(page, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
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
                            hotCallAdapter.loadMoreComplete();
                        } else {
                            hotCallAdapter.loadMoreEnd();
                            isLoadMore = false;

                        }
                        if (isRefresh) {
                            mHotOnetoOneList.clear();
                        }
                        LogUtils.e(TAG, "aaaListBean");
                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mHotOnetoOneList.addAll(content);
                            OneToOneListBean.DataBean.ListBean listBean = new OneToOneListBean.DataBean.ListBean();
                            listBean.setDataType(OneToOneListBean.DataBean.ListBean.BANNER);
                            if(content.size()>4){
                                mHotOnetoOneList.add(4,listBean);
                            }else{
                                mHotOnetoOneList.add(content.size(),listBean);
                            }
                        }
                        hotCallAdapter.notifyDataSetChanged();
                       hotCallAdapter.disableLoadMoreIfNotFullPage(rv);
                    }
                });
        getBannerData();
    }


}
