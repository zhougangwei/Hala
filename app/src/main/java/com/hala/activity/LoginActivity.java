package com.hala.activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hala.R;
import com.hala.base.BaseActivity;
import com.hala.utils.ToastUtils;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.ll_face_login)
    LinearLayout llFaceLogin;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_policy)
    TextView tvPolicy;
    private AccessToken accessToken;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }



    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        initFaceBook();

    }

    private void initFaceBook() {
        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();
    }

    @OnClick({R.id.ll_face_login, R.id.ll_phone, R.id.tv_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_face_login:
                loginFaceBook();
                break;
            case R.id.ll_phone:
                startLoginPhone();
                break;
            case R.id.tv_policy:
                alertPloicu();
                break;
        }
    }

    private void alertPloicu() {


    }


    private void startLoginPhone() {
        startActivity(new Intent(this,LoginPhoneActivity.class));
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

    private void loginFaceBook() {
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
        getLoginManager().logInWithReadPermissions(this, permissions);
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
                            String id = object.optString("id");   //比如:107274440068470
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
                ToastUtils.showToast(LoginActivity.this, getString(R.string.login_failed));
                Log.e(TAG, "e: " + e);
                e.printStackTrace();
            }
        });
    }
}
