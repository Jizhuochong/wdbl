package com.capinfo.wdbl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.wdbl.dao.StatDao;
import com.capinfo.wdbl.service.StatService;
@Service("statService")
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {
	
	@Autowired
	private StatDao statDao;

	public List searchByYear(String year) {
		List<Map<String,Object>> rtnlist = new ArrayList<Map<String,Object>>();
		String oldYear = String.valueOf(Long.valueOf(year)-1);
		List<Map<String,Object>> list = statDao.getCountByYear(year);
		List<Map<String,Object>> oldList = statDao.getCountByYear(oldYear);
		Map<String,Map<String,Object>> nMap = new HashMap<String, Map<String,Object>>();
		for (Map<String, Object> map : list) {
			nMap.put(map.get("month").toString(), map);
		}
		Map<String,Map<String,Object>> oMap = new HashMap<String, Map<String,Object>>();
		for (Map<String, Object> map : oldList) {
			oMap.put(map.get("month").toString(), map);
		}
		for(int i=1;i<=12;i++){
			Map<String,Object> currMap = null;
			String number = String.valueOf(i);
			if(i<10){
				number = "0"+i;
			}
			currMap = nMap.get(String.valueOf(number));
			if(currMap ==null){
				currMap = new HashMap<String,Object>();
				currMap.put("month", number);
				currMap.put("num", 0);
				rtnlist.add(currMap);
			}else{
				currMap =  nMap.get(String.valueOf(number));
				rtnlist.add(currMap);
			}
			Map<String,Object> oldMap = oMap.get(number);
			if(oldMap ==null){
				currMap.put("oldnum", 0);
			}else{
				currMap.put("oldnum", oldMap.get("num"));
			}
		}
		return rtnlist;
	}
	
	public List searchByMonth(String year,String month) {
		List<Map<String,Object>> rtnlist = new ArrayList<Map<String,Object>>();
		String mon = null;
		String newYear = year;
		if(Long.valueOf(month) ==1){
			mon = "12";
			newYear = String.valueOf(Long.valueOf(year) - 1);
		}else{
			String oldMonth = String.valueOf(Long.valueOf(month)-1);
			mon = String.valueOf(oldMonth);
			if(Long.valueOf(oldMonth)<10){
				mon = "0"+oldMonth;
			}
		}
		List<Map<String,Object>> list = statDao.getCountByMonth(year,month);
		List<Map<String,Object>> oldList = statDao.getCountByMonth(newYear,mon);
		Map<String,Map<String,Object>> nMap = new HashMap<String, Map<String,Object>>();
		for (Map<String, Object> map : list) {
			nMap.put(map.get("day").toString(), map);
		}
		Map<String,Map<String,Object>> oMap = new HashMap<String, Map<String,Object>>();
		for (Map<String, Object> map : oldList) {
			oMap.put(map.get("day").toString(), map);
		}
		for(int i=1;i<=31;i++){
			Map<String,Object> currMap = null;
			String number = String.valueOf(i);
			if(i<10){
				number = "0"+i;
			}
			currMap = nMap.get(String.valueOf(number));
			if(currMap ==null){
				currMap = new HashMap<String,Object>();
				currMap.put("day", number);
				currMap.put("num", 0);
				rtnlist.add(currMap);
			}else{
				currMap =  nMap.get(String.valueOf(number));
				rtnlist.add(currMap);
			}
			Map<String,Object> oldMap = oMap.get(number);
			if(oldMap ==null){
				currMap.put("oldnum", 0);
			}else{
				currMap.put("oldnum", oldMap.get("num"));
			}
		}
		return rtnlist;
	}
	
	public List searchByWeek(String year,String week) {
		List<Map<String,Object>> rtnlist = new ArrayList<Map<String,Object>>();
		String mon = null;
		String newYear = year;
		if(Long.valueOf(week) ==1){
			mon = "52";
			newYear = String.valueOf(Long.valueOf(year) - 1);
		}else{
			String oldMonth = String.valueOf(Long.valueOf(week)-1);
			mon = String.valueOf(oldMonth);
			if(Long.valueOf(oldMonth)<10){
				mon = "0"+oldMonth;
			}
		}
		List<Map<String,Object>> list = statDao.getCountByWeek(year,week);
		List<Map<String,Object>> oldList = statDao.getCountByWeek(newYear,mon);
		Map<String,Map<String,Object>> nMap = new HashMap<String, Map<String,Object>>();
		for (Map<String, Object> map : list) {
			nMap.put(map.get("day").toString(), map);
		}
		Map<String,Map<String,Object>> oMap = new HashMap<String, Map<String,Object>>();
		for (Map<String, Object> map : oldList) {
			oMap.put(map.get("day").toString(), map);
		}
		for(int i=1;i<=7;i++){
			Map<String,Object> currMap = null;
			String number = String.valueOf(i);
			currMap = nMap.get(String.valueOf(number));
			if(currMap ==null){
				currMap = new HashMap<String,Object>();
				currMap.put("day", number);
				currMap.put("num", 0);
				rtnlist.add(currMap);
			}else{
				currMap =  nMap.get(String.valueOf(number));
				rtnlist.add(currMap);
			}
			Map<String,Object> oldMap = oMap.get(number);
			if(oldMap ==null){
				currMap.put("oldnum", 0);
			}else{
				currMap.put("oldnum", oldMap.get("num"));
			}
		}
		return rtnlist;
	}
	
	public List searchByFormUnit(String year,String month) {
		String o = year;
		List list = statDao.getCountByFormUnit(o,month);
		return list;
	}
	
	public List getCountByRegistrat(String year,String month) {
		List list = statDao.getCountByRegistrat(year,month);
		return list;
	}
	
	public List getCountByState(String year,String month) {
		List list = statDao.getCountByState(year,month);
		return list;
	}
	
	public List getCountByType(String year,String month) {
		List list = statDao.getCountByType(year,month);
		return list;
	}
}
