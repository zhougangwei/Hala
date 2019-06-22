package com.hala.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hala.R;
import com.hala.activity.BestarActivity;
import com.hala.activity.OneToOneActivity;
import com.hala.activity.WalletActivity;
import com.hala.base.BaseFragment;
import com.hala.dialog.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

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




    @OnClick({R.id.iv_more, R.id.tv_charge, R.id.tv_money, R.id.tv_wallet, R.id.tv_certify, R.id.tv_feedback, R.id.tv_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                break;
            case R.id.tv_charge:
                gotoCharge();
                break;
            case R.id.tv_money:
                break;
            case R.id.tv_wallet:
                gotoWallet();
                break;
            case R.id.tv_certify:
                gotoCertify();
                break;
            case R.id.tv_feedback:
                break;
            case R.id.tv_loginout:
                new CommonDialog(getActivity())
                        .setMsg(getString(R.string.want_to_log_out))
                        .setListener(new CommonDialog.OnClickListener() {
                            @Override
                            public void onClickConfirm() {
                                getActivity().finish();
                            }
                            @Override
                            public void onClickCancel() {
                            }
                        })
                        .show();


                break;
        }
    }

    private void gotoCharge() {
        //OneToOneActivity.docallOneToOneActivity(getActivity(),5);
    }

    private void gotoCertify() {
        startActivity(new Intent(getActivity(), BestarActivity.class));
    }

    private void gotoWallet() {
        startActivity(new Intent(getActivity(), WalletActivity.class));
    }

}
