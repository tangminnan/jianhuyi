package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-09-23 09:20:06
 */
public class StatisticsSuccessRateDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Integer id;
    //设备号
    private String equipmentId;
    //类型1=清除，2=清除完毕
    private Integer type;
    //时间
    private String time;
    //保存时间
    private Date saveTime;

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
     * 设置：设备号
     */
    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    /**
     * 获取：设备号
     */
    public String getEquipmentId() {
        return equipmentId;
    }

    /**
     * 设置：类型1=清除，2=清除完毕
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：类型1=清除，2=清除完毕
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置：时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取：时间
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置：保存时间
     */
    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    /**
     * 获取：保存时间
     */
    public Date getSaveTime() {
        return saveTime;
    }
}
