package com.capinfo.wdbl.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.capinfo.wdbl.dao.StatDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository("statDao")
public class StatDaoImpl extends GenericDAOImpl implements StatDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public List<Map<String,Object>> getCountByYear(String year){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_month from(select id,to_char(register_time,'mm') reg_month from FILE_REG WHERE to_char(REGISTER_TIME,'yyyy') = '"+year+"') a group by a.reg_month order by a.reg_month");
		HibernateCallback<List<Map<String,Object>>> action = new HibernateCallback<List<Map<String,Object>>>(){
			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("month", String.valueOf( m[1]));
					map.put("num", Long.valueOf(String.valueOf( m[0])));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};
		return hibernateTemplate.execute(action);
	}
	
	public List<Map<String,Object>> getCountByMonth(String year,String month){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_day from(select id,to_char(register_time,'dd') reg_day from FILE_REG WHERE to_char(REGISTER_TIME,'yyyymm') = '"+year+month+"') a group by a.reg_day order by a.reg_day");
		HibernateCallback<List<Map<String,Object>>> action = new HibernateCallback<List<Map<String,Object>>>(){
			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("day", String.valueOf( m[1]));
					map.put("num", Long.valueOf(String.valueOf( m[0])));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};                                       
		return hibernateTemplate.execute(action);
	}
	
	public List<Map<String,Object>> getCountByWeek(String year,String week){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_day from(select id,to_char(register_time,'d') reg_day from FILE_REG WHERE to_char(REGISTER_TIME,'yyyyWW') = '"+year+week+"') a group by a.reg_day order by a.reg_day");
		HibernateCallback<List<Map<String,Object>>> action = new HibernateCallback<List<Map<String,Object>>>(){
			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("day", String.valueOf( m[1]));
					map.put("num", Long.valueOf(String.valueOf( m[0])));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};
		return hibernateTemplate.execute(action);
	}
	
	public List<Map<String,Object>> getCountByFormUnit(String year,String month){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_day from(SELECT id, FORM_UNIT reg_day FROM FILE_REG WHERE to_char(REGISTER_TIME,'yyyymm') = '"+year+month+"') a group by a.reg_day order by a.reg_day");
		HibernateCallback<List<Map<String,Object>>> action = new HibernateCallback<List<Map<String,Object>>>(){
			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,Object>> rtnList = new ArrayList<Map<String,Object>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("day", String.valueOf( m[1]));
					map.put("num", Long.valueOf(String.valueOf( m[0])));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};
		return hibernateTemplate.execute(action);
	}
	
	public List getCountByRegistrat(String year,String month){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_day from(SELECT id, REGISTRAR reg_day FROM FILE_REG WHERE to_char(REGISTER_TIME,'yyyymm') = '"+year+month+"') a group by a.reg_day order by a.reg_day");
		HibernateCallback<List<Map<String,String>>> action = new HibernateCallback<List<Map<String,String>>>(){
			public List<Map<String,String>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,String>> rtnList = new ArrayList<Map<String,String>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,String> map = new HashMap<String,String>();
					map.put("registrat", String.valueOf( m[1]));
					map.put("num", String.valueOf( m[0]));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};
		return hibernateTemplate.executeFind(action);
	}
	
	public List getCountByState(String year,String month){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_day from(SELECT id, HANDLE_STATUS reg_day FROM FILE_REG WHERE to_char(REGISTER_TIME,'yyyymm') = '"+year+month+"') a group by a.reg_day order by a.reg_day");
		HibernateCallback<List<Map<String,String>>> action = new HibernateCallback<List<Map<String,String>>>(){
			public List<Map<String,String>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,String>> rtnList = new ArrayList<Map<String,String>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,String> map = new HashMap<String,String>();
					map.put("state", String.valueOf( m[1]));
					map.put("num", String.valueOf( m[0]));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};
		return hibernateTemplate.executeFind(action);
	}
	
	public List getCountByType(String year,String month){
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(a.id) num,a.reg_day from(SELECT id, DOC_TYPE reg_day FROM FILE_REG WHERE to_char(REGISTER_TIME,'yyyymm') = '"+year+month+"') a group by a.reg_day order by a.reg_day");
		HibernateCallback<List<Map<String,String>>> action = new HibernateCallback<List<Map<String,String>>>(){
			public List<Map<String,String>> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				List<Map<String,String>> rtnList = new ArrayList<Map<String,String>>();
				List list = query.list();
				for(int i=0;i<list.size();i++){  
					Object[] m = (Object[])list.get(i);  
					Map<String,String> map = new HashMap<String,String>();
					if("1".equals(String.valueOf( m[1]))){
						map.put("type", "市委市政府");
					}
					if("2".equals(String.valueOf( m[1]))){
						map.put("type", "大范围分发");
					}
					if("3".equals(String.valueOf( m[1]))){
						map.put("type", "电话记录");
					}
					if("4".equals(String.valueOf( m[1]))){
						map.put("type", "公安部正式件");
					}
					if("5".equals(String.valueOf( m[1]))){
						map.put("type", "局长批示件");
					}
					if("7".equals(String.valueOf( m[1]))){
						map.put("type", "领导交办件");
					}
					if("8".equals(String.valueOf( m[1]))){
						map.put("type", "领导兼职");
					}
					if("9".equals(String.valueOf( m[1]))){
						map.put("type", "直接转文");
					}
					if("10".equals(String.valueOf( m[1]))){
						map.put("type", "亲启件");
					}
					if("11".equals(String.valueOf( m[1]))){
						map.put("type", "其它");
					}
					if("12".equals(String.valueOf( m[1]))){
						map.put("type", "主要领导文件");
					}
					if("13".equals(String.valueOf( m[1]))){
						map.put("type", "密码电报");
					}
					map.put("num", String.valueOf( m[0]));
					rtnList.add(map);
				} 
				return rtnList;
			}
		};
		return hibernateTemplate.executeFind(action);
	}

}
