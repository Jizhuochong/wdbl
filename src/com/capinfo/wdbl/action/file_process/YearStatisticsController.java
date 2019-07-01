package com.capinfo.wdbl.action.file_process;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.wdbl.bean.file_process.FileSearch;
import com.capinfo.wdbl.service.StatService;
import com.capinfo.wdbl.util.GridJsonBean;


/**
 * [文件登记Controller]
 * 
 */
@Controller
@RequestMapping("statistics")
public class YearStatisticsController {
	private static final Log log = LogFactory.getLog(YearStatisticsController.class);
	
	@Autowired
	@Qualifier("statService")
	private StatService statService;
	/**
	 * 跳转到年查询统计列表
	 * 
	 * @param 
	 * 
	 * @return
	 */
	@RequestMapping("/showList.do")
	public String showList() {
		return "tra_file/yearStatistics";
	}
	
	@RequestMapping("/showMonthList.do")
	public String showMonthList() {
		return "tra_file/monthStatistics";
	}
	
	@RequestMapping("/showWeekList.do")
	public String showWeekList() {
		return "tra_file/weekStatistics";
	}
	
	@RequestMapping("/showFormUnitList.do")
	public String showFormUnitList() {
		return "tra_file/formUnitStatistics";
	}
	
	@RequestMapping("/showStateList.do")
	public String showStateList() {
		return "tra_file/stateStatistics";
	}
	
	@RequestMapping("/showTypeList.do")
	public String showTypeList() {
		return "tra_file/typeStatistics";
	}
	
	@RequestMapping("/showRegistrarList.do")
	public String showRegistrarList() {
		return "tra_file/registrarStatistics";
	}
	
	/**列表加载年数据*/
	@RequestMapping(value="/listLoad.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoad(@RequestParam(value="year",required=true) String year){
		GridJsonBean grid = new GridJsonBean();
		try{
			List list = statService.searchByYear(year);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
	
	/**列表加载月数据*/
	@RequestMapping(value="/listLoadMonth.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoadMonth(@RequestParam(value="year",required=true) String year,
			@RequestParam(value="month",required=true) String month){
		GridJsonBean grid = new GridJsonBean();
		try{
			if(Long.valueOf(month)<10){
				month = "0"+month;
			}
			List list = statService.searchByMonth(year,month);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
	
	/**列表加载周数据*/
	@RequestMapping(value="/listLoadWeek.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoadWeek(@RequestParam(value="year",required=true) String year,
			@RequestParam(value="week",required=true) String week){
		GridJsonBean grid = new GridJsonBean();
		try{
			if(Long.valueOf(week)<10){
				week = "0"+week;
			}
			List list = statService.searchByWeek(year,week);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
	
	/**列表加载来文单位数据*/
	@RequestMapping(value="/listLoadFormUnit.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoadFormUnit(@RequestParam(value="year",required=true) String year,
			@RequestParam(value="month",required=true) String month){
		GridJsonBean grid = new GridJsonBean();
		try{
			if(Long.valueOf(month)<10){
				month = "0"+month;
			}
			List list = statService.searchByFormUnit(year,month);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
	
	/**列表加载类型数据*/
	@RequestMapping(value="/listLoadType.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoadType(@RequestParam(value="year",required=true) String year,
			@RequestParam(value="month",required=true) String month){
		GridJsonBean grid = new GridJsonBean();
		try{
			if(Long.valueOf(month)<10){
				month = "0"+month;
			}
			List list = statService.getCountByType(year,month);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
	
	/**列表加载办理人数据*/
	@RequestMapping(value="/listLoadRegistrar.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoadRegistrar(@RequestParam(value="year",required=true) String year,
			@RequestParam(value="month",required=true) String month){
		GridJsonBean grid = new GridJsonBean();
		try{
			if(Long.valueOf(month)<10){
				month = "0"+month;
			}
			List list = statService.getCountByRegistrat(year,month);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
	
	/**列表加载状态数据*/
	@RequestMapping(value="/listLoadState.do",method = RequestMethod.POST)  
	@ResponseBody 
	public Object listLoadState(@RequestParam(value="year",required=true) String year,
			@RequestParam(value="month",required=true) String month){
		GridJsonBean grid = new GridJsonBean();
		try{
			if(Long.valueOf(month)<10){
				month = "0"+month;
			}
			List list = statService.getCountByState(year,month);
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return grid; 
	}
}
