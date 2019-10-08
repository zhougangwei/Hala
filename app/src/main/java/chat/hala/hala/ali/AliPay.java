package chat.hala.hala.ali;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import chat.hala.hala.ali.demo.util.OrderInfoUtil2_0;
import chat.hala.hala.bean.AliPayBean;
import chat.hala.hala.bean.BaseBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wjy on 2019/9/30/030.
 */
public class AliPay {

    //public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9AyMyF1pMdsU98WwQUfTy9Kyjxu+bf9fv5VpnxS6kgiMWexNiMoNVP6MFu4bkOf0MAB0j8rR3W49Onn+JgM8BmwrlTsqXwRvapYOsTNncZ0UpdpYOY0LbdeGjFhmpkknbOrCP0KCNWo99d2rta7wARKRQ2qIPzjm1O1TbkUCUSlYbOvYltdcvpwrlF7Iv4Qd56993g0v91cmDi4TROr1uNaLiQKklq0pUjyJZKRQRbbv0JPYUlDf/8b1XtrXJ/wNUU9Ks2JH+C/XH8Y6T9alwqG8Q37vkq/bTAcVsmJpAG35u/btatORZOhv8c3B2gdzS5VioSKGGDVMRcrcimi7xAgMBAAECggEAKZLaF/d4/h6VrdU/TquoNBRLMM7d/xb0mSiaZP7DDp4tN5GkptGzf3m60tT3i5WRqiUQQ8rrlOFHuSQ1qznwzkqa7+sPtqdcCBCl0K0qskbouIED6nA80WaxVZzT0i+CHT1gfpGF7vF/ZDpzA3vR40E13y7nvJBAszM0rALb8IGz0jjNpm1+BVYd55o7tcgwd+JrZcKG1zy4xyGABZgsY6bL2ivmILr3GJT/bZeflCHFAhP2+lnbhVA1zUYMa2yw09QJimFBPAlb+2w95euEe/UH4K+g08HWjBhMJnYRDy9zlUgw5HfUMJICNbzN907Wx1hN5NiHkH31yv+F/OCozQKBgQD3pJtZ6Oxeq8nU3nX3602ugXnQCXqKczRRj1GlDEAae7f6oK0CFw8S4OebKpOrO05UXz6dILsa8oyXX+bH0HALhxR4RYe3Gr0A5+ne2kBZ4w/QINcpn97Hlx3maOdnb3UwPXJ6LZ3ieSM6TfbMeWnCNXydOsFSSxxBHVnIVihAKwKBgQDDZAS3VYFgvQvp5ZjXqMHobbrVMadsrJOUnr7n+PmJJWdFiGxGYdQbry6NOjcNKtiUABPYgwa9RgQ3U/fAV8T8use8453duWdbgoYAokI0qDnXrv1x542wjuZV2JYo3YimzJ87DiPxMTpeVqYz2sTY0ECgoF2oHXOzy2e/rRajUwKBgQDxbkx6kmfnQEoMOzn9kyzvxZERrjwGwJwwK66oX1Ibv6Q1NvKUyBBNEnEaSJ8g4DfUb+rmW2OuuOsyJTAke+TQiEf/naHbdLjxKihKuLLmL0VaXroF/bO/GGfxGzMHF5XDixSRYum7QuiCTkIfRPtDeMvdqcuRxGJuZQQEGWMycwKBgFrybkOL2EnYFPiNN0SCt6dhlApyL1UlSxhtf7zoJfaRY215H7TvClTMiOWRyFgnrmHNW293JabqNUIvom10LSvaIOiqJiwzhYmRyp/25swt1aawPCZNuqUJDuu8gcCJxXamXiOV5GL7IAkTWg9e8CtfXAWoP4/mDZoNvr/4aEYjAoGBAJigVw9n9mlooexRlvLZVizJx54oQsialnl7Fj5ytQSUzd4CMb4Wo61LyLbF+6XveurrDXy5p5xZVTztM0wekXKKbVDnDvW66/9B9BgvLgYtMNM9eKWA4R0IM8It3lrFTtNMIq6YgCBbCpukv8R5G4bdBDjwV9JaGpKRlwJ15aaM";
    public static final String APPID = "2019092467797295";

    @SuppressLint("CheckResult")
    public static void payV2(final Activity activity, String productId, final PayBack payBack) {

   /*     Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);*/
        RetrofitFactory.getInstance().startAliCharge(productId)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<AliPayBean>() {
                    @Override
                    public boolean test(AliPayBean aliPayBean) throws Exception {
                        return ResultUtils.cheekSuccess(aliPayBean);
                    }
                })
                .map(new Function<AliPayBean, String>() {
                    @Override
                    public String apply(AliPayBean aliPayBean) throws Exception {
                        return aliPayBean.getData();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String params) throws Exception {
                        PayTask alipay = new PayTask(activity);
                        Map<String, String> result = alipay.payV2(params, true);
                        payBack.backResult(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });




    }
    public interface PayBack{
        void backResult(Map<String, String> result);
    }


}
