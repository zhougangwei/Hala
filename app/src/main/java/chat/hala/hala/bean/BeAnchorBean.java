package chat.hala.hala.bean;

import java.util.List;

public class BeAnchorBean extends BaseBean {
    /**
     * data : {"anchorId":14,"memberId":1,"nickname":"old p","mobileNumber":"+8613851668725","height":182,"weight":82,"zodiac":"Taurus","city":"NJ","introduction":"this is introduction","biography":"this is biography","certifyUrl":"http://certify.url","starLevel":0,"cpm":20,"online":true,"available":true,"sortby":0,"verified":false,"createdAt":"2019-05-26T04:03:50.169+0000","updatedAt":"2019-05-26T04:03:50.169+0000","tags":[{"anchorId":16,"content":"tag1"},{"anchorId":19,"content":"tag2"}],"covers":[{"anchorId":2,"coverUrl":"http://test.anchor14.url2","sortby":2},{"anchorId":1,"coverUrl":"http://test.anchor14.url1","sortby":3}]}
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
         * anchorId : 14
         * memberId : 1
         * nickname : old p
         * mobileNumber : +8613851668725
         * height : 182
         * weight : 82
         * zodiac : Taurus
         * city : NJ
         * introduction : this is introduction
         * biography : this is biography
         * certifyUrl : http://certify.url
         * starLevel : 0
         * cpm : 20
         * online : true
         * available : true
         * sortby : 0
         * verified : false
         * createdAt : 2019-05-26T04:03:50.169+0000
         * updatedAt : 2019-05-26T04:03:50.169+0000
         * tags : [{"anchorId":16,"content":"tag1"},{"anchorId":19,"content":"tag2"}]
         * covers : [{"anchorId":2,"coverUrl":"http://test.anchor14.url2","sortby":2},{"anchorId":1,"coverUrl":"http://test.anchor14.url1","sortby":3}]
         */

        private int anchorId;
        private int memberId;
        private String nickname;
        private String mobileNumber;
        private int height;
        private int weight;
        private String zodiac;
        private String city;
        private String introduction;
        private String biography;
        private String certifyUrl;
        private int starLevel;
        private int cpm;
        private boolean online;
        private boolean available;
        private int sortby;
        private boolean verified;
        private String createdAt;
        private String updatedAt;
        private List<AnchorTagBean.DataBean> tags;
        private List<CoversBean> covers;

        public int getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(int anchorId) {
            this.anchorId = anchorId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
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

        public String getZodiac() {
            return zodiac;
        }

        public void setZodiac(String zodiac) {
            this.zodiac = zodiac;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getCertifyUrl() {
            return certifyUrl;
        }

        public void setCertifyUrl(String certifyUrl) {
            this.certifyUrl = certifyUrl;
        }

        public int getStarLevel() {
            return starLevel;
        }

        public void setStarLevel(int starLevel) {
            this.starLevel = starLevel;
        }

        public int getCpm() {
            return cpm;
        }

        public void setCpm(int cpm) {
            this.cpm = cpm;
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

        public int getSortby() {
            return sortby;
        }

        public void setSortby(int sortby) {
            this.sortby = sortby;
        }

        public boolean isVerified() {
            return verified;
        }

        public void setVerified(boolean verified) {
            this.verified = verified;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<AnchorTagBean.DataBean> getTags() {
            return tags;
        }

        public void setTags(List<AnchorTagBean.DataBean> tags) {
            this.tags = tags;
        }

        public List<CoversBean> getCovers() {
            return covers;
        }

        public void setCovers(List<CoversBean> covers) {
            this.covers = covers;
        }



        public static class CoversBean {
            /**
             * anchorId : 2
             * coverUrl : http://test.anchor14.url2
             * sortby : 2
             */

            private int id;
            private String coverUrl;
            private int sortby;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCoverUrl() {
                return coverUrl;
            }

            public void setCoverUrl(String coverUrl) {
                this.coverUrl = coverUrl;
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
