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
 * PubJunitleaderinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PUB_JUNITLEADERINFO", schema = "WDBL")
public class PubJunitleaderInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3170569599886146796L;
	// Fields

	private Long id;
	private String name;
	private String job;
	private String phone;
	private String unit;
	private String unitCall;
	private String address;

	// Constructors

	/** default constructor */
	public PubJunitleaderInfo() {
	}

	/** full constructor */
	public PubJunitleaderInfo(String name, String job, String phone,
			String unit, String unitCall, String address) {
		this.name = name;
		this.job = job;
		this.phone = phone;
		this.unit = unit;
		this.unitCall = unitCall;
		this.address = address;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "junitleaderId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_JUNITLEADERINFO") })
	@GeneratedValue(generator = "junitleaderId")
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

	@Column(name = "PHONE", length = 11)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}