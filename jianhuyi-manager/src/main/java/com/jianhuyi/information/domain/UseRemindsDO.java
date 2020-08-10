package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-07-22 09:43:53
 */
public class UseRemindsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//上传者id
	private Integer uploadId;
	//震动时间
	private Date remindsTime;
	//保存时间
	private Date saveTime;
	//用户id
	private Long userId;
	//设备id
	private Long equipmentId;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：上传者id
	 */
	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
	/**
	 * 获取：上传者id
	 */
	public Integer getUploadId() {
		return uploadId;
	}
	/**
	 * 设置：震动时间
	 */
	public void setRemindsTime(Date remindsTime) {
		this.remindsTime = remindsTime;
	}
	/**
	 * 获取：震动时间
	 */
	public Date getRemindsTime() {
		return remindsTime;
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
	 * 设置：设备id
	 */
	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}
	/**
	 * 获取：设备id
	 */
	public Long getEquipmentId() {
		return equipmentId;
	}
}
