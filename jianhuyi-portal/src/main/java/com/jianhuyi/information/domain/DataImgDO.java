package com.jianhuyi.information.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-03-25 09:40:42
 */
public class DataImgDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    private Long userId;
    //dataid
    private Long dataid;
    //时间
    private String imgTime;
    //图片路径
    private String filename;
    //图片文件
    private MultipartFile img;
    //类型
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImgTime() {
        return imgTime;
    }

    public void setImgTime(String imgTime) {
        this.imgTime = imgTime;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    /**
     * 设置：id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：dataid
     */
    public void setDataid(Long dataid) {
        this.dataid = dataid;
    }

    /**
     * 获取：dataid
     */
    public Long getDataid() {
        return dataid;
    }

    /**
     * 设置：时间
     */
    public void setTime(String time) {
        this.imgTime = time;
    }

    /**
     * 获取：时间
     */
    public String getTime() {
        return imgTime;
    }

    /**
     * 设置：图片路径
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 获取：图片路径
     */
    public String getFilename() {
        return filename;
    }
}
