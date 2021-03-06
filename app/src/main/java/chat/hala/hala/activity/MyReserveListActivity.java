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
import chat.hala.hala.adapter.ReverseListAdapter;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.bean.ReverseListBean;
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

    List<ReverseListBean.DataBean.ListBean> callList = new ArrayList<>();
    private ReverseListAdapter adapter;
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
        tvTitle.setText("预约");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new ReverseListAdapter(R.layout.item_appointment, callList,CallListAdapter.REVERSE);
        rv.setAdapter(adapter);

       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //VideoCallManager.gotoCallAnchor(MyReserveListActivity.this,callList.);
                if (AvchatInfo.isAnchor()){
                    VideoCallManager.gotoReplyReverse(MyReserveListActivity.this,callList.get(position).getReservationId(),callList.get(position).getCategory(),AvchatInfo.getAnchorId(),callList.get(position).getTargetInfo().getId());
                }
            }
        });
        RetrofitFactory.getInstance().readMessage("reserve")
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            RxBus.getIntanceBus().post(new RefreshMsgEvent(RefreshMsgEvent.MSG_REVERSE));
                        }
                    }
                });
        getData();
    }

    private void getData() {
        RetrofitFactory.getInstance().getReservationList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<ReverseListBean>() {
                    @Override
                    public void onGetData(ReverseListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != callListBean.getCode()) {
                            return;
                        }
                        List<ReverseListBean.DataBean.ListBean> content = callListBean.getData().getList();
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
