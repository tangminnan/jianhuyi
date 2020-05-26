package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-27 14:52:34
 */
public class EnergysDataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//时间
	private String time;
	//上传时间
	private Date updateTime;
	//电量等级（0-4）
	private Integer power;
	//usb状态 0 无usb 1usb接入
	private Integer usbStatus;
	//有效时长
	private String effectiveTime;
	//阅读时长
	private String readTime;
	//非阅读时长
	private String unreadTime;

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
	 * 设置：上传时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：上传时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：电量等级（0-4）
	 */
	public void setPower(Integer power) {
		this.power = power;
	}
	/**
	 * 获取：电量等级（0-4）
	 */
	public Integer getPower() {
		return power;
	}
	/**
	 * 设置：usb状态 0 无usb 1usb接入
	 */
	public void setUsbStatus(Integer usbStatus) {
		this.usbStatus = usbStatus;
	}
	/**
	 * 获取：usb状态 0 无usb 1usb接入
	 */
	public Integer getUsbStatus() {
		return usbStatus;
	}
	/**
	 * 设置：有效时长
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	/**
	 * 获取：有效时长
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * 设置：阅读时长
	 */
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	/**
	 * 获取：阅读时长
	 */
	public String getReadTime() {
		return readTime;
	}
	/**
	 * 设置：非阅读时长
	 */
	public void setUnreadTime(String unreadTime) {
		this.unreadTime = unreadTime;
	}
	/**
	 * 获取：非阅读时长
	 */
	public String getUnreadTime() {
		return unreadTime;
	}
}
