package chat.hala.hala.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.facebook.FacebookSdk;

import java.util.Locale;

import chat.hala.hala.activity.LoginActivity;
import chat.hala.hala.avchat.WorkerThread;
import chat.hala.hala.utils.SPUtil;

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

    private static App application;
    public static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        sContext=this;
        FacebookSdk.setApplicationId("306102296576531");
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        FacebookSdk.sdkInitialize(getApplicationContext());
        SPUtil.setContext(this);
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



    private WorkerThread mWorkerThread;

    public synchronized void initWorkerThread() {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(getApplicationContext());
            mWorkerThread.start();
            mWorkerThread.waitForReady();
        }
    }

    public synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

}