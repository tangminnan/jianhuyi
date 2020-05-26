package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
public class UseJianhuyiLogDO implements Serializable {
    private static final long serialVersionUID = 1L;

    // id
    private Integer id;
    // 用户Id
    private Integer userId;
    //数据测试时间
    private String saveTime;
    //设备号
    private String equipmentId;
    // 添加时间
    private Date addTime;
    // 阅读时长(分钟）
    private Double readDuration;
    // 户外时长(小时）
    private Double outdoorsDuration;
    // 阅读距离(厘米)
    private Double readDistance;
    // 阅读光照(lux)
    private Double readLight;
    // 看手机时长(分钟)
    private Double lookPhoneDuration;
    //看手机次数
    private Integer lookPhoneCount;
    //看电脑次数
    private Integer lookTvComputerCount;
    // 看电脑电视时长(分钟）
    private Double lookTvComputerDuration;
    // 坐姿倾斜度
    private Double sitTilt;
    // 使用监护仪时长(小时）
    private Double useJianhuyiDuration;
    // 运动时长(小时)
    private Double sportDuration;
    // 删除标志(1:删除 0：未删除)
    private Integer delFlag;
    //阅读状态
    private Integer status;

    private Integer num;

    private Double allreadDuration;


    public Double getAllreadDuration() {
        return allreadDuration;
    }

    public void setAllreadDuration(Double allreadDuration) {
        this.allreadDuration = allreadDuration;
    }

    public Integer getLookPhoneCount() {
        return lookPhoneCount;
    }

    public void setLookPhoneCount(Integer lookPhoneCount) {
        this.lookPhoneCount = lookPhoneCount;
    }

    public Integer getLookTvComputerCount() {
        return lookTvComputerCount;
    }

    public void setLookTvComputerCount(Integer lookTvComputerCount) {
        this.lookTvComputerCount = lookTvComputerCount;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    /**
     * 设置：id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：用户Id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户Id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置：添加时间
     */

    /**
     * 设置：阅读时长(分钟）
     */
    public void setReadDuration(Double readDuration) {
        this.readDuration = readDuration;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取：阅读时长(分钟）
     */
    public Double getReadDuration() {
        return readDuration;
    }

    /**
     * 设置：户外时长(小时）
     */
    public void setOutdoorsDuration(Double outdoorsDuration) {
        this.outdoorsDuration = outdoorsDuration;
    }

    /**
     * 获取：户外时长(小时）
     */
    public Double getOutdoorsDuration() {
        return outdoorsDuration;
    }

    /**
     * 设置：阅读距离(厘米)
     */
    public void setReadDistance(Double readDistance) {
        this.readDistance = readDistance;
    }

    /**
     * 获取：阅读距离(厘米)
     */
    public Double getReadDistance() {
        return readDistance;
    }

    /**
     * 设置：阅读光照(lux)
     */
    public void setReadLight(Double readLight) {
        this.readLight = readLight;
    }

    /**
     * 获取：阅读光照(lux)
     */
    public Double getReadLight() {
        return readLight;
    }

    /**
     * 设置：看手机时长(分钟)
     */
    public void setLookPhoneDuration(Double lookPhoneDuration) {
        this.lookPhoneDuration = lookPhoneDuration;
    }

    /**
     * 获取：看手机时长(分钟)
     */
    public Double getLookPhoneDuration() {
        return lookPhoneDuration;
    }

    /**
     * 设置：看电脑电视时长(分钟）
     */
    public void setLookTvComputerDuration(Double lookTvComputerDuration) {
        this.lookTvComputerDuration = lookTvComputerDuration;
    }

    /**
     * 获取：看电脑电视时长(分钟）
     */
    public Double getLookTvComputerDuration() {
        return lookTvComputerDuration;
    }

    /**
     * 设置：坐姿倾斜度
     */
    public void setSitTilt(Double sitTilt) {
        this.sitTilt = sitTilt;
    }

    /**
     * 获取：坐姿倾斜度
     */
    public Double getSitTilt() {
        return sitTilt;
    }

    /**
     * 设置：使用监护仪时长(小时）
     */
    public void setUseJianhuyiDuration(Double useJianhuyiDuration) {
        this.useJianhuyiDuration = useJianhuyiDuration;
    }

    /**
     * 获取：使用监护仪时长(小时）
     */
    public Double getUseJianhuyiDuration() {
        return useJianhuyiDuration;
    }

    /**
     * 设置：运动时长(小时)
     */
    public void setSportDuration(Double sportDuration) {
        this.sportDuration = sportDuration;
    }

    /**
     * 获取：运动时长(小时)
     */
    public Double getSportDuration() {
        return sportDuration;
    }

    /**
     * 设置：删除标志(1:删除 0：未删除)
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取：删除标志(1:删除 0：未删除)
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return "UseJianhuyiLogDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", saveTime='" + saveTime + '\'' +
                ", equipmentId='" + equipmentId + '\'' +
                ", addTime=" + addTime +
                ", readDuration=" + readDuration +
                ", outdoorsDuration=" + outdoorsDuration +
                ", readDistance=" + readDistance +
                ", readLight=" + readLight +
                ", lookPhoneDuration=" + lookPhoneDuration +
                ", lookPhoneCount=" + lookPhoneCount +
                ", lookTvComputerCount=" + lookTvComputerCount +
                ", lookTvComputerDuration=" + lookTvComputerDuration +
                ", sitTilt=" + sitTilt +
                ", useJianhuyiDuration=" + useJianhuyiDuration +
                ", sportDuration=" + sportDuration +
                ", delFlag=" + delFlag +
                ", status=" + status +
                ", num=" + num +
                ", allreadDuration=" + allreadDuration +
                '}';
    }
}
