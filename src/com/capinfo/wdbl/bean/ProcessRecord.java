package com.capinfo.wdbl.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
/**
 *  注办信息表
 */
@Entity
@Table(name = "ProcessRecord", schema = "WDBL")
public class ProcessRecord implements Serializable, Comparable<ProcessRecord>{
	private static final long serialVersionUID = -4877552766243592733L;
	private Long id;
	private FileReg fileReg;				/*文件登记*/	
	private String handlestatus;			/*办理方式*/
	
	private String name;					/*名称*/	
	private String duty;					/*职务*/
	private String agent;					/*代办人*/
	private String handlecontent;			/*办理内容*/
	private String approvalResult;			/*审批结果*/
	private String approvalOp;				/*审批意见*/
	private String remarkOp;				/*审批备注意见*/
	private String jishouren;				/*传真接收人*/
	private String psfk;					/*批示反馈*/
	
	private String sys_user;				/*系统用户*/
	private String sys_date;				/*系统时间*/
	
	
	public ProcessRecord(){
		
	}
	public ProcessRecord(Long id){
		this.id = id;
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_ProcessRecord", sequenceName = "SEQ_ProcessRecord")
	@GeneratedValue(generator = "SEQ_ProcessRecord")
	@Column(name = "id", length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ForeignKey(name="FK_FILE_REG_ID_OF_PROCESS")
	@JoinColumn(name = "FILE_REG_ID")
	@ManyToOne
	@JsonIgnore
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
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getHandlecontent() {
		return handlecontent;
	}
	public void setHandlecontent(String handlecontent) {
		this.handlecontent = handlecontent;
	}
	public String getApprovalOp() {
		return approvalOp;
	}
	public void setApprovalOp(String approvalOp) {
		this.approvalOp = approvalOp;
	}
	public String getSys_user() {
		return sys_user;
	}
	public void setSys_user(String sys_user) {
		this.sys_user = sys_user;
	}
	public String getSys_date() {
		return sys_date;
	}
	public void setSys_date(String sys_date) {
		this.sys_date = sys_date;
	}
	public String getHandlestatus() {
		return handlestatus;
	}
	public void setHandlestatus(String handlestatus) {
		this.handlestatus = handlestatus;
	}
	public String getRemarkOp() {
		return remarkOp;
	}
	public void setRemarkOp(String remarkOp) {
		this.remarkOp = remarkOp;
	}
	public String getApprovalResult() {
		return approvalResult;
	}
	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	public String getJishouren() {
		return jishouren;
	}
	public void setJishouren(String jishouren) {
		this.jishouren = jishouren;
	}
	@Override
	public int compareTo(ProcessRecord o) {
		return this.getId().compareTo(o.getId());
	}
	public String getPsfk() {
		return psfk;
	}
	public void setPsfk(String psfk) {
		this.psfk = psfk;
	}

}