package com.jianhuyi.information.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户信息表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 11:27:19
 */
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;

    //真实姓名
    private String name;
    private Integer sex;
    //出生年月
    private Date birthday;
    //所剩积分
    private Integer scores;
    //年龄
    private Integer age;
    //学校
    private String school;
    //班级
    private String grade;
    //个人任务的
    private Long taskId;
    //医生或老师下发的任务
    private Long taskIds;



    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
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

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Long taskIds) {
        this.taskIds = taskIds;
    }
}
