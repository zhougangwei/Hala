package chat.hala.hala.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplyAnchorBean extends BaseBean {


    /**
     * anchor : {"residentialPlace":"南京","introduction":"this is an introduction","weight":"70","height":"182","album":[{"sortby":"1","mediaUrl":"http://media.1.url"},{"sortby":"2","mediaUrl":"http://media.2.url"}],"birthDate":"2009-01-01"}
     * application : {"certifyVideo":"http://certify.video","idCardHandled":"http://id.card.and.self.img","idCardFront":"http://id.card.front.img","idCardBack":"http://id.card.back.img","realName":"pete","mobileNumber":"+8613851668723"}
     */

    @SerializedName("anchor")
    private AnchorBean anchor;
    @SerializedName("application")
    private ApplicationBean application;

    public AnchorBean getAnchor() {
        return anchor;
    }

    public void setAnchor(AnchorBean anchor) {
        this.anchor = anchor;
    }

    public ApplicationBean getApplication() {
        return application;
    }

    public void setApplication(ApplicationBean application) {
        this.application = application;
    }

    public static class AnchorBean {
        /**
         * residentialPlace : 南京
         * introduction : this is an introduction
         * weight : 70
         * height : 182
         * album : [{"sortby":"1","mediaUrl":"http://media.1.url"},{"sortby":"2","mediaUrl":"http://media.2.url"}]
         * birthDate : 2009-01-01
         */

        @SerializedName("residentialPlace")
        private String residentialPlace;
        @SerializedName("introduction")
        private String introduction;
        @SerializedName("weight")
        private String weight;
        @SerializedName("height")
        private String height;
        @SerializedName("birthDate")
        private String birthDate;
        @SerializedName("album")
        private List<AlbumBean> album;

        public String getResidentialPlace() {
            return residentialPlace;
        }

        public void setResidentialPlace(String residentialPlace) {
            this.residentialPlace = residentialPlace;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public List<AlbumBean> getAlbum() {
            return album;
        }

        public void setAlbum(List<AlbumBean> album) {
            this.album = album;
        }

        public static class AlbumBean {
            /**
             * sortby : 1
             * mediaUrl : http://media.1.url
             */

            @SerializedName("sortby")
            private String sortby;
            @SerializedName("mediaUrl")
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

    public static class ApplicationBean {
        /**
         * certifyVideo : http://certify.video
         * idCardHandled : http://id.card.and.self.img
         * idCardFront : http://id.card.front.img
         * idCardBack : http://id.card.back.img
         * realName : pete
         * mobileNumber : +8613851668723
         */

        @SerializedName("certifyVideo")
        private String certifyVideo;
        @SerializedName("idCardHandled")
        private String idCardHandled;
        @SerializedName("idCardFront")
        private String idCardFront;
        @SerializedName("idCardBack")
        private String idCardBack;
        @SerializedName("realName")
        private String realName;
        @SerializedName("mobileNumber")
        private String mobileNumber;

        public String getCertifyVideo() {
            return certifyVideo;
        }

        public void setCertifyVideo(String certifyVideo) {
            this.certifyVideo = certifyVideo;
        }

        public String getIdCardHandled() {
            return idCardHandled;
        }

        public void setIdCardHandled(String idCardHandled) {
            this.idCardHandled = idCardHandled;
        }

        public String getIdCardFront() {
            return idCardFront;
        }

        public void setIdCardFront(String idCardFront) {
            this.idCardFront = idCardFront;
        }

        public String getIdCardBack() {
            return idCardBack;
        }

        public void setIdCardBack(String idCardBack) {
            this.idCardBack = idCardBack;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }
    }
}
