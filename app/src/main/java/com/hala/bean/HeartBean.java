package com.hala.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/23 0023 19:57
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/23 0023$
 * @ 更新描述  ${TODO}
 */
public class HeartBean extends BaseBean {
    /**
     * data : {"restSeconds":1140}
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
         * restSeconds : 1140
         */

        private int restSeconds;

        public int getRestSeconds() {
            return restSeconds;
        }

        public void setRestSeconds(int restSeconds) {
            this.restSeconds = restSeconds;
        }
    }
}
