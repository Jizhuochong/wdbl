package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubZbsNamebook entity. @author MyEclipse Persistence Tools
 * 局属各单位值班室人员名册
 */
@Entity
@Table(name = "PUB_ZBS_NAMEBOOK", schema = "WDBL")
public class PubZbsNamebook implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8465133747888831939L;
	private Long id;
	private PubZbsInfo pubZbsInfo;//局属各单位值班室
	private String name;//姓名
	private String phone;//手机
	private String job;//职务
	private String address;//地址

	// Constructors

	/** default constructor */
	public PubZbsNamebook() {
	}

	/** full constructor */
	public PubZbsNamebook(PubZbsInfo pubZbsinfo, String name, String phone,
			String job, String address) {
		this.pubZbsInfo = pubZbsInfo;
		this.name = name;
		this.phone = phone;
		this.job = job;
		this.address = address;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "zbsNameBookId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_ZBSNAMEBOOK") })
	@GeneratedValue(generator = "zbsNameBookId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ZBSINFO_ID")
	public PubZbsInfo getPubZbsinfo() {
		return this.pubZbsInfo;
	}

	public void setPubZbsinfo(PubZbsInfo pubZbsinfo) {
		this.pubZbsInfo = pubZbsinfo;
	}

	@Column(name = "NAME", length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PHONE", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "JOB", length = 10)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}