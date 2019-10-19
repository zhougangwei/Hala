package chat.hala.hala.agore.ui.control;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.faceunity.OnFUControlListener;
import com.faceunity.entity.FaceMakeup;
import com.faceunity.entity.Filter;
import com.faceunity.entity.MakeupItem;


import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.agore.OnMultiClickListener;
import chat.hala.hala.agore.SpaceItemDecoration;
import chat.hala.hala.agore.entity.BeautyParameterModel;
import chat.hala.hala.agore.entity.FaceMakeupEnum;
import chat.hala.hala.agore.entity.FilterEnum;
import chat.hala.hala.agore.ui.CheckGroup;
import chat.hala.hala.agore.ui.adapter.BaseRecyclerAdapter;
import chat.hala.hala.agore.ui.beautybox.BaseBeautyBox;
import chat.hala.hala.agore.ui.beautybox.BeautyBox;
import chat.hala.hala.agore.ui.beautybox.BeautyBoxGroup;
import chat.hala.hala.agore.ui.dialog.BaseDialogFragment;
import chat.hala.hala.agore.ui.dialog.ConfirmDialogFragment;
import chat.hala.hala.agore.ui.seekbar.DiscreteSeekBar;

import static chat.hala.hala.agore.entity.BeautyParameterModel.STR_FILTER_LEVEL;
import static chat.hala.hala.agore.entity.BeautyParameterModel.getValue;
import static chat.hala.hala.agore.entity.BeautyParameterModel.isOpen;
import static chat.hala.hala.agore.entity.BeautyParameterModel.sFilter;
import static chat.hala.hala.agore.entity.BeautyParameterModel.sFilterLevel;
import static chat.hala.hala.agore.entity.BeautyParameterModel.sSkinDetect;
import static chat.hala.hala.agore.entity.BeautyParameterModel.setValue;


/**
 * Created by tujh on 2017/8/15.
 */
public class BeautyControlView extends FrameLayout {
    private static final String TAG = "BeautyControlView";
    private Context mContext;

    private OnFUControlListener mOnFUControlListener;
    private RecyclerView mRvMakeupItems;

    public void setOnFUControlListener(@NonNull OnFUControlListener onFUControlListener) {
        mOnFUControlListener = onFUControlListener;
    }

    private CheckGroup mBottomCheckGroup;
    private FrameLayout mFlFaceSkinItems;
    private BeautyBoxGroup mSkinBeautyBoxGroup;
    private BeautyBoxGroup mFaceShapeBeautyBoxGroup;
    private FrameLayout mFlFaceShapeItems;
    private ImageView mIvRecoverFaceShape;
    private TextView mTvRecoverFaceShape;
    private ImageView mIvRecoverFaceSkin;
    private TextView mTvRecoverFaceSkin;
    private View mBottomView;
    private RecyclerView mFilterRecyclerView;
    private FilterRecyclerAdapter mFilterRecyclerAdapter;
    private RadioGroup mRgBlurType;
    private DiscreteSeekBar mBeautySeekBar;
    private FaceMakeupAdapter mFaceMakeupAdapter;
    private boolean isShown;
    private boolean mFirstShowLightMakeup = true;
    private List<Filter> mFilters;
    // 默认选中第三个粉嫩
    private int mFilterPositionSelect = BeautyParameterModel.FilterPostion;

    public BeautyControlView(Context context) {
        this(context, null);
    }

