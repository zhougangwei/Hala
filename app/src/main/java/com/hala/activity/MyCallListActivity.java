package com.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hala.R;
import com.hala.adapter.CallListAdapter;
import com.hala.adapter.CountryAdapter;
import com.hala.base.BaseActivity;
import com.hala.bean.CallListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCallListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    List<CallListBean.DataBean.ContentBean> callList=new ArrayList<>() ;
    private CallListAdapter adapter;


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
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
    }
}
