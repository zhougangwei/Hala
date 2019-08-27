package chat.hala.hala.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.facebook.FacebookSdk;
import com.getui.gs.ias.core.GsConfig;
import com.getui.gs.sdk.GsManager;
import com.igexin.sdk.PushManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.Locale;

import chat.hala.hala.BuildConfig;
import chat.hala.hala.activity.LoginActivity;
import chat.hala.hala.avchat.WorkerThread;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.rongyun.MySendMessageListener;
import chat.hala.hala.rongyun.MyTextMessageItemProvider;
import chat.hala.hala.rongyun.customizemessage.CustomizeHongbaoMessage;
import chat.hala.hala.rongyun.customizemessage.CustomizeHongbaoMessageProvider;
import chat.hala.hala.rongyun.customizemessage.CustomizeHongbaoReceiveMessage;
import chat.hala.hala.rongyun.customizemessage.CustomizeHongbaoReceiveMessageProvider;
import chat.hala.hala.rongyun.customizemessage.CustomizePicMessageProvider;
import chat.hala.hala.rongyun.customizemessage.CustomizeVideoMessageProvider;
import chat.hala.hala.rongyun.customizemessage.CustomizeVideoMessage;
import chat.hala.hala.rongyun.customizemessage.CustomizeVoiceMessage;
import chat.hala.hala.rongyun.customizemessage.CustomizeVoiceMessageProvider;
import chat.hala.hala.service.DemoIntentService;
import chat.hala.hala.service.DemoPushService;
import chat.hala.hala.utils.SPUtil;
import chat.hala.hala.utils.ToolUtils;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import chat.hala.hala.rongyun.MyExtensionModule;
import chat.hala.hala.rongyun.MyPrivateConversationProvider;


/**
 * Created by oneki on 2017/8/24.
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃   神兽保佑
 * ┃　　　┃   代码无BUG！
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 */


public class App extends MultiDexApplication {

    private static final String TAG = "Application";


    private static App application;
    public static Context sContext;
    private int count;


    @Override
    public void onCreate() {
        super.onCreate();

        if (ToolUtils.isMainProcess(this)) {
            CrashReport.initCrashReport(getApplicationContext(), "1067e333f5", BuildConfig.DEBUG?true:false);
            application = this;
            sContext = this;
            initRong();
            initGetui();
            initRxjava();
            LogUtils.init(this, true, false, 'v', "Hala");
            FacebookSdk.setApplicationId("306102296576531");
            FacebookSdk.setAutoLogAppEventsEnabled(true);
            FacebookSdk.sdkInitialize(getApplicationContext());
            SPUtil.setContext(this);
            addOnline();
        }
    }

    private void initGetui() {
        PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
        GsConfig.setDebugEnable(BuildConfig.DEBUG?true:false);
        GsManager.getInstance().init(this);

    }


    private void initRong() {
        RongIM.init(this);

        RongIM.getInstance().registerConversationTemplate(new MyPrivateConversationProvider());
        RongIM.getInstance().setMessageAttachedUserInfo(true);
        RongIM.getInstance().registerMessageTemplate(new CustomizePicMessageProvider());

        RongIM.getInstance().registerMessageTemplate(new MyTextMessageItemProvider());
        RongIM.getInstance().registerMessageType(CustomizeVideoMessage.class);
        RongIM.getInstance().registerMessageType(CustomizeVoiceMessage.class);
        RongIM.getInstance().registerMessageType(CustomizeHongbaoMessage.class);
        RongIM.getInstance().registerMessageType(CustomizeHongbaoReceiveMessage.class);
        RongIM.getInstance().registerMessageTemplate(new CustomizeVideoMessageProvider());
        RongIM.getInstance().registerMessageTemplate(new CustomizeHongbaoMessageProvider());
        RongIM.getInstance().registerMessageTemplate(new CustomizeVoiceMessageProvider());
        RongIM.getInstance().registerMessageTemplate(new CustomizeHongbaoReceiveMessageProvider());
        setMyExtensionModule();
    }
    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
    }

    private void initRxjava() {
    /*    RxJavaPlugins.setErrorHandler(
                new Consumer<Throwable>() {
                                          @Override
                                          public void accept(Throwable throwable) throws Exception {

                                          }
                                      }
        );*/
    }

    private void addOnline() {
        ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                count++;
                if (0 == count - 1) {
                    RetrofitFactory.getInstance().online().subscribeOn(Schedulers.io())
                            .subscribe(new BaseCosumer<BaseBean>() {
                                @Override
                                public void onGetData(BaseBean baseBean) {
                                }
                            });
                }

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (0 == count) {
                    RetrofitFactory.getInstance().offline().subscribeOn(Schedulers.io())
                            .subscribe(new BaseCosumer<BaseBean>() {
                                @Override
                                public void onGetData(BaseBean baseBean) {
                                }
                            });
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (0 == count) {
                    Log.d(TAG, "onActivityDestroyed: ");
                }
            }
        };
        // 注册监听
        registerActivityLifecycleCallbacks(lifecycleCallbacks);

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
