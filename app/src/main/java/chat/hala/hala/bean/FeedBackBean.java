package chat.hala.hala.bean;

public class FeedBackBean extends BaseBean {
    /**
     * data : {"id":1,"memberId":2,"mediaUrl":"http://test.media.url","category":"general","description":"this is a description"}
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
         * id : 1
         * memberId : 2
         * mediaUrl : http://test.media.url
         * category : general
         * description : this is a description
         */

        private int id;
        private int memberId;
        private String mediaUrl;
        private String category;
        private String description;

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

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
