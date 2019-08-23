package chat.hala.hala.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.Group;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.activity.AboutUsActivity;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.activity.BeStarResultActivity;
import chat.hala.hala.activity.ChargeActivity;
import chat.hala.hala.activity.ChatSettingActivity;
import chat.hala.hala.activity.FeedBackActivity;
import chat.hala.hala.activity.FollowOrFansActivity;
import chat.hala.hala.activity.MyGainActivity;
import chat.hala.hala.activity.WalletActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.CoinBriefBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.dialog.ShareDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_charge)
    TextView tvCharge;

    @BindView(R.id.tv_income_value)
    TextView tvInComeValue;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    @BindView(R.id.tv_follow)
    TextView tvFollow;

    @BindView(R.id.tv_follow_name)
    TextView tvFollowName;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.gp_income)
    Group gpInCome;
    @BindView(R.id.swrl)
    SwipeRefreshLayout swrl;

    @Override
    protected void initView() {

        tvName.setText(AvchatInfo.getName());


        Glide.with(this)
                .load(AvchatInfo.getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(ivHead.getDrawable()))
                .into(ivHead);
        tvMoney.setText(AvchatInfo.getCoin() + "");
        if (AvchatInfo.isAnchor()) {
            initIncome();
            gpInCome.setVisibility(View.VISIBLE);
        }else{
            gpInCome.setVisibility(View.GONE);
        }

        swrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swrl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                        swrl.setRefreshing(false);
                    }
                }, 500);
            }
        });


    }

    private void initIncome() {
        RetrofitFactory.getInstance().getCoinInComeList(0, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onGetData(CoinListBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            tvInComeValue.setText(baseBean.getData().getTotal()+"");
                        }
                    }
                });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initData() {
        refreshData();
    }

    @SuppressLint("CheckResult")
    public void refreshData() {
        if (AvchatInfo.isAnchor()){
            tvFollowName.setText("粉丝");
            tvFollow.setText(AvchatInfo.getMemberBean().getFansCount()+"");
        }else{
            tvFollowName.setText("关注");
            tvFollow.setText(AvchatInfo.getMemberBean().getFollowingCount()+"");
        }

        RetrofitFactory.getInstance()
                .getAnchorData("member", AvchatInfo.getAccount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AnchorBean>() {
                    @Override
                    public void onGetData(AnchorBean baseBean) {
                        tvName.setText(baseBean.getData().getNickname());
                        int anchorId = baseBean.getData().getAnchorId();
                        if (anchorId!=0) {
                            AvchatInfo.setAnchorId(anchorId);
                        }
                        if (AvchatInfo.isAnchor()){
                            tvFollow.setText(baseBean.getData().getFansCount()+"");
                        }else{
                            tvFollow.setText(baseBean.getData().getFollowingCount()+"");
                        }
                        Glide.with(getActivity())
                                .load(baseBean.getData().getAlbum().get(0).getMediaUrl())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(ivHead.getDrawable()))
                                .into(ivHead);
                    }
                });
        RetrofitFactory.getInstance().getCoinBrief()
                .subscribeOn(Schedulers.io())
                .compose(this.<CoinBriefBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinBriefBean>() {
                    @Override
                    public void onGetData(CoinBriefBean coinBriefBean) {
                        if (ResultUtils.cheekSuccess(coinBriefBean)) {
                            tvMoney.setText(coinBriefBean.getData().getTotal() + "");
                        }
                    }
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshData();
        }
    }

    @OnClick({R.id.ll_about_us,R.id.tv_follow, R.id.tv_income, R.id.iv_head, R.id.tv_charge, R.id.tv_money, R.id.tv_wallet, R.id.tv_certify, R.id.tv_feedback, R.id.tv_invite, R.id.tv_chat_setting, R.id.tv_beauty_setting, R.id.tv_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_about_us:
                gotoAboutUs();
                break;
            case R.id.tv_follow:
                gotoFollow();
                break;
            case R.id.tv_income:
                gotoGetIncome();
                break;
            case R.id.iv_head:
                gotoEdit();
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
            case R.id.tv_invite:
                gotoInvite();
                break;
            case R.id.tv_chat_setting:
                gotoChatSetting();
                break;
            case R.id.tv_beauty_setting:
                gotoBeautySetting();
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

    private void gotoAboutUs() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }

    private void gotoFollow() {

        startActivity(new Intent(getActivity(), FollowOrFansActivity.class));
    }


    private void gotoGetIncome() {
        startActivity(new Intent(getActivity(), MyGainActivity.class));
    }

    private void gotoEdit() {
        Intent intent = new Intent(getActivity(), AnchorsActivity.class);
        intent.putExtra("fromAc", AnchorsActivity.EDIT_AC);
        intent.putExtra("anchorId", AvchatInfo.getAnchorId());
        intent.putExtra("memberId", AvchatInfo.getMemberId());
        startActivity(intent);
    }

    private void gotoBeautySetting() {

    }

    private void gotoChatSetting() {
        startActivity(new Intent(getActivity(), ChatSettingActivity.class));
    }

    private void gotoInvite() {
        new ShareDialog(getActivity()).show();
    }

    private void gotoFeedback() {
        startActivity(new Intent(getActivity(), FeedBackActivity.class));
    }

    private void gotoCharge() {
        startActivity(new Intent(getActivity(), ChargeActivity.class));
    }

    private void gotoCertify() {
        startActivity(new Intent(getActivity(), BeStarResultActivity.class));
    }

    private void gotoWallet() {
        startActivity(new Intent(getActivity(), WalletActivity.class));
    }


}
