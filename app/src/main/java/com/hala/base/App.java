package com.hala.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.bumptech.glide.annotation.GlideModule;
import com.hala.activity.LoginActivity;
import com.hala.utils.SPUtil;

import java.util.Locale;

/**
 * Created by oneki on 2017/8/24.
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *    ┃　　　┃   神兽保佑
 *    ┃　　　┃   代码无BUG！
 *    ┃　　　┗━━━┓
 *    ┃　　　　　　　┣┓
 *    ┃　　　　　　　┏┛
 *    ┗┓┓┏━┳┓┏┛
 *      ┃┫┫　┃┫┫
 *      ┗┻┛　┗┻┛
 */


public class App extends MultiDexApplication {

    private static final String TAG = "Application";


    /**
     * 每秒时间到了之后所执行的任务
     */

    public static String CHANNEL_NAME;
    public static int CHANNEL_CODE;
    private int count;

    private static App application;
    public static Context sContext;
    private long startTime;
    private static ChatManager mChatManager;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        sContext=this;
        SPUtil.setContext(this);
        mChatManager = new ChatManager(this);
        mChatManager.init();
    }

    //用来阿拉伯语 本地使用
    private void initLanguage() {

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        res.updateConfiguration(config, dm);
    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    /**
     * 登录失效后去登录
     */
    public static void goLogin() {
        Intent intent = new Intent(application, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);

    }

    /**
     * 获取上下文
     */
    public static App getApplication() {
        return application;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public static ChatManager getChatManager() {
        return mChatManager;
    }
}
