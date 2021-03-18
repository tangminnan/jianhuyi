package com.jianhuyi.users.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 11:27:19
 */
public class UserDO implements Serializable {
  private static final long serialVersionUID = 1L;

  // id
  private Long id;
  // 用户id
  private Long userId;
  // 微信id
  private String openId;
  // 昵称
  private String nickname;
  // 密码
  private String password;
  // 手机号
  private String phone;
  // 头像
  private String heardUrl;
  // 真实姓名
  private String name;
  // 身份证号
  private String identityCard;
  // qq标识
  private String unionid;
  // 注册时间
  private Date registerTime;
  // 消费金额
  private Double payNum;
  // 服务次数
  private Integer serveNum;
  // 余额
  private Double balance;
  // 返还
  private Double restitution;
  // 缴费日期
  private Date payTime;
  // 最后登录时间
  private Date loginTime;
  // 添加时间
  private Date addTime;
  // 修改时间
  private Date updateTime;
  // 0：是；1：否
  private Integer deleteFlag;
  //
  private String username;
  // 性别：值为1时是男性，值为2时是女性，值为0时是未知
  private Integer sex;
  // 出生年月
  private Date birthday;
  // 设置模式(0默认模式  1家长模式    2专业模式)
  private Integer flag;
  // 年龄
  private Integer age;
  // 学校
  private String school;
  // 班级
  private String grade;
  // 左视力
  private Double lVision;
  // 右视力
  private Double rVision;
  // 左眼球径
  private Double lEyeBallDiameter;
  //
  private Double rEyeBallDiameter;
  // 左眼柱径
  private Double lColumnDiameter;
  // 右眼柱径
  private Double rColumnDiameter;
  // 左眼轴
  private Double lEyeAxis;
  // 右眼轴
  private Double rEyeAxis;

  private String studentNum;

  private String ids;

  private int managerId;

  private int bindType;

  private String isWearGlasses;

  public String getIsWearGlasses() {
    return isWearGlasses;
  }

  public void setIsWearGlasses(String isWearGlasses) {
    this.isWearGlasses = isWearGlasses;
  }

  public int getManagerId() {
    return managerId;
  }

  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }

  public String getIds() {
    return ids;
  }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public int getBindType() {
    return bindType;
  }

  public void setBindType(int bindType) {
    this.bindType = bindType;
  }

  public String getStudentNum() {
    return studentNum;
  }

  public void setStudentNum(String studentNum) {
    this.studentNum = studentNum;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setHeardUrl(String heardUrl) {
    this.heardUrl = heardUrl;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }

  public void setRegisterTime(Date registerTime) {
    this.registerTime = registerTime;
  }

  public void setPayNum(Double payNum) {
    this.payNum = payNum;
  }

  public void setServeNum(Integer serveNum) {
    this.serveNum = serveNum;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public void setRestitution(Double restitution) {
    this.restitution = restitution;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public void setDeleteFlag(Integer deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public void setlVision(Double lVision) {
    this.lVision = lVision;
  }

  public void setrVision(Double rVision) {
    this.rVision = rVision;
  }

  public void setlEyeBallDiameter(Double lEyeBallDiameter) {
    this.lEyeBallDiameter = lEyeBallDiameter;
  }

  public void setrEyeBallDiameter(Double rEyeBallDiameter) {
    this.rEyeBallDiameter = rEyeBallDiameter;
  }

  public void setlColumnDiameter(Double lColumnDiameter) {
    this.lColumnDiameter = lColumnDiameter;
  }

  public void setrColumnDiameter(Double rColumnDiameter) {
    this.rColumnDiameter = rColumnDiameter;
  }

  public void setlEyeAxis(Double lEyeAxis) {
    this.lEyeAxis = lEyeAxis;
  }

  public void setrEyeAxis(Double rEyeAxis) {
    this.rEyeAxis = rEyeAxis;
  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public String getOpenId() {
    return openId;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public String getHeardUrl() {
    return heardUrl;
  }

  public String getName() {
    return name;
  }

  public String getIdentityCard() {
    return identityCard;
  }

  public String getUnionid() {
    return unionid;
  }

  public Date getRegisterTime() {
    return registerTime;
  }

  public Double getPayNum() {
    return payNum;
  }

  public Integer getServeNum() {
    return serveNum;
  }

  public Double getBalance() {
    return balance;
  }

  public Double getRestitution() {
    return restitution;
  }

  public Date getPayTime() {
    return payTime;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public Date getAddTime() {
    return addTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public Integer getDeleteFlag() {
    return deleteFlag;
  }

  public String getUsername() {
    return username;
  }

  public Integer getSex() {
    return sex;
  }

  public Date getBirthday() {
    return birthday;
  }

  public Integer getFlag() {
    return flag;
  }

  public Integer getAge() {
    return age;
  }

  public String getSchool() {
    return school;
  }

  public String getGrade() {
    return grade;
  }

  public Double getlVision() {
    return lVision;
  }

  public Double getrVision() {
    return rVision;
  }

  public Double getlEyeBallDiameter() {
    return lEyeBallDiameter;
  }

  public Double getrEyeBallDiameter() {
    return rEyeBallDiameter;
  }

  public Double getlColumnDiameter() {
    return lColumnDiameter;
  }

  public Double getrColumnDiameter() {
    return rColumnDiameter;
  }

  public Double getlEyeAxis() {
    return lEyeAxis;
  }

  public Double getrEyeAxis() {
    return rEyeAxis;
  }

  @Override
  public String toString() {
    return "UserDO{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", openId='"
        + openId
        + '\''
        + ", nickname='"
        + nickname
        + '\''
        + ", password='"
        + password
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", heardUrl='"
        + heardUrl
        + '\''
        + ", name='"
        + name
        + '\''
        + ", identityCard='"
        + identityCard
        + '\''
        + ", unionid='"
        + unionid
        + '\''
        + ", registerTime="
        + registerTime
        + ", payNum="
        + payNum
        + ", serveNum="
        + serveNum
        + ", balance="
        + balance
        + ", restitution="
        + restitution
        + ", payTime="
        + payTime
        + ", loginTime="
        + loginTime
        + ", addTime="
        + addTime
        + ", updateTime="
        + updateTime
        + ", deleteFlag="
        + deleteFlag
        + ", username='"
        + username
        + '\''
        + ", sex="
        + sex
        + ", birthday="
        + birthday
        + ", flag="
        + flag
        + ", age="
        + age
        + ", school='"
        + school
        + '\''
        + ", grade='"
        + grade
        + '\''
        + ", lVision="
        + lVision
        + ", rVision="
        + rVision
        + ", lEyeBallDiameter="
        + lEyeBallDiameter
        + ", rEyeBallDiameter="
        + rEyeBallDiameter
        + ", lColumnDiameter="
        + lColumnDiameter
        + ", rColumnDiameter="
        + rColumnDiameter
        + ", lEyeAxis="
        + lEyeAxis
        + ", rEyeAxis="
        + rEyeAxis
        + ", studentNum='"
        + studentNum
        + '\''
        + ", ids='"
        + ids
        + '\''
        + ", managerId="
        + managerId
        + ", bindType="
        + bindType
        + ", isWearGlasses='"
        + isWearGlasses
        + '\''
        + '}';
  }
}
