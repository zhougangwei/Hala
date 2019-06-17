package com.hala.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.activity.MyCallListActivity;
import com.hala.adapter.MsgAdapter;
import com.hala.base.BaseFragment;
import com.hala.base.Contact;
import com.hala.bean.MessageUnreadBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageListFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    private List<MessageUnreadBean.DataBean> msgDataList = new ArrayList<>();
    private MsgAdapter msgAdapter;

    public static final String VIDEO_CALL="video_call";
    public static final String COIN="coin";
    public static final String CALL_RESERVATION="call_reservation";

    @Override
    protected void initView() {
        msgAdapter = new MsgAdapter(R.layout.item_msg_list, msgDataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(msgAdapter);
        msgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (msgDataList.get(position).getCategory()) {
                    case VIDEO_CALL:
                        startActivity(new Intent(getActivity(), MyCallListActivity.class));
                        break;
                    case COIN:
                        startActivity(new Intent(getActivity(), MyCallListActivity.class));
                        break;
                    case CALL_RESERVATION:
                        startActivity(new Intent(getActivity(), MyCallListActivity.class));
                        break;
                }

            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_msg;
    }

    @Override
    protected void initData() {
        RetrofitFactory.getInstance().getMessageUnread()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<MessageUnreadBean>() {
                    @Override
                    public void onNext(MessageUnreadBean messageUnreadBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != messageUnreadBean.code) {
                            return;
                        }
                        List<MessageUnreadBean.DataBean> data = messageUnreadBean.getData();
                        msgDataList.clear();
                        msgDataList.addAll(data);
                        msgAdapter.notifyDataSetChanged();
                    }
                });


    }


}
