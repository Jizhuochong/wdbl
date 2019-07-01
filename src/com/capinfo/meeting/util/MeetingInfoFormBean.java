package com.capinfo.meeting.util;

import java.util.List;

import com.capinfo.meeting.bean.Meeting_Leader;

public class MeetingInfoFormBean {

	private List<Meeting_Leader> updateRecords;
	private List<Meeting_Leader> removeRecords;
	
	
	public MeetingInfoFormBean() {
		super();
	}
	public MeetingInfoFormBean(List<Meeting_Leader> updateRecords,
			List<Meeting_Leader> removeRecords) {
		super();
		this.updateRecords = updateRecords;
		this.removeRecords = removeRecords;
	}
	public List<Meeting_Leader> getUpdateRecords() {
		return updateRecords;
	}
	public void setUpdateRecords(List<Meeting_Leader> updateRecords) {
		this.updateRecords = updateRecords;
	}
	public List<Meeting_Leader> getRemoveRecords() {
		return removeRecords;
	}
	public void setRemoveRecords(List<Meeting_Leader> removeRecords) {
		this.removeRecords = removeRecords;
	}
	
}
