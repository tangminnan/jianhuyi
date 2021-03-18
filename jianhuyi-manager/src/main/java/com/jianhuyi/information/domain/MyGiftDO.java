package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2021-03-18 14:27:52
 */
public class MyGiftDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//获得者id
	private Integer userId;
	//礼物id
	private Integer giftId;
	//获得时间
	private Date createTime;
	//封面主图
	private String coverImg;
	//礼物名称
	private String giftName;
	//兑换结果 0=兑换中  1=已兑换
	private Integer flag;
	//姓名
	private String name;

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
	 * 设置：获得者id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：获得者id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：礼物id
	 */
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}
	/**
	 * 获取：礼物id
	 */
	public Integer getGiftId() {
		return giftId;
	}
	/**
	 * 设置：获得时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：获得时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
