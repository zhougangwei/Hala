package chat.hala.hala.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.Group;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.activity.AboutUsActivity;
import chat.hala.hala.activity.AnchorsActivity;
import chat.hala.hala.activity.ApplyAnchorActivity;
import chat.hala.hala.activity.ApplyFamilyActivity;
import chat.hala.hala.activity.BeStarResultActivity;
import chat.hala.hala.activity.ChargeActivity;
import chat.hala.hala.activity.ChatSettingActivity;
import chat.hala.hala.activity.FamilyManagerActivity;
import chat.hala.hala.activity.FeedBackActivity;
import chat.hala.hala.activity.FollowOrFansActivity;
import chat.hala.hala.activity.JoinFamilyActivity;
import chat.hala.hala.activity.LoginActivityNew;
import chat.hala.hala.activity.MyGainActivity;
import chat.hala.hala.activity.SettingActivity;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseFragment;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.AnchorBean;
import chat.hala.hala.bean.BeStarResultBean;
import chat.hala.hala.bean.CoinBriefBean;
import chat.hala.hala.bean.CoinListBean;
import chat.hala.hala.dialog.CommonDialog;
import chat.hala.hala.dialog.ShareDialog;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.manager.MoneyHelper;
import chat.hala.hala.rxbus.RxBus;
import chat.hala.hala.rxbus.event.RefreshUserInfoEvent;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;

