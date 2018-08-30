package com.isysnext.medviewmd.medviewconnect.modelDr;

/**
 * Created by Harsh on 06/05/18.
 */
public class PdfDocActivityDTO
{
    private String name="",path="";

    public PdfDocActivityDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
