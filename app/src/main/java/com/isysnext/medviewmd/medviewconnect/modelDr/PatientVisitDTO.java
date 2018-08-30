package com.isysnext.medviewmd.medviewconnect.modelDr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class PatientVisitDTO {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("callqueue")
    @Expose
    private List<Callqueue> callqueue = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Callqueue> getCallqueue() {
        return callqueue;
    }

    public void setCallqueue(List<Callqueue> callqueue) {
        this.callqueue = callqueue;
    }

    public  class Callqueue {

        @SerializedName("patient_id")
        @Expose
        private Integer patientId;
        @SerializedName("room")
        @Expose
        private Object room;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("birthday")
        @Expose
        private String birthday;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("waitingroom_id")
        @Expose
        private Integer waitingroomId;

        public Integer getPatientId() {
            return patientId;
        }

        public void setPatientId(Integer patientId) {
            this.patientId = patientId;
        }

        public Object getRoom() {
            return room;
        }

        public void setRoom(Object room) {
            this.room = room;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Integer getWaitingroomId() {
            return waitingroomId;
        }

        public void setWaitingroomId(Integer waitingroomId) {
            this.waitingroomId = waitingroomId;
        }

    }

}