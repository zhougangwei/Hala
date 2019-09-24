package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author wjy on 2019/9/23/023.
 */
public class FamilyAnchorBean extends BaseBean {

    /**
     * data : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"characterId":"08925802","mobileNumber":"+8615122220001","nickname":"????","gender":"male","birthDate":"2000-10-14","autograph":"?123","residentialPlace":"???","anchorId":2,"rongToken":"sm6ADusDTmrIhTYmXivermgPfwz4MZsem15zZgHcizS6wQxHd4hkc8YSLthfcy61y/KkdJBt5q5/aTia01YK+A==","coin":100000,"spent":0,"pea":1029,"followingCount":0,"online":false,"joinfamilyAt":"2019-09-19T11:22:41.000+0000","leavefamilyAt":"2019-09-19T11:22:42.000+0000","album":[{"id":16,"mediaUrl":"http://starchat.anchor.7halachat.com/Screenshot_2019-08-13-10-58-51.png","sortby":0},{"id":17,"mediaUrl":"http://starchat.anchor.7halachat.com/Screenshot_2019-08-13-14-51-22.png","sortby":1}],"setting":{"videoNotify":true,"audioNotify":false,"chatNotify":false},"memberId":6,"createdAtDate":"2019-08-12","genderOrdinal":1,"lastActiveMinuteGap":4207},{"characterId":"02544174","mobileNumber":"+8613811114444","nickname":"?","gender":"secret","birthDate":"2000-10-14","autograph":"??","residentialPlace":"??","anchorId":1,"rongToken":"I34rC+bQTeEMQcO7kwL3K16AsbGff9KvUT62Noch30mb6n+CHOf4VSjk5+knDcNk6ysOovR6+BFc1gC7seANjA==","coin":100000,"spent":0,"pea":39,"followingCount":0,"online":false,"joinfamilyAt":"2019-09-19T11:22:41.000+0000","leavefamilyAt":"2019-09-19T11:22:42.000+0000","album":[{"id":5,"mediaUrl":"http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg","sortby":1}],"setting":{"videoNotify":true,"audioNotify":true,"chatNotify":true},"memberId":5,"createdAtDate":"2019-08-12","genderOrdinal":0,"lastActiveMinuteGap":4199}]}
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
         * list : [{"characterId":"08925802","mobileNumber":"+8615122220001","nickname":"????","gender":"male","birthDate":"2000-10-14","autograph":"?123","residentialPlace":"???","anchorId":2,"rongToken":"sm6ADusDTmrIhTYmXivermgPfwz4MZsem15zZgHcizS6wQxHd4hkc8YSLthfcy61y/KkdJBt5q5/aTia01YK+A==","coin":100000,"spent":0,"pea":1029,"followingCount":0,"online":false,"joinfamilyAt":"2019-09-19T11:22:41.000+0000","leavefamilyAt":"2019-09-19T11:22:42.000+0000","album":[{"id":16,"mediaUrl":"http://starchat.anchor.7halachat.com/Screenshot_2019-08-13-10-58-51.png","sortby":0},{"id":17,"mediaUrl":"http://starchat.anchor.7halachat.com/Screenshot_2019-08-13-14-51-22.png","sortby":1}],"setting":{"videoNotify":true,"audioNotify":false,"chatNotify":false},"memberId":6,"createdAtDate":"2019-08-12","genderOrdinal":1,"lastActiveMinuteGap":4207},{"characterId":"02544174","mobileNumber":"+8613811114444","nickname":"?","gender":"secret","birthDate":"2000-10-14","autograph":"??","residentialPlace":"??","anchorId":1,"rongToken":"I34rC+bQTeEMQcO7kwL3K16AsbGff9KvUT62Noch30mb6n+CHOf4VSjk5+knDcNk6ysOovR6+BFc1gC7seANjA==","coin":100000,"spent":0,"pea":39,"followingCount":0,"online":false,"joinfamilyAt":"2019-09-19T11:22:41.000+0000","leavefamilyAt":"2019-09-19T11:22:42.000+0000","album":[{"id":5,"mediaUrl":"http://starchat.member.7halachat.com/magazine-unlock-01-2.3.1552-BE1C1D482E72759249831391682C2D38.jpg","sortby":1}],"setting":{"videoNotify":true,"audioNotify":true,"chatNotify":true},"memberId":5,"createdAtDate":"2019-08-12","genderOrdinal":0,"lastActiveMinuteGap":4199}]
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
