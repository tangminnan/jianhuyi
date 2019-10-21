package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 帮助表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:00:55
 */
public class HelpDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//序号
	private Integer number;
	//名称
	private String name;
	//标题
	private String title;
	//内容
	private String content;
	//添加时间
	private Date addTime;
	//更新时间
	private Date updateTime;

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
	 * 设置：序号
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
	/**
	 * 获取：序号
	 */
	public Integer getNumber() {
		return number;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：添加时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取：添加时间
	 */
	public Date getAddTime() {
		return addTime;
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
}
