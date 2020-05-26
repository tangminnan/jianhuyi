package com.jianhuyi.owneruser.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-27 09:55:49
 */
public class OwnerUserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//用户id
	private Long userId;
	//微信id
	private String openId;
	//昵称
	private String nickname;
	//密码
	private String password;
	//手机号
	private String phone;
	//头像
	private String heardUrl;
	//头像附件
	private MultipartFile fileImg; 
	//真实姓名
	private String name;
	//身份证号
	private String identityCard;
	//注册时间
	private Date registerTime;
	//消费金额
	private Double payNum;
	//服务次数
	private Integer serveNum;
	//余额
	private Double balance;
	//返还
	private Double restitution;
	//缴费日期
	private Date payTime;
	//最后登录时间
	private Date loginTime;
	//添加时间
	private Date addTime;
	//修改时间
	private Date updateTime;
	//0：是；1：否
	private Integer deleteFlag;
	//
	private String username;
	//性别：值为1时是男性，值为2时是女性，值为0时是未知
	private Integer sex;
	//出生年月
	private Date birthday;
	//设置模式(0默认模式  1家长模式    2专业模式)
	private Integer flag;
	
	//qq标识
	private String unionid;
	
	
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：微信id
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：微信id
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：头像
	 */
	public void setHeardUrl(String heardUrl) {
		this.heardUrl = heardUrl;
	}
	/**
	 * 获取：头像
	 */
	public String getHeardUrl() {
		return heardUrl;
	}
	/**
	 * 设置：真实姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：真实姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdentityCard() {
		return identityCard;
	}
	/**
	 * 设置：注册时间
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	/**
	 * 获取：注册时间
	 */
	public Date getRegisterTime() {
		return registerTime;
	}
	/**
	 * 设置：消费金额
	 */
	public void setPayNum(Double payNum) {
		this.payNum = payNum;
	}
	/**
	 * 获取：消费金额
	 */
	public Double getPayNum() {
		return payNum;
	}
	/**
	 * 设置：服务次数
	 */
	public void setServeNum(Integer serveNum) {
		this.serveNum = serveNum;
	}
	/**
	 * 获取：服务次数
	 */
	public Integer getServeNum() {
		return serveNum;
	}
	/**
	 * 设置：余额
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	/**
	 * 获取：余额
	 */
	public Double getBalance() {
		return balance;
	}
	/**
	 * 设置：返还
	 */
	public void setRestitution(Double restitution) {
		this.restitution = restitution;
	}
	/**
	 * 获取：返还
	 */
	public Double getRestitution() {
		return restitution;
	}
	/**
	 * 设置：缴费日期
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * 获取：缴费日期
	 */
	public Date getPayTime() {
		return payTime;
	}
	/**
	 * 设置：最后登录时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 获取：最后登录时间
	 */
	public Date getLoginTime() {
		return loginTime;
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
	 * 设置：
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：性别：值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别：值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：出生年月
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：出生年月
	 */
	public Date getBirthday() {
		return birthday;
	}
	public MultipartFile getFileImg() {
		return fileImg;
	}
	public void setFileImg(MultipartFile fileImg) {
		this.fileImg = fileImg;
	}

	@Override
	public String toString() {
		return "OwnerUserDO{" +
				"id=" + id +
				", userId=" + userId +
				", openId='" + openId + '\'' +
				", nickname='" + nickname + '\'' +
				", password='" + password + '\'' +
				", phone='" + phone + '\'' +
				", heardUrl='" + heardUrl + '\'' +
				", fileImg=" + fileImg +
				", name='" + name + '\'' +
				", identityCard='" + identityCard + '\'' +
				", registerTime=" + registerTime +
				", payNum=" + payNum +
				", serveNum=" + serveNum +
				", balance=" + balance +
				", restitution=" + restitution +
				", payTime=" + payTime +
				", loginTime=" + loginTime +
				", addTime=" + addTime +
				", updateTime=" + updateTime +
				", deleteFlag=" + deleteFlag +
				", username='" + username + '\'' +
				", sex=" + sex +
				", birthday=" + birthday +
				", flag=" + flag +
				", unionid='" + unionid + '\'' +
				'}';
	}
}
