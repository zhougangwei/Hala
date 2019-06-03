package com.hala.utils;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.hala.base.App;



/**
 * Created by ysl on 2017/11/30.
 */

public class ToastUtils {

    protected static Toast mToast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * 显示Toast
     * @param text
     */
    public static void showToast(Context context, String text){

        try {
            if (mToast == null) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N&&Build.VERSION.SDK_INT<=Build.VERSION_CODES.N_MR1){
                    mToast= Toast.makeText(App.sContext, text, Toast.LENGTH_SHORT);
                }else{
                    mToast = Toast.makeText(App.sContext, text, Toast.LENGTH_SHORT);
                }
                mToast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (text.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        mToast.show();
                    }
                } else {
                    oldMsg = text;
                    mToast.setText(text);
                    mToast.show();
                }
            }
            oneTime = twoTime;
        }catch (Exception e){

        }
    }
}
