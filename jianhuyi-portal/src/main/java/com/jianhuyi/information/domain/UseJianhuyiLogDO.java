package com.jianhuyi.information.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
@Data
public class UseJianhuyiLogDO implements Serializable {
    private static final long serialVersionUID = 1L;

    // id
    private Integer id;
    // 用户Id
    private Integer userId;
    // 用户Id
    private Integer uploadId;
    //数据测试时间
    private String saveTime;
    //设备号
    private String equipmentId;
    // 添加时间
    private Date addTime;
    // 阅读时长(分钟）
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double readDuration;
    // 户外时长(小时）
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double outdoorsDuration;
    // 阅读距离(厘米)
    private Double readDistance;
    // 阅读光照(lux)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double readLight;
    // 看手机时长(分钟)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double lookPhoneDuration;
    //看手机次数
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lookPhoneCount;
    //看电脑次数
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer lookTvComputerCount;
    // 看电脑电视时长(分钟）
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double lookTvComputerDuration;
    // 坐姿倾斜度
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double sitTilt;
    // 使用监护仪时长(小时）
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer useJianhuyiDuration;
    // 运动时长(小时)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double sportDuration;
    // 删除标志(1:删除 0：未删除)
    private Integer delFlag;
    //阅读状态
    private Integer status;

    private Integer num;

    private Double allreadDuration;
    //治疗提醒
    private Integer remind;


}
