package com.hala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hala.activity.LoginActivity;
import com.hala.base.Contact;
import com.hala.bean.AnchorBean;
import com.hala.bean.BaseBean;
import com.hala.bean.LoginBean;
import com.hala.dialog.CommonDialog;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;
import com.hala.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private long oldTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // Example of a call to a native method
    TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
      long newTime =  System.currentTimeMillis();
      if (newTime- oldTime <3000){
          finish();
      }else{
          new CommonDialog(this)
                  .setMsg(getString(R.string.want_to_log_out))
                  .show();
      }
      oldTime =newTime;

        login();
        getAuchor();


    }

    private void getAuchor() {
        RetrofitFactory.getInstance()
                .getAnchorData(14)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean<AnchorBean>>() {
                    @Override
                    public void onNext(BaseBean<AnchorBean> baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=baseBean.getCode()) {
                            return;
                        }
                        AnchorBean.DataBean data = baseBean.getT().getData();
                    }
                });
    }

    private void login() {
        RetrofitFactory.getInstance()
                .login(ProxyPostHttpRequest.getInstance().login(1,2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean<LoginBean>>() {
                    @Override
                    public void onNext(BaseBean<LoginBean> baseBean) {
                        if (Contact.REPONSE_CODE_SUCCESS!=baseBean.getCode()) {
                            return;
                        }
                        LoginBean t = baseBean.getT();
                        String action = t.getData().getAction();
                        if (Contact.SIGN_UP.equals(action)) {
                            ToastUtils.showToast(MainActivity.this,"需要注册!");
                        }else if(Contact.SIGN_IN.equals(action)){
                            ToastUtils.showToast(MainActivity.this,"登录成功!");
                        }
                    }
                });
    }

}


