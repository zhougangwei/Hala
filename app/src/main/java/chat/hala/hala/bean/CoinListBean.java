package chat.hala.hala.bean;

import java.util.List;

public class CoinListBean extends BaseBean{


    /**
     * data : {"total":900,"transactions":{"list":[{"id":22,"memberId":2,"category":"calling","digit":-20,"info":{"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"},"datetime":"2019-06-22 01:07","date":"2019-06-22"}],"pageable":{"nextPage":false,"totalPages":1,"currentPage":1}}}
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
         * total : 900
         * transactions : {"list":[{"id":22,"memberId":2,"category":"calling","digit":-20,"info":{"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"},"datetime":"2019-06-22 01:07","date":"2019-06-22"}],"pageable":{"nextPage":false,"totalPages":1,"currentPage":1}}
         */

        private int total;
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
             * list : [{"id":22,"memberId":2,"category":"calling","digit":-20,"info":{"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"},"datetime":"2019-06-22 01:07","date":"2019-06-22"}]
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
                 * id : 22
                 * memberId : 2
                 * category : calling
                 * digit : -20
                 * info : {"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"}
                 * datetime : 2019-06-22 01:07
                 * date : 2019-06-22
                 */

                private int id;
                private int      memberId;
                private String   category;
                private int      digit;
                private InfoBean info;
                private String   datetime;
                private String   date;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

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

                public int getDigit() {
                    return digit;
                }

                public void setDigit(int digit) {
                    this.digit = digit;
                }

                public InfoBean getInfo() {
                    return info;
                }

                public void setInfo(InfoBean info) {
                    this.info = info;
                }

                public String getDatetime() {
                    return datetime;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class InfoBean {
                    /**
                     * id : 14
                     * avatarUrl : http://me.avatar.url
                     * name : old p
                     * form : anchor
                     */

                    private int id;
                    private String avatarUrl;
                    private String name;
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
