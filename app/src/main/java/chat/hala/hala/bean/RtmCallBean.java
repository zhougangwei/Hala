package chat.hala.hala.bean;

public class RtmCallBean {
    /**
     * name : Lions
     * message : nihao
     * imageUrl : http://starchat.member.7halachat.com/FuIyAFAj9ed6MyIGfl2dZRrI-gM3
     */

    private String name;
    private String message;
    private String imageUrl;
    private String channelId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
