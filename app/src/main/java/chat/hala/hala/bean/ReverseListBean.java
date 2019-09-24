package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReverseListBean extends BaseBean {


    /**
     * data : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"category":"video","state":"in_process","worth":20,"createdAt":"2019-09-18T08:02:18.000+0000","updatedAt":"2019-09-18T08:02:18.000+0000","targetInfo":{"id":2,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1602-7332AF736519138CFB0243C320D4C222.jpg","name":"????","form":"anchor"},"reservationId":4,"stateTrans":"等待回拨","date":"2019-09-18","datetime":"2019-09-18 16:02"}]}
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
         * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
         * list : [{"category":"video","state":"in_process","worth":20,"createdAt":"2019-09-18T08:02:18.000+0000","updatedAt":"2019-09-18T08:02:18.000+0000","targetInfo":{"id":2,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1602-7332AF736519138CFB0243C320D4C222.jpg","name":"????","form":"anchor"},"reservationId":4,"stateTrans":"等待回拨","date":"2019-09-18","datetime":"2019-09-18 16:02"}]
         */

        @SerializedName("pageable")
        private PageableBean pageable;
        @SerializedName("list")
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

            @SerializedName("nextPage")
            private boolean nextPage;
            @SerializedName("totalPages")
            private int totalPages;
            @SerializedName("currentPage")
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
             * category : video
             * state : in_process
             * worth : 20
             * createdAt : 2019-09-18T08:02:18.000+0000
             * updatedAt : 2019-09-18T08:02:18.000+0000
             * targetInfo : {"id":2,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1602-7332AF736519138CFB0243C320D4C222.jpg","name":"????","form":"anchor"}
             * reservationId : 4
             * stateTrans : 等待回拨
             * date : 2019-09-18
             * datetime : 2019-09-18 16:02
             */

            @SerializedName("category")
            private String category;
            @SerializedName("state")
            private String state;
            @SerializedName("worth")
            private int worth;
            @SerializedName("createdAt")
            private String createdAt;
            @SerializedName("updatedAt")
            private String updatedAt;
            @SerializedName("targetInfo")
            private TargetInfoBean targetInfo;
            @SerializedName("reservationId")
            private int reservationId;
            @SerializedName("stateTrans")
            private String stateTrans;
            @SerializedName("date")
            private String date;
            @SerializedName("datetime")
            private String datetime;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
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

            public int getReservationId() {
                return reservationId;
            }

            public void setReservationId(int reservationId) {
                this.reservationId = reservationId;
            }

            public String getStateTrans() {
                return stateTrans;
            }

            public void setStateTrans(String stateTrans) {
                this.stateTrans = stateTrans;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public static class TargetInfoBean {
                /**
                 * id : 2
                 * avatarUrl : http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1602-7332AF736519138CFB0243C320D4C222.jpg
                 * name : ????
                 * form : anchor
                 */

                @SerializedName("id")
                private int id;
                @SerializedName("avatarUrl")
                private String avatarUrl;
                @SerializedName("name")
                private String name;
                @SerializedName("form")
                private String form;

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

                public String getForm() {
                    return form;
                }

                public void setForm(String form) {
                    this.form = form;
                }
            }
        }
    }
}
