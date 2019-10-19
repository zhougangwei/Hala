package chat.hala.hala.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chat.hala.hala.R;
import chat.hala.hala.activity.MyCallListActivity;
import chat.hala.hala.activity.MyCoinsListActivity;
import chat.hala.hala.activity.MyReserveListActivity;
import chat.hala.hala.adapter.MsgAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.MessageUnreadBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.rxbus.event.RefreshMsgCount;
import chat.hala.hala.rxbus.event.RefreshMsgEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MessageListFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rcv)
    RecyclerView rcv;

    @BindView(R.id.spf)
    SwipeRefreshLayout swrl;

    private List<MessageUnreadBean.DataBean> msgDataList = new ArrayList<>();
    private MsgAdapter msgAdapter;

    public static final String VIDEO_CALL="online_call";
    public static final String COIN="coin";
    public static final String CALL_RESERVATION="call_reservation";

    public static int readCount;

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
                        startActivity(new Intent(getActivity(), MyCoinsListActivity.class));
                        break;
                    case CALL_RESERVATION:
                        startActivity(new Intent(getActivity(), MyReserveListActivity.class));
                        break;
                }

            }
        });
        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        swrl.setRefreshing(false);
                    }
                }, 500);
            }
        });
        initRxbus();
        initConversation();
    }

    private void initConversation() {
        FragmentManager fragmentManage = getChildFragmentManager();
        ConversationListFragment fragement = (ConversationListFragment) fragmentManage.findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragement.setUri(uri);
    }

    private void initRxbus() {
        RxBus.getIntanceBus().doSubscribe(RefreshMsgEvent.class, new Consumer<RefreshMsgEvent>() {
            @Override
            public void accept(RefreshMsgEvent event) throws Exception {
                initData();
            }
        });
    };


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //initConversation();
        }
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
                    public void onGetData(MessageUnreadBean messageUnreadBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != messageUnreadBean.code) {
                            return;
                        }
                        readCount=0;
                        List<MessageUnreadBean.DataBean> data = messageUnreadBean.getData();
                        if (data!=null&&data.size()>0) {
                            msgDataList.clear();
                            msgDataList.addAll(data);
                            msgAdapter.notifyDataSetChanged();
                            for (int i = 0; i < data.size(); i++) {
                                readCount+=data.get(i).getUnreadCount();
                            }
                        }
                        RxBus.getIntanceBus().post(new RefreshMsgCount());

                    }
                });


    }


}
