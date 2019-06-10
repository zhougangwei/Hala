


package com.hala.base;

        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.ViewStub;


        import com.hala.rxbus.RxBus;
        import com.trello.rxlifecycle2.LifecycleTransformer;
        import com.trello.rxlifecycle2.components.RxFragment;

        import butterknife.ButterKnife;
        import butterknife.Unbinder;


/**
 * Created by renlei on 2016/5/23.
 */
public abstract class BaseFragment extends com.trello.rxlifecycle2.components.support.RxFragment {



    protected BaseActivity   mActivity;
    private   View           mView;


    protected abstract void initView();

    //获取fragment布局文件ID
    protected abstract int getLayoutId();
    public BaseActivity getBaseActivity() {
        return mActivity;
    }
    private Unbinder mUnbinder;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        mActivity = (BaseActivity) getActivity();
        initView();
        return mView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        this.mActivity = null;
        this.mView = null;
        this.mUnbinder = null;


    }

    public void unSubsrcibe(){
        RxBus.getIntanceBus().unSubscribe(this);
    }






}

