package com.capinfo.meeting.service;

import java.util.List;

import com.capinfo.base.service.BaseService;
import com.capinfo.meeting.bean.Meeting_Time;

public interface MeetingService extends BaseService {

	public void deleteByIds(Long[] ids);

	/****
	 * 通过与会时间查询会议信息
	 * 
	 */
	public List<Meeting_Time> findMeetings(String date);
}
