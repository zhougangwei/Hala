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
import chat.hala.hala.adapter.CallListAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.base.VideoCallManager;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.rxbus.event.RefreshMsgEvent;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyCallListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    List<CallListBean.DataBean.ListBean> callList=new ArrayList<>() ;
    private CallListAdapter adapter;
    private int page=0;
    private String TAG ="MyCallListActivity";


    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void beforeInitView() {
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.my_calls);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CallListAdapter(R.layout.item_call_list,callList,CallListAdapter.MYCALL) ;
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
            VideoCallManager.gotoCallOrReverse(MyCallListActivity.this,callList.get(position).getAnchorId(),callList.get(position).getTargetInfo().getId());
            RetrofitFactory.getInstance().readMessage("call")
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
        RetrofitFactory.getInstance().getCallList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CallListBean>() {
                    @Override
                    public void onGetData(CallListBean callListBean) {
                        LogUtils.e(TAG, "onGetData: "+GsonUtil.parseObjectToJson(callListBean.getData()) );
                        if (Contact.REPONSE_CODE_SUCCESS!=callListBean.getCode()) {
                            return;
                        }
                        List<CallListBean.DataBean.ListBean> content = callListBean.getData().getList();
                        if (content!=null&&content.size()>0) {
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
