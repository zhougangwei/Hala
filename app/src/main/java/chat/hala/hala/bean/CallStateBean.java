package chat.hala.hala.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/27 0027 8:47
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/27 0027$
 * @ 更新描述  ${TODO}
 */
public class CallStateBean extends BaseBean {
    /**
     * data : {"channel":"C6547880494525542400","memberId":2,"anchorMemberId":1,"anchorId":14,"anchorInitiate":false,"startedAt":"2019-06-21T16:59:38.000+0000","endedAt":"2019-06-21T17:07:15.638+0000","durationSeconds":60,"state":"succeed_hung_up","worth":20,"callId":12,"datetime":"2019-06-22 00:59","date":"2019-06-22"}
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
         * channel : C6547880494525542400
         * memberId : 2
         * anchorMemberId : 1
         * anchorId : 14
         * anchorInitiate : false
         * startedAt : 2019-06-21T16:59:38.000+0000
         * endedAt : 2019-06-21T17:07:15.638+0000
         * durationSeconds : 60
         * state : succeed_hung_up
         * worth : 20
         * callId : 12
         * datetime : 2019-06-22 00:59
         * date : 2019-06-22
         */

        private String channel;
        private int     memberId;
        private int     anchorMemberId;
        private int     anchorId;
        private boolean anchorInitiate;
        private String  startedAt;
        private String  endedAt;
        private int     durationSeconds;
        private String  state;
        private int     worth;
        private int     callId;
        private String  datetime;
        private String  date;

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

        public int getCallId() {
            return callId;
        }

        public void setCallId(int callId) {
            this.callId = callId;
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
    }
}
