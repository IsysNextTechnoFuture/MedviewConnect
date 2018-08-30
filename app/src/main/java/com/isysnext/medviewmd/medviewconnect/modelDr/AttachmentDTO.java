package com.isysnext.medviewmd.medviewconnect.modelDr;

public class AttachmentDTO {

    String fileName,fileType,attachmentKey;

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

    public AttachmentDTO(String fileName, String fileType, String attachmentKey) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.attachmentKey = attachmentKey;

    }
}
