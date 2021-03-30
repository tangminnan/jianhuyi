package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-19 15:07:20
 */
public class UserTaskLinshiDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//用户id
	private Long userId;
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
	//平均阅读光照
	private String avgLight;
	//创建时间
	private Date createTime;
	//当日所获积分
	private Integer score;
	//监护仪有效使用时长
	private Double hourtime;
	//是否完成  0=完成  1=未完成
	private Integer iffinish;
	//任务id
	private Integer taskId;
	private String day;

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
	 * 设置：当日所获积分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 获取：当日所获积分
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 设置：监护仪有效使用时长
	 */
	public void setHourtime(Double hourtime) {
		this.hourtime = hourtime;
	}
	/**
	 * 获取：监护仪有效使用时长
	 */
	public Double getHourtime() {
		return hourtime;
	}
	/**
	 * 设置：是否完成  0=完成  1=未完成
	 */
	public void setIffinish(Integer iffinish) {
		this.iffinish = iffinish;
	}
	/**
	 * 获取：是否完成  0=完成  1=未完成
	 */
	public Integer getIffinish() {
		return iffinish;
	}
	/**
	 * 设置：任务id
	 */
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：任务id
	 */
	public Integer getTaskId() {
		return taskId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
}
