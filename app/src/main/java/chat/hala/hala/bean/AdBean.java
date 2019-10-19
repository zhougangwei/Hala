package chat.hala.hala.bean;

import java.util.List;

public class AdBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * mediaUrl : https://hbimg.huabanimg.com/aa7820869882f16125951e55b2359096f0a3990a3dfa3-Oqgngt_fw658
         * locate : banner
         * sortby : 0
         * createdAt : 2019-05-28T07:49:01.000+0000
         * updatedAt : 2019-05-28T07:49:01.000+0000
         */

        private int id;
        private String mediaUrl;
        private String locate;
        private int sortby;
        private String createdAt;
        private String updatedAt;
        private String pointTo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getLocate() {
            return locate;
        }

        public void setLocate(String locate) {
            this.locate = locate;
        }

        public int getSortby() {
            return sortby;
        }

        public void setSortby(int sortby) {
            this.sortby = sortby;
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

        public String getPointTo() {
            return pointTo == null ? "" : pointTo;
        }

        public void setPointTo(String pointTo) {
            this.pointTo = pointTo == null ? "" : pointTo;
        }
    }
}
