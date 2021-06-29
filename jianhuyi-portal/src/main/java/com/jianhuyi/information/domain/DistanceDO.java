package com.jianhuyi.information.domain;

import java.util.Date;

/**
 *  16分位阅读距离信息
 */
public class DistanceDO {
    private Long id;
    private Integer userId;//用户ID
    private Integer uploadId;//上传人ID
    private String equipmentId;//设备号
    private String distanceData;//16分位阅读距离信息
    private String startTime;//开始测量时间
    private Date addTime;//添加时间
    private Integer type;//遮挡状态 0 遮挡   1 半遮挡   2无遮挡
    private Double distance;//阅读距离

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUploadId() {
        return uploadId;
    }

    public void setUploadId(Integer uploadId) {
        this.uploadId = uploadId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDistanceData() {
        return distanceData;
    }

    public void setDistanceData(String distanceData) {
        this.distanceData = distanceData;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
