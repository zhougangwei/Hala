package com.hala.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hala.R;


public class RatingBarView extends LinearLayout {

    private int currentChooseCount;
    private int lastCount;

    public interface OnRatingListener {
        void onRating(Object bindObject, int RatingScore);
    }

    private boolean mClickable = true;
    private OnRatingListener onRatingListener;
    private Object bindObject;
    private float starImageSize;
    private int starCount;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;

    private float mStarPadding;

    @Override
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    public RatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        starImageSize = ta.getDimension(R.styleable.RatingBarView_starImageSize, 20);
        starCount = ta.getInteger(R.styleable.RatingBarView_starCount, 5);
        starEmptyDrawable = ta.getDrawable(R.styleable.RatingBarView_starEmpty);
        starFillDrawable = ta.getDrawable(R.styleable.RatingBarView_starFill);
        mStarPadding = ta.getDimension(R.styleable.RatingBarView_starPadding,0);
        ta.recycle();

        for (int i = 0; i < starCount; ++i) {
            ImageView imageView = getStarImageView(context, attrs);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickable) {
                       int clickCount = indexOfChild(v) + 1;
                        setStar(clickCount, true);
                        if (onRatingListener != null) {
                            onRatingListener.onRating(bindObject, currentChooseCount);
                        }
                    }
                }
            });
            addView(imageView);
        }
    }




    private ImageView getStarImageView(Context context, AttributeSet attrs) {
        ImageView imageView = new ImageView(context);
        Log.e("RatingBarView", "starImageSize:" + starImageSize);
        MarginLayoutParams para = new MarginLayoutParams(Math.round(starImageSize), Math.round(starImageSize));
        imageView.setLayoutParams(para);
        // TODO:you can change gap between two stars use the padding
//        imageView.setPadding(0, 0, 40, 0);
        imageView.setPadding(0, 0, 0, 0);
        para.setMargins(0,0,Math.round(mStarPadding),0);
        imageView.setImageDrawable(starEmptyDrawable);


        return imageView;
    }

    public void setStar(int starCount, boolean animation) {
        if (currentChooseCount==starCount) {
            return;
        }
        starCount = starCount > this.starCount ? this.starCount : starCount;
        starCount = starCount < 0 ? 0 : starCount;
        lastCount = currentChooseCount;
        currentChooseCount = starCount;

        if (lastCount<currentChooseCount) {
            for (int i = lastCount; i <currentChooseCount ; i++) {
                ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
            }
          }else if(lastCount>currentChooseCount){
            for (int i = lastCount; i >currentChooseCount ; i--) {
                ((ImageView) getChildAt(i-1)).setImageDrawable(starEmptyDrawable);
            }
        }
    }



    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarCount(int startCount) {
        this.starCount = startCount;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public void setBindObject(Object bindObject) {
        this.bindObject = bindObject;
    }

/**
*这个回调，可以获取到用户评价给出的星星等级
*/
    public void setOnRatingListener(OnRatingListener onRatingListener) {
        this.onRatingListener = onRatingListener;
    }

    public int getCurrentChooseCount() {
        return currentChooseCount;
    }

    /**
     * 触发 显示文字
     */
    public void performClickStar(){
        if (onRatingListener != null) {
            onRatingListener.onRating(bindObject, 5);
        }
    }

}