    public BeautyControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeautyControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mFilters = FilterEnum.getFiltersByFilterType();
        LayoutInflater.from(context).inflate(R.layout.layout_beauty_control, this);
        initView();
    }

    private void initView() {
        mBottomView = findViewById(R.id.cl_bottom_view);
        mBottomView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        initViewBottomRadio();
        initViewSkinBeauty();
        initViewFaceShape();
        initViewFilterRecycler();
        initMakeupView();
        initViewTop();
    }

    public void onResume() {
        updateViewSkinBeauty();
        updateViewFaceShape();
        updateViewFilterRecycler();
        hideBottomLayoutAnimator();
    }

    private void initMakeupView() {
        mRvMakeupItems = findViewById(R.id.rv_face_makeup);
        mRvMakeupItems.setHasFixedSize(true);
        ((SimpleItemAnimator) mRvMakeupItems.getItemAnimator()).setSupportsChangeAnimations(false);
        mRvMakeupItems.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRvMakeupItems.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.x15), 0));
        mFaceMakeupAdapter = new FaceMakeupAdapter(FaceMakeupEnum.getBeautyFaceMakeup());
        OnFaceMakeupClickListener onMpItemClickListener = new OnFaceMakeupClickListener();
        mFaceMakeupAdapter.setOnItemClickListener(onMpItemClickListener);
        mRvMakeupItems.setAdapter(mFaceMakeupAdapter);
        mFaceMakeupAdapter.setItemSelected(0);
    }

    @Override
    public boolean isShown() {
        return isShown;
    }

    private void initViewBottomRadio() {
        mBottomCheckGroup = findViewById(R.id.beauty_radio_group);
        mBottomCheckGroup.setOnCheckedChangeListener(new CheckGroup.OnCheckedChangeListener() {
            int checkedidOld = View.NO_ID;

            @Override
            public void onCheckedChanged(CheckGroup group, int checkedId) {
                clickViewBottomRadio(checkedId);
                if ((checkedId == View.NO_ID || checkedId == checkedidOld) && checkedidOld != View.NO_ID) {
                    int endHeight = (int) getResources().getDimension(R.dimen.x1);
                    int startHeight = (int) getResources().getDimension(R.dimen.x268);
                    changeBottomLayoutAnimator(startHeight, endHeight);
                    mRgBlurType.setVisibility(INVISIBLE);
                    isShown = false;
                } else if (checkedId != View.NO_ID && checkedidOld == View.NO_ID) {
                    int startHeight = (int) getResources().getDimension(R.dimen.x1);
                    int endHeight = (int) getResources().getDimension(R.dimen.x268);
                    changeBottomLayoutAnimator(startHeight, endHeight);
                    if (checkedId == R.id.beauty_radio_skin_beauty && mSkinBeautyBoxGroup.getCheckedBeautyBoxId() == R.id.beauty_box_blur_level) {
                        mRgBlurType.setVisibility(VISIBLE);
                        seekToSeekBar(getValue(mRgBlurType.getCheckedRadioButtonId()));
                    }
                    isShown = true;
                }
                checkedidOld = checkedId;
            }
        });
    }

    private void updateViewSkinBeauty() {
        onChangeFaceBeautyLevel(R.id.beauty_box_skin_detect);
        onChangeFaceBeautyLevel(R.id.beauty_box_color_level);
        onChangeFaceBeautyLevel(R.id.beauty_box_blur_level);
        onChangeFaceBeautyLevel(R.id.beauty_box_red_level);
        onChangeFaceBeautyLevel(R.id.beauty_box_eye_bright);
        onChangeFaceBeautyLevel(R.id.beauty_box_tooth_whiten);
        onChangeFaceBeautyLevel(mRgBlurType.getCheckedRadioButtonId());
    }

    private void initViewSkinBeauty() {
        mFlFaceSkinItems = findViewById(R.id.fl_face_skin_items);
        mRgBlurType = findViewById(R.id.rg_blur_type);
        mRgBlurType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                seekToSeekBar(getValue(checkedId));
                int type = 0;
                int desc = R.string.beauty_box_heavy_blur_clear;
                if (checkedId == R.id.rb_blur_fine) {
                    type = 2;
                    desc = R.string.beauty_box_heavy_blur_fine;
                } else if (checkedId == R.id.rb_blur_hazy) {
                    type = 1;
                    desc = R.string.beauty_box_heavy_blur_hazy;
                }
                BeautyParameterModel.sBlurType = type;
               // ToastUtil.showNormalToast(mContext, desc);
                checkFaceSkinChanged();
                ((BeautyBox) findViewById(R.id.beauty_box_blur_level)).setOpen(isOpen(checkedId));
               // mOnFUControlListener.onBlurTypeSelected(type);
                mOnFUControlListener.onBlurLevelSelected(getValue(checkedId));
            }
        });
        mIvRecoverFaceSkin = findViewById(R.id.iv_recover_face_skin);
        mIvRecoverFaceSkin.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                ConfirmDialogFragment confirmDialogFragment = ConfirmDialogFragment.newInstance(mContext.getString(R.string.dialog_reset_avatar_model), new BaseDialogFragment.OnClickListener() {
                    @Override
                    public void onConfirm() {
                        // recover params
                        BeautyParameterModel.recoverFaceSkinToDefValue();
                        mRgBlurType.check(R.id.rb_blur_clear);
                        updateViewSkinBeauty();
                        int checkedId = mSkinBeautyBoxGroup.getCheckedBeautyBoxId();
                        if (checkedId == R.id.beauty_box_blur_level) {
                            checkedId = mRgBlurType.getCheckedRadioButtonId();
                        }
                        if (checkedId != R.id.beauty_box_skin_detect) {
                            seekToSeekBar(checkedId);
                        }
                        setRecoverFaceSkinEnable(false);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                confirmDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "ConfirmDialogFragmentReset");
            }
        });
        mTvRecoverFaceSkin = findViewById(R.id.tv_recover_face_skin);

        mSkinBeautyBoxGroup = findViewById(R.id.beauty_group_skin_beauty);
        mSkinBeautyBoxGroup.setOnCheckedChangeListener(new BeautyBoxGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BeautyBoxGroup group, int checkedId) {
                mBeautySeekBar.setVisibility(INVISIBLE);
                if (checkedId == R.id.beauty_box_blur_level) {
                    mRgBlurType.setVisibility(VISIBLE);
                    onChangeFaceBeautyLevel(R.id.rb_blur_fine);
                    onChangeFaceBeautyLevel(R.id.rb_blur_hazy);
                    onChangeFaceBeautyLevel(R.id.rb_blur_clear);
                } else {
                    mRgBlurType.setVisibility(INVISIBLE);
                }
                if (checkedId != R.id.beauty_box_skin_detect) {
                    if (checkedId == R.id.beauty_box_blur_level) {
                        checkedId = mRgBlurType.getCheckedRadioButtonId();
                    }
                    seekToSeekBar(checkedId);
                    onChangeFaceBeautyLevel(checkedId);
                }
            }
        });
        BeautyBox boxSkinDetect = findViewById(R.id.beauty_box_skin_detect);
        boxSkinDetect.setOnOpenChangeListener(new BaseBeautyBox.OnOpenChangeListener() {
            @Override
            public void onOpenChanged(BaseBeautyBox beautyBox, boolean isOpen) {
                sSkinDetect = isOpen ? 1 : 0;
                setDescriptionShowStr(sSkinDetect == 0 ? R.string.beauty_box_skin_detect_close : R.string.beauty_box_skin_detect_open);
                onChangeFaceBeautyLevel(R.id.beauty_box_skin_detect);
                checkFaceSkinChanged();
            }
        });
        checkFaceSkinChanged();
    }

    private void updateViewFaceShape() {
        onChangeFaceBeautyLevel(R.id.beauty_box_eye_enlarge);
        onChangeFaceBeautyLevel(R.id.beauty_box_cheek_thinning);
        onChangeFaceBeautyLevel(R.id.beauty_box_cheek_v);
        onChangeFaceBeautyLevel(R.id.beauty_box_cheek_narrow);
        onChangeFaceBeautyLevel(R.id.beauty_box_cheek_small);
        onChangeFaceBeautyLevel(R.id.beauty_box_intensity_chin);
        onChangeFaceBeautyLevel(R.id.beauty_box_intensity_forehead);
        onChangeFaceBeautyLevel(R.id.beauty_box_intensity_nose);
        onChangeFaceBeautyLevel(R.id.beauty_box_intensity_mouth);
    }

    private void initViewFilterRecycler() {
        mFilterRecyclerView = findViewById(R.id.filter_recycle_view);
        mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mFilterRecyclerView.setAdapter(mFilterRecyclerAdapter = new FilterRecyclerAdapter());
        ((SimpleItemAnimator) mFilterRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void updateViewFilterRecycler() {
        mFilterRecyclerAdapter.setFilter(sFilter);
        mOnFUControlListener.onFilterNameSelected(sFilter.filterName());
        float filterLevel = getFilterLevel(sFilter.filterName());
        mOnFUControlListener.onFilterLevelSelected(filterLevel);
    }

    private void initViewFaceShape() {
        mFlFaceShapeItems = findViewById(R.id.fl_face_shape_items);
        mIvRecoverFaceShape = findViewById(R.id.iv_recover_face_shape);
        mIvRecoverFaceShape.setOnClickListener(new OnMultiClickListener() {
            @Override
            protected void onMultiClick(View v) {
                ConfirmDialogFragment confirmDialogFragment = ConfirmDialogFragment.newInstance(mContext.getString(R.string.dialog_reset_avatar_model), new BaseDialogFragment.OnClickListener() {
                    @Override
                    public void onConfirm() {
                        // recover params
                        BeautyParameterModel.recoverFaceShapeToDefValue();
                        updateViewFaceShape();
                        int checkedId = mFaceShapeBeautyBoxGroup.getCheckedBeautyBoxId();
                        seekToSeekBar(checkedId);
                        setRecoverFaceShapeEnable(false);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                confirmDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "ConfirmDialogFragmentReset");
            }
        });
        mTvRecoverFaceShape = findViewById(R.id.tv_recover_face_shape);
        mFaceShapeBeautyBoxGroup = findViewById(R.id.beauty_group_face_shape);
        mFaceShapeBeautyBoxGroup.setOnCheckedChangeListener(new BeautyBoxGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BeautyBoxGroup group, int checkedId) {
                mBeautySeekBar.setVisibility(GONE);
                seekToSeekBar(checkedId);
                onChangeFaceBeautyLevel(checkedId);
            }
        });
        checkFaceShapeChanged();
    }

    private void setRecoverFaceShapeEnable(boolean enable) {
        if (enable) {
            mIvRecoverFaceShape.setAlpha(1f);
            mTvRecoverFaceShape.setAlpha(1f);
        } else {
            mIvRecoverFaceShape.setAlpha(0.6f);
            mTvRecoverFaceShape.setAlpha(0.6f);
        }
        mIvRecoverFaceShape.setEnabled(enable);
        mTvRecoverFaceShape.setEnabled(enable);
    }

    private void setRecoverFaceSkinEnable(boolean enable) {
        if (enable) {
            mIvRecoverFaceSkin.setAlpha(1f);
            mTvRecoverFaceSkin.setAlpha(1f);
        } else {
            mIvRecoverFaceSkin.setAlpha(0.6f);
            mTvRecoverFaceSkin.setAlpha(0.6f);
        }
        mIvRecoverFaceSkin.setEnabled(enable);
        mTvRecoverFaceSkin.setEnabled(enable);
    }

    private void onChangeFaceBeautyLevel(int viewId) {
        if (viewId == View.NO_ID) {
            return;
        }
        View view = findViewById(viewId);
        if (view instanceof BaseBeautyBox) {
            boolean open;
            if (viewId == R.id.beauty_box_blur_level) {
                open = isOpen(mRgBlurType.getCheckedRadioButtonId());
            } else {
                open = isOpen(viewId);
            }
            ((BaseBeautyBox) view).setOpen(open);
        }
        if (mOnFUControlListener == null) {
            return;
        }
        switch (viewId) {
            case R.id.beauty_box_skin_detect:
                mOnFUControlListener.onSkinDetectSelected(getValue(viewId));
                break;
            case R.id.beauty_box_blur_level:
                mOnFUControlListener.onBlurLevelSelected(getValue(mRgBlurType.getCheckedRadioButtonId()));
                break;
            case R.id.rb_blur_clear:
            case R.id.rb_blur_fine:
            case R.id.rb_blur_hazy:
                //mOnFUControlListener.onBlurTypeSelected(BeautyParameterModel.sBlurType);
                break;
            case R.id.beauty_box_color_level:
                mOnFUControlListener.onColorLevelSelected(getValue(viewId));
                break;
            case R.id.beauty_box_red_level:
                mOnFUControlListener.onRedLevelSelected(getValue(viewId));
                break;
            case R.id.beauty_box_eye_bright:
                mOnFUControlListener.onEyeBrightSelected(getValue(viewId));
                break;
            case R.id.beauty_box_tooth_whiten:
                mOnFUControlListener.onToothWhitenSelected(getValue(viewId));
                break;
            case R.id.beauty_box_eye_enlarge:
                mOnFUControlListener.onEyeEnlargeSelected(getValue(viewId));
                break;
            case R.id.beauty_box_cheek_thinning:
                mOnFUControlListener.onCheekThinningSelected(getValue(viewId));
                break;
            case R.id.beauty_box_cheek_narrow:
                mOnFUControlListener.onCheekNarrowSelected(getValue(viewId));
                break;
            case R.id.beauty_box_cheek_v:
                mOnFUControlListener.onCheekVSelected(getValue(viewId));
                break;
            case R.id.beauty_box_cheek_small:
                mOnFUControlListener.onCheekSmallSelected(getValue(viewId));
                break;
            case R.id.beauty_box_intensity_chin:
                mOnFUControlListener.onIntensityChinSelected(getValue(viewId));
                break;
            case R.id.beauty_box_intensity_forehead:
                mOnFUControlListener.onIntensityForeheadSelected(getValue(viewId));
                break;
            case R.id.beauty_box_intensity_nose:
                mOnFUControlListener.onIntensityNoseSelected(getValue(viewId));
                break;
            case R.id.beauty_box_intensity_mouth:
                mOnFUControlListener.onIntensityMouthSelected(getValue(viewId));
                break;
            default:
        }
    }

    private void initViewTop() {
        mBeautySeekBar = findViewById(R.id.beauty_seek_bar);
        mBeautySeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnSimpleProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar SeekBar, int value, boolean fromUser) {
                if (!fromUser) {
                    return;
                }

                float valueF = 1.0f * (value - SeekBar.getMin()) / 100;
                int checkedCheckBoxId = mBottomCheckGroup.getCheckedCheckBoxId();
                if (checkedCheckBoxId == R.id.beauty_radio_skin_beauty) {
                    int skinCheckedId = mSkinBeautyBoxGroup.getCheckedBeautyBoxId();
                    if (skinCheckedId == R.id.beauty_box_blur_level) {
                        setValue(mRgBlurType.getCheckedRadioButtonId(), valueF);
                    } else {
                        setValue(skinCheckedId, valueF);
                    }
                    onChangeFaceBeautyLevel(skinCheckedId);
                    checkFaceSkinChanged();
                } else if (checkedCheckBoxId == R.id.beauty_radio_face_shape) {
                    setValue(mFaceShapeBeautyBoxGroup.getCheckedBeautyBoxId(), valueF);
                    onChangeFaceBeautyLevel(mFaceShapeBeautyBoxGroup.getCheckedBeautyBoxId());
                    checkFaceShapeChanged();
                } else if (checkedCheckBoxId == R.id.beauty_radio_filter) {
                    mFilterRecyclerAdapter.setFilterLevels(valueF);
                } else if (checkedCheckBoxId == R.id.beauty_radio_face_beauty) {
                    // 整体妆容调节
                    float level = 1.0f * value / 100;
                    FaceMakeup faceMakeup = mFaceMakeupAdapter.getSelectedItems().valueAt(0);
                    String name = getResources().getString(faceMakeup.getNameId());
                    BeautyParameterModel.sBatchMakeupLevel.put(name, level);
                    List<MakeupItem> makeupItems = faceMakeup.getMakeupItems();
                    /* 数学公式
                     * 0.4        0.7
                     * strength  level
                     * --> strength = 0.4 * level / 0.7
                     *   if level = 1.0, then strength = 0.57
                     *   if level = 0.2, then strength = 0.11
                     *   so, float strength = item.defaultLevel * level / DEFAULT_BATCH_MAKEUP_LEVEL
                     * */
                    if (makeupItems != null) {
                        for (MakeupItem makeupItem : makeupItems) {
                            float lev = makeupItem.getDefaultLevel() * level / FaceMakeupEnum.MAKEUP_OVERALL_LEVEL.get(faceMakeup.getNameId());
                            makeupItem.setLevel(lev);
                        }
                    }
                    mOnFUControlListener.onLightMakeupOverallLevelChanged(level);
                    mOnFUControlListener.onFilterLevelSelected(level);
                }
            }
        });
    }

    private void checkFaceShapeChanged() {
        if (BeautyParameterModel.checkIfFaceShapeChanged()) {
            setRecoverFaceShapeEnable(true);
        } else {
            setRecoverFaceShapeEnable(false);
        }
    }

    private void checkFaceSkinChanged() {
        if (BeautyParameterModel.checkIfFaceSkinChanged()) {
            setRecoverFaceSkinEnable(true);
        } else {
            setRecoverFaceSkinEnable(false);
        }
    }

    /**
     * 点击底部 tab
     *
     * @param viewId
     */
    private void clickViewBottomRadio(int viewId) {
        mFlFaceShapeItems.setVisibility(GONE);
        mFlFaceSkinItems.setVisibility(GONE);
        mFilterRecyclerView.setVisibility(GONE);
        mRvMakeupItems.setVisibility(GONE);
        mBeautySeekBar.setVisibility(GONE);
        if (viewId == R.id.beauty_radio_skin_beauty) {
            mFlFaceSkinItems.setVisibility(VISIBLE);
            int id = mSkinBeautyBoxGroup.getCheckedBeautyBoxId();
            if (id != R.id.beauty_box_skin_detect) {
                if (id == R.id.beauty_box_blur_level) {
                    id = mRgBlurType.getCheckedRadioButtonId();
                    mRgBlurType.setVisibility(VISIBLE);
                }
                seekToSeekBar(id);
            }
        } else if (viewId == R.id.beauty_radio_face_shape) {
            mFlFaceShapeItems.setVisibility(VISIBLE);
            int id = mFaceShapeBeautyBoxGroup.getCheckedBeautyBoxId();
            seekToSeekBar(id);
            mRgBlurType.setVisibility(INVISIBLE);
        } else if (viewId == R.id.beauty_radio_filter) {
            mFilterRecyclerView.setVisibility(VISIBLE);
            mFilterRecyclerAdapter.setFilterProgress();
            mRgBlurType.setVisibility(INVISIBLE);
        } else if (viewId == R.id.beauty_radio_face_beauty) {
            mRgBlurType.setVisibility(INVISIBLE);
            mRvMakeupItems.setVisibility(VISIBLE);
            mBeautySeekBar.setVisibility(INVISIBLE);
            // 首次选中第一个桃花妆
            if (mFirstShowLightMakeup) {
                mFirstShowLightMakeup = false;
                mFaceMakeupAdapter.setItemSelected(BeautyParameterModel.BatchPosition);
                new OnFaceMakeupClickListener().onItemClick(mFaceMakeupAdapter, null, BeautyParameterModel.BatchPosition);
            }
            FaceMakeup faceMakeup = mFaceMakeupAdapter.getSelectedItems().valueAt(0);
            if (faceMakeup != null) {
                String name = getResources().getString(faceMakeup.getNameId());
                Float level = BeautyParameterModel.sBatchMakeupLevel.get(name);
                if (level == null) {
                    level = FaceMakeupEnum.MAKEUP_OVERALL_LEVEL.get(faceMakeup.getNameId());
                    BeautyParameterModel.sBatchMakeupLevel.put(name, level);
                }
                if (level != null) {
                    seekToSeekBar(level);
                }
            }
        }
    }



    private void seekToSeekBar(float value) {
        seekToSeekBar(value, 0, 100);
    }


    private void seekToSeekBar(float value, int min, int max) {
        mBeautySeekBar.setVisibility(VISIBLE);
        mBeautySeekBar.setMin(min);
        mBeautySeekBar.setMax(max);
        mBeautySeekBar.setProgress((int) (value * (max - min) + min));
    }

    private void seekToSeekBar(int checkedId) {
        if (checkedId == View.NO_ID) {
            return;
        }

        float value = getValue(checkedId);
        int min = 0;
        int max = 100;
        if (checkedId == R.id.beauty_box_intensity_chin || checkedId == R.id.beauty_box_intensity_forehead || checkedId == R.id.beauty_box_intensity_mouth) {
            min = -50;
            max = 50;
        }
        seekToSeekBar(value, min, max);
    }

    private void changeBottomLayoutAnimator(final int startHeight, final int endHeight) {
        if (mBottomLayoutAnimator != null && mBottomLayoutAnimator.isRunning()) {
            mBottomLayoutAnimator.end();
        }
        mBottomLayoutAnimator = ValueAnimator.ofInt(startHeight, endHeight).setDuration(150);
        mBottomLayoutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mBottomView.getLayoutParams();
                params.height = height;
                mBottomView.setLayoutParams(params);
                if (mOnBottomAnimatorChangeListener != null) {
                    float showRate = 1.0f * (height - startHeight) / (endHeight - startHeight);
                    mOnBottomAnimatorChangeListener.onBottomAnimatorChangeListener(startHeight > endHeight ? 1 - showRate : showRate);
                }
            }
        });
        mBottomLayoutAnimator.start();
    }

    public interface OnBottomAnimatorChangeListener {
        void onBottomAnimatorChangeListener(float showRate);
    }

    public void setOnBottomAnimatorChangeListener(OnBottomAnimatorChangeListener onBottomAnimatorChangeListener) {
        mOnBottomAnimatorChangeListener = onBottomAnimatorChangeListener;
    }

    private OnBottomAnimatorChangeListener mOnBottomAnimatorChangeListener;

    private ValueAnimator mBottomLayoutAnimator;

    private void setDescriptionShowStr(int str) {
        if (mOnDescriptionShowListener != null) {
            mOnDescriptionShowListener.onDescriptionShowListener(str);
        }
    }

    public void hideBottomLayoutAnimator() {
        mBottomCheckGroup.check(View.NO_ID);
    }

    public interface OnDescriptionShowListener {
        void onDescriptionShowListener(int str);
    }

    public void setOnDescriptionShowListener(OnDescriptionShowListener onDescriptionShowListener) {
        mOnDescriptionShowListener = onDescriptionShowListener;
    }

    private OnDescriptionShowListener mOnDescriptionShowListener;

    public void setFilterLevel(String filterName, float faceBeautyFilterLevel) {
        sFilterLevel.put(STR_FILTER_LEVEL + filterName, faceBeautyFilterLevel);
        if (mOnFUControlListener != null) {
            mOnFUControlListener.onFilterLevelSelected(faceBeautyFilterLevel);
        }
    }

    public float getFilterLevel(String filterName) {
        String key = STR_FILTER_LEVEL + filterName;
        Float level = sFilterLevel.get(key);
        if (level == null) {
            level = Filter.DEFAULT_FILTER_LEVEL;
            sFilterLevel.put(key, level);
        }
        setFilterLevel(filterName, level);
        return level;
    }

    class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.HomeRecyclerHolder> {

        @Override
        public FilterRecyclerAdapter.HomeRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FilterRecyclerAdapter.HomeRecyclerHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_beauty_control_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(FilterRecyclerAdapter.HomeRecyclerHolder holder, final int position) {
            final List<Filter> filters = mFilters;
            holder.filterImg.setImageResource(filters.get(position).resId());
            holder.filterName.setText(filters.get(position).description());
            if (mFilterPositionSelect == position) {
                holder.filterImg.setBackgroundResource(R.drawable.control_filter_select);
            } else {
                holder.filterImg.setBackgroundResource(0);
            }
            holder.itemView.setOnClickListener(new OnMultiClickListener() {
                @Override
                protected void onMultiClick(View v) {
                    BeautyParameterModel.FilterPostion=position;
                    mFilterPositionSelect = position;
                    mBeautySeekBar.setVisibility(position == 0 ? INVISIBLE : VISIBLE);
                    setFilterProgress();
                    notifyDataSetChanged();
                    if (mOnFUControlListener != null) {
                        sFilter = filters.get(mFilterPositionSelect);
                        mOnFUControlListener.onFilterNameSelected(sFilter.filterName());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mFilters.size();
        }

        public void setFilterLevels(float filterLevels) {
            if (mFilterPositionSelect >= 0) {
                setFilterLevel(mFilters.get(mFilterPositionSelect).filterName(), filterLevels);
            }
        }

        public void setFilter(Filter filter) {
            mFilterPositionSelect = mFilters.indexOf(filter);
        }

        public int indexOf(Filter filter) {
            for (int i = 0; i < mFilters.size(); i++) {
                if (filter.filterName().equals(mFilters.get(i).filterName())) {
                    return i;
                }
            }
            return -1;
        }

        public void setFilterProgress() {
            if (mFilterPositionSelect >= 0) {
                seekToSeekBar(getFilterLevel(mFilters.get(mFilterPositionSelect).filterName()));
            }
        }

        class HomeRecyclerHolder extends RecyclerView.ViewHolder {

            ImageView filterImg;
            TextView filterName;

            public HomeRecyclerHolder(View itemView) {
                super(itemView);
                filterImg = (ImageView) itemView.findViewById(R.id.control_recycler_img);
                filterName = (TextView) itemView.findViewById(R.id.control_recycler_text);
            }
        }
    }

    // ----------- 新添加的美妆组合，也叫质感美颜

    // 美妆列表点击事件
    private class OnFaceMakeupClickListener implements BaseRecyclerAdapter.OnItemClickListener<FaceMakeup> {

        @Override
        public void onItemClick(BaseRecyclerAdapter<FaceMakeup> adapter, View view, int position) {
            BeautyParameterModel.BatchPosition=position;
            FaceMakeup faceMakeup = adapter.getItem(position);
            if (position == 0) {
                // 卸妆
                mBeautySeekBar.setVisibility(View.INVISIBLE);
//                Filter origin = mFilters.get(0);
//                mOnFUControlListener.onFilterNameSelected(origin);
//                setFilterLevel(origin.filterName(), 0);
                int old = mFilterPositionSelect;
                mFilterPositionSelect = -1;
                mFilterRecyclerAdapter.notifyItemChanged(old);
                mOnFUControlListener.onLightMakeupBatchSelected(faceMakeup.getMakeupItems());
            } else {
                // 效果妆容
                mBeautySeekBar.setVisibility(View.VISIBLE);
                String name = getResources().getString(faceMakeup.getNameId());
                Float level = BeautyParameterModel.sBatchMakeupLevel.get(name);
                boolean used = true;
                if (level == null) {
                    used = false;
                    level = FaceMakeupEnum.MAKEUP_OVERALL_LEVEL.get(faceMakeup.getNameId());
                    BeautyParameterModel.sBatchMakeupLevel.put(name, level);
                }
                seekToSeekBar(level);
                mOnFUControlListener.onLightMakeupBatchSelected(faceMakeup.getMakeupItems());
                mOnFUControlListener.onLightMakeupOverallLevelChanged(level);

                Pair<Filter, Float> filterFloatPair = FaceMakeupEnum.MAKEUP_FILTERS.get(faceMakeup.getNameId());
                if (filterFloatPair != null) {
                    // 滤镜调整到对应的位置，没有就不做
                    Filter filter = filterFloatPair.first;
                    int i = mFilterRecyclerAdapter.indexOf(filter);
                    if (i >= 0) {
                        mFilterPositionSelect = i;
                        mFilterRecyclerAdapter.notifyItemChanged(i);
                        mFilterRecyclerView.scrollToPosition(i);
                    } else {
                        int old = mFilterPositionSelect;
                        mFilterPositionSelect = -1;
                        mFilterRecyclerAdapter.notifyItemChanged(old);
                    }
                    mOnFUControlListener.onFilterNameSelected(filter.filterName());
                    Float filterLevel = used ? level : filterFloatPair.second;
                    sFilter = filter;
                    String filterName = filter.filterName();
                    sFilterLevel.put(STR_FILTER_LEVEL + filterName, filterLevel);
                    setFilterLevel(filterName, filterLevel);
                }
            }
        }
    }

    // 妆容组合适配器
    private class FaceMakeupAdapter extends BaseRecyclerAdapter<FaceMakeup> {

        FaceMakeupAdapter(@NonNull List<FaceMakeup> data) {
            super(data, R.layout.layout_rv_makeup);
        }

        @Override
        protected void bindViewHolder(BaseViewHolder viewHolder, FaceMakeup item) {
            viewHolder.setText(R.id.tv_makeup, getResources().getString(item.getNameId()))
                    .setImageResource(R.id.iv_makeup, item.getIconId());
        }

        @Override
        protected void handleSelectedState(BaseViewHolder viewHolder, FaceMakeup data, boolean selected) {
            ((TextView) viewHolder.getViewById(R.id.tv_makeup)).setTextColor(selected ?
                    getResources().getColor(R.color.main_color) : getResources().getColor(R.color.colorWhite));
            viewHolder.setBackground(R.id.iv_makeup, selected ? R.drawable.control_filter_select : 0);
        }
    }

}