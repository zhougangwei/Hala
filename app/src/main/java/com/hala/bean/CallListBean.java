package com.hala.bean;

import java.util.List;

public class CallListBean extends BaseBean {
    /**
     * data : {"content":[{"id":1,"anchorInitiate":false,"startedAt":"2019-05-27T07:21:59.000+0000","endedAt":"2019-05-27T07:21:59.000+0000","durationSeconds":140,"state":"success","worth":60,"targetInfo":{"id":2,"avatarUrl":"http://me.avatar.url","name":"pp"}}],"pageable":{"nextPage":false,"totalPages":1,"currentPage":1}}
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
         * content : [{"id":1,"anchorInitiate":false,"startedAt":"2019-05-27T07:21:59.000+0000","endedAt":"2019-05-27T07:21:59.000+0000","durationSeconds":140,"state":"success","worth":60,"targetInfo":{"id":2,"avatarUrl":"http://me.avatar.url","name":"pp"}}]
         * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
         */

        private PageableBean pageable;
        private List<ContentBean> content;

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
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

        public static class ContentBean {
            /**
             * id : 1
             * anchorInitiate : false
             * startedAt : 2019-05-27T07:21:59.000+0000
             * endedAt : 2019-05-27T07:21:59.000+0000
             * durationSeconds : 140
             * state : success
             * worth : 60
             * targetInfo : {"id":2,"avatarUrl":"http://me.avatar.url","name":"pp"}
             */

            private int id;
            private boolean anchorInitiate;
            private String startedAt;
            private String endedAt;
            private int durationSeconds;
            private String state;
            private int worth;
            private TargetInfoBean targetInfo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isAnchorInitiate() {
                return anchorInitiate;
            }

            public void setAnchorInitiate(boolean anchorInitiate) {
                this.anchorInitiate = anchorInitiate;
            }

            public String getStartedAt() {
                return startedAt;
            }

            public void setStartedAt(String startedAt) {
                this.startedAt = startedAt;
            }

            public String getEndedAt() {
                return endedAt;
            }

            public void setEndedAt(String endedAt) {
                this.endedAt = endedAt;
            }

            public int getDurationSeconds() {
                return durationSeconds;
            }

            public void setDurationSeconds(int durationSeconds) {
                this.durationSeconds = durationSeconds;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getWorth() {
                return worth;
            }

            public void setWorth(int worth) {
                this.worth = worth;
            }

            public TargetInfoBean getTargetInfo() {
                return targetInfo;
            }

            public void setTargetInfo(TargetInfoBean targetInfo) {
                this.targetInfo = targetInfo;
            }

            public static class TargetInfoBean {
                /**
                 * id : 2
                 * avatarUrl : http://me.avatar.url
                 * name : pp
                 */

                private int id;
                private String avatarUrl;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getAvatarUrl() {
                    return avatarUrl;
                }

                public void setAvatarUrl(String avatarUrl) {
                    this.avatarUrl = avatarUrl;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
