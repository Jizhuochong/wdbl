/**
 * 
 */
package com.capinfo.schedule.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.schedule.bean.ScheduleConfig;
import com.capinfo.schedule.dao.ScheduleDao;
import com.capinfo.schedule.service.ScheduleService;
import com.capinfo.wdbl.bean.FileReg;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

/**
 * @author Zhendong
 *
 */
@Service("scheduleService")
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {
	
	private static final Log log = LogFactory.getLog(ScheduleServiceImpl.class);
	
	@Autowired
	private ScheduleDao scheduleDao;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;

	@Override
	public SearchResult<ScheduleConfig> getConfigs(ISearch search) {
		return scheduleDao.searchAndCount(search);
	}
	
	@PostConstruct
	public void init() {
		// 启动时，从数据库读取配置参数，替代默认trigger
		this.updateTask();
	}

	@Override
	public void updateTask() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(
					"cronTrigger", Scheduler.DEFAULT_GROUP);
			String originConExpression = trigger.getCronExpression();
			// 以上两句代码我没有用到,这个是可以获得现在定时器调度时间，可以用来和数据库的时间比较，如果一样，就不用调用了，我省了这步
			// 从数据库获得配置时间
			String cronExpression = getCronExceptionDB();
			// 以下就是重新对时间的设置，这样就可以达到动态设置效果。
			if (originConExpression != null
					&& !originConExpression.equals(cronExpression)) {
				trigger.setCronExpression(cronExpression);
				scheduler.rescheduleJob("cronTrigger", Scheduler.DEFAULT_GROUP,
						trigger);
			}
			log.debug("自动办结定时器已更新");
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pause() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.pauseAll();
			log.debug("自动办结定时器已暂停");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.start();
			log.debug("自动办结定时器已启动");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getCronExceptionDB() {
		final String sqlQueryA = "SELECT SC.EXECUTE_CRON FROM SCHEDULE_CONFIG SC";
		HibernateCallback<Map<String, String>> action = new HibernateCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public Map<String, String> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				q = session.createSQLQuery(sqlQueryA);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setFirstResult(0);
				q.setMaxResults(1);
				return (Map<String, String>) q.uniqueResult();
			}
		};
		Map<String, String> result = hibernateTemplate.execute(action);
		return result.get("EXECUTE_CRON");
	}
	
	private String getAutoDaysDB() {
		final String sqlQueryA = "SELECT SC.AUTOFINISH_DAYS FROM SCHEDULE_CONFIG SC";
		HibernateCallback<Map<String, String>> action = new HibernateCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public Map<String, String> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				q = session.createSQLQuery(sqlQueryA);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setFirstResult(0);
				q.setMaxResults(1);
				return (Map<String, String>) q.uniqueResult();
			}
		};
		Map<String, String> result = hibernateTemplate.execute(action);
		return String.valueOf(result.get("AUTOFINISH_DAYS"));
	}
	
	@Override
	public Map<String, String> getConfigDB() {
		final String sqlQueryA = "SELECT SC.AUTOFINISH_DAYS, SC.ISACTIVE, SC.EXECUTE_DATE, SC.EXECUTE_CRON FROM SCHEDULE_CONFIG SC";
		HibernateCallback<Map<String, String>> action = new HibernateCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public Map<String, String> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				q = session.createSQLQuery(sqlQueryA);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setFirstResult(0);
				q.setMaxResults(1);
				return (Map<String, String>) q.uniqueResult();
			}
		};
		Map<String, String> result = hibernateTemplate.execute(action);
		return result;
	}

	@Override
	public List<FileReg> getAutoFinishRecords() {
		int _days = 3;
		try {
			String _tmp = this.getAutoDaysDB();
			_days = Integer.valueOf(_tmp);
		} catch (Exception e) {
			log.debug("获取自动办结期限异常" + e.getMessage());
			log.error("获取自动办结期限异常" + e.getMessage());
		}
		
		_days = _days - _days * 2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String _fromDate = sdf.format(DateUtils.addDays(new Date(), _days));
		
		String hql = "from FILE_REG where handlestatus <> '办结' and handlestatus <> '系统办结' and deadline <= '"+ _fromDate +"' and instr(barNo, 'A') > 3";
		
		@SuppressWarnings("unchecked")
		List<FileReg> scList = hibernateTemplate.find(hql);
		return scList;
	}

}