package com.jianhuyi.common.utils.domain;


/**
 * 报错信息
 */
public class ErrorBean {
    private String time;
    private Integer storeError;//存储芯片
    private Integer memsError;//MEMS芯片
    private Integer sxterror;//摄像头模组
    private Integer disError;//测距芯片
    private Integer lightError;//环境光芯片

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStoreError() {
        return storeError;
    }

    public void setStoreError(Integer storeError) {
        this.storeError = storeError;
    }

    public Integer getMemsError() {
        return memsError;
    }

    public void setMemsError(Integer memsError) {
        this.memsError = memsError;
    }

    public Integer getSxterror() {
        return sxterror;
    }

    public void setSxterror(Integer sxterror) {
        this.sxterror = sxterror;
    }

    public Integer getDisError() {
        return disError;
    }

    public void setDisError(Integer disError) {
        this.disError = disError;
    }

    public Integer getLightError() {
        return lightError;
    }

    public void setLightError(Integer lightError) {
        this.lightError = lightError;
    }

    @Override
    public String toString() {
        return "ErrorBean{" +
                "time='" + time + '\'' +
                ", storeError=" + storeError +
                ", memsError=" + memsError +
                ", sxterror=" + sxterror +
                ", disError=" + disError +
                ", lightError=" + lightError +
                '}';
    }
}
