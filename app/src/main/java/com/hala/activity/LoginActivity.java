package com.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hala.R;
import com.hala.base.BaseActivity;

import butterknife.BindView;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ll_face_login)
    LinearLayout llFaceLogin;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_policy)
    TextView tvPolicy;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }



    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_face_login, R.id.ll_phone, R.id.tv_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_face_login:
                startFaceLogin();
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

    private void startFaceLogin() {

    }
}
