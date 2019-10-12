package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author wjy on 2019/8/14/014.
 */
public class ChatSettingBean {
    /**
     * audioCpm : 100
     * chatCpm : 80
     * videoCpm : 200
     * videoNotify : true
     * audioNotify : true
     * chatNotify : false
     */

    @SerializedName("audioCpm")
    private String audioCpm;
    @SerializedName("chatCpm")
    private String chatCpm;
    @SerializedName("videoCpm")
    private String videoCpm;
    @SerializedName("videoNotify")
    private boolean videoNotify;
    @SerializedName("audioNotify")
    private boolean audioNotify;
    @SerializedName("chatNotify")
    private boolean chatNotify;
    @SerializedName("greetWord")
    private String greetWord;


    public String getAudioCpm() {
        return audioCpm;
    }

    public void setAudioCpm(String audioCpm) {
        this.audioCpm = audioCpm;
    }

    public String getChatCpm() {
        return chatCpm;
    }

    public void setChatCpm(String chatCpm) {
        this.chatCpm = chatCpm;
    }

    public String getVideoCpm() {
        return videoCpm;
    }

    public void setVideoCpm(String videoCpm) {
        this.videoCpm = videoCpm;
    }

    public boolean isVideoNotify() {
        return videoNotify;
    }

    public void setVideoNotify(boolean videoNotify) {
        this.videoNotify = videoNotify;
    }

    public boolean isAudioNotify() {
        return audioNotify;
    }

    public void setAudioNotify(boolean audioNotify) {
        this.audioNotify = audioNotify;
    }

    public boolean isChatNotify() {
        return chatNotify;
    }

    public void setChatNotify(boolean chatNotify) {
        this.chatNotify = chatNotify;
    }

    public String getGreetWord() {
        return greetWord == null ? "" : greetWord;
    }

    public void setGreetWord(String greetWord) {
        this.greetWord = greetWord == null ? "" : greetWord;
    }
}
