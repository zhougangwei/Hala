package chat.hala.hala.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

public class Clickable extends ClickableSpan {
    private final IdViewListener mListener;
    private String id;
    private int mColor;
    private boolean isBold;

    public Clickable(IdViewListener l, String s, int color, boolean isBold) {
        mListener = l;
        this.id=s;
        mColor=color;
        this.isBold=isBold;
    }
    public Clickable(IdViewListener l, String s) {
        mListener = l;
        this.id=s;
    }


    /**
     * 重写父类点击事件
     */
    @Override
    public void onClick(View v) {

        if (mListener!=null) {
            mListener.onClick(v);
            mListener.clickId(id);
        }

    }

    /**
     * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        if (mColor!=0){
            ds.setColor(mColor);
        }
        if (!isBold){
            ds.setFakeBoldText(isBold);
        }

    }
    }