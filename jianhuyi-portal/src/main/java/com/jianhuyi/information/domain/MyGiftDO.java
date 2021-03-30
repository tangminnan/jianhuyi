package com.jianhuyi.information.domain;
import java.util.Date;

/**
 *  兑换记录
 */
public class MyGiftDO {
     private  Long id;
    /**
     * 获得者id
     */
    private  Long userId;
    /**
     *  礼物id
     */
    private  Long giftId;
    /**
     * 兑换时间
     */
    private Date createTime;
    /**
     *  礼物名称
     */
    private String giftName;
    /**
     *  所需积分
     */
    private Integer score;
    /**
     *  封面主要图
     */
    private String coverImg;
    /**
     *  0=兑换中 1=已兑换
     */
    private Integer flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
