package chat.hala.hala.bean;

public class StartCallBean extends BaseBean{
    /**
     * data : {"id":5,"channel":"C6546658162134933504","memberId":2,"anchorMemberId":1,"anchorId":14,"anchorInitiate":false,"durationSeconds":0,"state":"calling","worth":0,"extraInfo":{"maxSeconds":2940,"targetState":"offline"},"date":"2019-06-18"}
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
         * id : 5
         * channel : C6546658162134933504
         * memberId : 2
         * anchorMemberId : 1
         * anchorId : 14
         * anchorInitiate : false
         * durationSeconds : 0
         * state : calling
         * worth : 0
         * extraInfo : {"maxSeconds":2940,"targetState":"offline"}
         * date : 2019-06-18
         */

        private int id;
        private String channel;
        private int memberId;
        private int anchorMemberId;
        private int anchorId;
        private boolean anchorInitiate;
        private int durationSeconds;
        private String state;
        private int worth;
        private ExtraInfoBean extraInfo;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public ExtraInfoBean getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(ExtraInfoBean extraInfo) {
            this.extraInfo = extraInfo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public static class ExtraInfoBean {
            /**
             * maxSeconds : 2940
             * targetState : offline
             */

            private int maxSeconds;
            private String targetState;

            public int getMaxSeconds() {
                return maxSeconds;
            }

            public void setMaxSeconds(int maxSeconds) {
                this.maxSeconds = maxSeconds;
            }

            public String getTargetState() {
                return targetState;
            }

            public void setTargetState(String targetState) {
                this.targetState = targetState;
            }
        }
    }
}
