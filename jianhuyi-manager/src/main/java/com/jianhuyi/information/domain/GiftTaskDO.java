package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:13
 */
public class GiftTaskDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//
	private Long giftId;
	//任务时间
	private Double taskTime;
	//平均评级
	private String avgRate;
	//任务详情
	private String taskDetails;
	//创建时间
	private Date createTime;

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
	 * 设置：
	 */
	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}
	/**
	 * 获取：
	 */
	public Long getGiftId() {
		return giftId;
	}
	/**
	 * 设置：任务时间
	 */
	public void setTaskTime(Double taskTime) {
		this.taskTime = taskTime;
	}
	/**
	 * 获取：任务时间
	 */
	public Double getTaskTime() {
		return taskTime;
	}
	/**
	 * 设置：平均评级
	 */
	public void setAvgRate(String avgRate) {
		this.avgRate = avgRate;
	}
	/**
	 * 获取：平均评级
	 */
	public String getAvgRate() {
		return avgRate;
	}
	/**
	 * 设置：任务详情
	 */
	public void setTaskDetails(String taskDetails) {
		this.taskDetails = taskDetails;
	}
	/**
	 * 获取：任务详情
	 */
	public String getTaskDetails() {
		return taskDetails;
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
