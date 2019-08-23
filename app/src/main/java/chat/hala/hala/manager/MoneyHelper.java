package chat.hala.hala.manager;

import com.blankj.utilcode.utils.LogUtils;

import chat.hala.hala.bean.MinuteBean;
import chat.hala.hala.http.BaseCosumer;
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

    /*
    * 判断当前用户是否有钱
    * */
    public static boolean judgeMoney() {
        return false;
    }

    public static void costMoney(int anchorId) {
        RetrofitFactory.getInstance().minuteCharge(anchorId,"text")
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
}
