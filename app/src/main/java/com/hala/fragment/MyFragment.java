package com.hala.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hala.R;
import com.hala.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    Unbinder unbinder;

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_more, R.id.tv_charge, R.id.tv_money, R.id.gp_walllet, R.id.gp_certify, R.id.gp_feedback, R.id.gp_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                break;
            case R.id.tv_charge:
                break;
            case R.id.tv_money:
                break;
            case R.id.gp_walllet:
                break;
            case R.id.gp_certify:
                break;
            case R.id.gp_feedback:
                break;
            case R.id.gp_loginout:
                break;
        }
    }
}
