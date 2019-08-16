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

    public static class DataBean {
        /**
         * memberId : 5
         * characterId : 02544174
         * nickname : 周
         * gender : secret
         * height : 160
         * weight : 50
         * birthDate : 1990-01-01
         * residentialPlace : 杭州
         * marking : 80
         * fansCount : 0
         * introduction : 你号
         * autograph : 你号
         * online : false
         * available : true
         * tags : []
         * setting : {"videoCpm":20,"audioCpm":10,"chatCpm":1,"videoNotify":true,"audioNotify":true,"chatNotify":true}
         * album : [{"id":2,"mediaUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","sortby":0}]
         * anchorId : 1
         * answerRate : 0.0%
         * createdAtDate : 2019-08-12
         * lastActiveMinuteGap : 1014
         * spent :0
         * following:true
         * blocking:false
         */

        @SerializedName("memberId")
        private int memberId;
        @SerializedName("characterId")
        private String characterId;
        @SerializedName("nickname")
        private String nickname;
        @SerializedName("gender")
        private String gender;
        @SerializedName("height")
        private int height;
        @SerializedName("weight")
        private int weight;
        @SerializedName("birthDate")
        private String birthDate;
        @SerializedName("residentialPlace")
        private String residentialPlace;
        @SerializedName("marking")
        private int marking;
        @SerializedName("fansCount")
        private int fansCount;
        @SerializedName("introduction")
        private String introduction;
        @SerializedName("autograph")
        private String autograph;
        @SerializedName("online")
        private boolean online;
        @SerializedName("available")
        private boolean available;
        @SerializedName("setting")
        private SettingBean setting;
        @SerializedName("anchorId")
        private int anchorId;
        @SerializedName("answerRate")
        private String answerRate;
        @SerializedName("createdAtDate")
        private String createdAtDate;
        @SerializedName("lastActiveMinuteGap")
        private int lastActiveMinuteGap;
        @SerializedName("tags")
        private List<AnchorTagBean.DataBean> tags;
        @SerializedName("album")
        private List<AlbumBean> album;

        private int spent;
        private int followingCount;

        private boolean following;
        private boolean blocking;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getCharacterId() {
            return characterId;
        }

        public void setCharacterId(String characterId) {
            this.characterId = characterId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getResidentialPlace() {
            return residentialPlace;
        }

        public void setResidentialPlace(String residentialPlace) {
            this.residentialPlace = residentialPlace;
        }

        public int getMarking() {
            return marking;
        }

        public void setMarking(int marking) {
            this.marking = marking;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public boolean isOnline() {
            return online;
        }

        public void setOnline(boolean online) {
            this.online = online;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public SettingBean getSetting() {
            return setting;
        }

        public void setSetting(SettingBean setting) {
            this.setting = setting;
        }

        public int getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(int anchorId) {
            this.anchorId = anchorId;
        }

        public String getAnswerRate() {
            return answerRate;
        }

        public void setAnswerRate(String answerRate) {
            this.answerRate = answerRate;
        }

        public String getCreatedAtDate() {
            return createdAtDate;
        }

        public void setCreatedAtDate(String createdAtDate) {
            this.createdAtDate = createdAtDate;
        }

        public int getLastActiveMinuteGap() {
            return lastActiveMinuteGap;
        }

        public void setLastActiveMinuteGap(int lastActiveMinuteGap) {
            this.lastActiveMinuteGap = lastActiveMinuteGap;
        }

        public List<AnchorTagBean.DataBean> getTags() {
            return tags;
        }

        public void setTags(List<AnchorTagBean.DataBean> tags) {
            this.tags = tags;
        }

        public List<AlbumBean> getAlbum() {
            return album;
        }

        public void setAlbum(List<AlbumBean> album) {
            this.album = album;
        }

        public int getSpent() {
            return spent;
        }

        public void setSpent(int spent) {
            this.spent = spent;
        }

        public int getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(int followingCount) {
            this.followingCount = followingCount;
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

        public static class SettingBean {
            /**
             * videoCpm : 20
             * audioCpm : 10
             * chatCpm : 1
             * videoNotify : true
             * audioNotify : true
             * chatNotify : true
             */

            @SerializedName("videoCpm")
            private int videoCpm;
            @SerializedName("audioCpm")
            private int audioCpm;
            @SerializedName("chatCpm")
            private int chatCpm;
            @SerializedName("videoNotify")
            private boolean videoNotify;
            @SerializedName("audioNotify")
            private boolean audioNotify;
            @SerializedName("chatNotify")
            private boolean chatNotify;

            public int getVideoCpm() {
                return videoCpm;
            }

            public void setVideoCpm(int videoCpm) {
                this.videoCpm = videoCpm;
            }

            public int getAudioCpm() {
                return audioCpm;
            }

            public void setAudioCpm(int audioCpm) {
                this.audioCpm = audioCpm;
            }

            public int getChatCpm() {
                return chatCpm;
            }

            public void setChatCpm(int chatCpm) {
                this.chatCpm = chatCpm;
            }

            public boolean isVideoNotify() {
                return videoNotify;
            }

            public void setVideoNotify(boolean videoNotify) {
                this.videoNotify = videoNotify;
            }

            public boolean isAudioNotify() {
                return audioNotify;
            }

            public void setAudioNotify(boolean audioNotify) {
                this.audioNotify = audioNotify;
            }

            public boolean isChatNotify() {
                return chatNotify;
            }

            public void setChatNotify(boolean chatNotify) {
                this.chatNotify = chatNotify;
            }
        }

        public static class AlbumBean {
            /**
             * id : 2
             * mediaUrl : http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg
             * sortby : 0
             */

            @SerializedName("id")
            private int id;
            @SerializedName("mediaUrl")
            private String mediaUrl;
            @SerializedName("sortby")
            private int sortby;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMediaUrl() {
                return mediaUrl;
            }

            public void setMediaUrl(String mediaUrl) {
                this.mediaUrl = mediaUrl;
            }

            public int getSortby() {
                return sortby;
            }

            public void setSortby(int sortby) {
                this.sortby = sortby;
            }
        }
    }
}
