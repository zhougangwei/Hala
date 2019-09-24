package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

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
     * data : {"mainland_recharge_setting":[{"coin":990,"gift":50,"amount":99,"hot":true,"productId":"M1"},{"coin":290,"gift":0,"amount":29,"hot":false,"productId":"M2"},{"coin":590,"gift":20,"amount":59,"hot":false,"productId":"M3"},{"coin":2990,"gift":200,"amount":299,"hot":false,"productId":"M4"},{"coin":5990,"gift":500,"amount":599,"hot":false,"productId":"M5"},{"coin":9990,"gift":1000,"amount":999,"hot":false,"productId":"M6"},{"coin":19990,"gift":2500,"amount":1999,"hot":false,"productId":"M7"}],"video_cpm_setting":[100,200,300],"audio_cpm_setting":[50,100,150],"abroad_apple_recharge_setting":[{"coin":100,"amount":0.99,"productId":"C_100_1"},{"coin":1000,"amount":8.99,"productId":"C_1000_9"},{"coin":5000,"amount":43.99,"productId":"C_5500_48"},{"coin":12000,"amount":99.99,"productId":"C_12000_100"},{"coin":50000,"amount":399.99,"productId":"C_50000_400"}],"pic_cpm_setting":[50,100,150],"chat_cpm_setting":[10,20,30]}
     */

    @SerializedName("data")
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("mainland_recharge_setting")
        private List<MainlandRechargeSettingBean> mainlandRechargeSetting;
        @SerializedName("video_cpm_setting")
        private List<Integer> videoCpmSetting;
        @SerializedName("audio_cpm_setting")
        private List<Integer> audioCpmSetting;
        @SerializedName("abroad_apple_recharge_setting")
        private List<AbroadAppleRechargeSettingBean> abroadAppleRechargeSetting;
        @SerializedName("pic_cpm_setting")
        private List<Integer> picCpmSetting;
        @SerializedName("chat_cpm_setting")
        private List<Integer> chatCpmSetting;

        public List<MainlandRechargeSettingBean> getMainlandRechargeSetting() {
            return mainlandRechargeSetting;
        }

        public void setMainlandRechargeSetting(List<MainlandRechargeSettingBean> mainlandRechargeSetting) {
            this.mainlandRechargeSetting = mainlandRechargeSetting;
        }

        public List<Integer> getVideoCpmSetting() {
            return videoCpmSetting;
        }

        public void setVideoCpmSetting(List<Integer> videoCpmSetting) {
            this.videoCpmSetting = videoCpmSetting;
        }

        public List<Integer> getAudioCpmSetting() {
            return audioCpmSetting;
        }

        public void setAudioCpmSetting(List<Integer> audioCpmSetting) {
            this.audioCpmSetting = audioCpmSetting;
        }

        public List<AbroadAppleRechargeSettingBean> getAbroadAppleRechargeSetting() {
            return abroadAppleRechargeSetting;
        }

        public void setAbroadAppleRechargeSetting(List<AbroadAppleRechargeSettingBean> abroadAppleRechargeSetting) {
            this.abroadAppleRechargeSetting = abroadAppleRechargeSetting;
        }

        public List<Integer> getPicCpmSetting() {
            return picCpmSetting;
        }

        public void setPicCpmSetting(List<Integer> picCpmSetting) {
            this.picCpmSetting = picCpmSetting;
        }

        public List<Integer> getChatCpmSetting() {
            return chatCpmSetting;
        }

        public void setChatCpmSetting(List<Integer> chatCpmSetting) {
            this.chatCpmSetting = chatCpmSetting;
        }

        public static class MainlandRechargeSettingBean {
            /**
             * coin : 990
             * gift : 50
             * amount : 99.0
             * hot : true
             * productId : M1
             */

            @SerializedName("coin")
            private int coin;
            @SerializedName("gift")
            private int gift;
            @SerializedName("amount")
            private double amount;
            @SerializedName("hot")
            private boolean hot;
            @SerializedName("productId")
            private String productId;
            private boolean clicked;

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public int getGift() {
                return gift;
            }

            public void setGift(int gift) {
                this.gift = gift;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public boolean isHot() {
                return hot;
            }

            public void setHot(boolean hot) {
                this.hot = hot;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public void setClicked(boolean clicked) {
                this.clicked = clicked;
            }

            public boolean getClicked() {
                return clicked;
            }
        }

        public static class AbroadAppleRechargeSettingBean {
            /**
             * coin : 100
             * amount : 0.99
             * productId : C_100_1
             */

            @SerializedName("coin")
            private int coin;
            @SerializedName("amount")
            private double amount;
            @SerializedName("productId")
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
