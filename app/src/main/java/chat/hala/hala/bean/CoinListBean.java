package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoinListBean extends BaseBean{


    /**
     * data : {"total":-20,"transactions":{"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"memberId":6,"category":"reservation","info":{"id":1,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","name":"周","form":"anchor"},"categoryTrans":"预约花费","date":"2019-08-14","datetime":"2019-08-14 09:37","figure":"-20"}]}}
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
         * total : -20
         * transactions : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"memberId":6,"category":"reservation","info":{"id":1,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","name":"周","form":"anchor"},"categoryTrans":"预约花费","date":"2019-08-14","datetime":"2019-08-14 09:37","figure":"-20"}]}
         */

        @SerializedName("total")
        private int total;
        @SerializedName("transactions")
        private TransactionsBean transactions;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public TransactionsBean getTransactions() {
            return transactions;
        }

        public void setTransactions(TransactionsBean transactions) {
            this.transactions = transactions;
        }

        public static class TransactionsBean {
            /**
             * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
             * list : [{"memberId":6,"category":"reservation","info":{"id":1,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","name":"周","form":"anchor"},"categoryTrans":"预约花费","date":"2019-08-14","datetime":"2019-08-14 09:37","figure":"-20"}]
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
                 * memberId : 6
                 * category : reservation
                 * info : {"id":1,"avatarUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","name":"周","form":"anchor"}
                 * categoryTrans : 预约花费
                 * date : 2019-08-14
                 * datetime : 2019-08-14 09:37
                 * figure : -20
                 */

                @SerializedName("memberId")
                private int memberId;
                @SerializedName("category")
                private String category;
                @SerializedName("info")
                private InfoBean info;
                @SerializedName("categoryTrans")
                private String categoryTrans;
                @SerializedName("date")
                private String date;
                @SerializedName("datetime")
                private String datetime;
                @SerializedName("figure")
                private String figure;

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public String getCategory() {
                    return category;
                }

                public void setCategory(String category) {
                    this.category = category;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public String getCategoryTrans() {
                    return categoryTrans;
                }

                public void setCategoryTrans(String categoryTrans) {
                    this.categoryTrans = categoryTrans;
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

                public String getFigure() {
                    return figure;
                }

                public void setFigure(String figure) {
                    this.figure = figure;
                }

                public static class InfoBean {
                    /**
                     * id : 1
                     * avatarUrl : http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg
                     * name : 周
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
}
