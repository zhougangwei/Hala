package com.hala.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hala.base.Contact;


/**
 * @Description:SharedPreferences单例工具
 */
public class SPUtil {

    private SharedPreferences sp;

    private volatile static SPUtil spu = null;

    private final static String SHARED_NAME = "hala";

    public SPUtil(Context context) {
        this.sp = context.getSharedPreferences(SHARED_NAME, 0);
    }


    private volatile static Context sContext;

    public static SPUtil getInstance(Context context) {
        if (spu == null) {
            synchronized (SPUtil.class) {
                if (spu == null) {
                    spu = new SPUtil(sContext);
                }
            }
        }
        return spu;
    }




    public synchronized static void setContext(Context Context) {
        if (sContext==null){
            sContext =Context;
        }
    }

    /**
     * @param key 键值名
     * @param b   设置boolean值
     */
    public void setBoolean(String key, boolean b) {
        sp.edit().putBoolean(key, b).apply();
    }

    /**
     * @param key   键名
     * @param defult 默认返回值
     * @return boolean值
     */
    public boolean getBoolean(String key, boolean defult) {
        return sp.getBoolean(key, defult);
    }

    /**
     * @param key 键名
     * @param s   设置字符串
     */
    public void setString(String key, String s) {
        sp.edit().putString(key, s).apply();
    }

    /**
     * @param key    键名
     * @param defult 默认返回值
     * @return 字符串
     */
    public String getString(String key, String defult) {
        return sp.getString(key, defult);
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }


    /**
     * @param key 键名
     * @param i   设置整型值
     */
    public void setInt(String key, int i) {
        sp.edit().putInt(key, i).apply();
    }

    /**
     * @param key    键名
     * @param defult 默认返回int值
     * @return int
     */
    public int getInt(String key, int defult) {
        return sp.getInt(key, defult);

    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }


    public void clear() {
        sp.edit().clear().apply();
    }
}
