package com.jianhuyi.news.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;



/**
 * 咨讯表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-02-15 16:39:38
 */
public class NewsDO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
		//主键
		private Integer id;
		//专家ID
		private Long uId;
		//标题
		private String title;
		//内容
		private String content;
		//0  未删除     1  已删除
		private Integer deleted;
		//图片
		private MultipartFile picItems;
		private String tupianurl;
		//点击数
		private Integer clicks;
		//点赞数
		private Integer praises;
		//回复数
		private Integer replies;
		//最后回复
		private Long lastReplyId;
		//是否置顶  0：否   1：是
		private Integer isTop;
		//审核状态  1：未通过   0：已通过
		private Integer checkStatus;
		//
		private Date updateTime;
		//发布时间
		private Date createTime;
		private Date startTime;
		private Date endTime;

		
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public Date getEndTime() {
			return endTime;
		}
		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		public Long getuId() {
			return uId;
		}
		public void setuId(Long uId) {
			this.uId = uId;
		}
		public MultipartFile getPicItems() {
			return picItems;
		}
		public void setPicItems(MultipartFile picItems) {
			this.picItems = picItems;
		}
		/**
		 * 设置：主键
		 */
		public void setId(Integer id) {
			this.id = id;
		}
		/**
		 * 获取：主键
		 */
		public Integer getId() {
			return id;
		}
		/**
		 * 设置：专家ID
		 */
		public void setUId(Long uId) {
			this.uId = uId;
		}
		/**
		 * 获取：专家ID
		 */
		public Long getUId() {
			return uId;
		}
		/**
		 * 设置：标题
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		/**
		 * 获取：标题
		 */
		public String getTitle() {
			return title;
		}
		/**
		 * 设置：内容
		 */
		public void setContent(String content) {
			this.content = content;
		}
		/**
		 * 获取：内容
		 */
		public String getContent() {
			return content;
		}
		/**
		 * 设置：0  未删除     1  已删除
		 */
		public void setDeleted(Integer deleted) {
			this.deleted = deleted;
		}
		/**
		 * 获取：0  未删除     1  已删除
		 */
		public Integer getDeleted() {
			return deleted;
		}
		/**
		 * 设置：图片
		 */
		public void setTupianurl(String tupianurl) {
			this.tupianurl = tupianurl;
		}
		/**
		 * 获取：图片
		 */
		public String getTupianurl() {
			return tupianurl;
		}
		/**
		 * 设置：点击数
		 */
		public void setClicks(Integer clicks) {
			this.clicks = clicks;
		}
		/**
		 * 获取：点击数
		 */
		public Integer getClicks() {
			return clicks;
		}
		/**
		 * 设置：点赞数
		 */
		public void setPraises(Integer praises) {
			this.praises = praises;
		}
		/**
		 * 获取：点赞数
		 */
		public Integer getPraises() {
			return praises;
		}
		/**
		 * 设置：回复数
		 */
		public void setReplies(Integer replies) {
			this.replies = replies;
		}
		/**
		 * 获取：回复数
		 */
		public Integer getReplies() {
			return replies;
		}
		/**
		 * 设置：最后回复
		 */
		public void setLastReplyId(Long lastReplyId) {
			this.lastReplyId = lastReplyId;
		}
		/**
		 * 获取：最后回复
		 */
		public Long getLastReplyId() {
			return lastReplyId;
		}
		/**
		 * 设置：是否置顶  0：否   1：是
		 */
		public void setIsTop(Integer isTop) {
			this.isTop = isTop;
		}
		/**
		 * 获取：是否置顶  0：否   1：是
		 */
		public Integer getIsTop() {
			return isTop;
		}
		/**
		 * 设置：审核状态  1：未通过   0：已通过
		 */
		public void setCheckStatus(Integer checkStatus) {
			this.checkStatus = checkStatus;
		}
		/**
		 * 获取：审核状态  1：未通过   0：已通过
		 */
		public Integer getCheckStatus() {
			return checkStatus;
		}
		/**
		 * 设置：
		 */
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		/**
		 * 获取：
		 */
		public Date getUpdateTime() {
			return updateTime;
		}
		/**
		 * 设置：发布时间
		 */
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		/**
		 * 获取：发布时间
		 */
		public Date getCreateTime() {
			return createTime;
		}
	
	
	
	
	
}
