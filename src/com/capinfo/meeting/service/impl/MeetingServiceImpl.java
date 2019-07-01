package com.capinfo.meeting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capinfo.base.service.impl.BaseServiceImpl;
import com.capinfo.meeting.bean.Meeting_Time;
import com.capinfo.meeting.dao.MeetingDao;
import com.capinfo.meeting.service.MeetingService;

@Service("meetingService")
public class MeetingServiceImpl extends BaseServiceImpl implements
		MeetingService {

	@Autowired
	private MeetingDao meetingDao;
	
	public void deleteByIds(Long[] ids){
		this.meetingDao.deleteByIds(ids);
	}
	
	public List<Meeting_Time> findMeetings(String date) {
		return meetingDao.findMeetings(date);
	}
}
