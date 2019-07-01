package com.capinfo.schedule.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.schedule.bean.ScheduleConfig;
import com.capinfo.schedule.service.ScheduleService;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
	private static final Log s_logger = LogFactory
			.getLog(ScheduleController.class);

	@Autowired
	private BaseService baseService;

	@Autowired
	private ScheduleService scheduleService;

	@RequestMapping(value = "/toConfig", method = RequestMethod.GET)
	public String toConfig() {
		s_logger.debug("show schedule list invoked");
		return "security/schedule";
	}

	/** 列表加载数据 */
	@RequestMapping(value = "/listLoad.do", method = RequestMethod.POST)
	public @ResponseBody
	Object listLoad(PageBean page, @RequestParam Map<String, Object> paramMap) {
		SearchResult<ScheduleConfig> result = null;
		try {
			Search search = new Search(ScheduleConfig.class);
			// paging
			search.setFirstResult(page.getStart());
			search.setMaxResults(page.getLimit());
			result = scheduleService.getConfigs(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new GridJsonBean().valueOf(result);
	}

	/** 加载表单数据 */
	@RequestMapping(value = "/loadBean.do", method = RequestMethod.POST)
	@ResponseBody
	public Object loadBean(@RequestParam(value = "id", required = true) Long id) {
		FormLoadJsonBean<ScheduleConfig> result = new FormLoadJsonBean<ScheduleConfig>();
		try {
			ScheduleConfig bean = baseService.getObject(ScheduleConfig.class,
					id);
			result.setSuccess(true);
			result.setData(bean);
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**表单更新*/
	@RequestMapping(value="/editSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object editSave(ScheduleConfig t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			String time = t.getExecuteDate();
			String []times = time.split(":");
			
			times[0] = String.valueOf(Integer.valueOf(times[0]));
			times[1] = String.valueOf(Integer.valueOf(times[1]));
			
			// 生成cron表达式
			String cronExcepssion = "0 {mm} {hh} * * ?";
			cronExcepssion = cronExcepssion.replace("{hh}", times[0]);
			cronExcepssion = cronExcepssion.replace("{mm}", times[1]);
			
			t.setExecuteCron(cronExcepssion);
			
			baseService.updateObject(t);
			
			if(t.getIsActive() != null && t.getIsActive().equals("1")) {
				scheduleService.updateTask();
				scheduleService.start();
			} else {
				scheduleService.updateTask();
				scheduleService.pause();
			}
			
			result.setSuccess(true);
			result.setMsg(ConstantInfo.saveSuccessMsg);
		}catch(Exception e){
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
        return result; 
    }

}
