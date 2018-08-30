package com.isysnext.medviewmd.medviewconnect.modelDr;

public class AppointmentItem {
    String patient_name,time,date,state_name;

    public AppointmentItem(String patient_name, String time, String date, String state_name) {
        this.patient_name = patient_name;
        this.time = time;
        this.date = date;
        this.state_name = state_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
}
