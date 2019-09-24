package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/9/23/023.
 */
public class FamilyBeanA extends BaseBean {
    /**
     * data : {"familyId":1,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","familyName":"???","sumDurSeconds":0,"commission":0,"familyNumbers":0,"sumWorth":0}
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
         * familyId : 1
         * mediaUrl : http://starchat.general.7halachat.com/member_avatar_test.png
         * familyName : ???
         * sumDurSeconds : 0
         * commission : 0
         * familyNumbers : 0
         * sumWorth : 0
         */

        @SerializedName("familyId")
        private int familyId;
        @SerializedName("mediaUrl")
        private String mediaUrl;
        @SerializedName("familyName")
        private String familyName;
        @SerializedName("sumDurSeconds")
        private int sumDurSeconds;
        @SerializedName("commission")
        private int commission;
        @SerializedName("familyNumbers")
        private int familyNumbers;
        @SerializedName("sumWorth")
        private int sumWorth;

        public int getFamilyId() {
            return familyId;
        }

        public void setFamilyId(int familyId) {
            this.familyId = familyId;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }

        public int getSumDurSeconds() {
            return sumDurSeconds;
        }

        public void setSumDurSeconds(int sumDurSeconds) {
            this.sumDurSeconds = sumDurSeconds;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }

        public int getFamilyNumbers() {
            return familyNumbers;
        }

        public void setFamilyNumbers(int familyNumbers) {
            this.familyNumbers = familyNumbers;
        }

        public int getSumWorth() {
            return sumWorth;
        }

        public void setSumWorth(int sumWorth) {
            this.sumWorth = sumWorth;
        }
    }
}
