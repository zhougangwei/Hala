package chat.hala.hala.bean;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/26 0026 22:19
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/26 0026$
 * @ 更新描述  ${TODO}
 */
public class CoinBriefBean extends BaseBean {
    /**
     * data : {"total":980,"items":[{"iconUrl":"http://psgggscq6.bkt.clouddn.com/ic_wallet.png","title":"Recharge","category":"recharge"},{"iconUrl":"http://psgggscq6.bkt.clouddn.com/ic_cost.png","title":"Cost","category":"cost"},{"iconUrl":"http://psgggscq6.bkt.clouddn.com/ic_income.png","title":"Income","category":"income"}]}
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
         * total : 980
         * items : [{"iconUrl":"http://psgggscq6.bkt.clouddn.com/ic_wallet.png","title":"Recharge","category":"recharge"},{"iconUrl":"http://psgggscq6.bkt.clouddn.com/ic_cost.png","title":"Cost","category":"cost"},{"iconUrl":"http://psgggscq6.bkt.clouddn.com/ic_income.png","title":"Income","category":"income"}]
         */

        private int total;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * iconUrl : http://psgggscq6.bkt.clouddn.com/ic_wallet.png
             * title : Recharge
             * category : recharge
             */

            private String iconUrl;
            private String title;
            private String category;

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

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }
        }
    }
}
