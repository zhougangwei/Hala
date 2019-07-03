package chat.hala.hala.bean;

import java.util.List;

public class AnchorTagBean extends BaseBean{
    /**
     * code : 1
     * message : success
     * data : [{"id":46,"content":"نشيط"},"..."]
     */

    private List<DataBean> data;


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tagId : 46
         * content : نشيط
         */

        private boolean isChoose;
        private int tagId;
        private String content;

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }
    }
}
