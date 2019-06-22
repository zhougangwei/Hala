package com.hala.bean;

public class ReverseBean extends BaseBean {
    /**
     * data : {"id":4,"state":"in_process","worth":20,"createdAt":"2019-05-28T02:16:18.786+0000","updatedAt":"2019-05-28T02:16:18.786+0000"}
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
         * id : 4
         * state : in_process
         * worth : 20
         * createdAt : 2019-05-28T02:16:18.786+0000
         * updatedAt : 2019-05-28T02:16:18.786+0000
         */

        private int id;
        private String state;
        private int worth;
        private String createdAt;
        private String updatedAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
