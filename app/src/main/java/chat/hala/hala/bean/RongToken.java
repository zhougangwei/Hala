package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/8/16/016.
 */
public class RongToken extends BaseBean {
    /**
     * data : {"rong_cloud_token":"UlvzfykvapiIlMQ9XIV6eV6AsbGff9KvUT62Noch30mb6n+CHOf4VYU5A2i2vgM9+cNGMDsDspBc1gC7seANjA=="}
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
         * rong_cloud_token : UlvzfykvapiIlMQ9XIV6eV6AsbGff9KvUT62Noch30mb6n+CHOf4VYU5A2i2vgM9+cNGMDsDspBc1gC7seANjA==
         */

        @SerializedName("rong_cloud_token")
        private String rongCloudToken;

        public String getRongCloudToken() {
            return rongCloudToken;
        }

        public void setRongCloudToken(String rongCloudToken) {
            this.rongCloudToken = rongCloudToken;
        }
    }
}
