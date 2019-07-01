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
@Table(name="meeting_person")
@SequenceGenerator(name="seq_meeting_person",sequenceName="seq_meeting_person")
public class Meeting_Person implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -171816745815181836L;
	private Long id; // 主键
	private Meeting meeting;
	private String name; // 名字
	private String unit; // 单位
	private String job; // 职务
	private String phone; // 联系电话
	private String address; // 家庭住址	

	public Meeting_Person() {		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="seq_meeting_person")
	public Long getId() {
		return id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof Meeting_Person) {
			final Meeting_Person obj = (Meeting_Person) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}

}
