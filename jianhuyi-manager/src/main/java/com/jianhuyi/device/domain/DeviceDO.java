package com.jianhuyi.device.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;



/**
 * 用户设备表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-18 16:22:42
 */
public class DeviceDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//用户账号
	private String account;
	//日志时间
	private Date createTime;
	//设备号
	private String identity;
	//图片
	private String icon;
	private MultipartFile iconitems;
	
	//名称
	private String name;
	//mac物理地址
	private String mac;
	//设备是否连接   0连接中   1 未连接
	private Integer defaultDevice;
	//0 个人设备  1 非个人设备
	private Integer deviceType;
	//excel导入
	private MultipartFile excelDevice;
	
	//删除标志  0已删除   1未删除
	private Integer deleted;
	//用户id
	private Long userId;	

	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：用户账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：用户账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：日志时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：日志时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：设备号
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	/**
	 * 获取：设备号
	 */
	public String getIdentity() {
		return identity;
	}
	/**
	 * 设置：图片
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取：图片
	 */
	public String getIcon() {
		return icon;
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
	 * 设置：mac物理地址
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * 获取：mac物理地址
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * 设置：是否为默认设备   0 不默认  1 默认设备
	 */
	public void setDefaultDevice(Integer defaultDevice) {
		this.defaultDevice = defaultDevice;
	}
	/**
	 * 获取：是否为默认设备   0 不默认  1 默认设备
	 */
	public Integer getDefaultDevice() {
		return defaultDevice;
	}
	/**
	 * 设置：0 个人设备  1 非个人设备
	 */
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * 获取：0 个人设备  1 非个人设备
	 */
	public Integer getDeviceType() {
		return deviceType;
	}
		
	
	public MultipartFile getExcelDevice() {
		return excelDevice;
	}
	public void setExcelDevice(MultipartFile excelDevice) {
		this.excelDevice = excelDevice;
	}
	public MultipartFile getIconitems() {
		return iconitems;
	}
	public void setIconitems(MultipartFile iconitems) {
		this.iconitems = iconitems;
	}
	
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
