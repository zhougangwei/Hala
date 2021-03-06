package chat.hala.hala.manager;

import com.blankj.utilcode.utils.LogUtils;

import java.security.PublicKey;

import chat.hala.hala.avchat.AvchatInfo;
import chat.hala.hala.bean.MinuteBean;
import chat.hala.hala.http.BaseCosumer;
import chat.hala.hala.http.ProxyPostHttpRequest;
import chat.hala.hala.http.RetrofitFactory;
import chat.hala.hala.utils.ResultUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/8/1 0001 14:20
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/8/1 0001$
 * @ 更新描述  ${TODO}
 */
public class MoneyHelper {

    public static int money;

    public static int getMoney() {
        return money;
    }

    public static void setMoney(int money) {
        MoneyHelper.money = money;
    }
    /*
    * 判断当前用户是否有钱
    * */
    public static boolean judgeMoney() {
        return money>0;
    }

    public static void costMessageMoney(int anchorId) {
        RetrofitFactory.getInstance().minuteCharge(anchorId, ProxyPostHttpRequest.getInstance().minuteCharge("text"))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseCosumer<MinuteBean>() {
                    @Override
                    public void onGetData(MinuteBean minuteBean) {
                        if (ResultUtils.cheekSuccess(minuteBean)) {
                            LogUtils.e("付费成功"+minuteBean.toString());
                        }
                    }
                });
    }

    public static void costPicMoney(String anchorId, final PayBack payBack) {
        RetrofitFactory.getInstance().minuteCharge(Integer.parseInt(anchorId), ProxyPostHttpRequest.getInstance().minuteCharge("image"))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseCosumer<MinuteBean>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        payBack.fail();
                    }
                    @Override
                    public void onGetData(MinuteBean minuteBean) {
                        if (ResultUtils.cheekSuccess(minuteBean)) {
                            payBack.success();
                            LogUtils.e("付费成功"+minuteBean.toString());
                            return;
                        }
                        payBack.fail();
                    }
                });
    }

   public interface PayBack{
         void success();
        void fail();
    }
}
