package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-08-21 17:00:32
 */
public class UserTaskDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //用户id
    private Long userId;
    /**
     *  任务模式 0 = 家长模式   1= 医生模式
     */
    private Integer type;
    /**
     *  任务天数 7=7天  15=15天  30=30天
     */
    private Integer taskTime;
    /**
     *  用眼评级 5=优 4=良  2=差  1=极差
     *
     */
    private String countGrade;

    /**
     *  上次评级：平均每次阅读时长 5=优 4=良  2=差  1=极差
     */
    private String lastavgRead;
    /**
    /**
     *  目标设定：平均每次阅读时长 5=优 4=良  2=差  1=极差
     */
    private String avgRead;
    /**
     *  最终结果：平均每次阅读时长 5=优 4=良  2=差  1=极差
     */
    private String avgReadResult;
    /**
     *  本次平均每次阅读时长目标积分
     */
    private Integer avgReadScore;
    /**
     *  本次平均每次阅读时长最终积分
     */
    private Integer avgReadScoreResult;

    /**
     *  上次评级：平均户外时长 5=优 4=良  2=差  1=极差
     */
    private String lastavgOut;

    /**
     *  目标设定：平均户外时长 5=优 4=良  2=差  1=极差
     */
    private String avgOut;
    /**
     *  最终结果：平均户外时长 5=优 4=良  2=差  1=极差
     */
    private String avgOutResult;
    /**
     * 本次户外时长  目标积分
     */
    private Integer avgOutScore;
    /**
     * 本次户外时长  最终积分
     */
    private Integer avgOutScoreResult;


    /**
     *  上次评级：平均阅读距离 5=优 4=良  2=差  1=极差
     */
    private String lastavgReadDistance;
    /**
     *  目标设定：平均阅读距离 5=优 4=良  2=差  1=极差
     */
    private String avgReadDistance;
    /**
     *  最终结果：平均阅读距离 5=优 4=良  2=差  1=极差
     */
    private String avgReadDistanceResult;
    /**
     *  平均阅读距离 目标积分
     */
    private Integer avgReadDistanceScore;
    /**
     *  平均阅读距离 最终积分
     */
    private Integer avgReadDistanceScoreResult;


    /**
     *  上次评级：平均单次看手机时长 5=优 4=良  2=差  1=极差
     */
    private String lastavgLookPhone;
    /**
     *  目标设定：平均单次看手机时长 5=优 4=良  2=差  1=极差
     */
    private String avgLookPhone;
    /**
     *  最终结果：平均单次看手机时长 5=优 4=良  2=差  1=极差
     */
    private String  avgLookPhoneResult;
    /**
     *  平均单次看手机  目标积分
     */
    private Integer avgLookPhoneScore;
    /**
     *  平均单次看手机  最终积分
     */
    private Integer avgLookPhoneScoreResult;

    /**
     *  上次评级：平均单次看电脑电视时长 5=优 4=良  2=差  1=极差
     */
    private String lastavgLookTv;

    /**
     *  目标设定：平均单次看电脑电视时长 5=优 4=良  2=差  1=极差
     */
    private String avgLookTv;
    /**
     *  最终结果：平均单次看电脑电视时长 5=优 4=良  2=差  1=极差
     */
    private String avgLookTvResult;
    /**
     *  平均单次看电脑电视时长 目标积分
     */
    private Integer avgLookScore;
    /**
     *  平均单次看电脑电视时长最终积分
     */
    private Integer avgLookScoreResult;

    /**
     *  上次评级：平均阅读旋转角度 5=优 4=良  2=差  1=极差
     */
    private String lastavgSitTilt;
    /**
     *  目标设定：平均阅读旋转角度 5=优 4=良  2=差  1=极差
     */
    private String avgSitTilt;
    /**
     *  最终结果：平均阅读旋转角度 5=优 4=良  2=差  1=极差
     */
    private String avgSitTiltResult;
    /**
     * 平均阅读旋转角度目标积分
     */
    private Integer avgSitTiltScore;
    /**
     * 平均阅读旋转角度最终积分
     */
    private Integer avgSitTiltScoreResult;

    /**
     *  上次评级：有效使用时长 5=优 4=良  2=差  1=极差
     */
    private String lasteffectiveUseTime;

    /**
     *  目标设定：有效使用时长 5=优 4=良  2=差  1=极差
     */
    private String effectiveUseTime;
    /**
     *  最终结果：有效使用时长 5=优 4=良  2=差  1=极差
     */
    private String effectiveUseTimeResult;
    /**
     *  有效使用时长 目标积分
     */
    private Integer effectiveUseTimeScore;

    /**
     *  有效使用时长 最终积分
     */
    private Integer effectiveUseTimeScoreResult;



    /**
     *  上次评级：平均每次阅读光照 5=优 4=良  2=差  1=极差
     */
    private String lastavgLight;

    /**
     *  目标设定：平均每次阅读光照 5=优 4=良  2=差  1=极差
     */
    private String avgLight;
    /**
     *  最终结果：平均每次阅读光照 5=优 4=良  2=差  1=极差
     */
    private String avgLightResult;
    /**
     *  平均每次阅读光照  目标积分
     */
    private Integer avgLightScore;

    /**
     *  平均每次阅读光照  最终积分
     */
    private Integer avgLightScoreResult;
    /**
     *  家长模式下  礼品详情
     */
    private String gift;

    /**
     *  已完成天数
     */
    private Long finishDay;
    /**
     *  未完成天数
     */
    private Long unfinishedDay;
    /**
     *  任务创建时间
     */
    private Date createTime;
    /**
     * 0=任务进行中  1=任务已完成，数据未上传  2=任务已完成  数据已上传
     */

    private Integer flag;
    /**
     * 1 任务已结束  2 任务进行中
     */
    private String finishStatus;
    /**
     * 总积分
     */

    private Integer totalScore;
    /**
     *  有效使用时长
     */
    private Double totaluser;
    /**
     *  任务开始时间
     */
    private Date startTime;

    /**
     *  1=学校，2=个人
     */
    private Integer taskType;

    //有效使用时长
    private Double youxiaotime;

    private String idCards;
    //班级ID
    private String pcorapp;
    //任务的起止时间
    private String sstime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Integer taskTime) {
        this.taskTime = taskTime;
    }

    public String getCountGrade() {
        return countGrade;
    }

    public void setCountGrade(String countGrade) {
        this.countGrade = countGrade;
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

    public Long getFinishDay() {
        return finishDay;
    }

    public void setFinishDay(Long finishDay) {
        this.finishDay = finishDay;
    }

    public Long getUnfinishedDay() {
        return unfinishedDay;
    }

    public void setUnfinishedDay(Long unfinishedDay) {
        this.unfinishedDay = unfinishedDay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAvgReadResult() {
        return avgReadResult;
    }

    public void setAvgReadResult(String avgReadResult) {
        this.avgReadResult = avgReadResult;
    }

    public Integer getAvgReadScore() {
        return avgReadScore;
    }

    public void setAvgReadScore(Integer avgReadScore) {
        this.avgReadScore = avgReadScore;
    }

    public Integer getAvgReadScoreResult() {
        return avgReadScoreResult;
    }

    public void setAvgReadScoreResult(Integer avgReadScoreResult) {
        this.avgReadScoreResult = avgReadScoreResult;
    }

    public String getAvgOutResult() {
        return avgOutResult;
    }

    public void setAvgOutResult(String avgOutResult) {
        this.avgOutResult = avgOutResult;
    }

    public Integer getAvgOutScore() {
        return avgOutScore;
    }

    public void setAvgOutScore(Integer avgOutScore) {
        this.avgOutScore = avgOutScore;
    }

    public Integer getAvgOutScoreResult() {
        return avgOutScoreResult;
    }

    public void setAvgOutScoreResult(Integer avgOutScoreResult) {
        this.avgOutScoreResult = avgOutScoreResult;
    }

    public String getAvgReadDistanceResult() {
        return avgReadDistanceResult;
    }

    public void setAvgReadDistanceResult(String avgReadDistanceResult) {
        this.avgReadDistanceResult = avgReadDistanceResult;
    }

    public Integer getAvgReadDistanceScore() {
        return avgReadDistanceScore;
    }

    public void setAvgReadDistanceScore(Integer avgReadDistanceScore) {
        this.avgReadDistanceScore = avgReadDistanceScore;
    }

    public Integer getAvgReadDistanceScoreResult() {
        return avgReadDistanceScoreResult;
    }

    public void setAvgReadDistanceScoreResult(Integer avgReadDistanceScoreResult) {
        this.avgReadDistanceScoreResult = avgReadDistanceScoreResult;
    }

    public String getAvgLookPhoneResult() {
        return avgLookPhoneResult;
    }

    public void setAvgLookPhoneResult(String avgLookPhoneResult) {
        this.avgLookPhoneResult = avgLookPhoneResult;
    }

    public Integer getAvgLookPhoneScore() {
        return avgLookPhoneScore;
    }

    public void setAvgLookPhoneScore(Integer avgLookPhoneScore) {
        this.avgLookPhoneScore = avgLookPhoneScore;
    }

    public Integer getAvgLookPhoneScoreResult() {
        return avgLookPhoneScoreResult;
    }

    public void setAvgLookPhoneScoreResult(Integer avgLookPhoneScoreResult) {
        this.avgLookPhoneScoreResult = avgLookPhoneScoreResult;
    }

    public String getAvgLookTvResult() {
        return avgLookTvResult;
    }

    public void setAvgLookTvResult(String avgLookTvResult) {
        this.avgLookTvResult = avgLookTvResult;
    }

    public Integer getAvgLookScore() {
        return avgLookScore;
    }

    public void setAvgLookScore(Integer avgLookScore) {
        this.avgLookScore = avgLookScore;
    }

    public Integer getAvgLookScoreResult() {
        return avgLookScoreResult;
    }

    public void setAvgLookScoreResult(Integer avgLookScoreResult) {
        this.avgLookScoreResult = avgLookScoreResult;
    }

    public String getAvgSitTiltResult() {
        return avgSitTiltResult;
    }

    public void setAvgSitTiltResult(String avgSitTiltResult) {
        this.avgSitTiltResult = avgSitTiltResult;
    }

    public Integer getAvgSitTiltScore() {
        return avgSitTiltScore;
    }

    public void setAvgSitTiltScore(Integer avgSitTiltScore) {
        this.avgSitTiltScore = avgSitTiltScore;
    }

    public Integer getAvgSitTiltScoreResult() {
        return avgSitTiltScoreResult;
    }

    public void setAvgSitTiltScoreResult(Integer avgSitTiltScoreResult) {
        this.avgSitTiltScoreResult = avgSitTiltScoreResult;
    }

    public String getEffectiveUseTimeResult() {
        return effectiveUseTimeResult;
    }

    public void setEffectiveUseTimeResult(String effectiveUseTimeResult) {
        this.effectiveUseTimeResult = effectiveUseTimeResult;
    }

    public Integer getEffectiveUseTimeScore() {
        return effectiveUseTimeScore;
    }

    public void setEffectiveUseTimeScore(Integer effectiveUseTimeScore) {
        this.effectiveUseTimeScore = effectiveUseTimeScore;
    }

    public Integer getEffectiveUseTimeScoreResult() {
        return effectiveUseTimeScoreResult;
    }

    public void setEffectiveUseTimeScoreResult(Integer effectiveUseTimeScoreResult) {
        this.effectiveUseTimeScoreResult = effectiveUseTimeScoreResult;
    }

    public String getAvgLightResult() {
        return avgLightResult;
    }

    public void setAvgLightResult(String avgLightResult) {
        this.avgLightResult = avgLightResult;
    }

    public Integer getAvgLightScore() {
        return avgLightScore;
    }

    public void setAvgLightScore(Integer avgLightScore) {
        this.avgLightScore = avgLightScore;
    }

    public Integer getAvgLightScoreResult() {
        return avgLightScoreResult;
    }

    public void setAvgLightScoreResult(Integer avgLightScoreResult) {
        this.avgLightScoreResult = avgLightScoreResult;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Double getTotaluser() {
        return totaluser;
    }

    public void setTotaluser(Double totaluser) {
        this.totaluser = totaluser;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getLastavgRead() {
        return lastavgRead;
    }

    public void setLastavgRead(String lastavgRead) {
        this.lastavgRead = lastavgRead;
    }

    public String getLastavgOut() {
        return lastavgOut;
    }

    public void setLastavgOut(String lastavgOut) {
        this.lastavgOut = lastavgOut;
    }

    public String getLastavgReadDistance() {
        return lastavgReadDistance;
    }

    public void setLastavgReadDistance(String lastavgReadDistance) {
        this.lastavgReadDistance = lastavgReadDistance;
    }

    public String getLastavgLookPhone() {
        return lastavgLookPhone;
    }

    public void setLastavgLookPhone(String lastavgLookPhone) {
        this.lastavgLookPhone = lastavgLookPhone;
    }

    public String getLastavgLookTv() {
        return lastavgLookTv;
    }

    public void setLastavgLookTv(String lastavgLookTv) {
        this.lastavgLookTv = lastavgLookTv;
    }

    public String getLastavgSitTilt() {
        return lastavgSitTilt;
    }

    public void setLastavgSitTilt(String lastavgSitTilt) {
        this.lastavgSitTilt = lastavgSitTilt;
    }

    public String getLasteffectiveUseTime() {
        return lasteffectiveUseTime;
    }

    public void setLasteffectiveUseTime(String lasteffectiveUseTime) {
        this.lasteffectiveUseTime = lasteffectiveUseTime;
    }

    public String getLastavgLight() {
        return lastavgLight;
    }

    public void setLastavgLight(String lastavgLight) {
        this.lastavgLight = lastavgLight;
    }


    public String getIdCards() {
        return idCards;
    }

    public void setIdCards(String idCards) {
        this.idCards = idCards;
    }

    public String getPcorapp() {
        return pcorapp;
    }

    public Double getYouxiaotime() {
        return youxiaotime;
    }

    public void setYouxiaotime(Double youxiaotime) {
        this.youxiaotime = youxiaotime;
    }

    public void setPcorapp(String pcorapp) {
        this.pcorapp = pcorapp;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getSstime() {
        return sstime;
    }

    public void setSstime(String sstime) {
        this.sstime = sstime;
    }

    @Override
    public String toString() {
        return "UserTaskDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", taskTime=" + taskTime +
                ", countGrade='" + countGrade + '\'' +
                ", avgRead='" + avgRead + '\'' +
                ", avgReadResult='" + avgReadResult + '\'' +
                ", avgReadScore=" + avgReadScore +
                ", avgReadScoreResult=" + avgReadScoreResult +
                ", avgOut='" + avgOut + '\'' +
                ", avgOutResult='" + avgOutResult + '\'' +
                ", avgOutScore=" + avgOutScore +
                ", avgOutScoreResult=" + avgOutScoreResult +
                ", avgReadDistance='" + avgReadDistance + '\'' +
                ", avgReadDistanceResult='" + avgReadDistanceResult + '\'' +
                ", avgReadDistanceScore=" + avgReadDistanceScore +
                ", avgReadDistanceScoreResult=" + avgReadDistanceScoreResult +
                ", avgLookPhone='" + avgLookPhone + '\'' +
                ", avgLookPhoneResult='" + avgLookPhoneResult + '\'' +
                ", avgLookPhoneScore=" + avgLookPhoneScore +
                ", avgLookPhoneScoreResult=" + avgLookPhoneScoreResult +
                ", avgLookTv='" + avgLookTv + '\'' +
                ", avgLookTvResult='" + avgLookTvResult + '\'' +
                ", avgLookScore=" + avgLookScore +
                ", avgLookScoreResult=" + avgLookScoreResult +
                ", avgSitTilt='" + avgSitTilt + '\'' +
                ", avgSitTiltResult='" + avgSitTiltResult + '\'' +
                ", avgSitTiltScore=" + avgSitTiltScore +
                ", avgSitTiltScoreResult=" + avgSitTiltScoreResult +
                ", effectiveUseTime='" + effectiveUseTime + '\'' +
                ", effectiveUseTimeResult='" + effectiveUseTimeResult + '\'' +
                ", effectiveUseTimeScore=" + effectiveUseTimeScore +
                ", effectiveUseTimeScoreResult=" + effectiveUseTimeScoreResult +
                ", avgLight='" + avgLight + '\'' +
                ", avgLightResult='" + avgLightResult + '\'' +
                ", avgLightScore=" + avgLightScore +
                ", avgLightScoreResult=" + avgLightScoreResult +
                ", gift='" + gift + '\'' +
                ", finishDay=" + finishDay +
                ", unfinishedDay=" + unfinishedDay +
                ", createTime=" + createTime +
                ", flag=" + flag +
                ", totalScore=" + totalScore +
                ", totaluser=" + totaluser +
                ", startTime=" + startTime +
                '}';
    }


}
