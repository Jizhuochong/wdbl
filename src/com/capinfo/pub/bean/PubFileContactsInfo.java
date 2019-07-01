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
 * PubFileContactsInfo entity. @author MyEclipse Persistence Tools
 * 来文联系人信息
 */
@Entity
@Table(name = "PUB_FILE_CONTACTS_INFO", schema = "WDBL")
public class PubFileContactsInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1134148090827368237L;
	private Long id;
	private String name;
	private String unit;
	private String unitCall;
	private String phone;
	private String job;
	private String address;

	// Constructors

	/** default constructor */
	public PubFileContactsInfo() {
	}

	/** full constructor */
	public PubFileContactsInfo(String name, String unit, String unitCall,
			String phone, String job, String address) {
		this.name = name;
		this.unit = unit;
		this.unitCall = unitCall;
		this.phone = phone;
		this.job = job;
		this.address = address;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "fileContactsId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_FILECONTACTSINFO") })
	@GeneratedValue(generator = "fileContactsId")
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

	@Column(name = "UNIT", length = 200)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "UNIT_CALL", length = 20)
	public String getUnitCall() {
		return this.unitCall;
	}

	public void setUnitCall(String unitCall) {
		this.unitCall = unitCall;
	}

	@Column(name = "PHONE", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "JOB", length = 20)
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}