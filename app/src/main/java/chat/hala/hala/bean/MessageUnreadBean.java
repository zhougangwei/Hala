package chat.hala.hala.bean;


import java.util.List;

public class MessageUnreadBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * iconUrl : http://psgggscq6.bkt.clouddn.com/ic_call_history.png
         * title : Video Calls
         * unreadCount : 0
         * category : video_call
         * brief : just spent 20 coin
         * lastMessageTime : 1999-12-12 12:12
         */

        private String iconUrl;
        private String title;
        private int unreadCount;
        private String category;
        private String brief;
        private String lastMessageTime;

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUnreadCount() {
            return unreadCount;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getLastMessageTime() {
            return lastMessageTime == null ? "" : lastMessageTime;
        }

        public void setLastMessageTime(String lastMessageTime) {
            this.lastMessageTime = lastMessageTime == null ? "" : lastMessageTime;
        }
    }
}
