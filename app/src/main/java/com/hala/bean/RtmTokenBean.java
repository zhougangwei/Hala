package com.hala.bean;

public class RtmTokenBean extends BaseBean {


    /**
     * data : {"agora_rtm_token":"0063ca9a2cd0d144c209c5ca85e3a7440e4IABfCPtUCd3cMLV7Nnxqps8Tpc7+iVw2AbFxwujpZie5FLOaTtMAAAAAEAAjO4LUu0MKXQEA6ANTshwA"}
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
         * agora_rtm_token : 0063ca9a2cd0d144c209c5ca85e3a7440e4IABfCPtUCd3cMLV7Nnxqps8Tpc7+iVw2AbFxwujpZie5FLOaTtMAAAAAEAAjO4LUu0MKXQEA6ANTshwA
         */

        private String agora_rtm_token;

        public String getAgora_rtm_token() {
            return agora_rtm_token;
        }

        public void setAgora_rtm_token(String agora_rtm_token) {
            this.agora_rtm_token = agora_rtm_token;
        }
    }
}
