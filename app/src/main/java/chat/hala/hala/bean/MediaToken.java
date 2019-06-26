package chat.hala.hala.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 20:35
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class MediaToken extends BaseBean {
    /**
     * data : {"agora_media_token":"0063ca9a2cd0d144c209c5ca85e3a7440e4IAB+iRjyL1ukZDhM0PcbWunuc3Xgza1NR9Z3drMsOpBl3n3R6yXRJB3PEADuhiJRrv/5XAEAAQCAUQEA"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * agora_media_token : 0063ca9a2cd0d144c209c5ca85e3a7440e4IAB+iRjyL1ukZDhM0PcbWunuc3Xgza1NR9Z3drMsOpBl3n3R6yXRJB3PEADuhiJRrv/5XAEAAQCAUQEA
         */

        private String agora_media_token;

        public String getAgora_media_token() {
            return agora_media_token;
        }

        public void setAgora_media_token(String agora_media_token) {
            this.agora_media_token = agora_media_token;
        }
    }
}
