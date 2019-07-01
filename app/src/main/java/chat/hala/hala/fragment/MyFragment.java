package chat.hala.hala.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.activity.BestarActivity;
import chat.hala.hala.activity.ChargeActivity;
import chat.hala.hala.activity.EditProUserActivity;
import chat.hala.hala.activity.FeedBackActivity;
import chat.hala.hala.activity.WalletActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.dialog.CommonDialog;

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
        tvName.setText(AvchatInfo.getName());
        Glide.with(this)
                .load(AvchatInfo.getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(ivHead.getDrawable()))
                .into(ivHead);
        tvMoney.setText(AvchatInfo.getCoin() + "");
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
                Intent intent = new Intent(getActivity(), EditProUserActivity.class);
                intent.putExtra("type",EditProUserActivity.FROM_MYFRAG_MENT);
                startActivity(intent);
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
                gotoFeedback();
                break;
            case R.id.tv_loginout:
                new CommonDialog(getActivity())
                        .setMsg(getString(R.string.want_to_log_out))
                        .setListener(new CommonDialog.OnClickListener() {
                            @Override
                            public void onClickConfirm() {
                                AvchatInfo.clearBaseData(getActivity());
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

    private void gotoFeedback() {
        startActivity(new Intent(getActivity(), FeedBackActivity.class));
    }

    private void gotoCharge() {
        startActivity(new Intent(getActivity(), ChargeActivity.class));
    }

    private void gotoCertify() {
        startActivity(new Intent(getActivity(), BestarActivity.class));
    }

    private void gotoWallet() {
        startActivity(new Intent(getActivity(), WalletActivity.class));
    }


}
