package com.hala.base;

import android.util.Log;

import com.hala.avchat.AvchatInfo;
import com.hala.bean.RtmTokenBean;
import com.hala.http.BaseCosumer;
import com.hala.http.RetrofitFactory;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppLoginManager {

    public static void loginRtm(){
        RetrofitFactory.getInstance()
                .getRtmToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<RtmTokenBean>() {
                    @Override
                    public void onNext(RtmTokenBean rtmTokenBean) {
                        if (rtmTokenBean.getCode()!= Contact.REPONSE_CODE_SUCCESS) {
                            return;
                        }
                        String token = rtmTokenBean.getData().getAgora_rtm_token();
                        App.getChatManager().getRtmClient().login(AvchatInfo.getAccount()+"",token,new ResultCallback<Void>() {
                            @Override
                            public void onSuccess(Void responseInfo) {
                            }
                            @Override
                            public void onFailure(ErrorInfo errorInfo) {
                            }
                        });
                    }
                });
    }
}
