package com.capinfo.wdbl.dao;

import java.util.List;
import java.util.Map;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface StatDao  extends GenericDAO {
	
	public List<Map<String,Object>> getCountByYear(String year);
	
	public List<Map<String,Object>> getCountByMonth(String year,String month);
	
	public List<Map<String,Object>> getCountByWeek(String year,String week);
	
	public List<Map<String,Object>> getCountByFormUnit(String year,String month);
	
	public List getCountByRegistrat(String year,String month);
	
	public List getCountByState(String year,String month);
	
	public List getCountByType(String year,String month);
	
}
