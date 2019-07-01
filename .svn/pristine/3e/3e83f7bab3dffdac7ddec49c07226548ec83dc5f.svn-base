package com.capinfo.meeting.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.capinfo.base.dao.impl.BaseDaoImpl;
import com.capinfo.meeting.bean.Meeting_Time;
import com.capinfo.meeting.dao.MeetingDao;

@Repository("meetingDao")
public class MeetingDaoImpl extends BaseDaoImpl implements MeetingDao {

	public void deleteByIds(Long[] ids){
		if(ids!=null){
			Session session=this.getSessionFactory().openSession();
			Transaction tran=session.getTransaction();
			StringBuffer sqlBuffer=new StringBuffer("update Meeting m set m.oprstatus=3,m.isDeleted=1 where 1=2");
			for (Long id : ids) {
				sqlBuffer.append(" or id=").append(id);
			}
			tran.begin();
			session.createQuery(sqlBuffer.toString()).executeUpdate();
			tran.commit();
			if (session.isOpen()) {
				session.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Meeting_Time> findMeetings(final String date) {
		Session session = this.getSessionFactory().getCurrentSession();
//		String sql="from Meeting_Time mt where mt.meStartTime like '%"+date+
//			"%' or mt.meEndTime like '%"+date+"%'" ;
		String start=date+" 24:00";
		String end=date+" 00:00";
		StringBuffer sqlBuffer=new StringBuffer("from Meeting_Time mt where mt.meeting.isDeleted=0 and (mt.meStartTime <= '");
		sqlBuffer.append(start).append("' and mt.meEndTime >= '").append(end).append("') order by mt.meStartTime asc");
		List<Meeting_Time> lists=session.createQuery(sqlBuffer.toString()).list();
		for (Meeting_Time meetingTime : lists) {
			meetingTime.setMeeting(null);
//			meetingTime.getMeeting().setDocList(null);
//			meetingTime.getMeeting().setLeaderList(null);
//			meetingTime.getMeeting().setTimeList(null);
//			meetingTime.getMeeting().setPersons(null);
		}
		return lists;
	}
}
