package com.jianhuyi.common.utils.domain;

/**
 * 基础数据信息
 */
public class BaseDataBean {
    private String time;//时间
    private Integer deflection;//偏转 1 左 2 右
    private Double angles;//倾斜角度
    private Double lights;//光强
    private Double distances;//测距

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDeflection() {
        return deflection;
    }

    public void setDeflection(Integer deflection) {
        this.deflection = deflection;
    }

    public Double getAngles() {
        return angles;
    }

    public void setAngles(Double angles) {
        this.angles = angles;
    }

    public Double getLights() {
        return lights;
    }

    public void setLights(Double lights) {
        this.lights = lights;
    }

    public Double getDistances() {
        return distances;
    }

    public void setDistances(Double distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        return "BaseDataBean{" +
                "time='" + time + '\'' +
                ", deflection=" + deflection +
                ", angles=" + angles +
                ", lights=" + lights +
                ", distances=" + distances +
                '}';
    }
}
