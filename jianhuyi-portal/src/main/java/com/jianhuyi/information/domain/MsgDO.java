package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 消息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-19 11:33:19
 */
public class MsgDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//消息名称
	private String name;
	//推送类型  0：系统 1：个人
	private Integer type;
	//推送对象
	private String forNames;
	//接收人
	private Long forIds;
	//0：系统；1：个人
	private Integer forType;
	//推送内容
	private String forDetails;
	//添加时间
	private Date addTime;
	//修改时间
	private Date updateTime;
	//0：是；1：否
	private Integer deleteFlag;
	//发送人
	private Long createBy;
	//读取状态（1:未读 0:已读）
	private Integer statue;
	//用户消息中间表Id
	private Long muId;

	
	public Integer getStatue() {
		return statue;
	}
	public void setStatue(Integer statue) {
		this.statue = statue;
	}
	public Long getMuId() {
		return muId;
	}
	public void setMuId(Long muId) {
		this.muId = muId;
	}
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
	 * 设置：消息名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：消息名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：推送类型  0：系统 1：个人
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：推送类型  0：系统 1：个人
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：推送对象
	 */
	public void setForNames(String forNames) {
		this.forNames = forNames;
	}
	/**
	 * 获取：推送对象
	 */
	public String getForNames() {
		return forNames;
	}
	/**
	 * 设置：接收人
	 */
	public void setForIds(Long forIds) {
		this.forIds = forIds;
	}
	/**
	 * 获取：接收人
	 */
	public Long getForIds() {
		return forIds;
	}
	/**
	 * 设置：0：系统；1：个人
	 */
	public void setForType(Integer forType) {
		this.forType = forType;
	}
	/**
	 * 获取：0：系统；1：个人
	 */
	public Integer getForType() {
		return forType;
	}
	/**
	 * 设置：推送内容
	 */
	public void setForDetails(String forDetails) {
		this.forDetails = forDetails;
	}
	/**
	 * 获取：推送内容
	 */
	public String getForDetails() {
		return forDetails;
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
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：0：是；1：否
	 */
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * 获取：0：是；1：否
	 */
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * 设置：发送人
	 */
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：发送人
	 */
	public Long getCreateBy() {
		return createBy;
	}
}
