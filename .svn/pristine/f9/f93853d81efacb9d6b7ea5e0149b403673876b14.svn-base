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
@Table(name="history_meetingleader")
@SequenceGenerator(name="seq_history_meetingleader",sequenceName="seq_history_meetingleader")
public class History_MeetingLeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7469981762832967753L;
	private Long id; // 主键
	private History_Meeting meeting;
	private String leaderName; // 领导名字
	private String followPerson; // 随行人员
	private String roadMap; // 乘车路线
	private String carNum; // 车证
	private String contents; // 会议内容
	private String clothes; // 着装

	public History_MeetingLeader() {
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "meId")
	public History_Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(History_Meeting meeting) {
		this.meeting = meeting;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_history_meetingleader")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getFollowPerson() {
		return followPerson;
	}

	public void setFollowPerson(String followPerson) {
		this.followPerson = followPerson;
	}

	public String getRoadMap() {
		return roadMap;
	}

	public void setRoadMap(String roadMap) {
		this.roadMap = roadMap;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getClothes() {
		return clothes;
	}

	public void setClothes(String clothes) {
		this.clothes = clothes;
	}

	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof History_MeetingLeader) {
			final History_MeetingLeader obj = (History_MeetingLeader) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}

}
