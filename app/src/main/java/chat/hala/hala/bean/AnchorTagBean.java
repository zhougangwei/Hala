package chat.hala.hala.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import chat.hala.hala.adapter.TagsAdapter;

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

    public static class DataBean implements MultiItemEntity {
        /**
         * tagId : 46
         * content : نشيط
         */

        private boolean isChoose;
        private int tagId;
        private String content;

        private int itemType=TagsAdapter.TEXT;

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

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
