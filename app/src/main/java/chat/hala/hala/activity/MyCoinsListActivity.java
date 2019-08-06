package chat.hala.hala.activity;

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
import chat.hala.hala.adapter.CoinListAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.rxbus.event.RefreshMsgEvent;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyCoinsListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    List<CoinListBean.DataBean.TransactionsBean.ListBean> callList = new ArrayList<>();
    private CoinListAdapter adapter;
    private int page = 0;
    private String TAG ="MyCoinsListActivity";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_coins_list;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.mycoins);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CoinListAdapter(R.layout.item_coin_list, callList);
        rv.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage(rv);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RetrofitFactory.getInstance().readMessage("coin")
                        .subscribeOn(Schedulers.io())
                        .subscribe(new BaseCosumer<BaseBean>() {
                            @Override
                            public void onGetData(BaseBean baseBean) {
                                if (ResultUtils.cheekSuccess(baseBean)) {
                                    RxBus.getIntanceBus().post(new RefreshMsgEvent(RefreshMsgEvent.MSG_COIN));
                                }
                            }
                        });
            }
        });
        getData();
    }

    private void getData() {
        RetrofitFactory.getInstance().getCoinList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onGetData(CoinListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
                        LogUtils.e(TAG, "onGetData: "+GsonUtil.parseObjectToJson(callListBean));
                        List<CoinListBean.DataBean.TransactionsBean.ListBean> list = callListBean.getData().getTransactions().getList();
                        if (list != null && list.size() > 0) {
                            callList.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

}
