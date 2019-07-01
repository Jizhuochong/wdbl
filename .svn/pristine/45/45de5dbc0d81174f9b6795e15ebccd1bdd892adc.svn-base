package com.capinfo.wdbl.bean.file_process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.capinfo.wdbl.bean.FileReg;
/**
 * 呈送领导表
 */
@Entity
@Table(name = "File_Handle", schema = "WDBL")
public class File_Handle implements Serializable {
	private static final long serialVersionUID = -6581805656291014528L;
	private Long id;
	private FileReg fileReg;						/*文件登记*/
	private String sendtype;						/*送阅类型*/
	
	private String officeLeader_start;				/*拟办领导-开始词*/
	private String officeLeader_org;				/*拟办领导-部门*/	
	private String officeLeader_name;				/*拟办领导领导-名称*/	
	private String officeLeader_job;				/*拟办领导领导-职务*/
	private String officeLeader_action;				/*拟办领导领导-行为*/
	
	private String submitLeader;					/*呈送领导*/
	
	private String draft_op;						/*拟办意见*/	
	private String draft_remarkop;					/*拟办备注意见*/	
	private String draft_person;					/*拟办人*/
	private String draft_date;						/*拟办时间*/	
		 
	
	public File_Handle(){
	}
	public File_Handle(Long id){
		this.id = id;
	}
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "fileHandleIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_HANDLE_ID") })
    @GeneratedValue(generator = "fileHandleIdGen")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ForeignKey(name="FK_FILE_REG_ID_OF_HANDLE")
	@JoinColumn(name = "FILE_REG_ID")
	@ManyToOne
	@JsonIgnore
	public FileReg getFileReg() {
		return fileReg;
	}
	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}
	
	@Column(length = 80)
	public String getDraft_op() {
		return draft_op;
	}
	public void setDraft_op(String draft_op) {
		this.draft_op = draft_op;
	}
	@Column(length = 20)
	public String getDraft_person() {
		return draft_person;
	}
	public void setDraft_person(String draft_person) {
		this.draft_person = draft_person;
	}
	public String getDraft_date() {
		return draft_date;
	}
	public void setDraft_date(String draft_date) {
		this.draft_date = draft_date;
	}
	@Column(length = 200)
	public String getOfficeLeader_org() {
		return officeLeader_org;
	}
	public void setOfficeLeader_org(String officeLeader_org) {
		this.officeLeader_org = officeLeader_org;
	}
	@Column(length = 20)
	public String getOfficeLeader_name() {
		return officeLeader_name;
	}
	public void setOfficeLeader_name(String officeLeader_name) {
		this.officeLeader_name = officeLeader_name;
	}
	@Column(length = 20)
	public String getOfficeLeader_job() {
		return officeLeader_job;
	}
	public void setOfficeLeader_job(String officeLeader_job) {
		this.officeLeader_job = officeLeader_job;
	}
	
	@Column(length = 20)
	public String getOfficeLeader_action() {
		return officeLeader_action;
	}
	public void setOfficeLeader_action(String officeLeader_action) {
		this.officeLeader_action = officeLeader_action;
	}
	@Column(length = 200)
	public String getDraft_remarkop() {
		return draft_remarkop;
	}
	public void setDraft_remarkop(String draft_remarkop) {
		this.draft_remarkop = draft_remarkop;
	}
	@Column(length = 20)
	public String getOfficeLeader_start() {
		return officeLeader_start;
	}
	public void setOfficeLeader_start(String officeLeader_start) {
		this.officeLeader_start = officeLeader_start;
	}
	
	@Column(length = 20)
	public String getSendtype() {
		return sendtype;
	}
	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	@Column(length = 200)
	public String getSubmitLeader() {
		return submitLeader;
	}
	public void setSubmitLeader(String submitLeader) {
		this.submitLeader = submitLeader;
	}
	

	
	

}