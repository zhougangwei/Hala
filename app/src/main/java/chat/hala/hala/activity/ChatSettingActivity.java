package chat.hala.hala.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.base.BaseActivity;

public class ChatSettingActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView  mTvTitle;
    @BindView(R.id.iv_voice_set)
    ImageView mIvVoiceSet;
    @BindView(R.id.iv_video_set)
    ImageView mIvVideoSet;
    @BindView(R.id.iv_chat_set)
    ImageView mIvChatSet;


    @BindView(R.id.rl_video)
    RelativeLayout mRlVideo;
    @BindView(R.id.rl_voice)
    RelativeLayout mRlVoice;
    @BindView(R.id.rl_chat)
    RelativeLayout mRlChat;

    @BindView(R.id.tv_voice_coin)
    TextView mTvVoiceCoin;
    @BindView(R.id.tv_video_coin)
    TextView mTvVideoCoin;
    @BindView(R.id.tv_chat_coin)
    TextView mTvChatCoin;


    public boolean mVoiceOpen;
    public boolean mVideoOpen;
    public boolean mChatOpen;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_chat_setting;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        mTvTitle.setText(R.string.chat_setting);
        mIvVoiceSet.setSelected(mVoiceOpen);
        mIvVideoSet.setSelected(mVideoOpen);
        mIvChatSet.setSelected(mChatOpen);
    }

    @OnClick({R.id.iv_back, R.id.iv_voice_set, R.id.iv_video_set, R.id.iv_chat_set,R.id.rl_video, R.id.rl_voice, R.id.rl_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_voice_set:
                mVoiceOpen = !mVoiceOpen;
                mIvVoiceSet.setSelected(mVoiceOpen);
                break;
            case R.id.iv_video_set:
                mVideoOpen = !mVideoOpen;
                mIvVideoSet.setSelected(mVideoOpen);
                break;
            case R.id.iv_chat_set:
                mChatOpen = !mChatOpen;
                mIvChatSet.setSelected(mChatOpen);
                break;
            case R.id.rl_video:
                break;
            case R.id.rl_voice:
                break;
            case R.id.rl_chat:
                break;
        }
    }

}
