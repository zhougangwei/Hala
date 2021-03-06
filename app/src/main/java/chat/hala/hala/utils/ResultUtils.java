package chat.hala.hala.utils;

import android.text.TextUtils;

import chat.hala.hala.base.Contact;
import chat.hala.hala.bean.BaseBean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 11:36
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class ResultUtils {
    public static boolean cheekSuccess(BaseBean bean){
        if (Contact.REPONSE_CODE_SUCCESS!=bean.code) {
            return false;
        }
        return true;
    }


    public static boolean isNoMoney(BaseBean bean) {
        if (Contact.REPONSE_CODE_CALL_NO_MONEY==bean.code) {
            return true;
        }
        return false;
    }
    public static boolean isInCall(BaseBean bean) {
        if (Contact.REPONSE_CODE_CALL_IN_CALL==bean.code) {
            return true;
        }
        return false;
    }


    private boolean judgeEmpty(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            if (TextUtils.isEmpty(strings[i])) {
                return false;
            }
        }
        return true;
    }
}
