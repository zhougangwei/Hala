package chat.hala.hala.bean;

public class LoginBean extends BaseBean {


    /**
     * data : {"member":{"mobileNumber":"+8613851668725","username":"test2","gender":"male","birthDate":31,"avatarUrl":"http://me.avatar.url","anchorId":14,"coin":1520,"online":false,"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwianRpIjoiNTE1MjIxYTgtNTNlZC00OWNhLWJiODYtMzQ4MWVjOTJmNzU3In0.3uhD_ezq4aR9jsDn9JyF614_TI2tmXloQ_eQ87Uqofw","memberId":1},"action":"sign_in"}
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
         * member : {"mobileNumber":"+8613851668725","username":"test2","gender":"male","birthDate":31,"avatarUrl":"http://me.avatar.url","anchorId":14,"coin":1520,"online":false,"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwianRpIjoiNTE1MjIxYTgtNTNlZC00OWNhLWJiODYtMzQ4MWVjOTJmNzU3In0.3uhD_ezq4aR9jsDn9JyF614_TI2tmXloQ_eQ87Uqofw","memberId":1}
         * action : sign_in
         */

        private MemberBean member;
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
             * mobileNumber : +8613851668725
             * username : test2
             * gender : male
             * birthDate : 31
             * avatarUrl : http://me.avatar.url
             * anchorId : 14
             * coin : 1520
             * online : false
             * accessToken : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwianRpIjoiNTE1MjIxYTgtNTNlZC00OWNhLWJiODYtMzQ4MWVjOTJmNzU3In0.3uhD_ezq4aR9jsDn9JyF614_TI2tmXloQ_eQ87Uqofw
             * memberId : 1
             */

            private String mobileNumber;
            private String  username;
            private String  gender;
            private String     birthDate;
            private String  avatarUrl;
            private int     anchorId;
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

            public String getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(String birthDate) {
                this.birthDate = birthDate;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public int getAnchorId() {
                return anchorId;
            }

            public void setAnchorId(int anchorId) {
                this.anchorId = anchorId;
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
}