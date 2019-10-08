package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/9/24/024.
 */
public class FamilyCodeBean extends BaseBean {
    /**
     * data : gew6hs8
     */

    @SerializedName("data")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
