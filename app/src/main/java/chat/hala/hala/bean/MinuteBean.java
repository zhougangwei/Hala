package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/8/22/022.
 */
public class MinuteBean extends BaseBean{
    /**
     * data : {"anchorId":19,"memberId":15,"worth":1,"category":"text"}
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
         * anchorId : 19
         * memberId : 15
         * worth : 1
         * category : text
         */

        @SerializedName("anchorId")
        private int anchorId;
        @SerializedName("memberId")
        private int memberId;
        @SerializedName("worth")
        private int worth;
        @SerializedName("category")
        private String category;

        public int getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(int anchorId) {
            this.anchorId = anchorId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public int getWorth() {
            return worth;
        }

        public void setWorth(int worth) {
            this.worth = worth;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "anchorId=" + anchorId +
                    ", memberId=" + memberId +
                    ", worth=" + worth +
                    ", category='" + category + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MinuteBean{" +
                "data=" + data +
                '}';
    }
}
