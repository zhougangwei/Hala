package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/9/23/023.
 */
public class FamilyAnchorDetailBean extends BaseBean {
    /**
     * data : {"sumLiveTimes":0,"zoneLiveTimes":0,"mediaUrl":"http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg","zoneAnchorWorth":0,"name":"?","sumAnchorWorth":0}
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
         * sumLiveTimes : 0
         * zoneLiveTimes : 0
         * mediaUrl : http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg
         * zoneAnchorWorth : 0
         * name : ?
         * sumAnchorWorth : 0
         */

        @SerializedName("sumLiveTimes")
        private int sumLiveTimes;
        @SerializedName("zoneLiveTimes")
        private int zoneLiveTimes;
        @SerializedName("mediaUrl")
        private String mediaUrl;
        @SerializedName("zoneAnchorWorth")
        private int zoneAnchorWorth;
        @SerializedName("name")
        private String name;
        @SerializedName("sumAnchorWorth")
        private int sumAnchorWorth;

        public int getSumLiveTimes() {
            return sumLiveTimes;
        }

        public void setSumLiveTimes(int sumLiveTimes) {
            this.sumLiveTimes = sumLiveTimes;
        }

        public int getZoneLiveTimes() {
            return zoneLiveTimes;
        }

        public void setZoneLiveTimes(int zoneLiveTimes) {
            this.zoneLiveTimes = zoneLiveTimes;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public int getZoneAnchorWorth() {
            return zoneAnchorWorth;
        }

        public void setZoneAnchorWorth(int zoneAnchorWorth) {
            this.zoneAnchorWorth = zoneAnchorWorth;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSumAnchorWorth() {
            return sumAnchorWorth;
        }

        public void setSumAnchorWorth(int sumAnchorWorth) {
            this.sumAnchorWorth = sumAnchorWorth;
        }
    }
}
