package com.hala.bean;

public class LoginBean2 {
    /**
     * code : 1
     * message : success
     * data : {"action":"sign_up"}
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
         * action : sign_up
         */

        private String action;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
