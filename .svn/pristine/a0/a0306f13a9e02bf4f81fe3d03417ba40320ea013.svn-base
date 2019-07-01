package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubOfficeinfo entity. @author MyEclipse Persistence Tools
 * 办公室信息
 */
@Entity
@Table(name = "PUB_OFFICEINFO", schema = "WDBL")
public class PubOfficeInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5685161092985950241L;
	// Fields

	private Long id;
	private String name;//姓名
	private String job;//职务
	private String cityPhone;//市电
	private String specialPhone;//专电
	private String phone;//手机
	private String address;//住址

	// Constructors

	/** default constructor */
	public PubOfficeInfo() {
	}

	/** full constructor */
	public PubOfficeInfo(String name, String job, String cityPhone,
			String specialPhone, String phone, String address) {
		this.name = name;
		this.job = job;
		this.cityPhone = cityPhone;
		this.specialPhone = specialPhone;
		this.phone = phone;
		this.address = address;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "officeId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_OFFICEINFO") })
	@GeneratedValue(generator = "officeId")
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

	@Column(name = "JOB", length = 20)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "CITY_PHONE", length = 11)
	public String getCityPhone() {
		return this.cityPhone;
	}

	public void setCityPhone(String cityPhone) {
		this.cityPhone = cityPhone;
	}

	@Column(name = "SPECIAL_PHONE", length = 11)
	public String getSpecialPhone() {
		return this.specialPhone;
	}

	public void setSpecialPhone(String specialPhone) {
		this.specialPhone = specialPhone;
	}

	@Column(name = "PHONE", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}