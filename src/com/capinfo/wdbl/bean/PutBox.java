package com.capinfo.wdbl.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *投箱信息
 */
@Entity
@Table(name = "PutBox", schema = "WDBL")
@SequenceGenerator(name = "SEQ_PutBox", sequenceName = "SEQ_PutBox")
public class PutBox implements Serializable {
	private static final long serialVersionUID = -3591406314196322618L;
	private Long id;
	private String fileSeqId;			/*文件流水号*/
	private String file_QRCode;			/*文件二维码信息*/
	private String pages;				/*份数*/
	private String leader;				/*呈送领导*/
	private Date send_time;				/*呈送时间*/
	private String leader_opinion;		/*是否批示*/
	private String process_state;		/*流程状态*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PutBox")
	@Column(name = "id", length = 30)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileSeqId() {
		return fileSeqId;
	}
	public void setFileSeqId(String fileSeqId) {
		this.fileSeqId = fileSeqId;
	}
	public String getFile_QRCode() {
		return file_QRCode;
	}
	public void setFile_QRCode(String file_QRCode) {
		this.file_QRCode = file_QRCode;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public String getLeader_opinion() {
		return leader_opinion;
	}
	public void setLeader_opinion(String leader_opinion) {
		this.leader_opinion = leader_opinion;
	}
	public String getProcess_state() {
		return process_state;
	}
	public void setProcess_state(String process_state) {
		this.process_state = process_state;
	}

	

}