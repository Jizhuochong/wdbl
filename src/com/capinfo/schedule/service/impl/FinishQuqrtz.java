package com.capinfo.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.capinfo.base.service.BaseService;
import com.capinfo.schedule.service.ScheduleService;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.ProcessRecord;

public class FinishQuqrtz {
	
	private static final Log s_logger = LogFactory.getLog(FinishQuqrtz.class);
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	@Qualifier("baseService")
	private BaseService service;
	
	/**
	 * 自动办结
	 */
	public void autoFinish() {
		Map<String, String> configMap = scheduleService.getConfigDB();
		if(configMap != null && configMap.get("ISACTIVE") != null && configMap.get("ISACTIVE").equals("1")) {
			s_logger.debug("自动办结任务启动");
			List<FileReg> scList = scheduleService.getAutoFinishRecords();
			if(scList != null && scList.size() > 0) {
				Long[] ids = new Long[scList.size()];
				for(int i = 0; i < scList.size(); i++) {
					ids[i] = scList.get(i).getId();
				}
				this.finish(ids);
			}
			s_logger.debug("自动办结任务结束");
		}
	}
	
	private void finish(Long[] ids) {
		if (ids != null && ids.length > 0) {
			for (Long id : ids) {
				FileReg li = service.getObject(FileReg.class, id);
				li.setHandlestatus("系统办结");
				service.updateObject(li);
				ProcessRecord processRecord = new ProcessRecord();
				processRecord.setHandlestatus("系统办结");
				processRecord.setHandlecontent("系统办结了"+li.getTitle());
				processRecord.setApprovalOp(li.getLeadershipPS());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				processRecord.setSys_date(df.format(new Date()));
				processRecord.setSys_user("系统");
				processRecord.setAgent("系统");
				processRecord.setFileReg(li);
				service.saveObject(processRecord);
			}
		}
	}
}
