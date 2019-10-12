package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnchorBean extends BaseBean {


    /**
     * data : {"memberId":5,"characterId":"02544174","nickname":"周","gender":"secret","height":160,"weight":50,"birthDate":"1990-01-01","residentialPlace":"杭州","marking":80,"fansCount":0,"introduction":"你号","autograph":"你号","online":false,"available":true,"tags":[],"setting":{"videoCpm":20,"audioCpm":10,"chatCpm":1,"videoNotify":true,"audioNotify":true,"chatNotify":true},"album":[{"id":2,"mediaUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","sortby":0}],"anchorId":1,"answerRate":"0.0%","createdAtDate":"2019-08-12","lastActiveMinuteGap":1014}
     */

    @SerializedName("data")
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends LoginBean.DataBean.MemberBean {
        @SerializedName("marking")
        private int marking;
        @SerializedName("answerRate")
        private String answerRate;
        private boolean following;
        private boolean blocking;

        @SerializedName("tags")
        private List<AnchorTagBean.DataBean> tags;
        @SerializedName("createdAtDate")
        private String createdAtDate;

        @SerializedName("height")
        private int height;
        @SerializedName("weight")
        private int weight;

        public int getMarking() {
            return marking;
        }

        public void setMarking(int marking) {
            this.marking = marking;
        }

        public String getAnswerRate() {
            return answerRate == null ? "" : answerRate;
        }

        public void setAnswerRate(String answerRate) {
            this.answerRate = answerRate == null ? "" : answerRate;
        }

        public boolean isFollowing() {
            return following;
        }

        public void setFollowing(boolean following) {
            this.following = following;
        }

        public boolean isBlocking() {
            return blocking;
        }

        public void setBlocking(boolean blocking) {
            this.blocking = blocking;
        }
        public List<AnchorTagBean.DataBean> getTags() {
            return tags;
        }

        public void setTags(List<AnchorTagBean.DataBean> tags) {
            this.tags = tags;
        }

        public String getCreatedAtDate() {
            return createdAtDate == null ? "" : createdAtDate;
        }

        public void setCreatedAtDate(String createdAtDate) {
            this.createdAtDate = createdAtDate == null ? "" : createdAtDate;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
