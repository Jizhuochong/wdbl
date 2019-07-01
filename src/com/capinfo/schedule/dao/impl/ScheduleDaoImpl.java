package com.capinfo.schedule.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.capinfo.schedule.bean.ScheduleConfig;
import com.capinfo.schedule.dao.ScheduleDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

/**
 * @author Zhendong
 *
 */
@Repository("scheduleDao")
public class ScheduleDaoImpl extends GenericDAOImpl<ScheduleConfig,Long> implements ScheduleDao {
	private static final Log log = LogFactory.getLog(ScheduleDaoImpl.class);
	
	public ScheduleDaoImpl() {
		log.debug("ScheduleDao has been initialized");
	}
}
