package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 产品建议
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 17:40:54
 */
public class ProductAdviseDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//建议内容
	private String adviseContent;
	//建议用户id
	private Long userId;
	//添加时间
	private Date addTime;
	//删除标志(1:删除 0：未删除)
	private Integer delFlag;

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
	 * 设置：建议内容
	 */
	public void setAdviseContent(String adviseContent) {
		this.adviseContent = adviseContent;
	}
	/**
	 * 获取：建议内容
	 */
	public String getAdviseContent() {
		return adviseContent;
	}
	/**
	 * 设置：建议用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：建议用户id
	 */
	public Long getUserId() {
		return userId;
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
	 * 设置：删除标志(1:删除 0：未删除)
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标志(1:删除 0：未删除)
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
