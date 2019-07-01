package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubProvinceleaderinfo entity. @author MyEclipse Persistence Tools
 * 全国公安省厅领导情况
 */
@Entity
@Table(name = "PUB_PROVINCELEADERINFO", schema = "WDBL")
public class PubProvinceleaderInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3232032977353950054L;
	// Fields

	private Long id;
	private String name;//姓名
	private String unitCall;//单位电话
	private String job;//职务
	private String province;//省份
	private String addree;//地址
	private String phone;//手机

	// Constructors

	/** default constructor */
	public PubProvinceleaderInfo() {
	}

	/** full constructor */
	public PubProvinceleaderInfo(String name, String unitCall, String job,
			String province, String addree, String phone) {
		this.name = name;
		this.unitCall = unitCall;
		this.job = job;
		this.province = province;
		this.addree = addree;
		this.phone = phone;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "provinceleaderId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_PROVINCELEADERINFO") })
	@GeneratedValue(generator = "provinceleaderId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "UNIT_CALL", length = 11)
	public String getUnitCall() {
		return this.unitCall;
	}

	public void setUnitCall(String unitCall) {
		this.unitCall = unitCall;
	}

	@Column(name = "JOB", length = 20)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "PROVINCE", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "ADDREE", length = 200)
	public String getAddree() {
		return this.addree;
	}

	public void setAddree(String addree) {
		this.addree = addree;
	}

	@Column(name = "PHONE", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}