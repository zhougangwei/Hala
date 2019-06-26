package chat.hala.hala.bean;

import java.util.List;

public class CallListBean extends BaseBean {

    /**
     * data : {"pageable":{"nextPage":true,"totalPages":5,"currentPage":1},"list":[{"channel":"C6547837725262233600","memberId":2,"anchorMemberId":1,"anchorId":14,"anchorInitiate":false,"startedAt":"2019-06-21T14:09:26.000+0000","endedAt":"2019-06-21T14:09:26.000+0000","durationSeconds":0,"state":"calling","worth":0,"targetInfo":{"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"},"date":"2019-06-21","callId":6}]}
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
         * pageable : {"nextPage":true,"totalPages":5,"currentPage":1}
         * list : [{"channel":"C6547837725262233600","memberId":2,"anchorMemberId":1,"anchorId":14,"anchorInitiate":false,"startedAt":"2019-06-21T14:09:26.000+0000","endedAt":"2019-06-21T14:09:26.000+0000","durationSeconds":0,"state":"calling","worth":0,"targetInfo":{"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"},"date":"2019-06-21","callId":6}]
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
             * nextPage : true
             * totalPages : 5
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
             * channel : C6547837725262233600
             * memberId : 2
             * anchorMemberId : 1
             * anchorId : 14
             * anchorInitiate : false
             * startedAt : 2019-06-21T14:09:26.000+0000
             * endedAt : 2019-06-21T14:09:26.000+0000
             * durationSeconds : 0
             * state : calling
             * worth : 0
             * targetInfo : {"id":14,"avatarUrl":"http://me.avatar.url","name":"old p","form":"anchor"}
             * date : 2019-06-21
             * callId : 6
             */

            private String channel;
            private int memberId;
            private int anchorMemberId;
            private int anchorId;
            private boolean anchorInitiate;
            private String startedAt;
            private String endedAt;
            private int durationSeconds;
            private String state;
            private int worth;
            private TargetInfoBean targetInfo;
            private String date;
            private int callId;

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getAnchorMemberId() {
                return anchorMemberId;
            }

            public void setAnchorMemberId(int anchorMemberId) {
                this.anchorMemberId = anchorMemberId;
            }

            public int getAnchorId() {
                return anchorId;
            }

            public void setAnchorId(int anchorId) {
                this.anchorId = anchorId;
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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getCallId() {
                return callId;
            }

            public void setCallId(int callId) {
                this.callId = callId;
            }

            public static class TargetInfoBean {
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
