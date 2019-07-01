package com.capinfo.wdbl.service;

import java.util.List;

import com.capinfo.wdbl.bean.file_process.FileSearch;


public interface StatService {
	
	public List searchByYear(String year);

	public List searchByMonth(String year,String month);
	
	public List searchByWeek(String year,String week);
	
	public List searchByFormUnit(String year,String month);
	
	public List getCountByRegistrat(String year,String month);
	
	public List getCountByState(String year,String month);
	
	public List getCountByType(String year,String month);
}
