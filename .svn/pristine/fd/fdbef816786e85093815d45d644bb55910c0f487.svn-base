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
@Table(name="meeting_leader")
@SequenceGenerator(name="seq_meeting_leader",sequenceName="seq_meeting_leader")
public class Meeting_Leader implements Serializable {

	private static final long serialVersionUID = 4315239104315299662L;
	private Long id; // 主键
	private Meeting meeting;
	private String leaderName; // 领导名字
	private String followPerson; // 随行人员
	private String jionTime;
	private Boolean isJoin;
	private String roadMap; // 乘车路线
	private String carNum; // 车证
	private String contents; // 会议内容
	private String clothes; // 着装

	public Meeting_Leader() {
	}
	
	

	public Meeting_Leader(Meeting meeting, String leaderName,
			String followPerson, String jionTime, Boolean isJoin,
			String roadMap, String carNum, String contents, String clothes) {
		super();
		this.meeting = meeting;
		this.leaderName = leaderName;
		this.followPerson = followPerson;
		this.jionTime = jionTime;
		this.isJoin = isJoin;
		this.roadMap = roadMap;
		this.carNum = carNum;
		this.contents = contents;
		this.clothes = clothes;
	}



	public Meeting_Leader(Long id, Meeting meeting, String leaderName,
			String followPerson, String jionTime, Boolean isJoin,
			String roadMap, String carNum, String contents, String clothes) {
		super();
		this.id = id;
		this.meeting = meeting;
		this.leaderName = leaderName;
		this.followPerson = followPerson;
		this.jionTime = jionTime;
		this.isJoin = isJoin;
		this.roadMap = roadMap;
		this.carNum = carNum;
		this.contents = contents;
		this.clothes = clothes;
	}


	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "meId")
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_meeting_leader")
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

	public String getJionTime() {
		return jionTime;
	}

	public void setJionTime(String jionTime) {
		this.jionTime = jionTime;
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

	public Boolean getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(Boolean isJoin) {
		this.isJoin = isJoin;
	}

	public int hashCode() {
		return (this.id == null) ? 0 : this.id.hashCode();
	}

	public boolean equals(Object object) {
		if (object instanceof Meeting_Leader) {
			final Meeting_Leader obj = (Meeting_Leader) object;
			return (this.id != null) ? this.id.equals(obj.id)
					: (obj.id == null);
		}
		return false;
	}

}
