/*
package chat.hala.hala.pay;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.facebook.CallbackManager;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import chat.hala.hala.R;
import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.bean.RuleBean;
import chat.hala.hala.dialog.InitDialog;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.http.RetryWithDelay;
import chat.hala.hala.utils.GsonUtil;
import chat.hala.hala.utils.SPUtil;
import chat.hala.hala.utils.ToastUtils;
import chat.hala.hala.utils.ToolUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.gaoshou.money.googlepay.util.IabBroadcastReceiver;
import me.gaoshou.money.googlepay.util.IabHelper;
import me.gaoshou.money.googlepay.util.IabResult;
import me.gaoshou.money.googlepay.util.Inventory;
import me.gaoshou.money.googlepay.util.Purchase;

*
 * Created by zhougangwei on 2018/10/29.



public class GooglePayUi implements IabBroadcastReceiver.IabBroadcastListener ,GooglePayUiPrecneter {

    private static final String TAG = "GooglePayUi";

    private  int userId;
    public Activity activity;
    private List<RuleBean.DataBean.RechargeSettingBean> mProductList;

    private String base64EncodedPublicKey;

    private String productId;
    static final int RC_REQUEST = 10001;
    private IabHelper mHelper;
    private IabBroadcastReceiver mBroadcastReceiver;
    private DialogCircleProgress dialogCircleProgress;
    private CallbackManager callBackManager;
    private AppEventsLogger logger;

    private  ConstumCallBack constumCallBack;

    private int mEnterId;



    public GooglePayUi(){
        mProductList = new ArrayList<>();
    }
    @Override
    public void init(Activity activity, int enterId){
        logger = AppEventsLogger.newLogger(activity);
        callBackManager = FaceBookShareUtils.initCallbackManager(activity);
        this.activity = activity;
        this.userId  = SPUtil.getInstance(activity).getUserId();;
        this.mEnterId = enterId;

        initData(true);
    }

    @Override
    public void init(Activity context) {
        init(context,PayEnterEnum.USER_MAIN);
    }

    @Override
    public void addConstumCallBack(ConstumCallBack constumCallBack) {
        this.constumCallBack=constumCallBack;
    }



    public void initData(final boolean initGoogle){
        RetrofitFactory.getInstance().getProductList(0).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<RuleBean.DataBean.RechargeSettingBean>>() {
                    @Override
                    public void accept(BaseBean<RuleBean.DataBean.RechargeSettingBean> productBeanBaseBean) throws Exception {
                        LogUtils.e(TAG, GsonUtil.parseObjectToJson(productBeanBaseBean));
                        if (Contact.ERROR_OK.equals(productBeanBaseBean.getErrorcode())) {
                            List<RuleBean.DataBean.RechargeSettingBean> result = productBeanBaseBean.getResult();
                            mProductList.clear();
                            mProductList.addAll(result);
                            if (initGoogle) {
                                initGoogle();
                               initSomefaultData();
                            }
                        } else {
                            ToastUtils.showToast(activity,activity.getString(R.string.getDataFailed));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(TAG, throwable.getMessage());
                        ToastUtils.showToast(activity, activity.getString(R.string.getDataFailed));
                    }
                });
    }

    private void initSomefaultData() {
        try { List<ChargeBean> list = EntityManager.getInstance().getmChargeBeanDao()
                .queryBuilder().list();

        if (list!=null&&list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                ChargeBean chargeBean = list.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("developerPayload",chargeBean.getDeveloperPayload());        //本地的
                jsonObject.put("token",chargeBean.getToken());
                jsonObject.put("orderId",chargeBean.getOrderId());
                Purchase purchase = new Purchase("1", jsonObject.toString(), "1");
                saveData(purchase,true);
            }
        }}catch (Exception e){
            e.printStackTrace();
        }

    }





    private void initGoogle() {
        base64EncodedPublicKey = activity.getString(R.string.googlepaycode);
        mHelper = new IabHelper(activity, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {

                if (!result.isSuccess()) {

                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    if(activity instanceof GooglePayActivity){
                        ToastUtils.showToast(activity,activity.getString(R.string.please_open_google_pay));
                    }
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null)
                    return;

                // Important: Dynamically register for broadcast messages about updated purchases.
                // We register the receiver here instead of as a <receiver> in the Manifest
                // because we always call getPurchases() at startup, so therefore we can ignore
                // any broadcasts sent while the app isn't running.
                // Note: registering this listener in an Activity is a bad idea, but is done here
                // because this is a SAMPLE. Regardless, the receiver must be registered after
                // IabHelper is setup, but before first call to getPurchases().
                mBroadcastReceiver = new IabBroadcastReceiver(GooglePayUi.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                activity.registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                LogUtils.e(TAG, "Setup successful. Querying inventory.");
                try {
                    List<RuleBean.DataBean.RechargeSettingBean> list = mProductList;

                    List<String> skus = new ArrayList<>();
                    for (RuleBean.DataBean.RechargeSettingBean productBean : list) {
                        skus.add(productBean.getProductId());
                    }
                    mHelper.queryInventoryAsync(true, skus, null, mGotInventoryListener);



                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });
    }


    void complain(String message) {
        * 充值失败
        *

        LogUtils.e(TAG, "**** TrivialDrive Error: " + message);
        // alert("Error: " + message);
    }
    @Override
    public  void charge(String data) {


        productId = data;
        setWaitScreen(true);

 TODO: for security, generate your payload here for verification. See the comments on 2代表谷歌市场
         *        verifyDeveloperPayload() for more info.Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this.


        RetrofitFactory.getInstance().addProduct(userId, productId, ToolUtils.getSystemModel(),2,mEnterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean bean) throws Exception {
                        if (Contact.ERROR_OK.equals(bean.getErrorcode())) {
if(1==1){
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("developerPayload",payLoad);        //本地的
                                jsonObject.put("token","22");
                                jsonObject.put("purchaseToken","33");
                                jsonObject.put("orderId","44");
                                Purchase purchase = new Purchase("1", jsonObject.toString(), "1");
                                saveData(purchase,false);
                                return;
                            }

                            Log.d(TAG,"productId"+productId+"--bean.getMessage"+ bean.getMessage());
                            try {
                                mHelper.launchPurchaseFlow(activity, productId, RC_REQUEST,
                                        mPurchaseFinishedListener, bean.getMessage());
                            } catch (IabHelper.IabAsyncInProgressException e) {
                                ToastUtils.showToast(activity,activity.getString(R.string.google_pay_net_fail));
                                complain("Error launching purchase flow. Another async operation in progress.");
                                setWaitScreen(false);
                            }
                        } else {
                            ToastUtils.showToast(activity,activity.getString(R.string.google_pay_net_fail));
                            complain("错误5" + GsonUtil.parseObjectToJson(bean));
                            setWaitScreen(false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        complain("错误2" + throwable.getMessage());
                        ToastUtils.showToast(activity,activity.getString(R.string.google_pay_net_fail));
                        setWaitScreen(false);
                    }
                });
    }

    @Override
    public void charge(String productid, String price) {
        if (TextUtils.isEmpty(productid)) {
            setWaitScreen(false);
            return;
        }
        if(mProductList!=null){
             //存外面进来的单子
            ArrayList<String> stringPro = new ArrayList<>();
            for (int i = 0; i < mProductList.size(); i++) {
                stringPro.add(mProductList.get(i).getProductId());
            }
            if(!stringPro.contains(productid)){
                RuleBean.DataBean.RechargeSettingBean productBean = new RuleBean.DataBean.RechargeSettingBean();
                productBean.setCurrentprice(price);
                productBean.setProductid(productid);
                mProductList.add(productBean);
            }
        }
        charge(productid);
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            LogUtils.e(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            LogUtils.e(TAG, "Query inventory was successful.");
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().



            // Do we have the premium upgrade?

            // Check for gas delivery -- if we own gas, we should fill up the tank immediately

            final List<Purchase> purchases = new ArrayList<>();
            if (mProductList != null) {
                for (RuleBean.DataBean.RechargeSettingBean productBean : mProductList) {
                    Purchase gasPurchase = inventory.getPurchase(productBean.getProductId());
                    if (gasPurchase != null) {
                        purchases.add(gasPurchase);
                    }
                }
            }else{
                LogUtils.e(TAG, "purchases.size():" + purchases.size());
            }

            StringBuilder sbPayload = new StringBuilder();
            for (int i = 0; i < purchases.size(); i++) {
                String developerPayload = purchases.get(i).getDeveloperPayload();
                if (i == purchases.size() - 1) {
                    sbPayload.append(developerPayload);
                } else {
                    sbPayload.append(developerPayload).append(",");
                }
            }
            LogUtils.e(TAG, "sbPayload:" + sbPayload);

            LogUtils.e(TAG, "purchases:" + GsonUtil.parseListToJson(purchases));

            RetrofitFactory.getInstance().verifyDeveloperPayload(userId, sbPayload.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {

                            LogUtils.e(TAG,"orderpay:"+ GsonUtil.parseObjectToJson(baseBean));
                            if (Contact.ERROR_FAILED.equals(baseBean.getErrorcode())) {
                                //  complain("Error purchasing. Authenticity verification failed.");
                                setWaitScreen(false);
                            } else if (Contact.ERROR_OK.equals(baseBean.getErrorcode())) {
                                String[] split = baseBean.getMessage().split(",");
                                LogUtils.e(TAG,"orderpay3:"+ (split==null) +"111");
                                 List<Purchase> listCus = new ArrayList<>();
                                for (int i = 0; i <split.length; i++) {
                                    //0可支付
                                   if ("0".equals(split[i])) {
                                       listCus.add(purchases.get(i));
                                   }
                                }
                                LogUtils.e(TAG,"orderpay2:"+ GsonUtil.parseListToJson(listCus));
                                if (listCus.size() > 0) {
                                    try {
                                        mHelper.consumeAsync(listCus, mConsumeMultiFinishedListenerr);
                                    } catch (IabHelper.IabAsyncInProgressException e) {
                                        complain("Error consuming gas. Another async operation in progress.");
                                    }
                                } else {
                                    updateUi();
                                    setWaitScreen(false);
                                }
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtils.e(TAG,"orderpay4:"+ throwable.getMessage());

                            updateUi();
                            setWaitScreen(false);
                        }
                    });
        }
    };

    // Callback for when a purchase is finished  支付之后
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, final Purchase purchase) {
            LogUtils.e(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null){
                LogUtils.e(TAG, "mHelper " +"空了");
                return;
            }

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }

            try {

                 for (RuleBean.DataBean.RechargeSettingBean productBean : mProductList) {
                     if (productId .equals(productBean.getProductId())) {
                         logger.logPurchase(BigDecimal.valueOf(Double.parseDouble(productBean.getCurrentprice())), Currency.getInstance("USD"));
                     }
                 }

            }catch (Exception e){
                e.printStackTrace();
            }



            RetrofitFactory.getInstance()
                    .verifyDeveloperPayload(userId, purchase.getDeveloperPayload())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {LogUtils.e(TAG, "到这儿1: ");
                            if (Contact.ERROR_FAILED.equals(baseBean.getErrorcode())) {
                                //  complain("Error purchasing. Authenticity verification failed.");
                                setWaitScreen(false);
                            } else if (Contact.ERROR_OK.equals(baseBean.getErrorcode()) && "0".equals(baseBean.getMessage() + "")) {
                                if (purchase.getSku().equals(productId)) {
                                    // bought 1/4 tank of gas. So consume it.
                                    LogUtils.e(TAG, "Purchase is gas. Starting gas consumption.");
                                    try {
                                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                                    } catch (IabHelper.IabAsyncInProgressException e) {
                                        complain("Error consuming gas. Another async operation in progress.");
                                        setWaitScreen(false);
                                        return;
                                    }
                                }else{
                                    LogUtils.e("GooglePayActivity", GsonUtil.parseObjectToJson(baseBean));
                                    setWaitScreen(false);
                                }
                            }else{
                                LogUtils.e("GooglePayActivity", "111"+ GsonUtil.parseObjectToJson(baseBean));
                                setWaitScreen(false);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            // complain("Error purchasing. Authenticity verification failed.");
                            setWaitScreen(false);
                        }
                    });
        }
    };

    private void setWaitScreen(boolean isshow) {
        if (isshow){
            if (dialogCircleProgress==null) {
                dialogCircleProgress =new DialogCircleProgress(activity);
            }
            InitDialog.setDialogMatchParent(dialogCircleProgress);
            dialogCircleProgress.show();
        }else{
            if (dialogCircleProgress!=null) {
                dialogCircleProgress.dismiss();
            }
        }
    }
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            LogUtils.e(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                saveData(purchase,false);
            } else {
                setWaitScreen(false);
                //  complain("Error while consuming: " + result);
            }
            updateUi();

            LogUtils.e(TAG, "End consumption flow.");
        }
    };

    IabHelper.OnConsumeMultiFinishedListener mConsumeMultiFinishedListenerr = new IabHelper.OnConsumeMultiFinishedListener() {

        @Override
        public void onConsumeMultiFinished(List<Purchase> purchases, List<IabResult> results) {
            LogUtils.e(TAG, "Consumption finished. Purchase: " + purchases + ", result: " + results);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            for (int i = 0; i < results.size(); i++) {
                IabResult result = results.get(i);
                if (result.isSuccess()) {
                    // successfully consumed, so we apply the effects of the item in our
                    if (purchases.size() > i) {
                        saveData(purchases.get(i),true);
                    } else {
                        LogUtils.e("GooglePayActivity", "有问题你");
                    }
                } else {
                    //complain("Error while consuming: " + result);
                }
            }

            updateUi();
            setWaitScreen(false);
            LogUtils.e(TAG, "End consumption flow.");
        }
    };

    //给钱
    private void saveData(final Purchase purchase, final boolean isBuDan) {

        if(!isBuDan && !TextUtils.isEmpty(productId)){
            double price = 0;

           for (RuleBean.DataBean.RechargeSettingBean productBean : mProductList) {
               if (productId .equals(productBean.getProductId())) {
                   price = Double.parseDouble(productBean.getCurrentprice());
               }
           }
        }

        ChargeBean chargeBean = new ChargeBean();
        chargeBean.setDeveloperPayload(purchase.getDeveloperPayload());
        chargeBean.setOrderId(purchase.getOrderId());
        chargeBean.setToken(purchase.getToken());

        ChargeBeanDao chargeBeanDao = EntityManager.getInstance().getmChargeBeanDao();
        //按理这里只有一个
        List<ChargeBean> unique = chargeBeanDao.queryBuilder().
                where(ChargeBeanDao.Properties.DeveloperPayload.eq(purchase.getDeveloperPayload()))
                .list();
        if (unique==null||unique.size()==0) {
            chargeBeanDao.insert(chargeBean);
        }

        LogUtils.e(TAG, "purchaseToken"+purchase.getToken());
        LogUtils.e(TAG, "purchase"+GsonUtil.parseObjectToJson(purchase));

        RetrofitFactory.getInstance().googleUpdateProduct(purchase.getDeveloperPayload(), purchase.getToken(), productId,purchase.getOrderId())
                .subscribeOn(Schedulers.io()).
                flatMap(new Function<BaseBean, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(BaseBean baseBean)  {
                        if (!Contact.ERROR_OK.equals(baseBean.getErrorcode())){
                            return Observable.error(new ErrorCodeException());
                        }
                        return Observable.just(baseBean);
                    }
                }).
                retryWhen(new RetryWithDelay(2,3000)).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {
                        LogUtils.e(TAG, GsonUtil.parseObjectToJson(baseBean));
                        if (Contact.ERROR_OK.equals(baseBean.getErrorcode())&&"2".equals(baseBean.getMessage())) {
                            constumSuccess();
                            ToastUtils.showToast(activity, activity.getString(R.string.charge_success));
                            initData(false);
                            setWaitScreen(false);
                        } else {
                            //其实这里就是 message不为空的 134""啥的
                           // complain(GsonUtil.parseObjectToJson(baseBean));
                            setWaitScreen(false);
                            ToastUtils.showToast(activity, activity.getString(R.string.charge_fail));
                        }
                        //以上两种 都不做重复操作
                        EntityManager.getInstance().getmChargeBeanDao()
                                .queryBuilder().where(ChargeBeanDao.Properties.DeveloperPayload.eq(purchase.getDeveloperPayload()))
                                .buildDelete().executeDeleteWithoutDetachingEntities();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        complain("错误4" + throwable.getMessage());
                        setWaitScreen(false);
                        EntityManager.getInstance().getmChargeBeanDao()
                                .deleteAll();
                        if (!isBuDan) {
                            ToastUtils.showToast(activity,activity. getString(R.string.charge_fail)); 
                        }
                    }
                });
    }


    private void constumSuccess(){
        if (constumCallBack!=null) {
            constumCallBack.constumSuccess();
        }
    }


    @Override
    public void receivedBroadcast() {
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }
    private void updateUi() {

    }

    @Override
    public void onDestroy(){
        if (mBroadcastReceiver != null) {
            activity.unregisterReceiver(mBroadcastReceiver);
        }
        // very important:
        LogUtils.e(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }

    }


   @Override
   public boolean onActivityResult(int requestCode, int resultCode, Intent data){
        if (mHelper == null) return false;
        callBackManager.onActivityResult(requestCode, resultCode, data);
        // Pass on the activity result to the helper for handling
        if (mHelper.handleActivityResult(requestCode, resultCode, data)) {
            return true;
        } else {
            //needHandSelf
            return false;
        }
    }

    @Override
    public void setEnterId(int enterId) {
       mEnterId = enterId;
       //顺便更新下数据好了

    }



}
*/
