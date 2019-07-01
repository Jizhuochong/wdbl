package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubJob entity. @author MyEclipse Persistence Tools
 * 办公室信息
 */
@Entity
@Table(name = "PUB_JOB", schema = "WDBL")
public class PubJob implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5685161092985950241L;
	// Fields

	private Long id;
	private String jobName;//职务名称
	private String jobDiscription;//职务描述
	

	// Constructors

	/** default constructor */
	public PubJob() {
	}

	/** full constructor */
	public PubJob(String jobName, String jobDiscription) {
		this.jobName = jobName;
		this.jobDiscription = jobDiscription;
	
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "seqJobId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_JOB") })
	@GeneratedValue(generator = "seqJobId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "JOB_NAME", length = 20)
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	@Column(name = "JOB_DISCRIPTION", length = 200)
	public String getJobDiscription() {
		return jobDiscription;
	}

	public void setJobDiscription(String jobDiscription) {
		this.jobDiscription = jobDiscription;
	}



}