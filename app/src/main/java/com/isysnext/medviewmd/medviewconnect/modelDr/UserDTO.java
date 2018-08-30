package com.isysnext.medviewmd.medviewconnect.modelDr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UserDTO {

    //Patient and Doctor Sign In
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("uinfo")
    @Expose
    private Uinfo uinfo;

    //Patient Sign Up
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("residenceType")
    @Expose
    private String residenceType;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("partner_id")
    @Expose
    private String partnerId;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("security_answer")
    @Expose
    private String securityAnswer;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("repeat_password")
    @Expose
    private String repeatPassword;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("security_question")
    @Expose
    private String securityQuestion;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("filename")
    @Expose
    private String filename;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Uinfo getUinfo() {
        return uinfo;
    }

    public void setUinfo(Uinfo uinfo) {
        this.uinfo = uinfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //Patient and Doctor Forgot Password
    @SerializedName("link")
    @Expose
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //Patient Add to favorite
    @SerializedName("status")
    @Expose
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //Patient sign in
    public class Uinfo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("is_superadmin")
        @Expose
        private String isSuperadmin;
        @SerializedName("provider_id")
        @Expose
        private Integer providerId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("speciality")
        @Expose
        private String speciality;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("securityQ")
        @Expose
        private String securityQ;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getIsSuperadmin() {
            return isSuperadmin;
        }

        public void setIsSuperadmin(String isSuperadmin) {
            this.isSuperadmin = isSuperadmin;
        }

        public Integer getProviderId() {
            return providerId;
        }

        public void setProviderId(Integer providerId) {
            this.providerId = providerId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
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

        public String getSecurityQ() {
            return securityQ;
        }

        public void setSecurityQ(String securityQ) {
            this.securityQ = securityQ;
        }
    }
    //Patient Sign up getter setter
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

