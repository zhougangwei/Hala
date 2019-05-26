package com.hala.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;



public abstract class BaseFragment extends RxFragment {

    protected static final String TAG = "BaseFragment";

    public View mRootView;

    /**
     * 布局的id
     */
    protected int mContentViewId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);//避免重复加载vonDestroyViewiew
            }
        } else {
            beforeInitView();
            mRootView = inflater.inflate(mContentViewId, container, false);
            ButterKnife.bind(this, mRootView);
            initView(mRootView);
        }

        return mRootView;
    }

    protected abstract void initView(View view);

    protected abstract void beforeInitView();

    protected abstract void initViewCreate();



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
