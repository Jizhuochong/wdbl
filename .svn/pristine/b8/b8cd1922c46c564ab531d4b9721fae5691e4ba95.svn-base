package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity(name="SN")
public class Sn  implements Serializable{
	//properties
	private static final long serialVersionUID = -8136160648731070689L;
	/**
	 * 编号（自增长序列）
	 */
	@GenericGenerator(name = "snIdGen", strategy = "sequence",
			  parameters = { @Parameter(name = "sequence", value = "SEQ_SN_ID") })
	@GeneratedValue(generator = "snIdGen")
	@Id
	private Long id;
	/**
	 * 流水号
	 */
	private String sn;
	

	//gettes and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
