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
public class ErrorDataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	private Long userId;

	//测距芯片（0 正常 1故障）
	private Integer disError;
	//环境光芯片（0 正常 1故障）
	private Integer lightError;
	//MEMS芯片 0正常 1故障
	private Integer memsError;
	//存储芯片 0正常 1故障
	private Integer storeError;
	//摄像头模组 0正常 1故障
	private Integer sxterror;
	//时间
	private String time;
	//上传时间
	private Date updateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * 设置：测距芯片（0 正常 1故障）
	 */
	public void setDisError(Integer disError) {
		this.disError = disError;
	}
	/**
	 * 获取：测距芯片（0 正常 1故障）
	 */
	public Integer getDisError() {
		return disError;
	}
	/**
	 * 设置：环境光芯片（0 正常 1故障）
	 */
	public void setLightError(Integer lightError) {
		this.lightError = lightError;
	}
	/**
	 * 获取：环境光芯片（0 正常 1故障）
	 */
	public Integer getLightError() {
		return lightError;
	}
	/**
	 * 设置：MEMS芯片 0正常 1故障
	 */
	public void setMemsError(Integer memsError) {
		this.memsError = memsError;
	}
	/**
	 * 获取：MEMS芯片 0正常 1故障
	 */
	public Integer getMemsError() {
		return memsError;
	}
	/**
	 * 设置：存储芯片 0正常 1故障
	 */
	public void setStoreError(Integer storeError) {
		this.storeError = storeError;
	}
	/**
	 * 获取：存储芯片 0正常 1故障
	 */
	public Integer getStoreError() {
		return storeError;
	}
	/**
	 * 设置：摄像头模组 0正常 1故障
	 */
	public void setSxterror(Integer sxterror) {
		this.sxterror = sxterror;
	}
	/**
	 * 获取：摄像头模组 0正常 1故障
	 */
	public Integer getSxterror() {
		return sxterror;
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

	@Override
	public String toString() {
		return "ErrorDataDO{" +
				"id=" + id +
				", disError=" + disError +
				", lightError=" + lightError +
				", memsError=" + memsError +
				", storeError=" + storeError +
				", sxterror=" + sxterror +
				", time='" + time + '\'' +
				", updateTime=" + updateTime +
				'}';
	}
}
