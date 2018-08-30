package com.isysnext.medviewmd.medviewconnect.modelDr;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeProviderListDTO {
    @SerializedName("providers")
    @Expose
    private List<Provider> providers = null;
    @SerializedName("favouriteCount")
    @Expose
    private Integer favouriteCount;
    @SerializedName("providersCount")
    @Expose
    private Integer providersCount;

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public Integer getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(Integer favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    public Integer getProvidersCount() {
        return providersCount;
    }

    public void setProvidersCount(Integer providersCount) {
        this.providersCount = providersCount;
    }

    public class Provider {

        @SerializedName("provider_id")
        @Expose
        private Integer providerId;
        @SerializedName("npi")
        @Expose
        private String npi;
        @SerializedName("spi")
        @Expose
        private String spi;
        @SerializedName("partner_id")
        @Expose
        private Integer partnerId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("specialty")
        @Expose
        private String specialty;
        @SerializedName("videoprofile")
        @Expose
        private String videoprofile;
        @SerializedName("videoProfileUrl")
        @Expose
        private Object videoProfileUrl;
        @SerializedName("textprofile")
        @Expose
        private String textprofile;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("education")
        @Expose
        private String education;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("board")
        @Expose
        private String board;
        @SerializedName("residency")
        @Expose
        private String residency;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("clinicname")
        @Expose
        private String clinicname;
        @SerializedName("cost")
        @Expose
        private String cost;
        @SerializedName("livestatus")
        @Expose
        private Integer livestatus;
        @SerializedName("signature")
        @Expose
        private Object signature;
        @SerializedName("calculatedRating")
        @Expose
        private Integer calculatedRating;
        @SerializedName("created_at")
        @Expose
        private Object createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("transaction_url")
        @Expose
        private String transactionUrl;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("is_favourite")
        @Expose
        private Integer isFavourite;
        @SerializedName("chatcounter")
        @Expose
        private Integer chatcounter;
        @SerializedName("rating")
        @Expose
        private Integer rating;

        public Integer getProviderId() {
            return providerId;
        }

        public void setProviderId(Integer providerId) {
            this.providerId = providerId;
        }

        public String getNpi() {
            return npi;
        }

        public void setNpi(String npi) {
            this.npi = npi;
        }

        public String getSpi() {
            return spi;
        }

        public void setSpi(String spi) {
            this.spi = spi;
        }

        public Integer getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(Integer partnerId) {
            this.partnerId = partnerId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getVideoprofile() {
            return videoprofile;
        }

        public void setVideoprofile(String videoprofile) {
            this.videoprofile = videoprofile;
        }

        public Object getVideoProfileUrl() {
            return videoProfileUrl;
        }

        public void setVideoProfileUrl(Object videoProfileUrl) {
            this.videoProfileUrl = videoProfileUrl;
        }

        public String getTextprofile() {
            return textprofile;
        }

        public void setTextprofile(String textprofile) {
            this.textprofile = textprofile;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
        }

        public String getResidency() {
            return residency;
        }

        public void setResidency(String residency) {
            this.residency = residency;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getClinicname() {
            return clinicname;
        }

        public void setClinicname(String clinicname) {
            this.clinicname = clinicname;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public Integer getLivestatus() {
            return livestatus;
        }

        public void setLivestatus(Integer livestatus) {
            this.livestatus = livestatus;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public Integer getCalculatedRating() {
            return calculatedRating;
        }

        public void setCalculatedRating(Integer calculatedRating) {
            this.calculatedRating = calculatedRating;
        }

        public Object getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Object createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getTransactionUrl() {
            return transactionUrl;
        }

        public void setTransactionUrl(String transactionUrl) {
            this.transactionUrl = transactionUrl;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Integer getIsFavourite() {
            return isFavourite;
        }

        public void setIsFavourite(Integer isFavourite) {
            this.isFavourite = isFavourite;
        }

        public Integer getChatcounter() {
            return chatcounter;
        }

        public void setChatcounter(Integer chatcounter) {
            this.chatcounter = chatcounter;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

    }
}