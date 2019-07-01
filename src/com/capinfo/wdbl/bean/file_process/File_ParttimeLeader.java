package com.capinfo.wdbl.bean.file_process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.capinfo.wdbl.bean.FileReg;
/**
 * 兼职领导信息
 */
@Entity
@Table(name = "File_ParttimeLeader", schema = "WDBL")
public class File_ParttimeLeader implements Serializable {
	private static final long serialVersionUID = 6386718122597528244L;
	private Long id;
	private FileReg fileReg;					/*文件登记*/
	
	private String leader_name;					/*兼职领导名称*/	
	private String leader_job;					/*兼职领导-职务*/
	
	private String part_job;					/*兼职领导-兼职职务*/
	
	private String leader_opinion;				/*兼职领导-意见 */
	private String leader_Approvaltime;			/*兼职领导-审批时间*/	 
	private String leader_enter;				/*兼职领导-录入人*/ 
	private String leader_entertime;			/*兼职领导-录入时间*/ 	 
	
	public File_ParttimeLeader(){
	}
	public File_ParttimeLeader(Long id){
		this.id = id;
	}
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name="foreignKey", strategy="foreign", parameters=@Parameter(name="property", value="fileReg"))  
    @GeneratedValue(generator="foreignKey")  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public FileReg getFileReg() {
		return fileReg;
	}
	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}
	
	public String getLeader_name() {
		return leader_name;
	}
	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}
	public String getLeader_job() {
		return leader_job;
	}
	public void setLeader_job(String leader_job) {
		this.leader_job = leader_job;
	}
	public String getPart_job() {
		return part_job;
	}
	public void setPart_job(String part_job) {
		this.part_job = part_job;
	}
	public String getLeader_opinion() {
		return leader_opinion;
	}
	public void setLeader_opinion(String leader_opinion) {
		this.leader_opinion = leader_opinion;
	}
	public String getLeader_Approvaltime() {
		return leader_Approvaltime;
	}
	public void setLeader_Approvaltime(String leader_Approvaltime) {
		this.leader_Approvaltime = leader_Approvaltime;
	}
	public String getLeader_enter() {
		return leader_enter;
	}
	public void setLeader_enter(String leader_enter) {
		this.leader_enter = leader_enter;
	}
	public String getLeader_entertime() {
		return leader_entertime;
	}
	public void setLeader_entertime(String leader_entertime) {
		this.leader_entertime = leader_entertime;
	}
	

	
	

}