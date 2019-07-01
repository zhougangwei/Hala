package chat.hala.hala.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chat.hala.hala.R;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.adapter.HotCallAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.AdBean;
import chat.hala.hala.bean.OneToOneListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import chat.hala.hala.wight.GlideImageLoader;
import chat.hala.hala.wight.banner.Banner;
import chat.hala.hala.wight.banner.BannerConfig;
import chat.hala.hala.wight.banner.listener.OnBannerListener;
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
    Banner banner;
    private List<String> imagesList=new ArrayList<>();

    @Override
    protected void initView() {
        hotCallAdapter = new HotCallAdapter(R.layout.item_hot_list, mHotOnetoOneList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(hotCallAdapter);

        View headView = View.inflate(getActivity(), R.layout.item_hotfragment_header, null);
        banner=headView.findViewById(R.id.banner);
        hotCallAdapter.addHeaderView(headView);

        hotCallAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnchorsActivity.startAnchorAc(getActivity(), mHotOnetoOneList.get(position).getAnchorId(), mHotOnetoOneList.get(position).getMemberId());
            }
        });

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoadMore = true;
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "wo222");
                        getData(true);
                        swrl.setRefreshing(false);
                    }
                }, 200);
            }
        });
        hotCallAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e(TAG, "wo ");
                getData(false);
            }
        }, rv);
        getBannerData();


        hotCallAdapter.setPreLoadNumber(5);
        hotCallAdapter.disableLoadMoreIfNotFullPage(rv);
    }

    private void getBannerData() {
        RetrofitFactory.getInstance().getAd().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AdBean>() {
                    @Override
                    public void onNext(AdBean adBean) {
                        if (ResultUtils.cheekSuccess(adBean)) {
                            List<AdBean.DataBean> data = adBean.getData();
                            if (data!=null&&data.size()>0) {
                                imagesList.clear();
                                for (AdBean.DataBean datum : data) {
                                    imagesList.add(datum.getMediaUrl());
                                }
                                //imagesList.add( "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561969870482&di=5c1c1ce287af862e3165902039c59cbd&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160522%2F29bb43e8e4d44c94846ae13520d15f88_th.jpg");
                                //imagesList.add( "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561969870482&di=5c1c1ce287af862e3165902039c59cbd&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160522%2F29bb43e8e4d44c94846ae13520d15f88_th.jpg");
                                //imagesList.add( "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561969870482&di=5c1c1ce287af862e3165902039c59cbd&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160522%2F29bb43e8e4d44c94846ae13520d15f88_th.jpg");
                                //imagesList.add( "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561969870482&di=5c1c1ce287af862e3165902039c59cbd&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160522%2F29bb43e8e4d44c94846ae13520d15f88_th.jpg");
                                banner();
                            }
                        }
                    }
                });
    }


    private void banner() {
        banner.setImages(imagesList)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                    }
                })
                .start();
        banner.setIndicatorGravity(BannerConfig.RIGHT);
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

        if (!isLoadMore) {
            return;
        }
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        Log.e(TAG, "aaa" + page);

        RetrofitFactory.getInstance().getHotOneToOneList(0, Contact.PAGE_SIZE).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<OneToOneListBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        hotCallAdapter.loadMoreFail();
                    }

                    @Override
                    public void onNext(OneToOneListBean oneToOneListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != oneToOneListBean.getCode()) {
                            hotCallAdapter.loadMoreFail();
                            return;
                        }
                        Log.e(TAG, "onNext: " + GsonUtil.parseObjectToJson(oneToOneListBean));

                        if (oneToOneListBean.getData().getPageable().isNextPage()) {
                            hotCallAdapter.loadMoreEnd();
                            isLoadMore = false;
                        } else {
                            hotCallAdapter.loadMoreComplete();
                        }
                        if (isRefresh) {
                            mHotOnetoOneList.clear();
                        }
                        Log.e(TAG, "aaaListBean");

                        List<OneToOneListBean.DataBean.ListBean> content = oneToOneListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            mHotOnetoOneList.addAll(content);
                        }
                        hotCallAdapter.notifyDataSetChanged();
                    }
                });
    }


}
