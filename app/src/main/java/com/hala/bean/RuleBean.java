package com.hala.bean;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 11:33
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class RuleBean extends BaseBean {
    /**
     * data : {"cpm_setting":[100,200,300],"recharge_setting":[{"coin":100,"amount":0.99,"productId":"SC100_1"},{"coin":1000,"amount":8.99,"productId":"SC1000_9"},{"coin":5000,"amount":43.99,"productId":"SC5000_44"},{"coin":12000,"amount":99.99,"productId":"SC12000_100"},{"coin":50000,"amount":399.99,"productId":"SC50000_400"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<Integer>             cpm_setting;
        private List<RechargeSettingBean> recharge_setting;

        public List<Integer> getCpm_setting() {
            return cpm_setting;
        }

        public void setCpm_setting(List<Integer> cpm_setting) {
            this.cpm_setting = cpm_setting;
        }

        public List<RechargeSettingBean> getRecharge_setting() {
            return recharge_setting;
        }

        public void setRecharge_setting(List<RechargeSettingBean> recharge_setting) {
            this.recharge_setting = recharge_setting;
        }

        public static class RechargeSettingBean {
            /**
             * coin : 100
             * amount : 0.99
             * productId : SC100_1
             */

            private int coin;
            private double amount;
            private String productId;

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }
        }
    }
}
