package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author wjy on 2019/8/16/016.
 */
public class ReportBean  extends BaseBean{
    @SerializedName("data")
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
         * title : 政治谣言
         */

        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