public class MyFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_charge)
    TextView tvCharge;

    @BindView(R.id.tv_income_value)
    TextView tvInComeValue;
    @BindView(R.id.tv_follow)
    TextView tvFollow;

    @BindView(R.id.tv_fans)
    TextView tvFans;

    @BindView(R.id.tv_friend)
    TextView tvFriend;

    @BindView(R.id.tv_my_money)
    TextView tvMyMoney;

    @BindView(R.id.tv_follow_name)
    TextView tvFollowName;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;

    @BindView(R.id.tv_joinfamily)
    TextView tvJoinFamily;
    @BindView(R.id.gp_income)
    Group gpInCome;
    @BindView(R.id.gp_loginout)
    Group gpLoginOut;
    @BindView(R.id.ll_family_manager)
    LinearLayout llFamilyManager;


    @Override
    protected void initView() {
        gpLoginOut.setVisibility(View.GONE);
        if(!AvchatInfo.isLogin()){
            return;
        }
        tvName.setText(AvchatInfo.getName());
        Glide.with(this)
                .load(AvchatInfo.getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(ivHead.getDrawable()))
                .into(ivHead);
        tvMyMoney.setText(AvchatInfo.getCoin() + "Pa币");

        RxBus intanceBus = RxBus.getIntanceBus();
        Disposable disposable=intanceBus.doSubscribe(RefreshUserInfoEvent.class, new Consumer<RefreshUserInfoEvent>() {
            @Override
            public void accept(RefreshUserInfoEvent data) throws Exception {
                refreshData();
            }
        });
        intanceBus .addSubscription(this,disposable);




    }

    private void initIncome() {
        RetrofitFactory.getInstance().getCoinInComeList(0, Contact.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<CoinListBean>() {
                    @Override
                    public void onGetData(CoinListBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            tvInComeValue.setText(baseBean.getData().getTotal()+"Pa豆");
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
        if(!AvchatInfo.isLogin()){
            return;
        }
        RetrofitFactory.getInstance()
                .getAnchorData(AvchatInfo.isAnchor()?"anchor":"member",AvchatInfo.isAnchor()?AvchatInfo.getAnchorId(): AvchatInfo.getAccount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<AnchorBean>() {
                    @Override
                    public void onGetData(AnchorBean baseBean) {
                        tvName.setText(baseBean.getData().getUsername());
                        int anchorId = baseBean.getData().getAnchorId();
                        if (anchorId!=0) {
                            AvchatInfo.setAnchorId(anchorId);
                        }
                        AvchatInfo.saveBaseData(baseBean.getData(),getActivity(),true);
                        tvFans.setText(baseBean.getData().getFansCount()+"");
                        tvFollow.setText(baseBean.getData().getFollowingCount()+"");
                        tvFriend.setText(baseBean.getData().getFriendCount()+"");
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
                            MoneyHelper.setMoney(coinBriefBean.getData().getTotal());
                            tvMyMoney.setText(coinBriefBean.getData().getTotal() + "Pa币");
                        }
                    }
                });
        if (AvchatInfo.isAnchor()) {
            initIncome();
        }

        Observable.timer(
                500, TimeUnit.MILLISECONDS
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if(AvchatInfo.isFamilyLeader()){
                            llFamilyManager.setVisibility(View.VISIBLE);
                        }else{
                            llFamilyManager.setVisibility(View.GONE);
                        }
                        if(AvchatInfo.isAnchor()){
                            tvJoinFamily.setVisibility(View.VISIBLE);
                        }else{
                            tvJoinFamily.setVisibility(View.GONE);
                        }
                        if (AvchatInfo.isAnchor()) {
                            initIncome();
                            gpInCome.setVisibility(View.VISIBLE);
                        }else{
                            gpInCome.setVisibility(View.GONE);
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


    @OnClick({R.id.iv_setting,R.id.iv_family,R.id.tv_my_money,R.id.tv_income_value,R.id.tv_joinfamily,R.id.tv_friend,R.id.tv_fans,R.id.ll_about_us,R.id.tv_follow, R.id.tv_income,R.id.tv_edit, R.id.iv_head, R.id.tv_charge,  R.id.tv_wallet, R.id.tv_certify,R.id.ll_family_manager, R.id.tv_feedback, R.id.tv_invite, R.id.tv_chat_setting, R.id.tv_beauty_setting, R.id.tv_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                getActivity().startActivityForResult(new Intent(getActivity(), SettingActivity.class),Contact.REQUEST_CLOSE_MAIN);
                break;
            case R.id.iv_family:
                startActivity(new Intent(getActivity(), ApplyFamilyActivity.class));
                break;
            case R.id.tv_joinfamily:
                startActivity(new Intent(getActivity(), JoinFamilyActivity.class));
                break;
            case R.id.tv_friend:
                gotoFriend();
                break;
            case R.id.tv_fans:
                gotoFans();
                break;
            case R.id.ll_about_us:
                gotoAboutUs();
                break;
            case R.id.tv_follow:
                gotoFollow();
                break;
            case R.id.tv_income_value:
            case R.id.tv_income:
                gotoGetIncome();
                break;
            case R.id.iv_head:
                gotoEdit();
                break;
            case R.id.tv_edit:
                gotoEdit();
                break;
            case R.id.tv_charge:
               gotoCharge();
                break;
            case R.id.tv_my_money:
            case R.id.tv_wallet:
                gotoWallet();
                break;
            case R.id.tv_certify:
                gotoCertify();
                break;
            case R.id.ll_family_manager:
                gotoFamilyManager();
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
                                LoginActivityNew.startLogin(getActivity());
                                RongIM.getInstance().logout();
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

    private void gotoFamilyManager() {
        startActivity(new Intent(getActivity(), FamilyManagerActivity.class));
    }

    private void gotoFriend() {
        Intent intent = new Intent(getActivity(), FollowOrFansActivity.class);
        intent.putExtra("type",FansFragment.FRIENDS);
        startActivity(intent);
    }

    private void gotoFans() {
        Intent intent = new Intent(getActivity(), FollowOrFansActivity.class);
        intent.putExtra("type",FansFragment.FANS);
        startActivity(intent);
    }

    private void gotoAboutUs() {
        startActivity(new Intent(getActivity(), AboutUsActivity.class));
    }


    private void gotoFollow() {
        Intent intent = new Intent(getActivity(), FollowOrFansActivity.class);
        intent.putExtra("type",FansFragment.FOLLOW);
        startActivity(intent);
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

        RetrofitFactory.getInstance().
                getBeStarState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BeStarResultBean>() {
                    @Override
                    public void onGetData(BeStarResultBean beStarResultBean) {
                        if (ResultUtils.cheekSuccess(beStarResultBean)) {
                            switch (beStarResultBean.getData().getState()) {
                                case BeStarResultBean.BESTAR_OPEN:
                                    startActivityForResult(new Intent(getActivity(), ApplyAnchorActivity.class),1);
                                    return;
                            }
                        }
                        Intent intent = new Intent(getActivity(), BeStarResultActivity.class);
                        intent.putExtra("type",beStarResultBean.getData().getState());
                        startActivity(intent);
                    }
                });

    }

    private void gotoWallet() {
        startActivity(new Intent(getActivity(),ChargeActivity.class));
       // startActivity(new Intent(getActivity(), WalletActivity.class));
    }


}
