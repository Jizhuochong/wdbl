package com.capinfo.util;

import com.capinfo.meeting.bean.Meeting;
import com.capinfo.meeting.bean.Meeting_Time;

public class WeekTimeBean {
	private Long id; // 主键
	private Meeting meeting;
	private String meStartTime; // 会议开始时间
	private String meEndTime; // 会议结束时间
	private Integer day;
	
	public WeekTimeBean() {
	}
	
	public WeekTimeBean(Meeting_Time time,Integer day) {
		this.id=time.getId();
		this.meeting=time.getMeeting();
		this.meAddress=time.getMeAddress();
		this.meEndTime=time.getMeEndTime();
		this.meStartTime=time.getMeStartTime();
		this.day=day;
	}
	
	public WeekTimeBean(Long id, Meeting meeting, String meStartTime,
			String meEndTime, Integer day, String meAddress) {
		super();
		this.id = id;
		this.meeting = meeting;
		this.meStartTime = meStartTime;
		this.meEndTime = meEndTime;
		this.day = day;
		this.meAddress = meAddress;
	}
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Meeting getMeeting() {
		return meeting;
	}
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
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
	private String meAddress; // 会议地点
}
