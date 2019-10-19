package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author wjy on 2019/10/16/016.
 */
public class ChargeListBean extends BaseBean {

    /**
     * data : {"transactions":{"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"id":20,"memberId":8,"through":"alipay","outTransactionCode":"2019101822001415051403375651","transactionNumber":"A6590883107688345600","amount":0.01,"coin":290,"state":"success","createdAt":"2019-10-18T08:56:26.000+0000","updatedAt":"2019-10-18T08:56:51.000+0000"}]}}
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
         * transactions : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"id":20,"memberId":8,"through":"alipay","outTransactionCode":"2019101822001415051403375651","transactionNumber":"A6590883107688345600","amount":0.01,"coin":290,"state":"success","createdAt":"2019-10-18T08:56:26.000+0000","updatedAt":"2019-10-18T08:56:51.000+0000"}]}
         */

        @SerializedName("transactions")
        private TransactionsBean transactions;

        public TransactionsBean getTransactions() {
            return transactions;
        }

        public void setTransactions(TransactionsBean transactions) {
            this.transactions = transactions;
        }

        public static class TransactionsBean {
            /**
             * pageable : {"nextPage":false,"totalPages":1,"currentPage":1}
             * list : [{"id":20,"memberId":8,"through":"alipay","outTransactionCode":"2019101822001415051403375651","transactionNumber":"A6590883107688345600","amount":0.01,"coin":290,"state":"success","createdAt":"2019-10-18T08:56:26.000+0000","updatedAt":"2019-10-18T08:56:51.000+0000"}]
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
                 * id : 20
                 * memberId : 8
                 * through : alipay
                 * outTransactionCode : 2019101822001415051403375651
                 * transactionNumber : A6590883107688345600
                 * amount : 0.01
                 * coin : 290
                 * state : success
                 * createdAt : 2019-10-18T08:56:26.000+0000
                 * updatedAt : 2019-10-18T08:56:51.000+0000
                 */

                @SerializedName("id")
                private int id;
                @SerializedName("memberId")
                private int memberId;
                @SerializedName("through")
                private String through;
                @SerializedName("outTransactionCode")
                private String outTransactionCode;
                @SerializedName("transactionNumber")
                private String transactionNumber;
                @SerializedName("amount")
                private double amount;
                @SerializedName("coin")
                private int coin;
                @SerializedName("state")
                private String state;
                @SerializedName("createdAt")
                private String createdAt;
                @SerializedName("updatedAt")
                private String updatedAt;

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

                public String getThrough() {
                    return through;
                }

                public void setThrough(String through) {
                    this.through = through;
                }

                public String getOutTransactionCode() {
                    return outTransactionCode;
                }

                public void setOutTransactionCode(String outTransactionCode) {
                    this.outTransactionCode = outTransactionCode;
                }

                public String getTransactionNumber() {
                    return transactionNumber;
                }

                public void setTransactionNumber(String transactionNumber) {
                    this.transactionNumber = transactionNumber;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public int getCoin() {
                    return coin;
                }

                public void setCoin(int coin) {
                    this.coin = coin;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
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
            }
        }
    }
}
