package chat.hala.hala.bean;

import java.util.List;

/**
 * @ 创建者   zhou
 * @ 创建时间   2019/8/10 0010 15:18
 * @ 描述    ${TODO}
 * @ 更新者  $AUTHOR$
 * @ 更新时间    2019/8/10 0010$
 * @ 更新描述  ${TODO}
 */
public class EditUserBean {
    /**
     * gender : male
     * residentialPlace : 南京
     * code : 151439
     * birthDate : 2019-01-01
     * username : pp8
     * mobileNumber : +8613851668728
     * album : [{"sortby":"1","mediaUrl":"http://media.url.1"}]
     * autograph : 这是一个简介这是一个简介
     */

    private String gender;
    private String          residentialPlace;
    private String          code;
    private String          birthDate;
    private String          username;
    private String          mobileNumber;
    private String          autograph;
    private List<AlbumBean> album;

    private List<Integer> tagIds;
    private String height;
    private String weight;
    private String introduction;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResidentialPlace() {
        return residentialPlace;
    }

    public void setResidentialPlace(String residentialPlace) {
        this.residentialPlace = residentialPlace;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public List<AlbumBean> getAlbum() {
        return album;
    }

    public void setAlbum(List<AlbumBean> album) {
        this.album = album;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public String getHeight() {
        return height == null ? "" : height;
    }

    public void setHeight(String height) {
        this.height = height == null ? "" : height;
    }

    public String getWeight() {
        return weight == null ? "" : weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? "" : weight;
    }

    public String getIntroduction() {
        return introduction == null ? "" : introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? "" : introduction;
    }

    public static class AlbumBean {
        /**
         * sortby : 1
         * mediaUrl : http://media.url.1
         */

        private String sortby;
        private String mediaUrl;

        public String getSortby() {
            return sortby;
        }

        public void setSortby(String sortby) {
            this.sortby = sortby;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }
    }
}
