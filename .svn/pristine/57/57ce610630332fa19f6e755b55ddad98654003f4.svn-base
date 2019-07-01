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

/**
 * 会议时间表实体
 * @author LMZ
 *
 */
@Entity
@Table(name="history_meetingTime")
@SequenceGenerator(name="seq_histoy_meetingtime",sequenceName="seq_histoy_meetingtime")
public class History_MeetingTime implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5808290232714056364L;
	private Long id;			//主键
	private History_Meeting meeting;
	private String meStartTime;			//会议开始时间
	private String meEndTime;			//会议结束时间
	private String meAddress;			//会议地点

	public History_MeetingTime() {
	}

	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="meId")
	public History_Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(History_Meeting meeting) {
		this.meeting = meeting;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="seq_histoy_meetingtime")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeStartTime() {
		return meStartTime;
	}

	public void setMeStartTime(String meStartTime) {
		this.meStartTime = meStartTime;
	}

	public String getMeEndTime() {
		return meEndTime;
	}

	public void setMeEndTime(String meEndTime) {
		this.meEndTime = meEndTime;
	}

	public String getMeAddress() {
		return meAddress;
	}

	public void setMeAddress(String meAddress) {
		this.meAddress = meAddress;
	}

	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof History_MeetingTime) {
			final History_MeetingTime obj = (History_MeetingTime) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}

}
