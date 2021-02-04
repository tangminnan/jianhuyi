package com.jianhuyi.information.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;


/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-08-18 11:44:12
 */
public class GiftDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //礼物序号
    private String giftId;
    //封面主图
    private String coverImg;
    //礼物名称
    private String giftName;
    //图文详情
    private String giftDetails;
    //礼物创建时间
    private Date createTime;
    //性别   1=男 2=女
    private Integer sex;
    //所需积分
    private Integer score;

    private MultipartFile imgFile;

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
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
     * 设置：礼物序号
     */
    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    /**
     * 获取：礼物序号
     */
    public String getGiftId() {
        return giftId;
    }

    /**
     * 设置：封面主图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取：封面主图
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 设置：礼物名称
     */
    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    /**
     * 获取：礼物名称
     */
    public String getGiftName() {
        return giftName;
    }

    /**
     * 设置：图文详情
     */
    public void setGiftDetails(String giftDetails) {
        this.giftDetails = giftDetails;
    }

    /**
     * 获取：图文详情
     */
    public String getGiftDetails() {
        return giftDetails;
    }

    /**
     * 设置：礼物创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：礼物创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
