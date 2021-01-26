package com.jianhuyi.common.utils.domain;

/**
 * 图片信息
 */
public class PictureBean {

    private String time;//时间
    private String type;//图片类型
    private String filename;//图片路径

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
