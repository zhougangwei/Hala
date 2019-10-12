package chat.hala.hala.bean;

public class FamilyApplication {


    private String familyName;
    private String memNumbers;
    private String linkmanMobel;//联系方式
    private String linkman;//人
    private String mediaUrl;//



    public String getFamilyName() {
        return familyName == null ? "" : familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName == null ? "" : familyName;
    }

    public String getMemNumbers() {
        return memNumbers;
    }

    public void setMemNumbers(String memNumbers) {
        this.memNumbers = memNumbers;
    }

    public String getLinkmanMobel() {
        return linkmanMobel == null ? "" : linkmanMobel;
    }

    public void setLinkmanMobel(String linkmanMobel) {
        this.linkmanMobel = linkmanMobel == null ? "" : linkmanMobel;
    }

    public String getMediaUrl() {
        return mediaUrl == null ? "" : mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl == null ? "" : mediaUrl;
    }

    public String getLinkman() {
        return linkman == null ? "" : linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? "" : linkman;
    }
}
