package chat.hala.hala.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import chat.hala.hala.adapter.TagsAdapter;

/**
 * @author wjy on 2019/8/13/013.
 */
public class TagBean extends BaseBean {

    @SerializedName("data")
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  {
        /**
         * name : general
         * tags : [{"content":"aspiring","tagId":19},{"content":"enthusiastic","tagId":23},{"content":"games","tagId":33},{"content":"cooking","tagId":35},{"content":"expressive","tagId":24},{"content":"hard-working","tagId":26},{"content":"active","tagId":16},{"content":"careful","tagId":20},{"content":"confident","tagId":21},{"content":"humorous","tagId":27},{"content":"generous","tagId":31},{"content":"fashion","tagId":32},{"content":"make up","tagId":40},{"content":"dancing","tagId":41},{"content":"panting","tagId":45},{"content":"considerate","tagId":22},{"content":"friendly","tagId":25},{"content":"optimism","tagId":28},{"content":"cute","tagId":29},{"content":"free","tagId":30},{"content":"music","tagId":34},{"content":"photography","tagId":36},{"content":"films","tagId":37},{"content":"sports","tagId":38},{"content":"reading","tagId":39},{"content":"singing","tagId":42},{"content":"traveling","tagId":43},{"content":"football","tagId":44}]
         */

        @SerializedName("name")
        private String name;
        @SerializedName("tags")
        private List<AnchorTagBean.DataBean> tags;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<AnchorTagBean.DataBean> getTags() {
            return tags;
        }

        public void setTags(List<AnchorTagBean.DataBean> tags) {
            this.tags = tags;
        }
    }
}
