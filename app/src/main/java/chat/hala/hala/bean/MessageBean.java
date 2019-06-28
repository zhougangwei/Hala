package chat.hala.hala.bean;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/6/28 0028 23:44
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/6/28 0028$
 * @ 更新描述  ${TODO}
 */
public class MessageBean {
    String id;
    String message;

    public MessageBean(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public MessageBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageBean setMessage(String message) {
        this.message = message;
        return this;
    }
}
