package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-02-11 17:10:35
 */
public class VersionUpdateDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//版本号
	private Integer versionNum;
	//版本名称
	private String versionName;
	//更新时间
	private Date updateTime;
	//1 安卓; 2  ios
	private Integer type;
	//更新内容
	private String versionContent;
	//下载地址
	private String linkUrl;

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
	 * 设置：版本号
	 */
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	/**
	 * 获取：版本号
	 */
	public Integer getVersionNum() {
		return versionNum;
	}
	/**
	 * 设置：版本名称
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	/**
	 * 获取：版本名称
	 */
	public String getVersionName() {
		return versionName;
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
	 * 设置：1 安卓; 2  ios
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1 安卓; 2  ios
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：更新内容
	 */
	public void setVersionContent(String versionContent) {
		this.versionContent = versionContent;
	}
	/**
	 * 获取：更新内容
	 */
	public String getVersionContent() {
		return versionContent;
	}
	/**
	 * 设置：下载地址
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	/**
	 * 获取：下载地址
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
}
