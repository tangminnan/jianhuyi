package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-04-07 11:03:48
 */
public class ModelartApiDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//id
	private Long id;
	//模型名称
	private String modelName;
	//模型api地址
	private String modelApi;
	//保存时间
	private Date saveTime;
	//更新时间
	private Date updateTime;
	//类型（1 户外， 2 看屏幕 ）
	private Integer type;

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
	 * 设置：模型名称
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * 获取：模型名称
	 */
	public String getModelName() {
		return modelName;
	}
	/**
	 * 设置：模型api地址
	 */
	public void setModelApi(String modelApi) {
		this.modelApi = modelApi;
	}
	/**
	 * 获取：模型api地址
	 */
	public String getModelApi() {
		return modelApi;
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
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：类型（1 户外， 2 看屏幕 ）
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型（1 户外， 2 看屏幕 ）
	 */
	public Integer getType() {
		return type;
	}
}
