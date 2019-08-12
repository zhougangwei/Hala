package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/8/12/012.
 */
public class BeStarResultBean extends BaseBean {

    public static final String BESTAR_OPEN = "open";// 可申请
    public static final String BESTAR_WAITING = "waiting";// 等待审核
    public static final String BESTAR_PASS = "pass";// 审核通过
    public static final String BESTAR_REJECTED = "rejected";// 审核失败

    /**
     * data : {"state":"rejected","info":"video"}
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
         * state : rejected
         * info : video
         */

        @SerializedName("state")
        private String state;
        @SerializedName("info")
        private String info;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
