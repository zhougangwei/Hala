package chat.hala.hala.http;

import android.util.Log;

import chat.hala.hala.bean.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  abstract class BaseCosumer< T extends BaseBean> implements Observer<T> {

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
