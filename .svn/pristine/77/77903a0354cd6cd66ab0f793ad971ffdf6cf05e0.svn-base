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
 * 领导批示表
 */
@Entity
@Table(name = "File_Leader", schema = "WDBL")
public class File_Leader implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -427949102298084992L;
	private Long id;
	private FileReg fileReg;  //文件登记
	private String leader;   //批示领导
	private String leaderType;  //批示方式
	private String leaderOpinion;  //批示意见
	private String leaderRemarop;//备注
	private String recorder;  //录入人
	private String recordDate;  //录入时间
	
	public File_Leader(){
	}
	public File_Leader(Long id){
		this.id = id;
	}
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "fileLeaderIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_FILE_LEADER_ID") })
    @GeneratedValue(generator = "fileLeaderIdGen")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ForeignKey(name="FK_FILE_REG_ID_OF_LEADER")
	@JoinColumn(name = "FILE_REG_ID")
	@ManyToOne
	@JsonIgnore
	public FileReg getFileReg() {
		return fileReg;
	}
	public void setFileReg(FileReg fileReg) {
		this.fileReg = fileReg;
	}
	@Column(length = 100)
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getLeader() {
		return leader;
	}
	@Column(length = 50)
	public void setLeaderType(String leaderType) {
		this.leaderType = leaderType;
	}
	public String getLeaderType() {
		return leaderType;
	}
	@Column(length = 500)
	public void setLeaderOpinion(String leaderOpinion) {
		this.leaderOpinion = leaderOpinion;
	}
	public String getLeaderOpinion() {
		return leaderOpinion;
	}
	@Column(length = 500)
	public void setLeaderRemarop(String leaderRemarop) {
		this.leaderRemarop = leaderRemarop;
	}
	public String getLeaderRemarop() {
		return leaderRemarop;
	}
	@Column(length = 50)
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public String getRecorder() {
		return recorder;
	}
	@Column(length = 100)
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getRecordDate() {
		return recordDate;
	}
}
