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
@Table(name="meeting_doc")
@SequenceGenerator(name="seq_meeting_doc",sequenceName="seq_meeting_doc")
public class Meeting_Doc implements Serializable {

	private static final long serialVersionUID = -2562359447578410376L;
	private Long id;//主键
	private Meeting meeting;
	private String docName;//文档名称
	private String docUrl;//文档存放路径
	private String dateCenterId;//数据中心id

	public Meeting_Doc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="seq_meeting_doc")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="meId")
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
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

	public String getDateCenterId() {
		return dateCenterId;
	}

	public void setDateCenterId(String dateCenterId) {
		this.dateCenterId = dateCenterId;
	}

	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof Meeting_Doc) {
			final Meeting_Doc obj = (Meeting_Doc) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}

}
