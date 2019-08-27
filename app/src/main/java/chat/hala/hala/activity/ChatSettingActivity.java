package chat.hala.hala.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.ChatSettingBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.ResultUtils;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatSettingActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
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
    @BindView(R.id.ll)
    LinearLayout mLl;

    public boolean mVoiceOpen;
    public boolean mVideoOpen;
    public boolean mChatOpen;
    private String audioCpm;
    private String chatCmp;
    private String videoCpm;

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
        if (AvchatInfo.isAnchor()) {
            mLl.setVisibility(View.VISIBLE);
        }
        initBackData();
        mIvVoiceSet.setSelected(mVoiceOpen);
        mIvVideoSet.setSelected(mVideoOpen);
        mIvChatSet.setSelected(mChatOpen);

        mTvVideoCoin.setText(String.format(getString(R.string.coin_min), videoCpm));
        mTvVoiceCoin.setText(String.format(getString(R.string.coin_min), audioCpm));
        mTvChatCoin.setText(String.format(getString(R.string.coin_min), chatCmp));

    }

    private void initBackData() {
        mVoiceOpen = AvchatInfo.getAudioNotify();
        mVideoOpen = AvchatInfo.getVideoNotify();
        mChatOpen = AvchatInfo.getChatNotify();
        audioCpm = AvchatInfo.getAudioCpm() + "";
        chatCmp = AvchatInfo.getChatCpm() + "";
        videoCpm = AvchatInfo.getVideoCpm() + "";

    }

    @OnClick({R.id.iv_back, R.id.iv_voice_set, R.id.iv_video_set, R.id.iv_chat_set, R.id.rl_video, R.id.rl_voice, R.id.rl_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_voice_set:
                mVoiceOpen = !mVoiceOpen;
                mIvVoiceSet.setSelected(mVoiceOpen);
                gotoSave();
                break;
            case R.id.iv_video_set:
                mVideoOpen = !mVideoOpen;
                mIvVideoSet.setSelected(mVideoOpen);
                gotoSave();
                break;
            case R.id.iv_chat_set:
                mChatOpen = !mChatOpen;
                mIvChatSet.setSelected(mChatOpen);
                gotoSave();
                break;
            case R.id.rl_video:
                choseVideoCpm();

                break;
            case R.id.rl_voice:
                choseVoiceCpm();

                break;
            case R.id.rl_chat:
                choseChatCpm();

                break;
        }
    }

    private void choseChatCpm() {
        String[] sexArr = new String[]{"0", "20", "40", "60"};
        List<String> data = Arrays.asList(sexArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(17);
        picker.setTextPadding(10);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                chatCmp = item;
                mTvChatCoin.setText(String.format(getString(R.string.coin_min), item));
                gotoSave();
            }
        });
        picker.show();
    }

    private void choseVoiceCpm() {
        String[] sexArr = new String[]{"0", "100", "150", "200"};
        List<String> data = Arrays.asList(sexArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(17);
        picker.setTextPadding(10);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                audioCpm = item;
                mTvVoiceCoin.setText(String.format(getString(R.string.coin_min), item));
                gotoSave();
            }
        });
        picker.show();
    }

    private void choseVideoCpm() {
        String[] sexArr = new String[]{"0", "100", "150", "200"};
        List<String> data = Arrays.asList(sexArr);
        SinglePicker<String> picker = new SinglePicker<String>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(17);
        picker.setTextPadding(10);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                videoCpm = item;
                mTvVideoCoin.setText(String.format(getString(R.string.coin_min), item));
                gotoSave();
            }
        });
        picker.show();

    }

    private void gotoSave() {



        ChatSettingBean chatSettingBean = new ChatSettingBean();
        chatSettingBean.setAudioCpm(audioCpm);
        chatSettingBean.setChatCpm(chatCmp);
        chatSettingBean.setVideoCpm(videoCpm);
        chatSettingBean.setVideoNotify(mVideoOpen);
        chatSettingBean.setAudioNotify(mVoiceOpen);
        chatSettingBean.setChatNotify(mChatOpen);
        RetrofitFactory.getInstance()
                .chatSetting(AvchatInfo.isAnchor()?"anchor":"member",ProxyPostHttpRequest.getJsonInstance().chatSetting(GsonUtil.parseObjectToJson(chatSettingBean)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseCosumer<BaseBean>() {
                    @Override
                    public void onGetData(BaseBean baseBean) {
                        if (ResultUtils.cheekSuccess(baseBean)) {
                            AvchatInfo.setAudioCpm(audioCpm);
                            AvchatInfo.setVideoCpm(videoCpm);
                            AvchatInfo.setChatCpm(chatCmp);
                            AvchatInfo.setVideoNotify(mVideoOpen);
                            AvchatInfo.setAudioNotify(mVoiceOpen);
                            AvchatInfo.setChatNotify(mChatOpen);
                        }
                    }
                });
    }


}
