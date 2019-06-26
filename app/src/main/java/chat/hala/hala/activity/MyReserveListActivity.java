package chat.hala.hala.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import chat.hala.hala.R;
import chat.hala.hala.adapter.CallListAdapter;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.CallListBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

      /*  adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoCallManager.gotoCallOrReverse(MyReserveListActivity.this,callList.);
                OneToOneActivity.docallOneToOneActivity(MyReserveListActivity.this, callList.get(position).getTargetInfo().getId());
            }
        });*/

        getData();
    }

    private void getData() {
        RetrofitFactory.getInstance().getReservationList(page, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CallListBean>() {
                    @Override
                    public void onNext(CallListBean callListBean) {
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
