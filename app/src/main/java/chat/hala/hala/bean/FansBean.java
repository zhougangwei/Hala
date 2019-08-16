package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author wjy on 2019/8/15/015.
 */
public class FansBean extends BaseBean{
    /**
     * data : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"memberId":5,"characterId":"02544174","nickname":"周","gender":"secret","height":160,"weight":50,"birthDate":"1990-01-01","residentialPlace":"杭州","marking":80,"fansCount":0,"introduction":"你号","autograph":"你号","online":true,"available":true,"tags":[],"setting":{"videoCpm":20,"audioCpm":10,"chatCpm":1,"videoNotify":true,"audioNotify":true,"chatNotify":true},"album":[{"id":2,"mediaUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","sortby":0}],"anchorId":1,"answerRate":"0.0%","createdAtDate":"2019-08-12","lastActiveMinuteGap":4156}]}
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
         * list : [{"memberId":5,"characterId":"02544174","nickname":"周","gender":"secret","height":160,"weight":50,"birthDate":"1990-01-01","residentialPlace":"杭州","marking":80,"fansCount":0,"introduction":"你号","autograph":"你号","online":true,"available":true,"tags":[],"setting":{"videoCpm":20,"audioCpm":10,"chatCpm":1,"videoNotify":true,"audioNotify":true,"chatNotify":true},"album":[{"id":2,"mediaUrl":"http://starchat.anchor.7halachat.com/magazine-unlock-05-2.3.1562-715B87CD042446D7AFF69D07210E0131.jpg","sortby":0}],"anchorId":1,"answerRate":"0.0%","createdAtDate":"2019-08-12","lastActiveMinuteGap":4156}]
         */

        @SerializedName("pageable")
        private PageableBean pageable;
        @SerializedName("list")
        private List<OneToOneListBean.DataBean.ListBean> list;

        public PageableBean getPageable() {
            return pageable;
        }

        public void setPageable(PageableBean pageable) {
            this.pageable = pageable;
        }

        public List<OneToOneListBean.DataBean.ListBean> getList() {
            return list;
        }

        public void setList(List<OneToOneListBean.DataBean.ListBean> list) {
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


    }
}
