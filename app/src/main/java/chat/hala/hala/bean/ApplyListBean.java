package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author wjy on 2019/9/16/016.
 */
public class ApplyListBean extends BaseBean {


    /**
     * data : {"pageable":{"nextPage":false,"totalPages":1,"currentPage":1},"list":[{"LootChat":{"id":68,"memberId":8,"category":"video","coin":99,"version":0,"islooped":false},"Member":{"characterId":"10486931","mobileNumber":"+8612345678910","nickname":"pp","gender":"male","birthDate":"2019-01-01","autograph":"??","rongToken":"dck/4HVqHEl58hAUWXeZiV6AsbGff9KvUT62Noch30mb6n+CHOf4Vdoco+C/pVxohCVnUyo2V5Rc1gC7seANjA==","coin":99980,"spent":20,"followingCount":0,"online":true,"album":[{"id":8,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","sortby":0}],"setting":{"videoNotify":true,"audioNotify":true,"chatNotify":true},"memberId":8,"lastActiveMinuteGap":8598,"createdAtDate":"2019-08-12","genderOrdinal":1}}]}
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
         * list : [{"LootChat":{"id":68,"memberId":8,"category":"video","coin":99,"version":0,"islooped":false},"Member":{"characterId":"10486931","mobileNumber":"+8612345678910","nickname":"pp","gender":"male","birthDate":"2019-01-01","autograph":"??","rongToken":"dck/4HVqHEl58hAUWXeZiV6AsbGff9KvUT62Noch30mb6n+CHOf4Vdoco+C/pVxohCVnUyo2V5Rc1gC7seANjA==","coin":99980,"spent":20,"followingCount":0,"online":true,"album":[{"id":8,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","sortby":0}],"setting":{"videoNotify":true,"audioNotify":true,"chatNotify":true},"memberId":8,"lastActiveMinuteGap":8598,"createdAtDate":"2019-08-12","genderOrdinal":1}}]
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
             * LootChat : {"id":68,"memberId":8,"category":"video","coin":99,"version":0,"islooped":false}
             * Member : {"characterId":"10486931","mobileNumber":"+8612345678910","nickname":"pp","gender":"male","birthDate":"2019-01-01","autograph":"??","rongToken":"dck/4HVqHEl58hAUWXeZiV6AsbGff9KvUT62Noch30mb6n+CHOf4Vdoco+C/pVxohCVnUyo2V5Rc1gC7seANjA==","coin":99980,"spent":20,"followingCount":0,"online":true,"album":[{"id":8,"mediaUrl":"http://starchat.general.7halachat.com/member_avatar_test.png","sortby":0}],"setting":{"videoNotify":true,"audioNotify":true,"chatNotify":true},"memberId":8,"lastActiveMinuteGap":8598,"createdAtDate":"2019-08-12","genderOrdinal":1}
             */

            @SerializedName("LootChat")
            private LootChatBean LootChat;
            @SerializedName("Member")
            private OneToOneListBean.DataBean.ListBean Member;
            private State state;

            public enum State {
                apply_wating,
                apply_failed,
                apply_waited,
                apply_successed,
            }


            public State getState() {
                return state;
            }

            public void setState(State state) {
                this.state = state;
            }


            public LootChatBean getLootChat() {
                return LootChat;
            }

            public void setLootChat(LootChatBean LootChat) {
                this.LootChat = LootChat;
            }

            public OneToOneListBean.DataBean.ListBean getMember() {
                return Member;
            }

            public void setMember(OneToOneListBean.DataBean.ListBean Member) {
                this.Member = Member;
            }

            public static class LootChatBean {
                /**
                 * id : 68
                 * memberId : 8
                 * category : video
                 * coin : 99
                 * version : 0
                 * islooped : false
                 */

                @SerializedName("id")
                private int id;
                @SerializedName("memberId")
                private int memberId;
                @SerializedName("category")
                private String category;
                @SerializedName("coin")
                private int coin;
                @SerializedName("version")
                private int version;
                @SerializedName("islooped")
                private boolean islooped;

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

                public int getCoin() {
                    return coin;
                }

                public void setCoin(int coin) {
                    this.coin = coin;
                }

                public int getVersion() {
                    return version;
                }

                public void setVersion(int version) {
                    this.version = version;
                }

                public boolean isIslooped() {
                    return islooped;
                }

                public void setIslooped(boolean islooped) {
                    this.islooped = islooped;
                }
            }

        }
    }
}
