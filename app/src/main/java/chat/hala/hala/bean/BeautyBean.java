package chat.hala.hala.bean;

import com.faceunity.param.BeautificationParam;

/**
 * @author wjy on 2019/10/19/019.
 */
public class BeautyBean {
    private float mFilterLevel = 1.0f;//滤镜强度
    private float mSkinDetect = 1.0f;//肤色检测开关
    private float mBlurLevel = 0.7f;//磨皮程度
    private float mBlurType = 0.0f;//磨皮类型
    private float mColorLevel = 0.3f;//美白
    private float mRedLevel = 0.3f;//红润
    private float mEyeBright = 0.0f;//亮眼
    private float mToothWhiten = 0.0f;//美牙
    private float mFaceShape = BeautificationParam.FACE_SHAPE_CUSTOM;//脸型
    private float mFaceShapeLevel = 1.0f;//程度
    private float mCheekThinning = 0f;//瘦脸
    private float mCheekV = 0.5f;//V脸
    private float mCheekNarrow = 0f;//窄脸
    private float mCheekSmall = 0f;//小脸
    private float mEyeEnlarging = 0.4f;//大眼
    private float mIntensityChin = 0.3f;//下巴
    private float mIntensityForehead = 0.3f;//额头
    private float mIntensityMouth = 0.4f;//嘴形
    private float mIntensityNose = 0.5f;//瘦鼻
    private int batchPosition;
    private int filterPostion;

    public float getmFilterLevel() {
        return mFilterLevel;
    }

    public void setmFilterLevel(float mFilterLevel) {
        this.mFilterLevel = mFilterLevel;
    }

    public float getmSkinDetect() {
        return mSkinDetect;
    }

    public void setmSkinDetect(float mSkinDetect) {
        this.mSkinDetect = mSkinDetect;
    }

    public float getmBlurLevel() {
        return mBlurLevel;
    }

    public void setmBlurLevel(float mBlurLevel) {
        this.mBlurLevel = mBlurLevel;
    }

    public float getmBlurType() {
        return mBlurType;
    }

    public void setmBlurType(float mBlurType) {
        this.mBlurType = mBlurType;
    }

    public float getmColorLevel() {
        return mColorLevel;
    }

    public void setmColorLevel(float mColorLevel) {
        this.mColorLevel = mColorLevel;
    }

    public float getmRedLevel() {
        return mRedLevel;
    }

    public void setmRedLevel(float mRedLevel) {
        this.mRedLevel = mRedLevel;
    }

    public float getmEyeBright() {
        return mEyeBright;
    }

    public void setmEyeBright(float mEyeBright) {
        this.mEyeBright = mEyeBright;
    }

    public float getmToothWhiten() {
        return mToothWhiten;
    }

    public void setmToothWhiten(float mToothWhiten) {
        this.mToothWhiten = mToothWhiten;
    }

    public float getmFaceShape() {
        return mFaceShape;
    }

    public void setmFaceShape(float mFaceShape) {
        this.mFaceShape = mFaceShape;
    }

    public float getmFaceShapeLevel() {
        return mFaceShapeLevel;
    }

    public void setmFaceShapeLevel(float mFaceShapeLevel) {
        this.mFaceShapeLevel = mFaceShapeLevel;
    }

    public float getmCheekThinning() {
        return mCheekThinning;
    }

    public void setmCheekThinning(float mCheekThinning) {
        this.mCheekThinning = mCheekThinning;
    }

    public float getmCheekV() {
        return mCheekV;
    }

    public void setmCheekV(float mCheekV) {
        this.mCheekV = mCheekV;
    }

    public float getmCheekNarrow() {
        return mCheekNarrow;
    }

    public void setmCheekNarrow(float mCheekNarrow) {
        this.mCheekNarrow = mCheekNarrow;
    }

    public float getmCheekSmall() {
        return mCheekSmall;
    }

    public void setmCheekSmall(float mCheekSmall) {
        this.mCheekSmall = mCheekSmall;
    }

    public float getmEyeEnlarging() {
        return mEyeEnlarging;
    }

    public void setmEyeEnlarging(float mEyeEnlarging) {
        this.mEyeEnlarging = mEyeEnlarging;
    }

    public float getmIntensityChin() {
        return mIntensityChin;
    }

    public void setmIntensityChin(float mIntensityChin) {
        this.mIntensityChin = mIntensityChin;
    }

    public float getmIntensityForehead() {
        return mIntensityForehead;
    }

    public void setmIntensityForehead(float mIntensityForehead) {
        this.mIntensityForehead = mIntensityForehead;
    }

    public float getmIntensityMouth() {
        return mIntensityMouth;
    }

    public void setmIntensityMouth(float mIntensityMouth) {
        this.mIntensityMouth = mIntensityMouth;
    }

    public float getmIntensityNose() {
        return mIntensityNose;
    }

    public void setmIntensityNose(float mIntensityNose) {
        this.mIntensityNose = mIntensityNose;
    }

    public int getBatchPosition() {
        return batchPosition;
    }

    public void setBatchPosition(int batchPosition) {
        this.batchPosition = batchPosition;
    }

    public void setFilterPostion(int filterPostion) {
        this.filterPostion = filterPostion;
    }

    public int getFilterPostion() {
        return filterPostion;
    }
}
