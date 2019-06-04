package com.hala.bean;

public class LoginBean  {

    /**
     * data : {"member":{"id":1,"mobileNumber":"+8613851668725","username":"pete","gender":"male","birthDate":31,"avatarUrl":"http://me.avatar.url","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwianRpIjoiZjdmZDQxZWUtMTNlMS00N2VjLWI4ZjktM2Y3Mjg4MWExMmZhIn0.uwXL8of0EtKK5kG8KLFr0wgG05hoZWVud6243XnnjuY"},"action":"sign_in"}
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
         * member : {"id":1,"mobileNumber":"+8613851668725","username":"pete","gender":"male","birthDate":31,"avatarUrl":"http://me.avatar.url","accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwianRpIjoiZjdmZDQxZWUtMTNlMS00N2VjLWI4ZjktM2Y3Mjg4MWExMmZhIn0.uwXL8of0EtKK5kG8KLFr0wgG05hoZWVud6243XnnjuY"}
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
             * id : 1
             * mobileNumber : +8613851668725
             * username : pete
             * gender : male
             * birthDate : 31
             * avatarUrl : http://me.avatar.url
             * accessToken : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwianRpIjoiZjdmZDQxZWUtMTNlMS00N2VjLWI4ZjktM2Y3Mjg4MWExMmZhIn0.uwXL8of0EtKK5kG8KLFr0wgG05hoZWVud6243XnnjuY
             */

            private int id;
            private String mobileNumber;
            private String username;
            private String gender;
            private int birthDate;
            private String avatarUrl;
            private String accessToken;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }
        }
    }
}
