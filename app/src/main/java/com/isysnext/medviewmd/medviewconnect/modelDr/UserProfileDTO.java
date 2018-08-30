package com.isysnext.medviewmd.medviewconnect.modelDr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileDTO {

    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Profile {

        @SerializedName("fname")
        @Expose
        private String fname;
        @SerializedName("lname")
        @Expose
        private String lname;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("verified")
        @Expose
        private Boolean verified;
        @SerializedName("securityQ")
        @Expose
        private String securityQ;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("speciality")
        @Expose
        private Integer speciality;
        @SerializedName("is_superadmin")
        @Expose
        private Integer isSuperadmin;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("company")
        @Expose
        private Object company;
        @SerializedName("provider_id")
        @Expose
        private Integer providerId;

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getVerified() {
            return verified;
        }

        public void setVerified(Boolean verified) {
            this.verified = verified;
        }

        public String getSecurityQ() {
            return securityQ;
        }

        public void setSecurityQ(String securityQ) {
            this.securityQ = securityQ;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Integer getSpeciality() {
            return speciality;
        }

        public void setSpeciality(Integer speciality) {
            this.speciality = speciality;
        }

        public Integer getIsSuperadmin() {
            return isSuperadmin;
        }

        public void setIsSuperadmin(Integer isSuperadmin) {
            this.isSuperadmin = isSuperadmin;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public Integer getProviderId() {
            return providerId;
        }

        public void setProviderId(Integer providerId) {
            this.providerId = providerId;
        }

    }
}