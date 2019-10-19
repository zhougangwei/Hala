package chat.hala.hala.activity;

import android.text.TextUtils;
import android.view.MotionEvent;

import com.faceunity.FURenderer;

import chat.hala.hala.R;
import chat.hala.hala.agore.entity.BeautyParameterModel;
import chat.hala.hala.agore.ui.control.BeautyControlView;
import chat.hala.hala.bean.BeautyBean;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.SPUtil;
import chat.hala.hala.utils.StatusbarUtils;

/**
 * 美颜界面
 * Created by tujh on 2018/1/31.
 */

public class FUBeautyActivity extends FUBaseActivity {
    public final static String TAG = FUBeautyActivity.class.getSimpleName();
    private BeautyControlView mBeautyControlView;



    @Override
    protected void onCreate() {
        StatusbarUtils.setBlackTextBar(this);
        mBottomViewStub.setLayoutResource(R.layout.layout_fu_beauty);
        mBeautyControlView = (BeautyControlView) mBottomViewStub.inflate();
        mBeautyControlView.setOnFUControlListener(mFURenderer);
        mBeautyControlView.setOnBottomAnimatorChangeListener(new BeautyControlView.OnBottomAnimatorChangeListener() {
            private int px166 = getResources().getDimensionPixelSize(R.dimen.x160);
            private int px156 = getResources().getDimensionPixelSize(R.dimen.x156);
            private int px402 = getResources().getDimensionPixelSize(R.dimen.x402);
            private int diff = px402 - px156;

            @Override
            public void onBottomAnimatorChangeListener(float showRate) {
            }
        });
        mBeautyControlView.setOnDescriptionShowListener(new BeautyControlView.OnDescriptionShowListener() {
            @Override
            public void onDescriptionShowListener(int str) {
                showDescription(str, 1000);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBeautyControlView.isShown()) {
            mBeautyControlView.hideBottomLayoutAnimator();
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected FURenderer initFURenderer() {
        return new FURenderer
                .Builder(this)
                .maxFaces(4)
                .inputTextureType(FURenderer.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE)
                .setOnFUDebugListener(this)
                .setOnTrackingStatusChangedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBeautyControlView != null) {
            mBeautyControlView.onResume();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BeautyBean beautyBean = new BeautyBean();
        beautyBean.setmFilterLevel(FURenderer.mFilterLevel);
        beautyBean.setmSkinDetect(FURenderer.mSkinDetect);
        beautyBean.setmBlurLevel(FURenderer.mBlurLevel);
        beautyBean.setmBlurType(FURenderer.mBlurType);
        beautyBean.setmColorLevel(FURenderer.mColorLevel);
        beautyBean.setmRedLevel(FURenderer.mRedLevel);
        beautyBean.setmEyeBright(FURenderer.mEyeBright);
        beautyBean.setmToothWhiten(FURenderer.mToothWhiten);
        beautyBean.setmFaceShape(FURenderer.mFaceShape);
        beautyBean.setmFaceShapeLevel(FURenderer.mFaceShapeLevel);
        beautyBean.setmCheekThinning(FURenderer.mCheekThinning);
        beautyBean.setmCheekV(FURenderer.mCheekV);
        beautyBean.setmCheekNarrow(FURenderer.mCheekNarrow);
        beautyBean.setmCheekSmall(FURenderer.mCheekSmall);
        beautyBean.setmEyeEnlarging(FURenderer.mEyeEnlarging);
        beautyBean.setmIntensityChin(FURenderer.mIntensityChin);
        beautyBean.setmIntensityForehead(FURenderer.mIntensityForehead);
        beautyBean.setmIntensityMouth(FURenderer.mIntensityMouth);
        beautyBean.setmIntensityNose(FURenderer.mIntensityNose);
        beautyBean.setBatchPosition(BeautyParameterModel.BatchPosition);
        beautyBean.setFilterPostion(BeautyParameterModel.FilterPostion);
        String s = GsonUtil.parseObjectToJson(beautyBean);
        SPUtil.getInstance(this).saveBeauty(s);

    }
}
