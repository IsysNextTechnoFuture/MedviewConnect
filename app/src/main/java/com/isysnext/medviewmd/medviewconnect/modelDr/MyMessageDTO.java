package com.isysnext.medviewmd.medviewconnect.modelDr;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name = "MyMessageDT")
public class MyMessageDTO extends Model {


    @SerializedName("message status")
    @Expose
    @Column(name = "Messagestatus")
    private String messageStatus;

    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;

    @SerializedName("success")
    @Expose
    @Column(name = "Success")
    private Integer success;

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public MyMessageDTO() {
        super();
    }

    @Table(name = "MyMessage")
    public  class Message {

        @SerializedName("fromname")
        @Expose
        @Column(name = "Fromname")
        private String fromname;
        @SerializedName("toname")
        @Expose
        @Column(name = "Toname")
        private String toname;
        @SerializedName("from")
        @Expose
        @Column(name = "From")
        private Integer from;
        @SerializedName("to")
        @Expose
        @Column(name = "To")
        private Integer to;
        @SerializedName("read")
        @Expose
        @Column(name = "Read")
        private Integer read;
        @SerializedName("subject")
        @Expose
        @Column(name = "Subject")
        private String subject;
        @SerializedName("message")
        @Expose
        @Column(name = "Message")
        private String message;
        @SerializedName("message_id")
        @Expose
        @Column(name = "MessageId")
        private Integer messageId;
        @SerializedName("created_at")
        @Expose
        @Column(name = "CreatedAt")
        private String createdAt;
        @SerializedName("history")
        @Expose
        private List<History> history = null;
        @SerializedName("attachment")
        @Expose
        private List<Attachment> attachment = null;

        public String getFromname() {
            return fromname;
        }

        public void setFromname(String fromname) {
            this.fromname = fromname;
        }

        public String getToname() {
            return toname;
        }

        public void setToname(String toname) {
            this.toname = toname;
        }

        public Integer getFrom() {
            return from;
        }

        public void setFrom(Integer from) {
            this.from = from;
        }

        public Integer getTo() {
            return to;
        }

        public void setTo(Integer to) {
            this.to = to;
        }

        public Integer getRead() {
            return read;
        }

        public void setRead(Integer read) {
            this.read = read;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getMessageId() {
            return messageId;
        }

        public void setMessageId(Integer messageId) {
            this.messageId = messageId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public List<History> getHistory() {
            return history;
        }

        public void setHistory(List<History> history) {
            this.history = history;
        }

        public List<Attachment> getAttachment() {
            return attachment;
        }

        public void setAttachment(List<Attachment> attachment) {
            this.attachment = attachment;
        }

        public Message() {
            super();
        }

    }

            public class History {

                @SerializedName("toname")
                @Expose
                private String toname;
                @SerializedName("fromname")
                @Expose
                private String fromname;
                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("message")
                @Expose
                private String message;
                @SerializedName("subject")
                @Expose
                private String subject;
                @SerializedName("created_at")
                @Expose
                private String createdAt;
                @SerializedName("recipient_id")
                @Expose
                private Integer recipientId;
                @SerializedName("recipient_avatar")
                @Expose
                private String recipientAvatar;
                @SerializedName("sender_id")
                @Expose
                private Integer senderId;
                @SerializedName("sender_avatar")
                @Expose
                private String senderAvatar;

                public String getToname() {
                    return toname;
                }

                public void setToname(String toname) {
                    this.toname = toname;
                }

                public String getFromname() {
                    return fromname;
                }

                public void setFromname(String fromname) {
                    this.fromname = fromname;
                }

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }

                public String getSubject() {
                    return subject;
                }

                public void setSubject(String subject) {
                    this.subject = subject;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public Integer getRecipientId() {
                    return recipientId;
                }

                public void setRecipientId(Integer recipientId) {
                    this.recipientId = recipientId;
                }

                public String getRecipientAvatar() {
                    return recipientAvatar;
                }

                public void setRecipientAvatar(String recipientAvatar) {
                    this.recipientAvatar = recipientAvatar;
                }

                public Integer getSenderId() {
                    return senderId;
                }

                public void setSenderId(Integer senderId) {
                    this.senderId = senderId;
                }

                public String getSenderAvatar() {
                    return senderAvatar;
                }

                public void setSenderAvatar(String senderAvatar) {
                    this.senderAvatar = senderAvatar;
                }
        }
    public class Attachment {

        @SerializedName("fileName")
        @Expose
        private String fileName;
        @SerializedName("fileType")
        @Expose
        private String fileType;
        @SerializedName("attachmentKey")
        @Expose
        private String attachmentKey;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getAttachmentKey() {
            return attachmentKey;
        }

        public void setAttachmentKey(String attachmentKey) {
            this.attachmentKey = attachmentKey;
        }

    }
        }
