package com.jianhuyi.common.utils.domain;


/**
 * 震动信息
 */
public class RemindBean {
    private String time;
    private Integer type;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RemindBean{" +
                "time='" + time + '\'' +
                ", type=" + type +
                '}';
    }
}
