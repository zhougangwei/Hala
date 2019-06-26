package chat.hala.hala.bean;

public class AnchorStateBean extends BaseBean {
    /**
     * data : {"cpm":20,"available":true,"online":true,"maxMinutes":0,"restCoins":0}
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
         * cpm : 20
         * available : true
         * online : true
         * maxMinutes : 0
         * restCoins : 0
         */

        private int cpm;
        private boolean available;
        private boolean online;
        private int maxMinutes;
        private int restCoins;

        public int getCpm() {
            return cpm;
        }

        public void setCpm(int cpm) {
            this.cpm = cpm;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public boolean isOnline() {
            return online;
        }

        public void setOnline(boolean online) {
            this.online = online;
        }

        public int getMaxMinutes() {
            return maxMinutes;
        }

        public void setMaxMinutes(int maxMinutes) {
            this.maxMinutes = maxMinutes;
        }

        public int getRestCoins() {
            return restCoins;
        }

        public void setRestCoins(int restCoins) {
            this.restCoins = restCoins;
        }
    }
}
