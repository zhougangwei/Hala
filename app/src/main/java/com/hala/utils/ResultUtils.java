package com.hala.utils;

import com.hala.base.Contact;
import com.hala.bean.BaseBean;
import com.hala.bean.CallBean;

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
}
