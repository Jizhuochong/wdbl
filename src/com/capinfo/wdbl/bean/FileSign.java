package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *  转送业务单位/领导 信息
 */
@Entity
@Table(name = "FILE_SIGN", schema = "WDBL")
public class FileSign implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1467560010266195457L;
	private Long id;
	private String title;					/*文件标题*/	
	private String docNumber;				/*文号*/
	private String fromUnit;				/*来文单位*/
	private String signUser;				/*签收人*/
	private String backTime;				/*退件时间*/
	private String remark;				    /*备注*/
	private String barNo;                   /*条码编号*/
	
	@Id
	@SequenceGenerator(name = "SeqFileSign", sequenceName = "SEQ_FILE_SIGN")
	@GeneratedValue(generator = "SeqFileSign")
	@Column(name = "id", length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "TITLE",length=2000)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "DOC_NUMBER")
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	@Column(name = "FROM_UNIT")
	public String getFromUnit() {
		return fromUnit;
	}
	public void setFromUnit(String fromUnit) {
		this.fromUnit = fromUnit;
	}
	@Column(name = "SIGN_USER")
	public String getSignUser() {
		return signUser;
	}
	public void setSignUser(String signUser) {
		this.signUser = signUser;
	}
	
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "BAR_NO")
	public String getBarNo() {
		return barNo;
	}
	public void setBarNo(String barNo) {
		this.barNo = barNo;
	}
	
	@Column(name = "BACK_TIME")
	public String getBackTime() {
		return backTime;
	}
	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}
}