package com.hala;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hala.adapter.TabAdapter;
import com.hala.base.BaseActivity;
import com.hala.dialog.CommonDialog;
import com.hala.wight.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    private long oldTime;
    private TabAdapter mTabAdapter;

    List<View> viewList = new ArrayList<>();
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        vp.setAdapter(mTabAdapter);
        vp .setOffscreenPageLimit(5);
        vp.setCurrentItem(0, false);
        viewList.add(ivHome);
        viewList.add(ivMsg);
        viewList.add(ivMy);

    }


    @Override
    public void onBackPressed() {
        long newTime = System.currentTimeMillis();
        if (newTime - oldTime < 3000) {
            finish();
        } else {
            new CommonDialog(this)
                    .setMsg(getString(R.string.want_to_log_out))
                    .show();
        }
        oldTime = newTime;

        login();


    }


    private void login() {

    }





    @OnClick({R.id.iv_home, R.id.iv_msg, R.id.iv_my})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                setClicked(ivHome);
                break;
            case R.id.iv_msg:
                setClicked(ivMsg);
                break;
            case R.id.iv_my:
                setClicked(ivMy);
                break;
        }
    }

    private void setClicked(ImageView clickView) {
        for (int i = 0; i < viewList.size(); i++) {
            View view=viewList.get(i);
            if (view!=clickView) {
                view.setSelected(false);
            }else{
                view.setSelected(true);
                vp.setCurrentItem(i, false);
            }

        }
    }
}


