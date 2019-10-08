package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/9/30/030.
 */
public class AliPayBean extends  BaseBean {
    /**
     * data : alipay_sdk=alipay-sdk-java-4.7.12.ALL&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22tradePay00000009%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign=VAR%2BQ0oRlvJWaxvSeZOGR6qrKU97QZIO2u8COBOhDV2aFueg0lfpl9RdnIIlMiPOvpUf8BqxvY4gNCrG%2Fh733A%2B18kX1vWsFOvIDrKdWSx8GSHrtxIdTZ0P%2B2hND%2B3srM05zvFXn7%2BlzHifRsYJ7m%2BQ40w2wwP0GXW1lYgyMngUVcDKRzljmQqz2L3JnMt8z1iW7ZJsVBLyim7K2prQUFkvN69P2QNxs4ZOdMWOb1fnhp%2FLbLErgVGeWCy5zL%2BdRMx%2FkdTHv2o6YbZv5zCd26eoZgpngkmJ0fkErLSO1m1Qo1%2ByM%2B6Y74j5lm1%2FWFmO0PphoIjuY%2Bv39RgKmyg50SA%3D%3D&sign_type=RSA2&timestamp=2019-09-30+13%3A48%3A29&version=1.0
     */

    @SerializedName("data")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
