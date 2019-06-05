package com.hala.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hala.R;
import com.hala.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.ll_phone_num)
    LinearLayout llPhoneNum;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.et_zodiac)
    EditText etZodiac;
    @BindView(R.id.ll_zodiac)
    LinearLayout llZodiac;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.et_introction)
    EditText etIntroction;
    @BindView(R.id.ll_introction)
    LinearLayout llIntroction;
    @BindView(R.id.et_tags)
    EditText etTags;
    @BindView(R.id.ll_tags)
    LinearLayout llTags;
    @BindView(R.id.et_bio)
    EditText etBio;
    @BindView(R.id.ll_bio)
    LinearLayout llBio;
    @BindView(R.id.et_certified)
    EditText etCertified;
    @BindView(R.id.ll_certified)
    LinearLayout llCertified;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_pro;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {

    }

   

    @OnClick({R.id.ll_username, R.id.ll_phone_num, R.id.ll_height, R.id.ll_weight, R.id.ll_zodiac, R.id.ll_city, R.id.ll_introction, R.id.ll_tags, R.id.ll_bio, R.id.ll_certified})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_username:
                break;
            case R.id.ll_phone_num:
                break;
            case R.id.ll_height:
                break;
            case R.id.ll_weight:
                break;
            case R.id.ll_zodiac:
                break;
            case R.id.ll_city:
                break;
            case R.id.ll_introction:
                break;
            case R.id.ll_tags:
                break;
            case R.id.ll_bio:
                break;
            case R.id.ll_certified:
                break;
        }
    }
}
