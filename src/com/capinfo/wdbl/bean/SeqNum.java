package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity(name = "FILE_SEQ_NUM")
public class SeqNum implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 590422887717616828L;
	
	private Long id;
	
	private String seqType;
	
	private String year;
	
	private Long seqNum;

	/* 自增长序列 */
	@GenericGenerator(name = "numSeqGen", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_SEQ_NUM") })
	@GeneratedValue(generator = "numSeqGen")
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="SEQ_TYPE")
	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	@Column(name="SEQ_YEAR")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name="SEQ_NUM")
	public Long getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}
	
}