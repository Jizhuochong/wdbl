package com.capinfo.schedule.service;

import java.util.List;
import java.util.Map;

import com.capinfo.schedule.bean.ScheduleConfig;
import com.capinfo.wdbl.bean.FileReg;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface ScheduleService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract SearchResult<ScheduleConfig> getConfigs( ISearch search);
	
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract List<FileReg> getAutoFinishRecords();
	
	/**
	 * 更新定时任务
	 */
	public void updateTask();
	
	/**
	 * 暂停调度
	 */
	public void pause();
	
	/**
	 * 启动调度
	 */
	public void start();
	
	/**
	 * 获取调度配置信息
	 */
	public Map<String, String> getConfigDB();
	
}
