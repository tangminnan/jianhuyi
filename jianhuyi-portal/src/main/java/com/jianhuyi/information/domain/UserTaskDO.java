package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;


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
    //礼物id
    private Long giftId;
    //任务时长
    private Integer taskTime;
    //健康用眼评级
    private String eyeRate;
    //平均单次阅读时长
    private String avgRead;
    //平均户外时长
    private String avgOut;
    //平均阅读距离
    private String avgReadDistance;
    //平均单次看手机时长
    private String avgLookPhone;
    //看电子屏
    private String avgLookTv;
    //平均坐姿倾斜度
    private String avgSitTilt;
    //有效使用时长
    private String effectiveUseTime;
    //完成状态（1=已完成，2=未完成，3=失败）
    private String finishStatus;
    //已完成天数
    private Integer finishDay;
    //未完成天数
    private Integer unfinishedDay;
    //平均阅读光照
    private String avgLight;
    //创建时间
    private Date createTime;

    private Integer type;

    //封面主图
    private String coverImg;
    //礼物名称
    private String giftName;
    //计算后的总评级
    private String countGrade;

    private String[] idCards;

    private String idCard;

    private int taskType;

    private GiftDO giftDO;

    private GiftPcDO giftPcDO;

    public GiftDO getGiftDO() {
        return giftDO;
    }

    public void setGiftDO(GiftDO giftDO) {
        this.giftDO = giftDO;
    }

    public GiftPcDO getGiftPcDO() {
        return giftPcDO;
    }

    public void setGiftPcDO(GiftPcDO giftPcDO) {
        this.giftPcDO = giftPcDO;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String[] getIdCards() {
        return idCards;
    }

    public void setIdCards(String[] idCards) {
        this.idCards = idCards;
    }

    public String getCountGrade() {
        return countGrade;
    }

    public void setCountGrade(String countGrade) {
        this.countGrade = countGrade;
    }

    public String getCoverImg() {
        return coverImg;
    }


    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
     * 设置：用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：礼物id
     */
    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    /**
     * 获取：礼物id
     */
    public Long getGiftId() {
        return giftId;
    }

    /**
     * 设置：任务时长
     */
    public void setTaskTime(Integer taskTime) {
        this.taskTime = taskTime;
    }

    /**
     * 获取：任务时长
     */
    public Integer getTaskTime() {
        return taskTime;
    }

    /**
     * 设置：健康用眼评级
     */
    public void setEyeRate(String eyeRate) {
        this.eyeRate = eyeRate;
    }

    /**
     * 获取：健康用眼评级
     */
    public String getEyeRate() {
        return eyeRate;
    }

    /**
     * 设置：平均单次阅读时长
     */
    public void setAvgRead(String avgRead) {
        this.avgRead = avgRead;
    }

    /**
     * 获取：平均单次阅读时长
     */
    public String getAvgRead() {
        return avgRead;
    }

    /**
     * 设置：平均户外时长
     */
    public void setAvgOut(String avgOut) {
        this.avgOut = avgOut;
    }

    /**
     * 获取：平均户外时长
     */
    public String getAvgOut() {
        return avgOut;
    }

    /**
     * 设置：平均阅读距离
     */
    public void setAvgReadDistance(String avgReadDistance) {
        this.avgReadDistance = avgReadDistance;
    }

    /**
     * 获取：平均阅读距离
     */
    public String getAvgReadDistance() {
        return avgReadDistance;
    }

    /**
     * 设置：平均单次看手机时长
     */
    public void setAvgLookPhone(String avgLookPhone) {
        this.avgLookPhone = avgLookPhone;
    }

    /**
     * 获取：平均单次看手机时长
     */
    public String getAvgLookPhone() {
        return avgLookPhone;
    }

    /**
     * 设置：看电子屏
     */
    public void setAvgLookTv(String avgLookTv) {
        this.avgLookTv = avgLookTv;
    }

    /**
     * 获取：看电子屏
     */
    public String getAvgLookTv() {
        return avgLookTv;
    }

    /**
     * 设置：平均坐姿倾斜度
     */
    public void setAvgSitTilt(String avgSitTilt) {
        this.avgSitTilt = avgSitTilt;
    }

    /**
     * 获取：平均坐姿倾斜度
     */
    public String getAvgSitTilt() {
        return avgSitTilt;
    }

    /**
     * 设置：有效使用时长
     */
    public void setEffectiveUseTime(String effectiveUseTime) {
        this.effectiveUseTime = effectiveUseTime;
    }

    /**
     * 获取：有效使用时长
     */
    public String getEffectiveUseTime() {
        return effectiveUseTime;
    }

    /**
     * 设置：完成状态（1=已完成，2=未完成，3=失败）
     */
    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    /**
     * 获取：完成状态（1=已完成，2=未完成，3=失败）
     */
    public String getFinishStatus() {
        return finishStatus;
    }

    /**
     * 设置：已完成天数
     */
    public void setFinishDay(Integer finishDay) {
        this.finishDay = finishDay;
    }

    /**
     * 获取：已完成天数
     */
    public Integer getFinishDay() {
        return finishDay;
    }

    /**
     * 设置：未完成天数
     */
    public void setUnfinishedDay(Integer unfinishedDay) {
        this.unfinishedDay = unfinishedDay;
    }

    /**
     * 获取：未完成天数
     */
    public Integer getUnfinishedDay() {
        return unfinishedDay;
    }

    /**
     * 设置：平均阅读光照
     */
    public void setAvgLight(String avgLight) {
        this.avgLight = avgLight;
    }

    /**
     * 获取：平均阅读光照
     */
    public String getAvgLight() {
        return avgLight;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }
}
