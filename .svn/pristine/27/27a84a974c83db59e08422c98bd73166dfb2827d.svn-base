package com.capinfo.pub.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * PubFileCaseInfo entity. @author MyEclipse Persistence Tools
 * 文件办理典型案例
 */
@Entity
@Table(name = "PUB_FILE_CASE_INFO", schema = "WDBL")
public class PubFileCaseInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2252836743327993358L;
	private Long id;
	private String caseName;//案例名称
	private String caseDiscription;//案例描述
	private String caseFileUrl;//案例文件
	private String handleResult;//处理结果
	private String handlePeople;//处理人
	private Date handleDate;//处理时间

	// Constructors

	/** default constructor */
	public PubFileCaseInfo() {
	}

	/** full constructor */
	public PubFileCaseInfo(String caseName, String caseDiscription,
			String caseFileUrl, String handleResult, String handlePeople,
			Date handleDate) {
		this.caseName = caseName;
		this.caseDiscription = caseDiscription;
		this.caseFileUrl = caseFileUrl;
		this.handleResult = handleResult;
		this.handlePeople = handlePeople;
		this.handleDate = handleDate;
	}

	@Id
	@GenericGenerator(name = "fileCaseId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_FILECASEINFO") })
	@GeneratedValue(generator = "fileCaseId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CASE_NAME", length = 80)
	public String getCaseName() {
		return this.caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	@Column(name = "CASE_DISCRIPTION", length = 200)
	public String getCaseDiscription() {
		return this.caseDiscription;
	}

	public void setCaseDiscription(String caseDiscription) {
		this.caseDiscription = caseDiscription;
	}

	@Column(name = "CASE_FILE_URL", length = 80)
	public String getCaseFileUrl() {
		return this.caseFileUrl;
	}

	public void setCaseFileUrl(String caseFileUrl) {
		this.caseFileUrl = caseFileUrl;
	}

	@Column(name = "HANDLE_RESULT", length = 60)
	public String getHandleResult() {
		return this.handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	@Column(name = "HANDLE_PEOPLE", length = 10)
	public String getHandlePeople() {
		return this.handlePeople;
	}

	public void setHandlePeople(String handlePeople) {
		this.handlePeople = handlePeople;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "HANDLE_DATE")
	public Date getHandleDate() {
		return this.handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

}