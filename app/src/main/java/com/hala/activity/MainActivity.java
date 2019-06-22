package com.hala.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hala.R;
import com.hala.adapter.TabAdapter;
import com.hala.avchat.AvchatInfo;
import com.hala.avchat.QiniuInfo;
import com.hala.base.App;
import com.hala.base.AppLoginManager;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.QiNiuToken;
import com.hala.bean.RtmTokenBean;
import com.hala.dialog.CommonDialog;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;
import com.hala.wight.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    private long oldTime;
    private TabAdapter mTabAdapter;

    List<View> viewList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        vp.setAdapter(mTabAdapter);
        vp.setOffscreenPageLimit(5);
        vp.setCurrentItem(0, false);
        viewList.add(ivHome);
        viewList.add(ivMsg);
        viewList.add(ivMy);
        initQiniuData();
        initChat();
    }

    private void initChat() {
        AppLoginManager.loginRtm();
    }

    private void initQiniuData() {
        RetrofitFactory
                .getInstance()
                .getQiNiuToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<QiNiuToken>() {
                    @Override
                    public void onNext(QiNiuToken baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                            return;
                        }
                        QiNiuToken.DataBean.StarchatanchorBean starchatanchor = baseBean.getData().getStarchatanchor();
                        QiNiuToken.DataBean.StarchatfeedbackBean starchatfeedback = baseBean.getData().getStarchatfeedback();
                        QiNiuToken.DataBean.StarchatmemberBean starchatmember = baseBean.getData().getStarchatmember();

                        QiniuInfo.setmStarchatanchorBean(starchatanchor);
                        QiniuInfo.setmStarchatfeedbackBean(starchatfeedback);
                        QiniuInfo.setmStarchatmemberBean(starchatmember);
                    }
                });

    }


    @Override
    public void onBackPressed() {
        long newTime = System.currentTimeMillis();
        if (newTime - oldTime < 3000) {
            finish();
        } else {
            new CommonDialog(this)
                    .setMsg(getString(R.string.want_to_log_out))
                    .setListener(new CommonDialog.OnClickListener() {
                        @Override
                        public void onClickConfirm() {
                            finish();
                        }

                        @Override
                        public void onClickCancel() {
                        }
                    })
                    .show();
        }
        oldTime = newTime;

        login();


    }


    private void login() {

    }


    @OnClick({R.id.iv_home, R.id.iv_msg, R.id.iv_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                setClicked(ivHome);
                break;
            case R.id.iv_msg:
                setClicked(ivMsg);
                break;
            case R.id.iv_my:
                setClicked(ivMy);
                break;
        }
    }

    private void setClicked(ImageView clickView) {
        for (int i = 0; i < viewList.size(); i++) {
            View view = viewList.get(i);
            if (view != clickView) {
                view.setSelected(false);
            } else {
                view.setSelected(true);
                vp.setCurrentItem(i, false);
            }

        }
    }
}


