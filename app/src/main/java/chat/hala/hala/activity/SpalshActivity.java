package chat.hala.hala.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.VersionBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SpalshActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_spalsh;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        RetrofitFactory.getInstance().getVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<VersionBean>() {
                    @Override
                    public void onGetData(VersionBean baseBean) {
                    }
                });
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SpalshActivity.this,MainActivity.class));
                        finish();
                    }
                });

    }
}
