package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/9/23/023.
 */
public class FamilyBeanB extends BaseBean {
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


        @SerializedName("anthorNumber")
        private Integer anthorNumber;
        @SerializedName("sumLiveTimes")
        private Integer sumLiveTimes;
        @SerializedName("familyName")
        private String familyName;
        @SerializedName("sumAnchorWorth")
        private Integer sumAnchorWorth;
        @SerializedName("sumLeaderWorth")
        private Integer sumLeaderWorth;

        public Integer getAnthorNumber() {
            return anthorNumber;
        }

        public void setAnthorNumber(Integer anthorNumber) {
            this.anthorNumber = anthorNumber;
        }

        public Integer getSumLiveTimes() {
            return  sumLiveTimes;
        }

        public void setSumLiveTimes(Integer sumLiveTimes) {
            this.sumLiveTimes = sumLiveTimes ;
        }

        public String getFamilyName() {
            return familyName == null ? "" : familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName == null ? "" : familyName;
        }

        public Integer getSumAnchorWorth() {
            return sumAnchorWorth;
        }

        public void setSumAnchorWorth(Integer sumAnchorWorth) {
            this.sumAnchorWorth = sumAnchorWorth;
        }

        public Integer getSumLeaderWorth() {
            return sumLeaderWorth;
        }

        public void setSumLeaderWorth(Integer sumLeaderWorth) {
            this.sumLeaderWorth = sumLeaderWorth;
        }
    }
}
