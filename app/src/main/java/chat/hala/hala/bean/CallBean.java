package chat.hala.hala.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 16:28
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class CallBean extends BaseBean{
    /**
     * code : 1
     * message : success
     * data : {"channel":"C6547889694427029504","memberId":2,"anchorMemberId":1,"anchorId":14,"anchorInitiate":false,"durationSeconds":0,"state":"calling","worth":0,"maxSeconds":360,"callId":13,"datetime":"2019-06-22 01:35","date":"2019-06-22"}
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
         * channel : C6547889694427029504
         * memberId : 2
         * anchorMemberId : 1
         * anchorId : 14
         * anchorInitiate : false
         * durationSeconds : 0
         * state : calling
         * worth : 0
         * maxSeconds : 360
         * callId : 13
         * datetime : 2019-06-22 01:35
         * date : 2019-06-22
         */

        private String channel;
        private int     memberId;
        private int     anchorMemberId;
        private int     anchorId;
        private boolean anchorInitiate;
        private int     durationSeconds;
        private String  state;
        private int     worth;
        private int     maxSeconds;
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

        public int getMaxSeconds() {
            return maxSeconds;
        }

        public void setMaxSeconds(int maxSeconds) {
            this.maxSeconds = maxSeconds;
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
