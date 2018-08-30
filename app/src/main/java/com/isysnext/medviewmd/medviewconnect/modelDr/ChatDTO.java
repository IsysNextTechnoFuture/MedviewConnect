package com.isysnext.medviewmd.medviewconnect.modelDr;

public class ChatDTO {

    String senderName,DateTime,Message;

    public String getSenderName() {
        return senderName;
    }

    public ChatDTO(String senderName, String dateTime, String message) {
        this.senderName = senderName;
        DateTime = dateTime;
        Message = message;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
