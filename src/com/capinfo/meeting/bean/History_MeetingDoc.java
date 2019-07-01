package com.capinfo.meeting.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="history_meetingdoc")
@SequenceGenerator(name="seq_history_meetingdoc",sequenceName="seq_history_meetingdoc")
public class History_MeetingDoc implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2307402637989500801L;
	private Long id;//主键
	private History_Meeting meeting;
	private String docName;//文档名称
	private String docUrl;//文档存放路径
	private Long dateCenterId;//数据中心id
	
	public History_MeetingDoc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="seq_history_meetingdoc")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="meId")
	public History_Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(History_Meeting meeting) {
		this.meeting = meeting;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public Long getDateCenterId() {
		return dateCenterId;
	}

	public void setDateCenterId(Long dateCenterId) {
		this.dateCenterId = dateCenterId;
	}

	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof History_MeetingDoc) {
			final History_MeetingDoc obj = (History_MeetingDoc) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}

}
