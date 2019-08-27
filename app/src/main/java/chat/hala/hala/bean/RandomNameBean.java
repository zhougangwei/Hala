package chat.hala.hala.bean;

/**
 * Created by zhougangwei on 2018/8/16.
 */

public class RandomNameBean {
    private String name;


    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }
}
