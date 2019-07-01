package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *  转送业务单位/领导 信息
 */
@Entity
@Table(name = "TransferInfo", schema = "WDBL")
public class TransferInfo implements Serializable {
	private static final long serialVersionUID = -4341661996465385148L;
	private Long id;
	private FileReg fileReg;				/*文件登记*/	
	private String name;					/*名称*/	
	private String sendtime;				/*发送时间*/
	private String backtime;				/*回收时间*/
	private String sendenter;				/*发送录入人*/
	private String sendsysuser;				/*发送系统用户*/
	private String sendsysdate;				/*发送系统时间*/
	private String backenter;				/*回收录入人*/
	private String backsysuser;				/*回收系统用户*/
	private String backsysdate;				/*回收系统时间*/
	
	@Id
	@SequenceGenerator(name = "SEQ_TransferInfo", sequenceName = "SEQ_TransferInfo")
	@GeneratedValue(generator = "SEQ_TransferInfo")
	@Column(name = "id", length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "fileRegId")
	public FileReg getFileReg() {
		return fileReg;
	}
	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getBacktime() {
		return backtime;
	}
	public void setBacktime(String backtime) {
		this.backtime = backtime;
	}
	public String getSendenter() {
		return sendenter;
	}
	public void setSendenter(String sendenter) {
		this.sendenter = sendenter;
	}
	public String getSendsysuser() {
		return sendsysuser;
	}
	public void setSendsysuser(String sendsysuser) {
		this.sendsysuser = sendsysuser;
	}
	public String getSendsysdate() {
		return sendsysdate;
	}
	public void setSendsysdate(String sendsysdate) {
		this.sendsysdate = sendsysdate;
	}
	public String getBackenter() {
		return backenter;
	}
	public void setBackenter(String backenter) {
		this.backenter = backenter;
	}
	public String getBacksysuser() {
		return backsysuser;
	}
	public void setBacksysuser(String backsysuser) {
		this.backsysuser = backsysuser;
	}
	public String getBacksysdate() {
		return backsysdate;
	}
	public void setBacksysdate(String backsysdate) {
		this.backsysdate = backsysdate;
	}

	

}