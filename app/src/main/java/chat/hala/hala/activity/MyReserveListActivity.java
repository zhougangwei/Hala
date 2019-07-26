package chat.hala.hala.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.adapter.CallListAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.rxbus.event.RefreshMsgEvent;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyReserveListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    List<CallListBean.DataBean.ListBean> callList = new ArrayList<>();
    private CallListAdapter adapter;
    private int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_reserve_list;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CallListAdapter(R.layout.item_appointment, callList,CallListAdapter.REVERSE);
        rv.setAdapter(adapter);

       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //VideoCallManager.gotoCallOrReverse(MyReserveListActivity.this,callList.);

                if (AvchatInfo.isAnchor()){
                    VideoCallManager.gotoCallOrReverse(MyReserveListActivity.this,AvchatInfo.getAnchorId(),AvchatInfo.getAccount());
                }
                RetrofitFactory.getInstance().readMessage("reserve")
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                if (ResultUtils.cheekSuccess(baseBean)) {
                                    RxBus.getIntanceBus().post(new RefreshMsgEvent(RefreshMsgEvent.MSG_REVERSE));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                            }
                        });

            }
        });

        getData();
    }

    private void getData() {
        RetrofitFactory.getInstance().getReservationList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CallListBean>() {
                    @Override
                    public void onGetData(CallListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
                        List<CallListBean.DataBean.ListBean> content = callListBean.getData().getList();
                        if (content != null && content.size() > 0) {
                            callList.addAll(content);
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
