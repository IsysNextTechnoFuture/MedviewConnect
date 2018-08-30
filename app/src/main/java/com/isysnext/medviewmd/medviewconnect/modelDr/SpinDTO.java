package com.isysnext.medviewmd.medviewconnect.modelDr;

/**
 * Created by Harsh on 13/04/18.
 */
public class SpinDTO {
    private String key;
    private String value;

    public SpinDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
