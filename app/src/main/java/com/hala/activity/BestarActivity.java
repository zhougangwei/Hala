package com.hala.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hala.R;
import com.hala.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BestarActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_start)
    TextView tvStart;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bestar;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        ivBack.setImageResource(R.mipmap.ic_close);
        tvTitle.setText(R.string.start_certified);
    }


    @OnClick({R.id.iv_back, R.id.tv_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_start:
                break;
        }
    }
}
