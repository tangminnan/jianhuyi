package com.jianhuyi.information.domain;

public class ResultData {

    private double avgReadDuration; // 平均每次阅读时长
    private double avgReadDistance; // 平均阅读距离
    private double avgReadLight; // 平均阅读光照
    private double avgLookPhoneDuration; // 平均单次看手机时长
    private double avgLookTvComputerDuration; // 平均单次看电脑及电视时长
    private double avgSitTilt; // 平均旋转角度

    public Double getAvgReadDuration() {
        return avgReadDuration;
    }

    public void setAvgReadDuration(Double avgReadDuration) {
        this.avgReadDuration = avgReadDuration;
    }

    public Double getAvgReadDistance() {
        return avgReadDistance;
    }

    public void setAvgReadDistance(Double avgReadDistance) {
        this.avgReadDistance = avgReadDistance;
    }

    public Double getAvgReadLight() {
        return avgReadLight;
    }

    public void setAvgReadLight(Double avgReadLight) {
        this.avgReadLight = avgReadLight;
    }

    public Double getAvgLookPhoneDuration() {
        return avgLookPhoneDuration;
    }

    public void setAvgLookPhoneDuration(Double avgLookPhoneDuration) {
        this.avgLookPhoneDuration = avgLookPhoneDuration;
    }

    public Double getAvgLookTvComputerDuration() {
        return avgLookTvComputerDuration;
    }

    public void setAvgLookTvComputerDuration(Double avgLookTvComputerDuration) {
        this.avgLookTvComputerDuration = avgLookTvComputerDuration;
    }

    public Double getAvgSitTilt() {
        return avgSitTilt;
    }

    public void setAvgSitTilt(Double avgSitTilt) {
        this.avgSitTilt = avgSitTilt;
    }
}
