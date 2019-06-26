package chat.hala.hala.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import chat.hala.hala.R;
import chat.hala.hala.activity.MyCallListActivity;
import chat.hala.hala.activity.MyIncomeActivity;
import chat.hala.hala.adapter.MsgAdapter;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.MessageUnreadBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;

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
                        startActivity(new Intent(getActivity(), MyIncomeActivity.class));
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
                        if (data!=null&&data.size()>0) {
                            msgDataList.clear();
                            msgDataList.addAll(data);
                            msgAdapter.notifyDataSetChanged();
                        }
                    }
                });


    }


}
