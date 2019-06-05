package com.hala.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hala.R;
import com.hala.base.BaseActivity;

public class VideoCallActivity extends BaseActivity {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_call;
    }
    @Override
    protected void beforeInitView() {
    }

    @Override
    protected void initView() {

    }

    public static void startVideoCallAc(Context context,int anchorId){
        Intent intent = new Intent(context,VideoCallActivity.class);
        intent.putExtra("anchorId",anchorId);
        context.startActivity(intent);

    }

}
