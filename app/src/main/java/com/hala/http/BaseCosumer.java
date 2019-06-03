package com.hala.http;

import android.util.Log;

import com.hala.base.Contact;
import com.hala.bean.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  class BaseCosumer< T extends BaseBean> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }




    @Override
    public void onError(Throwable e) {
        try{
            Log.e("BaseCosumer", e.getMessage());
        }catch (Exception e2){
            Log.e("BaseCosumer", e2.getMessage()+"11");
        }
    }

    @Override
    public void onComplete() {

    }

}
