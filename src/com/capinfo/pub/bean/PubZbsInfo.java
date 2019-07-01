package com.capinfo.pub.bean;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * PubZbsinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PUB_ZBSINFO", schema = "WDBL")
public class PubZbsInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8322131218523152694L;
	// Fields

	private Long id;
	private String name;
	private String unitCall;
	private Set<PubZbsNamebook> pubZbsNamebooks = new HashSet<PubZbsNamebook>(0);

	// Constructors

	/** default constructor */
	public PubZbsInfo() {
	}

	/** full constructor */
	public PubZbsInfo(String name, String unitCall,
			Set<PubZbsNamebook> pubZbsNamebooks) {
		this.name = name;
		this.unitCall = unitCall;
		this.pubZbsNamebooks = pubZbsNamebooks;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "zbsId", strategy = "sequence",parameters = {
			@Parameter(name = "sequence", value = "SEQ_PUB_FILECASEINFO") })
	@GeneratedValue(generator = "zbsId")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 100)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pubZbsinfo")
	public Set<PubZbsNamebook> getPubZbsNamebooks() {
		return this.pubZbsNamebooks;
	}

	public void setPubZbsNamebooks(Set<PubZbsNamebook> pubZbsNamebooks) {
		this.pubZbsNamebooks = pubZbsNamebooks;
	}

}