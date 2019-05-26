package com.hala.base;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.hala.utils.ActivityManagerUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity  {


    private AlertDialog alertDialog;

    protected View mRootView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity栈
        ActivityManagerUtil.getActivityManager().pushActivity2Stack(this);
        beforeInitView();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initView();
    }

    protected abstract int getContentViewId();

    protected abstract void beforeInitView();
    protected abstract void initView();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getActivityManager().popActivityStack(this);

    }

    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true.默认返回false
     */
    protected boolean needRegisterNetworkChangeObserver() {
        return true;
    }




}
