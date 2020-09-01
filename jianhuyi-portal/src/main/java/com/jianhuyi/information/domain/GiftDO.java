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
@Data
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


}
