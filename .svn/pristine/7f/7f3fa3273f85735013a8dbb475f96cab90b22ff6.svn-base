package com.capinfo.pub.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubUnitInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "UNITINFO", schema = "WDBL")
public class PubUnitInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -751574348145349680L;
	// Fields
	
	/**
	 * 单位主键
	 */
	private Long id;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 职务
	 */
	private String position;
	/**
	 * 单位名称
	 */
	private String company;
	/**
	 * 联系电话
	 */
	private String telephoneUnit;

	// Constructors

	/** default constructor */
	public PubUnitInfo() {
	}

	/** full constructor */
	public PubUnitInfo(String name, String position, String company,
			String telephoneUnit) {
		this.name = name;
		this.position = position;
		this.company = company;
		this.telephoneUnit = telephoneUnit;
	}

	// Property accessors
	// Property accessors
	@Id
	@GenericGenerator(name = "zbsNameBookId", strategy = "sequence",parameters = {
	@Parameter(name = "sequence", value = "SEQ_UNITINFO") })
	@GeneratedValue(generator = "zbsNameBookId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "position", length = 100)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "company", length = 100)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "telephone_unit", length = 100)
	public String getTelephoneUnit() {
		return this.telephoneUnit;
	}

	public void setTelephoneUnit(String telephoneUnit) {
		this.telephoneUnit = telephoneUnit;
	}

}