package com.hala.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hala.R;
import com.hala.adapter.MsgAdapter;
import com.hala.base.BaseFragment;
import com.hala.http.RetrofitFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CallListFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rcv)
    RecyclerView rcv;



    @Override
    protected void initView() {
        rcv.setAdapter(new MsgAdapter(this,R.layout.item_msg_list));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_msg;
    }

    @Override
    protected void initData() {

    }





}
