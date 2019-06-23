package com.hala.bean;

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
     * data : {"mobileNumber":"+8613851668723","username":"pppp","gender":"male","birthDate":31,"avatarUrl":"http://me.avatar.url","coin":0,"online":true,"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwianRpIjoiMzliZmViODMtZDJjNS00MDMwLThlNzAtMWE1Y2Q4MTBkZmRhIn0.0ELVtvnn7F1X_Fpqw--t8keQsnU6gbmH5RXv5_Q29Is","memberId":6}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mobileNumber : +8613851668723
         * username : pppp
         * gender : male
         * birthDate : 31
         * avatarUrl : http://me.avatar.url
         * coin : 0
         * online : true
         * accessToken : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwianRpIjoiMzliZmViODMtZDJjNS00MDMwLThlNzAtMWE1Y2Q4MTBkZmRhIn0.0ELVtvnn7F1X_Fpqw--t8keQsnU6gbmH5RXv5_Q29Is
         * memberId : 6
         */

        private String mobileNumber;
        private String  username;
        private String  gender;
        private int     birthDate;
        private String  avatarUrl;
        private int     coin;
        private boolean online;
        private String  accessToken;
        private int     memberId;

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

        public int getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(int birthDate) {
            this.birthDate = birthDate;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
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
    }
}
