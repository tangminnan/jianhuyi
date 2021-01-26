package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-25 14:23:41
 */
public class DataDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //开始时间
    private String startTime;
    private Date updateTime;

    //1 阅读，2 非阅读，3 遮挡状态，4 无效
    private Integer status;
    //设备号
    private String equipmentId;
    //角度信息（以json数据保存）
    private String baseData;
    //震动提醒（json存储）
    private String remaind;

    private Integer picNum;

    private String picStatus;
    private String imgs;

    private List<DataImgDO> pictures;

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public List<DataImgDO> getPictures() {
        return pictures;
    }

    public void setPictures(List<DataImgDO> pictures) {
        this.pictures = pictures;
    }

    public Integer getPicNum() {
        return picNum;
    }

    public void setPicNum(Integer picNum) {
        this.picNum = picNum;
    }

    public String getPicStatus() {
        return picStatus;
    }

    public void setPicStatus(String picStatus) {
        this.picStatus = picStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
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
     * 设置：开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取：开始时间
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置：1 阅读，2 非阅读，3 遮挡状态，4 无效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：1 阅读，2 非阅读，3 遮挡状态，4 无效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：角度信息（以json数据保存）
     */
    public void setBaseData(String baseData) {
        this.baseData = baseData;
    }

    /**
     * 获取：角度信息（以json数据保存）
     */
    public String getBaseData() {
        return baseData;
    }

    /**
     * 设置：震动提醒（json存储）
     */
    public void setRemaind(String remaind) {
        this.remaind = remaind;
    }

    /**
     * 获取：震动提醒（json存储）
     */
    public String getRemaind() {
        return remaind;
    }
}
