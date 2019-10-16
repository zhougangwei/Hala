package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/8/16/016.
 */
public class VersionBean extends BaseBean {

    /**
     * data : {"android":{"e":false,"v":"1.0.0","l":"http://baidu.com/","m":false,"n":true,"desc":"更新了!!!"},"ios":{"e":false,"v":"1.0.2","m":false,"n":false,"desc":"更新了!"}}
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
         * android : {"e":false,"v":"1.0.0","l":"http://baidu.com/","m":false,"n":true,"desc":"更新了!!!"}
         * ios : {"e":false,"v":"1.0.2","m":false,"n":false,"desc":"更新了!"}
         */

        @SerializedName("android")
        private AndroidBean android;
        @SerializedName("ios")
        private IosBean ios;

        public AndroidBean getAndroid() {
            return android;
        }

        public void setAndroid(AndroidBean android) {
            this.android = android;
        }

        public IosBean getIos() {
            return ios;
        }

        public void setIos(IosBean ios) {
            this.ios = ios;
        }

        public static class AndroidBean {
            /**
             * e : false
             * v : 1.0.0
             * l : http://baidu.com/
             * m : false
             * n : true
             * desc : 更新了!!!
             */

            @SerializedName("e")
            private boolean e;
            @SerializedName("v")
            private String v;
            @SerializedName("l")
            private String l;
            @SerializedName("m")
            private boolean m;
            @SerializedName("n")
            private boolean n;
            @SerializedName("desc")
            private String desc;

            public boolean isE() {
                return e;
            }

            public void setE(boolean e) {
                this.e = e;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }

            public String getL() {
                return l;
            }

            public void setL(String l) {
                this.l = l;
            }

            public boolean isM() {
                return m;
            }

            public void setM(boolean m) {
                this.m = m;
            }

            public boolean isN() {
                return n;
            }

            public void setN(boolean n) {
                this.n = n;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class IosBean {
            /**
             * e : false
             * v : 1.0.2
             * m : false
             * n : false
             * desc : 更新了!
             */

            @SerializedName("e")
            private boolean e;
            @SerializedName("v")
            private String v;
            @SerializedName("m")
            private boolean m;
            @SerializedName("n")
            private boolean n;
            @SerializedName("desc")
            private String desc;

            public boolean isE() {
                return e;
            }

            public void setE(boolean e) {
                this.e = e;
            }

            public String getV() {
                return v;
            }

            public void setV(String v) {
                this.v = v;
            }

            public boolean isM() {
                return m;
            }

            public void setM(boolean m) {
                this.m = m;
            }

            public boolean isN() {
                return n;
            }

            public void setN(boolean n) {
                this.n = n;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
