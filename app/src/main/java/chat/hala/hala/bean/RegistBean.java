package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 22:16
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class RegistBean extends BaseBean {

    /**
     * data : {"characterId":"10486931","mobileNumber":"+8612345678910","username":"pp","gender":"male","birthDate":"2019-01-01","autograph":"不是","rongToken":"dck/4HVqHEl58hAUWXeZiV6AsbGff9KvUT62Noch30mb6n+CHOf4Vdoco+C/pVxohCVnUyo2V5Rc1gC7seANjA==","coin":0,"spent":0,"followingCount":0,"online":true,"album":[{"id":8,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","sortby":0}],"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwianRpIjoiNjljOTRlMzktNzIxMi00Y2JjLWIzODctZjc3ZTY2NDMxY2FlIn0.n6T5M64hKS7MC5djcuXYnNOQuCpoKiqTLJHL8qx9J3c","memberId":8,"lastActiveMinuteGap":0}
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
         * characterId : 10486931
         * mobileNumber : +8612345678910
         * username : pp
         * gender : male
         * birthDate : 2019-01-01
         * autograph : 不是
         * rongToken : dck/4HVqHEl58hAUWXeZiV6AsbGff9KvUT62Noch30mb6n+CHOf4Vdoco+C/pVxohCVnUyo2V5Rc1gC7seANjA==
         * coin : 0
         * spent : 0
         * followingCount : 0
         * online : true
         * album : [{"id":8,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","sortby":0}]
         * accessToken : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwianRpIjoiNjljOTRlMzktNzIxMi00Y2JjLWIzODctZjc3ZTY2NDMxY2FlIn0.n6T5M64hKS7MC5djcuXYnNOQuCpoKiqTLJHL8qx9J3c
         * memberId : 8
         * lastActiveMinuteGap : 0
         */

        @SerializedName("characterId")
        private String characterId;
        @SerializedName("mobileNumber")
        private String mobileNumber;
        @SerializedName("username")
        private String username;
        @SerializedName("gender")
        private String gender;
        @SerializedName("birthDate")
        private String birthDate;
        @SerializedName("autograph")
        private String autograph;
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

        public static class AlbumBean {
            /**
             * id : 8
             * mediaUrl : http://starchat.general.7halachat.com/member_avatar_test.png
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
