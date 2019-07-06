package chat.hala.hala.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import jp.wasabeef.glide.transformations.BlurTransformation;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.R;
import chat.hala.hala.wight.RatingBarView;


import butterknife.BindView;

import butterknife.OnClick;

public class VideoFinishActivity extends BaseActivity {


    @BindView(R.id.iv_bg)
    ImageView        mIvBg;
    @BindView(R.id.iv_head)
    ImageView        mIvHead;
    @BindView(R.id.tv_name)
    TextView         mTvName;
    @BindView(R.id.rbv)
    RatingBarView    mRbv;
    @BindView(R.id.cl)
    ConstraintLayout mCl;
    @BindView(R.id.tv_time)
    TextView         mTvTime;
    @BindView(R.id.tv_cost)
    TextView         mTvCost;
    @BindView(R.id.tv_ok)
    TextView         mTvOk;


    private String mName;
    private String mTime;
    private String mCost;
    private int    starLevel;
    private String anchorUrl;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_finish;
    }

    @Override
    protected void beforeInitView() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mTime = intent.getStringExtra("time");
        mCost = intent.getStringExtra("cost");
        anchorUrl = intent.getStringExtra("anchorUrl");
        starLevel = intent.getIntExtra("starLevel", 0);

    }

    @Override
    protected void initView() {
        mTvName.setText(mName);
        mRbv.setStar(starLevel, false);
        mTvCost.setText(String.format(getString(R.string.fee_100_coins), mCost + ""));
        mTvTime.setText(String.format(getString(R.string.call_duration_10_mins), mTime + ""));
        Glide.with(VideoFinishActivity.this)
                .load(anchorUrl)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)).placeholder(mIvHead.getDrawable()))
                .into(mIvHead);

    }

    @OnClick(R.id.tv_ok)
    public void onClick() {
        finish();
    }
}
