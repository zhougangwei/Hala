package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginBean extends BaseBean {


    /**
     * data : {"member":{"characterId":"02544174","mobileNumber":"+8613811114444","username":"周","gender":"secret","birthDate":"2000-10-14","autograph":"你号","residentialPlace":"杭州","anchorId":1,"rongToken":"I34rC+bQTeEMQcO7kwL3K16AsbGff9KvUT62Noch30mb6n+CHOf4VSjk5+knDcNk6ysOovR6+BFc1gC7seANjA==","coin":0,"spent":0,"followingCount":0,"online":false,"album":[{"id":5,"mediaUrl":"http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg","sortby":1}],"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwianRpIjoiM2EzNjA1ZGQtMDFkNi00YzBkLWIyM2QtZTY0OGE5NzA1NThmIn0.cMF8aKFCDUX_OeqyKMTFFbDKequm7qFympz0Zdk3MSE","memberId":5,"lastActiveMinuteGap":0},"action":"sign_in"}
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
         * member : {"characterId":"02544174","mobileNumber":"+8613811114444","username":"周","gender":"secret","birthDate":"2000-10-14","autograph":"你号","residentialPlace":"杭州","anchorId":1,"rongToken":"I34rC+bQTeEMQcO7kwL3K16AsbGff9KvUT62Noch30mb6n+CHOf4VSjk5+knDcNk6ysOovR6+BFc1gC7seANjA==","coin":0,"spent":0,"followingCount":0,"online":false,"album":[{"id":5,"mediaUrl":"http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg","sortby":1}],"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwianRpIjoiM2EzNjA1ZGQtMDFkNi00YzBkLWIyM2QtZTY0OGE5NzA1NThmIn0.cMF8aKFCDUX_OeqyKMTFFbDKequm7qFympz0Zdk3MSE","memberId":5,"lastActiveMinuteGap":0}
         * action : sign_in
         */

        @SerializedName("member")
        private MemberBean member;
        @SerializedName("action")
        private String action;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public static class MemberBean {
            /**
             * characterId : 02544174
             * mobileNumber : +8613811114444
             * username : 周
             * gender : secret
             * birthDate : 2000-10-14
             * autograph : 你号
             * residentialPlace : 杭州
             * anchorId : 1
             * rongToken : I34rC+bQTeEMQcO7kwL3K16AsbGff9KvUT62Noch30mb6n+CHOf4VSjk5+knDcNk6ysOovR6+BFc1gC7seANjA==
             * coin : 0
             * spent : 0
             * followingCount : 0
             * online : false
             * album : [{"id":5,"mediaUrl":"http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg","sortby":1}]
             * accessToken : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwianRpIjoiM2EzNjA1ZGQtMDFkNi00YzBkLWIyM2QtZTY0OGE5NzA1NThmIn0.cMF8aKFCDUX_OeqyKMTFFbDKequm7qFympz0Zdk3MSE
             * memberId : 5
             * lastActiveMinuteGap : 0,
             * introduction :"介绍"
             * genderOrdinal:"1"
             *fansCount
             */

            @SerializedName("characterId")
            private String characterId;
            @SerializedName("mobileNumber")
            private String mobileNumber;
            @SerializedName("nickname")
            private String username;
            @SerializedName("gender")
            private String gender;
            @SerializedName("birthDate")
            private String birthDate;
            @SerializedName("autograph")
            private String autograph;
            @SerializedName("residentialPlace")
            private String residentialPlace;
            @SerializedName("anchorId")
            private int anchorId;
            @SerializedName("rongToken")
            private String rongToken;
            @SerializedName("coin")
            private int coin;
            @SerializedName("spent")
            private int spent;
            @SerializedName("followingCount")
            private int followingCount;
            @SerializedName("online")
            private boolean online;
            @SerializedName("accessToken")
            private String accessToken;
            @SerializedName("memberId")
            private int memberId;
            @SerializedName("lastActiveMinuteGap")
            private int lastActiveMinuteGap;
            @SerializedName("album")
            private List<AlbumBean> album;
            @SerializedName("setting")
            private SettingBean setting;

            private int genderOrdinal;

            @SerializedName("introduction")
            private String introduction;

            @SerializedName("fansCount")
            private int fansCount;

            public String getCharacterId() {
                return characterId;
            }

            public void setCharacterId(String characterId) {
                this.characterId = characterId;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(String birthDate) {
                this.birthDate = birthDate;
            }

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }

            public String getResidentialPlace() {
                return residentialPlace;
            }

            public void setResidentialPlace(String residentialPlace) {
                this.residentialPlace = residentialPlace;
            }

            public int getAnchorId() {
                return anchorId;
            }

            public void setAnchorId(int anchorId) {
                this.anchorId = anchorId;
            }

            public String getRongToken() {
                return rongToken;
            }

            public void setRongToken(String rongToken) {
                this.rongToken = rongToken;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
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

            public boolean isOnline() {
                return online;
            }

            public void setOnline(boolean online) {
                this.online = online;
            }

            public String getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getLastActiveMinuteGap() {
                return lastActiveMinuteGap;
            }

            public void setLastActiveMinuteGap(int lastActiveMinuteGap) {
                this.lastActiveMinuteGap = lastActiveMinuteGap;
            }

            public List<AlbumBean> getAlbum() {
                return album;
            }

            public void setAlbum(List<AlbumBean> album) {
                this.album = album;
            }

            public String getIntroduction() {
                return introduction == null ? "" : introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction == null ? "" : introduction;
            }

            public SettingBean getSetting() {
                return setting;
            }

            public void setSetting(SettingBean setting) {
                this.setting = setting;
            }

            public int getGenderOrdinal() {
                return genderOrdinal;
            }

            public void setGenderOrdinal(int genderOrdinal) {
                this.genderOrdinal = genderOrdinal;
            }

            public int getFansCount() {
                return fansCount;
            }

            public void setFansCount(int fansCount) {
                this.fansCount = fansCount;
            }

            public static class AlbumBean {
                /**
                 * id : 5
                 * mediaUrl : http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg
                 * sortby : 1
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

            public static class SettingBean{

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

        }


    }
}