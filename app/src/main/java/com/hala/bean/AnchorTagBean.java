package com.hala.bean;

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
         * id : 46
         * content : نشيط
         */

        private int id;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
