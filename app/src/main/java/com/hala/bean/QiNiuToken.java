package com.hala.bean;

import com.google.gson.annotations.SerializedName;

public class QiNiuToken extends BaseBean {
    /**
     * data : {"starchat-anchor":{"url":"http://ps5zyirgy.bkt.clouddn.com","token":"iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:OyW8wAMqU83MyQGhIQaNGNBhYAs=:eyJzY29wZSI6InN0YXJjaGF0LWFuY2hvciIsImRlYWRsaW5lIjoxNTU5ODI3NDk0fQ=="},"starchat-member":{"url":"http://ps5z2mui2.bkt.clouddn.com","token":"iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:wGRgJhVrBWj2-KDoKLPa39zyOPs=:eyJzY29wZSI6InN0YXJjaGF0LW1lbWJlciIsImRlYWRsaW5lIjoxNTU5ODI3NDk0fQ=="},"starchat-feedback":{"url":"http://ps5zsqfgp.bkt.clouddn.com","token":"iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:JYrHxe4RWCptM4eftpBFdbaiVfs=:eyJzY29wZSI6InN0YXJjaGF0LWZlZWRiYWNrIiwiZGVhZGxpbmUiOjE1NTk4Mjc0OTR9"}}
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
         * starchat-anchor : {"url":"http://ps5zyirgy.bkt.clouddn.com","token":"iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:OyW8wAMqU83MyQGhIQaNGNBhYAs=:eyJzY29wZSI6InN0YXJjaGF0LWFuY2hvciIsImRlYWRsaW5lIjoxNTU5ODI3NDk0fQ=="}
         * starchat-member : {"url":"http://ps5z2mui2.bkt.clouddn.com","token":"iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:wGRgJhVrBWj2-KDoKLPa39zyOPs=:eyJzY29wZSI6InN0YXJjaGF0LW1lbWJlciIsImRlYWRsaW5lIjoxNTU5ODI3NDk0fQ=="}
         * starchat-feedback : {"url":"http://ps5zsqfgp.bkt.clouddn.com","token":"iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:JYrHxe4RWCptM4eftpBFdbaiVfs=:eyJzY29wZSI6InN0YXJjaGF0LWZlZWRiYWNrIiwiZGVhZGxpbmUiOjE1NTk4Mjc0OTR9"}
         */

        @SerializedName("starchat-anchor")
        private StarchatanchorBean starchatanchor;
        @SerializedName("starchat-member")
        private StarchatmemberBean starchatmember;
        @SerializedName("starchat-feedback")
        private StarchatfeedbackBean starchatfeedback;

        public StarchatanchorBean getStarchatanchor() {
            return starchatanchor;
        }

        public void setStarchatanchor(StarchatanchorBean starchatanchor) {
            this.starchatanchor = starchatanchor;
        }

        public StarchatmemberBean getStarchatmember() {
            return starchatmember;
        }

        public void setStarchatmember(StarchatmemberBean starchatmember) {
            this.starchatmember = starchatmember;
        }

        public StarchatfeedbackBean getStarchatfeedback() {
            return starchatfeedback;
        }

        public void setStarchatfeedback(StarchatfeedbackBean starchatfeedback) {
            this.starchatfeedback = starchatfeedback;
        }

        public static class StarchatanchorBean {
            /**
             * url : http://ps5zyirgy.bkt.clouddn.com
             * token : iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:OyW8wAMqU83MyQGhIQaNGNBhYAs=:eyJzY29wZSI6InN0YXJjaGF0LWFuY2hvciIsImRlYWRsaW5lIjoxNTU5ODI3NDk0fQ==
             */

            private String url;
            private String token;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }

        public static class StarchatmemberBean {
            /**
             * url : http://ps5z2mui2.bkt.clouddn.com
             * token : iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:wGRgJhVrBWj2-KDoKLPa39zyOPs=:eyJzY29wZSI6InN0YXJjaGF0LW1lbWJlciIsImRlYWRsaW5lIjoxNTU5ODI3NDk0fQ==
             */

            private String url;
            private String token;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }

        public static class StarchatfeedbackBean {
            /**
             * url : http://ps5zsqfgp.bkt.clouddn.com
             * token : iUupkhLCgeH_MZtg1bKM7d1KsLTFuTmD-qczkaRo:JYrHxe4RWCptM4eftpBFdbaiVfs=:eyJzY29wZSI6InN0YXJjaGF0LWZlZWRiYWNrIiwiZGVhZGxpbmUiOjE1NTk4Mjc0OTR9
             */

            private String url;
            private String token;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
