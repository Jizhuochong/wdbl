package com.capinfo.pub.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 大范围分发表-分发单位表
 */
@Entity
@Table(name = "PublishedOrg", schema = "WDBL")
@SequenceGenerator(name = "SEQ_PublishedOrg", sequenceName = "SEQ_PublishedOrg")
public class PublishedOrg implements Serializable {
	private static final long serialVersionUID = 5859918146580733945L;
	private Long id;
	private Long largeRangePublishedId;	/*大范围分发表主键*/	
	private Long orgId;					/*单位主键*/	
	private String orgCode;				/*单位代码*/	
	private String orgName;				/*单位名称*/	
	private String pages;				/*份数*/
	private String fileNum_begin;		/*序号范围-开始*/
	private String fileNum_end;			/*序号范围-结束*/
	private String state;				/*状态*/
	private Date send_time;				/*发送时间*/
	private Date back_time;				/*回收时间*/
	private String handle_operator;		/*经办人*/
	private String handle_unit;			/*经办单位*/
	private Date handle_time;			/*办理时间*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PublishedOrg")
	@Column(name = "id", length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLargeRangePublishedId() {
		return largeRangePublishedId;
	}
	public void setLargeRangePublishedId(Long largeRangePublishedId) {
		this.largeRangePublishedId = largeRangePublishedId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getFileNum_begin() {
		return fileNum_begin;
	}
	public void setFileNum_begin(String fileNum_begin) {
		this.fileNum_begin = fileNum_begin;
	}
	public String getFileNum_end() {
		return fileNum_end;
	}
	public void setFileNum_end(String fileNum_end) {
		this.fileNum_end = fileNum_end;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public Date getBack_time() {
		return back_time;
	}
	public void setBack_time(Date back_time) {
		this.back_time = back_time;
	}
	public String getHandle_operator() {
		return handle_operator;
	}
	public void setHandle_operator(String handle_operator) {
		this.handle_operator = handle_operator;
	}
	public String getHandle_unit() {
		return handle_unit;
	}
	public void setHandle_unit(String handle_unit) {
		this.handle_unit = handle_unit;
	}
	public Date getHandle_time() {
		return handle_time;
	}
	public void setHandle_time(Date handle_time) {
		this.handle_time = handle_time;
	}

	

}