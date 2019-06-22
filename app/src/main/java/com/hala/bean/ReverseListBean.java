package com.hala.bean;

import java.util.List;

public class ReverseListBean extends BaseBean {


    /**
     * data : {"content":[{"id":1,"state":"in_process","worth":20,"createdAt":"2019-05-27T06:13:09.000+0000","updatedAt":"2019-05-27T06:13:09.000+0000","targetInfo":{"id":2,"avatarUrl":"http://me.avatar.url","name":"pp"}}],"pageable":{"nextPage":false,"totalPages":1,"currentPage":1}}
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
         * content : [{"id":1,"state":"in_process","worth":20,"createdAt":"2019-05-27T06:13:09.000+0000","updatedAt":"2019-05-27T06:13:09.000+0000","targetInfo":{"id":2,"avatarUrl":"http://me.avatar.url","name":"pp"}}]
         * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
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
             * id : 1
             * state : in_process
             * worth : 20
             * createdAt : 2019-05-27T06:13:09.000+0000
             * updatedAt : 2019-05-27T06:13:09.000+0000
             * targetInfo : {"id":2,"avatarUrl":"http://me.avatar.url","name":"pp"}
             */

            private int id;
            private String state; //state的枚举值包括in_process进行中，success已通话，failed未回拨
            private int worth;
            private String createdAt;
            private String updatedAt;
            private TargetInfoBean targetInfo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
