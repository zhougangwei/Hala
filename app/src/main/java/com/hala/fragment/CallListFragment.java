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
import com.hala.bean.MsgBean;
import com.hala.http.RetrofitFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CallListFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    private List<MsgBean> callList;


    @Override
    protected void initView() {
        rcv.setAdapter(new MsgAdapter(R.layout.item_msg_list,callList));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_msg;
    }

    @Override
    protected void initData() {

    }





}
