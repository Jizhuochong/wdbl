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
 * PubWdsYwInfo entity. @author MyEclipse Persistence Tools
 * 文电室业务信息
 */
@Entity
@Table(name = "PUB_WDS_YW_INFO", schema = "WDBL")
public class PubWdsYwInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1166258120635424136L;
	private Long id;
	private String fileName;//文件名称
	private String fileTitle;//文件标题
	private String fileContent;//文件内容
	private String fileUrl;//文件存储url
	private String uploadPeople;//上传人
	private String uploadPeopleunit;//上传人单位
	private Date uploadTime;//上传时间

	// Constructors

	/** default constructor */
	public PubWdsYwInfo() {
	}

	/** full constructor */
	public PubWdsYwInfo(String fileName, String fileTitle, String fileContent,
			String fileUrl, String uploadPeople, String uploadPeopleunit,
			Date uploadTime) {
		this.fileName = fileName;
		this.fileTitle = fileTitle;
		this.fileContent = fileContent;
		this.fileUrl = fileUrl;
		this.uploadPeople = uploadPeople;
		this.uploadPeopleunit = uploadPeopleunit;
		this.uploadTime = uploadTime;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "wdsYwId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_WDSYWINFO") })
	@GeneratedValue(generator = "wdsYwId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FILE_NAME", length = 40)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_TITLE", length = 80)
	public String getFileTitle() {
		return this.fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	@Column(name = "FILE_CONTENT", length = 300)
	public String getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	@Column(name = "FILE_URL", length = 100)
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "UPLOAD_PEOPLE", length = 10)
	public String getUploadPeople() {
		return this.uploadPeople;
	}

	public void setUploadPeople(String uploadPeople) {
		this.uploadPeople = uploadPeople;
	}

	@Column(name = "UPLOAD_PEOPLEUNIT", length = 200)
	public String getUploadPeopleunit() {
		return this.uploadPeopleunit;
	}

	public void setUploadPeopleunit(String uploadPeopleunit) {
		this.uploadPeopleunit = uploadPeopleunit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPLOAD_TIME", length = 7)
	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

}