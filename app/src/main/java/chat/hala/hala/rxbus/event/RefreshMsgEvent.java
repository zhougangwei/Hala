package chat.hala.hala.rxbus.event;

public class RefreshMsgEvent {
    public static final int MSG_COIN=1;
    public static final int MSG_REVERSE=2;
    public static final int MSG_CALL=3;
    public int type;

    public RefreshMsgEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
