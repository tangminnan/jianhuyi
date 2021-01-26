package com.jianhuyi.information.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:12
 */
//@Data
@ToString(exclude = "Password")
public class GiftDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //礼物序号
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String giftId;
    //封面主图
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String coverImg;
    //礼物名称
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String giftName;
    //图文详情
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String giftDetails;
    //礼物创建时间
    private Date createTime;

    private MultipartFile imgFile;

    //任务时间
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double taskTime;
    //平均评级
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avgRate;
    //任务详情
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String taskDetails;

    private int type;

    private GiftPcDO giftPc;

    public GiftPcDO getGiftPc() {
        return giftPc;
    }

    public void setGiftPc(GiftPcDO giftPc) {
        this.giftPc = giftPc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftDetails() {
        return giftDetails;
    }

    public void setGiftDetails(String giftDetails) {
        this.giftDetails = giftDetails;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }

    public Double getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Double taskTime) {
        this.taskTime = taskTime;
    }

    public String getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(String avgRate) {
        this.avgRate = avgRate;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }
}
