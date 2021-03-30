package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 14:22:00
 */
public class UserTaskDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//用户id
	private Long userId;
	//任务天数 7=7天  15=15天  30=30天
	private Integer taskTime;
	//用户名
	private String name;
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
	//类型 0=家长模式，1=医生模式 2=老师模式(批量）
	private Integer type;
	//1=学校，2=个人
	private Integer taskType;
	//最终结果：平均每次阅读时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String avgReadResult;
	//本次平均每次阅读时长目标积分
	private Integer avgReadScore;
	//本次平均每次阅读时长最终积分
	private Integer avgReadScoreResult;
	//最终结果：平均户外时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String avgOutResult;
	//本次户外时长  目标积分
	private Integer avgOutScore;
	//本次户外时长  最终积分
	private String avgOutScoreResult;
	//最终结果：平均阅读距离 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private Double avgReadDistanceResult;
	//平均阅读距离 目标积分
	private Double avgReadDistanceScore;
	//平均阅读距离 最终积分
	private Double avgReadDistanceScoreResult;
	//最终结果：平均单次看手机时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String avgLookPhoneResult;
	//平均单次看手机  目标积分
	private Integer avgLookPhoneScore;
	//平均单次看手机  最终积分
	private Integer avgLookPhoneScoreResult;
	//最终结果：平均单次看电脑电视时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String avgLookTvResult;
	//平均单次看电脑电视时长 目标积分
	private Integer avgLookScore;
	//平均单次看电脑电视时长最终积分
	private Integer avgLookScoreResult;
	//最终结果：平均阅读旋转角度 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String avgSitTiltResult;
	//平均阅读旋转角度目标积分
	private Integer avgSitTiltScore;
	//平均阅读旋转角度最终积分
	private Integer avgSitTiltScoreResult;
	//最终结果：有效使用时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String effectiveUseTimeResult;
	//有效使用时长 目标积分
	private Integer effectiveUseTimeScore;
	//有效使用时长 最终积分
	private Integer effectiveUseTimeScoreResult;
	//最终结果：平均每次阅读光照 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String avgLightResult;
	//平均每次阅读光照  目标积分
	private Integer avgLightScore;
	//平均每次阅读光照  最终积分
	private Integer avgLightScoreResult;
	//用眼评级 YOU=优 LIANG=良  CHA=差  JICHA=极差
	private String countGrade;
	//家长模式下礼品详情
	private String gift;
	//本次任务总积分
	private Integer totalScore;
	//0=任务进行中  1=任务已完成，数据未上传  2=任务已完成  数据已上传
	private Integer flag;
	//任务开始时间
	private Date startTime;
	//user表中的积分
	private Integer addreadyScore;
	//上次评级
	private String lastavgread;
	//上次评级
	private String lastavgout;
	//上次评级
	private String lastavgreaddistance;
	//上次评级
	private String lastavglooktv;
	//上次评级
	private String lastavgsittilt;
	//上次评级
	private String lasteffectiveusetime;
	//上次评级
	private String lastavglight;
	//上次评级
	private String lastavglookphone;
	//APP=app端任务  PC=pc端任务
	private String pcorapp;

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
	 * 设置：任务天数 7=7天  15=15天  30=30天
	 */
	public void setTaskTime(Integer taskTime) {
		this.taskTime = taskTime;
	}
	/**
	 * 获取：任务天数 7=7天  15=15天  30=30天
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
	/**
	 * 设置：类型 0=家长模式，1=医生模式 2=老师模式(批量）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型 0=家长模式，1=医生模式 2=老师模式(批量）
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：1=学校，2=个人
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	/**
	 * 获取：1=学校，2=个人
	 */
	public Integer getTaskType() {
		return taskType;
	}
	/**
	 * 设置：最终结果：平均每次阅读时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgReadResult(String avgReadResult) {
		this.avgReadResult = avgReadResult;
	}
	/**
	 * 获取：最终结果：平均每次阅读时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getAvgReadResult() {
		return avgReadResult;
	}
	/**
	 * 设置：本次平均每次阅读时长目标积分
	 */
	public void setAvgReadScore(Integer avgReadScore) {
		this.avgReadScore = avgReadScore;
	}
	/**
	 * 获取：本次平均每次阅读时长目标积分
	 */
	public Integer getAvgReadScore() {
		return avgReadScore;
	}
	/**
	 * 设置：本次平均每次阅读时长最终积分
	 */
	public void setAvgReadScoreResult(Integer avgReadScoreResult) {
		this.avgReadScoreResult = avgReadScoreResult;
	}
	/**
	 * 获取：本次平均每次阅读时长最终积分
	 */
	public Integer getAvgReadScoreResult() {
		return avgReadScoreResult;
	}
	/**
	 * 设置：最终结果：平均户外时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgOutResult(String avgOutResult) {
		this.avgOutResult = avgOutResult;
	}
	/**
	 * 获取：最终结果：平均户外时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getAvgOutResult() {
		return avgOutResult;
	}
	/**
	 * 设置：本次户外时长  目标积分
	 */
	public void setAvgOutScore(Integer avgOutScore) {
		this.avgOutScore = avgOutScore;
	}
	/**
	 * 获取：本次户外时长  目标积分
	 */
	public Integer getAvgOutScore() {
		return avgOutScore;
	}
	/**
	 * 设置：本次户外时长  最终积分
	 */
	public void setAvgOutScoreResult(String avgOutScoreResult) {
		this.avgOutScoreResult = avgOutScoreResult;
	}
	/**
	 * 获取：本次户外时长  最终积分
	 */
	public String getAvgOutScoreResult() {
		return avgOutScoreResult;
	}
	/**
	 * 设置：最终结果：平均阅读距离 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgReadDistanceResult(Double avgReadDistanceResult) {
		this.avgReadDistanceResult = avgReadDistanceResult;
	}
	/**
	 * 获取：最终结果：平均阅读距离 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public Double getAvgReadDistanceResult() {
		return avgReadDistanceResult;
	}
	/**
	 * 设置：平均阅读距离 目标积分
	 */
	public void setAvgReadDistanceScore(Double avgReadDistanceScore) {
		this.avgReadDistanceScore = avgReadDistanceScore;
	}
	/**
	 * 获取：平均阅读距离 目标积分
	 */
	public Double getAvgReadDistanceScore() {
		return avgReadDistanceScore;
	}
	/**
	 * 设置：平均阅读距离 最终积分
	 */
	public void setAvgReadDistanceScoreResult(Double avgReadDistanceScoreResult) {
		this.avgReadDistanceScoreResult = avgReadDistanceScoreResult;
	}
	/**
	 * 获取：平均阅读距离 最终积分
	 */
	public Double getAvgReadDistanceScoreResult() {
		return avgReadDistanceScoreResult;
	}
	/**
	 * 设置：最终结果：平均单次看手机时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgLookPhoneResult(String avgLookPhoneResult) {
		this.avgLookPhoneResult = avgLookPhoneResult;
	}
	/**
	 * 获取：最终结果：平均单次看手机时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getAvgLookPhoneResult() {
		return avgLookPhoneResult;
	}
	/**
	 * 设置：平均单次看手机  目标积分
	 */
	public void setAvgLookPhoneScore(Integer avgLookPhoneScore) {
		this.avgLookPhoneScore = avgLookPhoneScore;
	}
	/**
	 * 获取：平均单次看手机  目标积分
	 */
	public Integer getAvgLookPhoneScore() {
		return avgLookPhoneScore;
	}
	/**
	 * 设置：平均单次看手机  最终积分
	 */
	public void setAvgLookPhoneScoreResult(Integer avgLookPhoneScoreResult) {
		this.avgLookPhoneScoreResult = avgLookPhoneScoreResult;
	}
	/**
	 * 获取：平均单次看手机  最终积分
	 */
	public Integer getAvgLookPhoneScoreResult() {
		return avgLookPhoneScoreResult;
	}
	/**
	 * 设置：最终结果：平均单次看电脑电视时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgLookTvResult(String avgLookTvResult) {
		this.avgLookTvResult = avgLookTvResult;
	}
	/**
	 * 获取：最终结果：平均单次看电脑电视时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getAvgLookTvResult() {
		return avgLookTvResult;
	}
	/**
	 * 设置：平均单次看电脑电视时长 目标积分
	 */
	public void setAvgLookScore(Integer avgLookScore) {
		this.avgLookScore = avgLookScore;
	}
	/**
	 * 获取：平均单次看电脑电视时长 目标积分
	 */
	public Integer getAvgLookScore() {
		return avgLookScore;
	}
	/**
	 * 设置：平均单次看电脑电视时长最终积分
	 */
	public void setAvgLookScoreResult(Integer avgLookScoreResult) {
		this.avgLookScoreResult = avgLookScoreResult;
	}
	/**
	 * 获取：平均单次看电脑电视时长最终积分
	 */
	public Integer getAvgLookScoreResult() {
		return avgLookScoreResult;
	}
	/**
	 * 设置：最终结果：平均阅读旋转角度 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgSitTiltResult(String avgSitTiltResult) {
		this.avgSitTiltResult = avgSitTiltResult;
	}
	/**
	 * 获取：最终结果：平均阅读旋转角度 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getAvgSitTiltResult() {
		return avgSitTiltResult;
	}
	/**
	 * 设置：平均阅读旋转角度目标积分
	 */
	public void setAvgSitTiltScore(Integer avgSitTiltScore) {
		this.avgSitTiltScore = avgSitTiltScore;
	}
	/**
	 * 获取：平均阅读旋转角度目标积分
	 */
	public Integer getAvgSitTiltScore() {
		return avgSitTiltScore;
	}
	/**
	 * 设置：平均阅读旋转角度最终积分
	 */
	public void setAvgSitTiltScoreResult(Integer avgSitTiltScoreResult) {
		this.avgSitTiltScoreResult = avgSitTiltScoreResult;
	}
	/**
	 * 获取：平均阅读旋转角度最终积分
	 */
	public Integer getAvgSitTiltScoreResult() {
		return avgSitTiltScoreResult;
	}
	/**
	 * 设置：最终结果：有效使用时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setEffectiveUseTimeResult(String effectiveUseTimeResult) {
		this.effectiveUseTimeResult = effectiveUseTimeResult;
	}
	/**
	 * 获取：最终结果：有效使用时长 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getEffectiveUseTimeResult() {
		return effectiveUseTimeResult;
	}
	/**
	 * 设置：有效使用时长 目标积分
	 */
	public void setEffectiveUseTimeScore(Integer effectiveUseTimeScore) {
		this.effectiveUseTimeScore = effectiveUseTimeScore;
	}
	/**
	 * 获取：有效使用时长 目标积分
	 */
	public Integer getEffectiveUseTimeScore() {
		return effectiveUseTimeScore;
	}
	/**
	 * 设置：有效使用时长 最终积分
	 */
	public void setEffectiveUseTimeScoreResult(Integer effectiveUseTimeScoreResult) {
		this.effectiveUseTimeScoreResult = effectiveUseTimeScoreResult;
	}
	/**
	 * 获取：有效使用时长 最终积分
	 */
	public Integer getEffectiveUseTimeScoreResult() {
		return effectiveUseTimeScoreResult;
	}
	/**
	 * 设置：最终结果：平均每次阅读光照 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setAvgLightResult(String avgLightResult) {
		this.avgLightResult = avgLightResult;
	}
	/**
	 * 获取：最终结果：平均每次阅读光照 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getAvgLightResult() {
		return avgLightResult;
	}
	/**
	 * 设置：平均每次阅读光照  目标积分
	 */
	public void setAvgLightScore(Integer avgLightScore) {
		this.avgLightScore = avgLightScore;
	}
	/**
	 * 获取：平均每次阅读光照  目标积分
	 */
	public Integer getAvgLightScore() {
		return avgLightScore;
	}
	/**
	 * 设置：平均每次阅读光照  最终积分
	 */
	public void setAvgLightScoreResult(Integer avgLightScoreResult) {
		this.avgLightScoreResult = avgLightScoreResult;
	}
	/**
	 * 获取：平均每次阅读光照  最终积分
	 */
	public Integer getAvgLightScoreResult() {
		return avgLightScoreResult;
	}
	/**
	 * 设置：用眼评级 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public void setCountGrade(String countGrade) {
		this.countGrade = countGrade;
	}
	/**
	 * 获取：用眼评级 YOU=优 LIANG=良  CHA=差  JICHA=极差
	 */
	public String getCountGrade() {
		return countGrade;
	}
	/**
	 * 设置：家长模式下礼品详情
	 */
	public void setGift(String gift) {
		this.gift = gift;
	}
	/**
	 * 获取：家长模式下礼品详情
	 */
	public String getGift() {
		return gift;
	}
	/**
	 * 设置：本次任务总积分
	 */
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	/**
	 * 获取：本次任务总积分
	 */
	public Integer getTotalScore() {
		return totalScore;
	}
	/**
	 * 设置：0=任务进行中  1=任务已完成，数据未上传  2=任务已完成  数据已上传
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 * 获取：0=任务进行中  1=任务已完成，数据未上传  2=任务已完成  数据已上传
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * 设置：任务开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：任务开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：user表中的积分
	 */
	public void setAddreadyScore(Integer addreadyScore) {
		this.addreadyScore = addreadyScore;
	}
	/**
	 * 获取：user表中的积分
	 */
	public Integer getAddreadyScore() {
		return addreadyScore;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavgread(String lastavgread) {
		this.lastavgread = lastavgread;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavgread() {
		return lastavgread;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavgout(String lastavgout) {
		this.lastavgout = lastavgout;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavgout() {
		return lastavgout;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavgreaddistance(String lastavgreaddistance) {
		this.lastavgreaddistance = lastavgreaddistance;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavgreaddistance() {
		return lastavgreaddistance;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavglooktv(String lastavglooktv) {
		this.lastavglooktv = lastavglooktv;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavglooktv() {
		return lastavglooktv;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavgsittilt(String lastavgsittilt) {
		this.lastavgsittilt = lastavgsittilt;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavgsittilt() {
		return lastavgsittilt;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLasteffectiveusetime(String lasteffectiveusetime) {
		this.lasteffectiveusetime = lasteffectiveusetime;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLasteffectiveusetime() {
		return lasteffectiveusetime;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavglight(String lastavglight) {
		this.lastavglight = lastavglight;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavglight() {
		return lastavglight;
	}
	/**
	 * 设置：上次评级
	 */
	public void setLastavglookphone(String lastavglookphone) {
		this.lastavglookphone = lastavglookphone;
	}
	/**
	 * 获取：上次评级
	 */
	public String getLastavglookphone() {
		return lastavglookphone;
	}
	/**
	 * 设置：APP=app端任务  PC=pc端任务
	 */
	public void setPcorapp(String pcorapp) {
		this.pcorapp = pcorapp;
	}
	/**
	 * 获取：APP=app端任务  PC=pc端任务
	 */
	public String getPcorapp() {
		return pcorapp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
