package com.hala.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.adapter.CallListAdapter;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.CallListBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCallListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    List<CallListBean.DataBean.ContentBean> callList=new ArrayList<>() ;
    private CallListAdapter adapter;
    private int page=0;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_call;
    }

    @Override
    protected void beforeInitView() {
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CallListAdapter(R.layout.item_call_list,callList) ;
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OneToOneActivity.docallOneToOneActivity(MyCallListActivity.this,callList.get(position).getTargetInfo().getId());
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
                    public void onNext(CallListBean callListBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=callListBean.getCode()) {
                            return;
                        }
                        List<CallListBean.DataBean.ContentBean> content = callListBean.getData().getContent();
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
