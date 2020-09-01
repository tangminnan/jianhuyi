package com.jianhuyi.information.domain;

import java.io.Serializable;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:12
 */
public class RecordDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //日期
    private String date;
    //有效使用监护仪时长
    private double useJianhuyiTime;
    //评级 1=优，2=良，3=差，4=极差
    private String grade;
    //完成度
    private boolean isfinish;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUseJianhuyiTime() {
        return useJianhuyiTime;
    }

    public void setUseJianhuyiTime(double useJianhuyiTime) {
        this.useJianhuyiTime = useJianhuyiTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isIsfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

    @Override
    public String toString() {
        return "RecordDO{" +
                "date='" + date + '\'' +
                ", useJianhuyiTime=" + useJianhuyiTime +
                ", grade='" + grade + '\'' +
                ", isfinish=" + isfinish +
                '}';
    }
}
