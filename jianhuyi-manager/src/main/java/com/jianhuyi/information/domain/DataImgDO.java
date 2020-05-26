package com.jianhuyi.information.domain;

import java.io.Serializable;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-03-25 14:23:41
 */
public class DataImgDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//dataid
	private Long dataid;
	//时间
	private String time;
	//图片路径
	private String filename;

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
	 * 设置：dataid
	 */
	public void setDataid(Long dataid) {
		this.dataid = dataid;
	}
	/**
	 * 获取：dataid
	 */
	public Long getDataid() {
		return dataid;
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
	 * 设置：图片路径
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * 获取：图片路径
	 */
	public String getFilename() {
		return filename;
	}
}
