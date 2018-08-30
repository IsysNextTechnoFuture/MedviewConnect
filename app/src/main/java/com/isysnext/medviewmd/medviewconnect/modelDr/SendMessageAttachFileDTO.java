package com.isysnext.medviewmd.medviewconnect.modelDr;

/**
 * Created by Harsh on 07/05/18.
 */
public class SendMessageAttachFileDTO {

    private String strAttachmentFilePath;
    private String strAttachmentFileType;
    private String strEncodedFilePath;

    public SendMessageAttachFileDTO(String strAttachmentFilePath, String strAttachmentFileType, String strEncodedFilePath) {
        this.strAttachmentFilePath = strAttachmentFilePath;
        this.strAttachmentFileType = strAttachmentFileType;
        this.strEncodedFilePath = strEncodedFilePath;
    }

    public String getStrAttachmentFilePath() {
        return strAttachmentFilePath;
    }

    public void setStrAttachmentFilePath(String strAttachmentFilePath) {
        this.strAttachmentFilePath = strAttachmentFilePath;
    }

    public String getStrAttachmentFileType() {
        return strAttachmentFileType;
    }

    public void setStrAttachmentFileType(String strAttachmentFileType) {
        this.strAttachmentFileType = strAttachmentFileType;
    }

    public String getStrEncodedFilePath() {
        return strEncodedFilePath;
    }

    public void setStrEncodedFilePath(String strEncodedFilePath) {
        this.strEncodedFilePath = strEncodedFilePath;
    }
}
