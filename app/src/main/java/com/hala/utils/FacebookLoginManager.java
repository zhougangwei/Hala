package com.hala.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hala.MainActivity;
import com.hala.R;
import com.hala.activity.EditProUserActivity;
import com.hala.activity.LoginActivity;
import com.hala.activity.LoginPhoneActivity;
import com.hala.base.BaseActivity;
import com.hala.base.Contact;
import com.hala.bean.BaseBean;
import com.hala.bean.LoginBean;
import com.hala.http.BaseCosumer;
import com.hala.http.ProxyPostHttpRequest;
import com.hala.http.RetrofitFactory;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FacebookLoginManager {


    private AccessToken accessToken;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private static final String TAG="FacebookLoginManager";
    public OnResult mOnResult;

    public void init(){
        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();
    }

    public void loginFaceBook(final BaseActivity activity, OnResult onResult) {
        mOnResult=onResult;
        //清空登录状态
        if (accessToken != null) {
            signOutFaceBook();
        }
        //判断是否登录
        if (isLoggedIn()) {
            return;
        }
        // , "user_photos", "user_location", "email", "user_status"
        List permissions = Arrays.asList("public_profile");
        getLoginManager().logInWithReadPermissions(activity, permissions);
        getLoginManager().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.e(TAG, "onSuccess = " + "token: " + loginResult.getAccessToken().getToken() +
                        ", id = " + loginResult.getAccessToken().getUserId());

                accessToken = loginResult.getAccessToken();

                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest
                        .GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //获取登录成功之后的用户详细信息
                        if (object != null) {
                            final String id = object.optString("id");   //比如:107274440068470
                            String name = object.optString("name");  //比如：Yang ShouLe

                            String gender = object.optString("gender");  //性别：比如 male （男）  female （女）
                            String emali = object.optString("email");  //邮箱：ysle0313@gmail.com
                            //获取用户头像
                            JSONObject object_pic = object.optJSONObject("picture");
                            JSONObject object_data = object_pic.optJSONObject("data");
                            String photo = object_data.optString("url");

                            //获取地域信息
                            String locale = object.optString("locale");   //zh_CN 代表中文简体

                            Log.e(TAG, "onCompleted: " + object.toString() + "---" + photo + "---" + locale + "---" +
                                    name);
                            String token = loginResult.getAccessToken().getToken();
                            int sex = 0;
                            if (gender.equals("male")) {
                                sex = 1;
                            } else if (gender.equals("female")) {
                                sex = 0;
                            } else {
                                sex = 2;
                            }
                            RetrofitFactory.getInstance()
                                    .loginFacebook(ProxyPostHttpRequest.getInstance().loginFacebook(token))
                                    .compose(activity.<LoginBean>bindToLifecycle())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseCosumer<LoginBean>() {
                                        @Override
                                        public void onNext(LoginBean baseBean) {
                                            if (Contact.REPONSE_CODE_SUCCESS != baseBean.getCode()) {
                                                mOnResult.fail();
                                                return;
                                            }

                                            String action = baseBean.getData().getAction();
                                            if (Contact.SIGN_UP.equals(action)) {
                                                mOnResult.regist(id);

                                            } else if (Contact.SIGN_IN.equals(action)) {

                                                mOnResult.success();

                                            }
                                        }
                                    });

                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time," +
                        "timezone,age_range,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                mOnResult.fail();
                Log.e(TAG, "e: " + e);
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取loginMananger
     *
     * @return
     */
    private LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    /**
     * 退出
     */
    private void signOutFaceBook() {
        getLoginManager().logOut();
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    public interface OnResult{
        void success();
        void fail();
        void regist(String id);
    }
}
