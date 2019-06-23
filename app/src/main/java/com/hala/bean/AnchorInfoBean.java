package com.hala.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/22 0022 14:08
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/22 0022$
 * @ 更新描述  ${TODO}
 */
public class AnchorInfoBean {
    String name;
    String content;

    public AnchorInfoBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public AnchorInfoBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AnchorInfoBean setContent(String content) {
        this.content = content;
        return this;
    }
}
