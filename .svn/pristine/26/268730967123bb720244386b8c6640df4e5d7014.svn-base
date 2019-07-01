package com.capinfo.meeting.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.meeting.bean.Meeting_Time;
import com.capinfo.meeting.service.MeetingService;
import com.capinfo.util.WeekTimeBean;
import com.capinfo.wdbl.util.GridJsonBean;

@Controller
@Scope("prototype")
@RequestMapping(value = "meSchedule")
public class meScheduleController {

	private Logger log = Logger.getLogger(meScheduleController.class);

	@Autowired
	private MeetingService meetingService;


	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView toList() {
		ModelAndView result = new ModelAndView("meeting/meetingSchedule");
		return result;
	}

	/***
	 * 通过时间查询会议信息
	 * 
	 * @param day
	 * @return
	 */
	@RequestMapping(value = "/timeList.do", method = RequestMethod.POST)
	@ResponseBody
	public Object timeList(@RequestParam String day) {
		GridJsonBean result = new GridJsonBean();
		try {		
			if (day != null) {
				/***
				 * 时间处理.........
				 * 时间字符串处理:火狐/谷歌获取的时间格式不一致:
				 * 火狐:Sat Nov 15 2014 10:48:19 GMT+0800
				 * 谷歌:Sat Nov 15 2014 10:51:07 GMT+0800 (中国标准时间)
				 * ie:debug出错
				 */
				log.debug(day);
				List<Meeting_Time> list = meetingService.findMeetings(day);
				result.setRows(list);
				result.setTotalrows(list.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setTotalrows(0);
		}
		return result;
	}
	
	/***
	 *通过日期列表查询周会议信息
	 *原因:还未对时间进行处理,临时方案
	 * @param days
	 * @return
	 */
	@RequestMapping(value="/weekList.do",method=RequestMethod.POST)
	@ResponseBody
	public Object weekList(String[] days){
		GridJsonBean result=new GridJsonBean();
		try {
			if (days!=null&&days.length>0) {
				SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
				Calendar cl=Calendar.getInstance();
				Date date;
				int dayofweek=0;
				
				List<WeekTimeBean> weeks=new ArrayList<WeekTimeBean>();//存储得到的值
				
				for (String day : days) {
					log.debug(day);
					List<Meeting_Time> list = meetingService.findMeetings(day);//查找
					date=sdf.parse(day);
					log.debug(date);
					cl.setTime(date);
					dayofweek=cl.get(Calendar.DAY_OF_WEEK)-2;
					log.debug(dayofweek);
					
					for (Meeting_Time meetingTime : list) {
						if (dayofweek<0) {
							dayofweek=6;
						}
						WeekTimeBean week=new WeekTimeBean(meetingTime,dayofweek);
						weeks.add(week);
					}
					
					log.debug("list.size()"+list.size());
					log.debug("weeks.size()"+weeks.size());
				}
				
				result.setRows(weeks);
				result.setTotalrows(weeks.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	/*****
	 *通过单个日期查询周会议信息
	 * @param days
	 * @return
	 */
	public Object weekList(String days){
		GridJsonBean result=new GridJsonBean();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return result;
	}
}
