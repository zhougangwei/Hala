package com.hala.bean;

import java.util.List;

public class ApplyAnchorBean extends BaseBean {


    /**
     * mobileNumber : +8613851668725
     * introduction : this is introduction
     * tags : [{"content":"tag1","id":"16"},{"content":"tag2"}]
     * weight : 82
     * certifyUrl : http://certify.url
     * biography : this is biography
     * city : NJ
     * height : 182
     * covers : [{"coverUrl":"http://test.anchor.url2","sortby":2},{"coverUrl":"http://test.anchor.url1","sortby":3}]
     * nickname : old p
     * zodiac : Taurus
     * cpm : 20
     */

    private String mobileNumber;
    private String introduction;
    private int weight;
    private String certifyUrl;
    private String biography;
    private String city;
    private int height;
    private String nickname;
    private String zodiac;
    private String cpm;
    private List<TagsBean> tags;
    private List<CoversBean> covers;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCertifyUrl() {
        return certifyUrl;
    }

    public void setCertifyUrl(String certifyUrl) {
        this.certifyUrl = certifyUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getCpm() {
        return cpm;
    }

    public void setCpm(String cpm) {
        this.cpm = cpm;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<CoversBean> getCovers() {
        return covers;
    }

    public void setCovers(List<CoversBean> covers) {
        this.covers = covers;
    }

    public static class TagsBean {
        /**
         * content : tag1
         * id : 16
         */

        private String content;
        private String id;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class CoversBean {
        /**
         * coverUrl : http://test.anchor.url2
         * sortby : 2
         */

        private String coverUrl;
        private int sortby;

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
