package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;


/**
 *  任务进度表
 */
public class UserTaskLinshiDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    /**
     *  用户id
     */
    private Long userId;
    /**
     *  任务id
     */
    private Long taskId;
    /**
     *  当日等级 5=优 4=良  2=差  1=极差
     */
    private String eyeRate;
    /**
     *  平均每次阅读时长 5=优 4=良  2=差  1=极差
     */
    private String avgRead;
    /**
     *  平均户外时长 5=优 4=良  2=差  1=极差
     */
    private String avgOut;
    /**
     *  平均阅读距离 5=优 4=良  2=差  1=极差
     */
    private String avgReadDistance;
    /**
     *  平均单次看手机时长 5=优 4=良  2=差  1=极差
     */
    private String avgLookPhone;
    /**
     *  平均单次看电脑电视时长 5=优 4=良  2=差  1=极差
     */
    private String avgLookTv;
    /**
     *  平均阅读旋转角度 5=优 4=良  2=差  1=极差
     */
    private String avgSitTilt;
    /**
     *  有效使用时长 5=优 4=良  2=差  1=极差
     */
    private String effectiveUseTime;
    /**
     *  平均每次阅读光照 5=优 4=良  2=差  1=极差
     */
    private String avgLight;
    /**
     *  数据生成时间
     */
    private Date createTime;
    /**
     *  监护仪有效使用时长
     */
    private String hourtime;
    /**
     *  当日所获积分
     */
    private Integer score;
    /**
     *  是否完成 0=完成   1=未完成
     */
    private Integer iffinish;
    /**
     * 日期
     */
    private String day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getEyeRate() {
        return eyeRate;
    }

    public void setEyeRate(String eyeRate) {
        this.eyeRate = eyeRate;
    }

    public String getAvgRead() {
        return avgRead;
    }

    public void setAvgRead(String avgRead) {
        this.avgRead = avgRead;
    }

    public String getAvgOut() {
        return avgOut;
    }

    public void setAvgOut(String avgOut) {
        this.avgOut = avgOut;
    }

    public String getAvgReadDistance() {
        return avgReadDistance;
    }

    public void setAvgReadDistance(String avgReadDistance) {
        this.avgReadDistance = avgReadDistance;
    }

    public String getAvgLookPhone() {
        return avgLookPhone;
    }

    public void setAvgLookPhone(String avgLookPhone) {
        this.avgLookPhone = avgLookPhone;
    }

    public String getAvgLookTv() {
        return avgLookTv;
    }

    public void setAvgLookTv(String avgLookTv) {
        this.avgLookTv = avgLookTv;
    }

    public String getAvgSitTilt() {
        return avgSitTilt;
    }

    public void setAvgSitTilt(String avgSitTilt) {
        this.avgSitTilt = avgSitTilt;
    }

    public String getEffectiveUseTime() {
        return effectiveUseTime;
    }

    public void setEffectiveUseTime(String effectiveUseTime) {
        this.effectiveUseTime = effectiveUseTime;
    }

    public String getAvgLight() {
        return avgLight;
    }

    public void setAvgLight(String avgLight) {
        this.avgLight = avgLight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHourtime() {
        return hourtime;
    }

    public void setHourtime(String hourtime) {
        this.hourtime = hourtime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIffinish() {
        return iffinish;
    }

    public void setIffinish(Integer iffinish) {
        this.iffinish = iffinish;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "UserTaskLinshiDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", eyeRate='" + eyeRate + '\'' +
                ", avgRead='" + avgRead + '\'' +
                ", avgOut='" + avgOut + '\'' +
                ", avgReadDistance='" + avgReadDistance + '\'' +
                ", avgLookPhone='" + avgLookPhone + '\'' +
                ", avgLookTv='" + avgLookTv + '\'' +
                ", avgSitTilt='" + avgSitTilt + '\'' +
                ", effectiveUseTime='" + effectiveUseTime + '\'' +
                ", avgLight='" + avgLight + '\'' +
                ", createTime=" + createTime +
                ", hourtime='" + hourtime + '\'' +
                ", score=" + score +
                ", iffinish=" + iffinish +
                ", day='" + day + '\'' +
                '}';
    }
}
