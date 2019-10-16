package chat.hala.hala.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneToOneListBean extends BaseBean {


    /**
     * data : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"memberId":5,"characterId":"02544174","nickname":"周","gender":"secret","height":160,"weight":50,"birthDate":"1990-01-01","residentialPlace":"杭州","marking":80,"fansCount":0,"introduction":"你号","autograph":"你号","online":false,"available":true,"tags":[],"setting":{"videoCpm":20,"audioCpm":10,"chatCpm":1,"videoNotify":true,"audioNotify":true,"chatNotify":true},"album":[{"id":2,"mediaUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","sortby":0}],"anchorId":1,"answerRate":"0.0%","createdAtDate":"2019-08-12","lastActiveMinuteGap":76}]}
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
         * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
         * list : [{"memberId":5,"characterId":"02544174","nickname":"周","gender":"secret","height":160,"weight":50,"birthDate":"1990-01-01","residentialPlace":"杭州","marking":80,"fansCount":0,"introduction":"你号","autograph":"你号","online":false,"available":true,"tags":[],"setting":{"videoCpm":20,"audioCpm":10,"chatCpm":1,"videoNotify":true,"audioNotify":true,"chatNotify":true},"album":[{"id":2,"mediaUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","sortby":0}],"anchorId":1,"answerRate":"0.0%","createdAtDate":"2019-08-12","lastActiveMinuteGap":76}]
         */

        @SerializedName("pageable")
        private PageableBean pageable;
        @SerializedName("list")
        private List<ListBean> list;

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageableBean {
            /**
             * nextPage : false
             * totalPages : 1
             * currentPage : 1
             */

            @SerializedName("nextPage")
            private boolean nextPage;
            @SerializedName("totalPages")
            private int totalPages;
            @SerializedName("currentPage")
            private int currentPage;

            public boolean isNextPage() {
                return nextPage;
            }

            public void setNextPage(boolean nextPage) {
                this.nextPage = nextPage;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }
        }

        public static class ListBean implements MultiItemEntity {
            public static final int BANNER = 1;
            public static final int NORMAL = 0;
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
             * lastActiveMinuteGap : 76
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
            private List<?> tags;
            @SerializedName("album")
            private List<AlbumBean> album;
            private int dataType;

            @SerializedName("joinfamilyAt")
            private String joinfamilyAt;
            @SerializedName("following")
            private boolean following;


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

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }

            public List<AlbumBean> getAlbum() {
                return album;
            }

            public void setAlbum(List<AlbumBean> album) {
                this.album = album;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            @Override
            public int getItemType() {
                return dataType;
            }

            public String getJoinfamilyAt() {
                return joinfamilyAt == null ? "" : joinfamilyAt;
            }

            public void setJoinfamilyAt(String joinfamilyAt) {
                this.joinfamilyAt = joinfamilyAt == null ? "" : joinfamilyAt;
            }

            public boolean getFollowing() {
                return following;
            }

            public void setFollowing(boolean following) {
                this.following = following;
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
}
