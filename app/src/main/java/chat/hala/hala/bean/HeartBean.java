package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 19:57
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class HeartBean extends BaseBean {

    /**
     * data : {"callInfoState":"success","restSeconds":12905}
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
        /**
         * callInfoState : success
         * restSeconds : 12905
         */
        public enum CallInfoState {
            countdown,
            hangup,
            success,
        }


        @SerializedName("callInfoState")
        private String callInfoState;
        @SerializedName("restSeconds")
        private int restSeconds;

        public String getCallInfoState() {
            return callInfoState;
        }

        public void setCallInfoState(String callInfoState) {
            this.callInfoState = callInfoState;
        }

        public int getRestSeconds() {
            return restSeconds;
        }

        public void setRestSeconds(int restSeconds) {
            this.restSeconds = restSeconds;
        }
    }
}
