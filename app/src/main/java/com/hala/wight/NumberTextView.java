package com.hala.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.hala.utils.NumberUtils;


/**
 * Created by zhougangwei on 2018/7/17.
 */

public class NumberTextView extends AppCompatTextView {
    public NumberTextView(Context context) {
        super(context);
    }

    public NumberTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


  public void setText(String text) {
        try{
            if (NumberUtils.isInteger(text)){
                int v = Integer.parseInt(text);
                //千分位
                if (((v/1000))>0&&((v/1000000)==0)){
                    text = String.format("%.1f", ((v / 1000f)))+"k";
                }else if(v/1000000>0) {
                    text = String.format("%.1f", ((v / 1000000f) ))+"m";
                }
                super.setText(text,  BufferType.NORMAL);
            }else {
                super.setText(text,  BufferType.NORMAL);
            }
        }catch (Exception e){
            super.setText(text,  BufferType.NORMAL);
        }
    }
}
