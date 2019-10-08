package chat.hala.hala.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.blankj.utilcode.utils.LogUtils;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chat.hala.hala.R;
import chat.hala.hala.avchat.QiniuInfo;
import chat.hala.hala.base.BaseActivity;
import chat.hala.hala.bean.QiNiuToken;
import chat.hala.hala.http.UploadPicManger;
import chat.hala.hala.manager.ChoosePicManager;
import chat.hala.hala.utils.ToastUtils;

public class VideoVerityActivity extends BaseActivity {


    private static final String TAG = "VideoVerityActivity" ;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.video)
    VideoView video;
    @BindView(R.id.ic_play)
    ImageView icPlay;



    @BindView(R.id.fl_video)
    FrameLayout flVideo;
    ArrayList uriList = new ArrayList();
    private boolean clickUp;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_video_verity;
    }

    @Override
    protected void beforeInitView() {

    }

    @Override
    protected void initView() {
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                icPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_video, R.id.video,R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_video:
                ChoosePicManager.chooseVideo(this,1);
                break;
            case R.id.video:
                if (!video.isPlaying()) {
                    video.start();
                    icPlay.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_submit:
                upQiniu();
                ToastUtils.showToast(this,"正在上传!");
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ChoosePicManager.REQUEST_CODE_CHOOSE) {
                List<String> strings = Matisse.obtainPathResult(data);
                video.setVideoPath(strings.get(0));
                uriList.add(strings.get(0));
                flVideo.setVisibility(View.VISIBLE);
                ivVideo.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void upQiniu() {
        if(clickUp){
            return;
        }
        clickUp =true;
        QiNiuToken.DataBean.StarchatanchorBean starchatanchorBean = QiniuInfo.getmStarchatanchorBean();
        if (starchatanchorBean == null) {
            return;
        }
        new UploadPicManger().uploadImageArray(uriList, 0, starchatanchorBean.getToken(), starchatanchorBean.getUrl(), new UploadPicManger.QiNiuUploadCompletionHandler() {
            @Override
            public void uploadSuccess(String path, List<String> paths) {
                try {
                    clickUp=false;
                    Intent intent = new Intent();
                    intent.putExtra("videoUrl", paths.get(0));
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    ToastUtils.showToast(VideoVerityActivity.this, "上传失败,请重新上传");
                }
            }

            @Override
            public void uploadFailure() {
                clickUp=false;
                // TODO: 2019/6/25 0025 上传图片失败
                LogUtils.e(TAG, "uploadFailure: 失败");
            }
        });
    }


}
