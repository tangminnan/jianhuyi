package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2021-01-11 15:27:22
 */
public class GiftPcDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//礼物id
	private Long giftId;
	//任务时长
	private Integer taskTime;
	//健康用眼评级（优，良，差，极差）
	private String eyeRate;
	//平均单次阅读时长（优，良，差，极差）
	private String avgRead;
	//平均户外时长（优，良，差，极差）
	private String avgOut;
	//平均阅读距离（优，良，差，极差）
	private String avgReadDistance;
	//平均单次看手机时长（优，良，差，极差）
	private String avgLookPhone;
	//看电子屏（优，良，差，极差）
	private String avgLookTv;
	//平均坐姿倾斜度（优，良，差，极差）
	private String avgSitTilt;
	//有效使用时长（优，良，差，极差）
	private String effectiveUseTime;
	//平均阅读光照（优，良，差，极差）
	private String avgLight;
	//创建时间
	private Date createTime;

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
	 * 设置：健康用眼评级（优，良，差，极差）
	 */
	public void setEyeRate(String eyeRate) {
		this.eyeRate = eyeRate;
	}
	/**
	 * 获取：健康用眼评级（优，良，差，极差）
	 */
	public String getEyeRate() {
		return eyeRate;
	}
	/**
	 * 设置：平均单次阅读时长（优，良，差，极差）
	 */
	public void setAvgRead(String avgRead) {
		this.avgRead = avgRead;
	}
	/**
	 * 获取：平均单次阅读时长（优，良，差，极差）
	 */
	public String getAvgRead() {
		return avgRead;
	}
	/**
	 * 设置：平均户外时长（优，良，差，极差）
	 */
	public void setAvgOut(String avgOut) {
		this.avgOut = avgOut;
	}
	/**
	 * 获取：平均户外时长（优，良，差，极差）
	 */
	public String getAvgOut() {
		return avgOut;
	}
	/**
	 * 设置：平均阅读距离（优，良，差，极差）
	 */
	public void setAvgReadDistance(String avgReadDistance) {
		this.avgReadDistance = avgReadDistance;
	}
	/**
	 * 获取：平均阅读距离（优，良，差，极差）
	 */
	public String getAvgReadDistance() {
		return avgReadDistance;
	}
	/**
	 * 设置：平均单次看手机时长（优，良，差，极差）
	 */
	public void setAvgLookPhone(String avgLookPhone) {
		this.avgLookPhone = avgLookPhone;
	}
	/**
	 * 获取：平均单次看手机时长（优，良，差，极差）
	 */
	public String getAvgLookPhone() {
		return avgLookPhone;
	}
	/**
	 * 设置：看电子屏（优，良，差，极差）
	 */
	public void setAvgLookTv(String avgLookTv) {
		this.avgLookTv = avgLookTv;
	}
	/**
	 * 获取：看电子屏（优，良，差，极差）
	 */
	public String getAvgLookTv() {
		return avgLookTv;
	}
	/**
	 * 设置：平均坐姿倾斜度（优，良，差，极差）
	 */
	public void setAvgSitTilt(String avgSitTilt) {
		this.avgSitTilt = avgSitTilt;
	}
	/**
	 * 获取：平均坐姿倾斜度（优，良，差，极差）
	 */
	public String getAvgSitTilt() {
		return avgSitTilt;
	}
	/**
	 * 设置：有效使用时长（优，良，差，极差）
	 */
	public void setEffectiveUseTime(String effectiveUseTime) {
		this.effectiveUseTime = effectiveUseTime;
	}
	/**
	 * 获取：有效使用时长（优，良，差，极差）
	 */
	public String getEffectiveUseTime() {
		return effectiveUseTime;
	}
	/**
	 * 设置：平均阅读光照（优，良，差，极差）
	 */
	public void setAvgLight(String avgLight) {
		this.avgLight = avgLight;
	}
	/**
	 * 获取：平均阅读光照（优，良，差，极差）
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
