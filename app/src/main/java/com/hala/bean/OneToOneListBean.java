package com.hala.bean;

import java.util.List;

public class OneToOneListBean extends BaseBean {


    /**
     * data : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"memberId":1,"nickname":"old p","avatarUrl":"http://me.avatar.url","mobileNumber":"+8613851668725","height":182,"weight":82,"zodiac":"Aries","city":"NJ","introduction":"this is introduction","biography":"this is biography","certifyUrl":"http://certify.url","starLevel":4,"cpm":20,"online":true,"available":true,"hotweight":0,"sortby":0,"verified":true,"createdAt":"2019-05-26T04:03:50.000+0000","updatedAt":"2019-05-26T04:03:50.000+0000","tags":[{"content":"active","tagId":16},{"content":"aspiring","tagId":19}],"covers":[{"id":2,"coverUrl":"http://test.anchor14.url2","sortby":2},{"id":1,"coverUrl":"http://test.anchor14.url1","sortby":3}],"anchorId":14}]}
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
         * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
         * list : [{"memberId":1,"nickname":"old p","avatarUrl":"http://me.avatar.url","mobileNumber":"+8613851668725","height":182,"weight":82,"zodiac":"Aries","city":"NJ","introduction":"this is introduction","biography":"this is biography","certifyUrl":"http://certify.url","starLevel":4,"cpm":20,"online":true,"available":true,"hotweight":0,"sortby":0,"verified":true,"createdAt":"2019-05-26T04:03:50.000+0000","updatedAt":"2019-05-26T04:03:50.000+0000","tags":[{"content":"active","tagId":16},{"content":"aspiring","tagId":19}],"covers":[{"id":2,"coverUrl":"http://test.anchor14.url2","sortby":2},{"id":1,"coverUrl":"http://test.anchor14.url1","sortby":3}],"anchorId":14}]
         */

        private PageableBean pageable;
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

            private boolean nextPage;
            private int totalPages;
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

        public static class ListBean {
            /**
             * memberId : 1
             * nickname : old p
             * avatarUrl : http://me.avatar.url
             * mobileNumber : +8613851668725
             * height : 182
             * weight : 82
             * zodiac : Aries
             * city : NJ
             * introduction : this is introduction
             * biography : this is biography
             * certifyUrl : http://certify.url
             * starLevel : 4
             * cpm : 20
             * online : true
             * available : true
             * hotweight : 0
             * sortby : 0
             * verified : true
             * createdAt : 2019-05-26T04:03:50.000+0000
             * updatedAt : 2019-05-26T04:03:50.000+0000
             * tags : [{"content":"active","tagId":16},{"content":"aspiring","tagId":19}]
             * covers : [{"id":2,"coverUrl":"http://test.anchor14.url2","sortby":2},{"id":1,"coverUrl":"http://test.anchor14.url1","sortby":3}]
             * anchorId : 14
             */

            private int memberId;
            private String nickname;
            private String avatarUrl;
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
            private int hotweight;
            private int sortby;
            private boolean verified;
            private String createdAt;
            private String updatedAt;
            private int anchorId;
            private List<TagsBean> tags;
            private List<CoversBean> covers;

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

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
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

            public int getHotweight() {
                return hotweight;
            }

            public void setHotweight(int hotweight) {
                this.hotweight = hotweight;
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

            public int getAnchorId() {
                return anchorId;
            }

            public void setAnchorId(int anchorId) {
                this.anchorId = anchorId;
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
                 * content : active
                 * tagId : 16
                 */

                private String content;
                private int tagId;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getTagId() {
                    return tagId;
                }

                public void setTagId(int tagId) {
                    this.tagId = tagId;
                }
            }

            public static class CoversBean {
                /**
                 * id : 2
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
}
