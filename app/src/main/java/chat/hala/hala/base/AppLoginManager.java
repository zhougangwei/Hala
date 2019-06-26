/*
package com.hala.base;

import android.util.Log;

import OneToOneActivity;
import AvchatInfo;
import RtmTokenBean;
import BaseCosumer;
import RetrofitFactory;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClientListener;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmStatusCode;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppLoginManager {

    public volatile static MyRtmClientListener calllistener;
    private static String TAG="AppLoginManager";

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
                        AvchatInfo.setRTMToken(token);
                        App.getChatManager().getRtmClient().login(token,AvchatInfo.getAccount()+"",new ResultCallback<Void>() {
                            @Override
                            public void onSuccess(Void responseInfo) {
                                Log.e("Applogin","登录成功");
                                if (calllistener==null) {
                                    synchronized (this){
                                        calllistener = new MyRtmClientListener();
                                    }
                                }
                                App.getChatManager().registerListener(calllistener);
                            }
                            @Override
                            public void onFailure(ErrorInfo errorInfo) {
                                Log.e("Applogin",errorInfo.getErrorDescription()+"--"+errorInfo.getErrorCode());
                            }
                        });
                    }
                });
    }

    static class MyRtmClientListener implements RtmClientListener {



        @Override
        public void onConnectionStateChanged(final int state, int reason) {
        }

        @Override
        public void onMessageReceived(final RtmMessage message, final String peerId) {
            String text = message.getText();
            Log.e(TAG, "message" + message + "peerId" + peerId);
            if (text.contains(Contact.RTM_DO_CALL_STRING)) {
                OneToOneActivity.doReceivveOneToOneActivity(App.sContext, Integer.parseInt(peerId));
            }
        }

        @Override
        public void onTokenExpired() {

        }
    }


}
*/
