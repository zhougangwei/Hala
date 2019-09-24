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
    private Integer callId;
    private Integer lootId;
    private boolean enableVideo=true;

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

    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public boolean isEnableVideo() {
        return enableVideo;
    }

    public Integer getLootId() {
        return lootId;
    }

    public void setLootId(Integer lootId) {
        this.lootId = lootId;
    }

    public void setEnableVideo(boolean enableVideo) {
        this.enableVideo = enableVideo;
    }
}
