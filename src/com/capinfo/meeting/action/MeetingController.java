package com.capinfo.meeting.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.meeting.bean.Meeting;
import com.capinfo.meeting.bean.Meeting_Time;
import com.capinfo.meeting.service.MeetingService;
import com.capinfo.meeting.util.IsDeleted;
import com.capinfo.meeting.util.OperateStatu;
import com.capinfo.util.JsonUtils;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;

@Controller
@Scope("prototype")
@RequestMapping("/meeting")
public class MeetingController {
	Logger logger = Logger.getLogger(MeetingController.class);

	@Autowired
	private MeetingService meetingService;

	// 排序方式
	private Map<String, String> orderMap = new HashMap<String, String>();

	// 更新时间
	private String updateTime;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private Calendar cl = Calendar.getInstance();

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toList.do", method = RequestMethod.GET)
	public ModelAndView toList() {
		return new ModelAndView("meeting/meetinglist");
	}

	/**
	 * 加载会议信息列表
	 * 
	 * @param page
	 * @param meeting
	 * @return
	 */
	@RequestMapping(value = "list.do", method = RequestMethod.POST)
	@ResponseBody
	public Object List(PageBean page, Meeting meeting) {
		GridJsonBean result = new GridJsonBean();
		try {
			if (page != null) {
				orderMap.put(page.getProperty(), page.getDirection());
			} else {
				orderMap.put("id", "desc");
			}

			if (meeting.getTitle() == "" || meeting.getTitle().length() == 0) {
				meeting.setTitle(null);
			}

			meeting.setIsDeleted(IsDeleted.No.getIndex());// 未删除状态

			List<Meeting> list = meetingService.findByPage(page, Meeting.class,
					meeting, orderMap);
			;
			if (list != null) {
				for (Meeting me : list) {
					me.setDocList(null);
					me.setLeaderList(null);
					me.setPersons(null);
					me.setTimeList(null);
				}
				result.setRows(list);
				result.setTotalrows(page.getTotal());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 加载开会时间地址信息列表
	 * 
	 * @param page
	 * @param meeting
	 * @return
	 */
	@RequestMapping(value = "/timeList.do", method = RequestMethod.POST)
	@ResponseBody
	public Object timeList(Long meid) {
		GridJsonBean result = new GridJsonBean();
		try {
			if (meid != null && meid > 0) {
				Meeting meeting = meetingService.getObject(Meeting.class, meid);
				List<Meeting_Time> times = meeting.getTimeList();
				for (Meeting_Time time : times) {
					time.setMeeting(null);
				}
				result.setRows(times);
				result.setTotalrows(times.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/***
	 * 加载单位信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/orgList", method = RequestMethod.POST)
	@ResponseBody
	public Object orgList() {
		GridJsonBean result = new GridJsonBean();
//		try {
//			PageBean page = new PageBean();
//			Info_Org org = new Info_Org();
//			orderMap.put("id", "asc");
//			List<Info_Org> orglist = meetingService.findByPage(page,Info_Org.class, org, orderMap);
//			if (orglist != null) {
//				result.setRows(orglist);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e);
//		}
		return result;
	}

	/**
	 * 保存会议信息
	 * 
	 * @param meeting
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addSave.do", method = RequestMethod.POST)
	@ResponseBody
	public Object addSave(Meeting meeting,String updateRecords) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			updateTime = sdf.format(cl.getTime());
			meeting.setUpdateTime(updateTime);// 设置更新时间
			/**
			 * 系统用户+系统用户单位
			 */
			meeting.setIsDeleted(IsDeleted.No.getIndex());
			meeting.setOprstatus(OperateStatu.Add.getIndex());// 操作状态
			meeting = meetingService.saveObject(meeting);// 保存
			//保存会议时间地址信息
			JsonUtils jsonUtils = new JsonUtils();
			logger.debug(updateRecords);
			if (updateRecords != null && updateRecords.length() > 2) {
				List<Meeting_Time> updates = (List<Meeting_Time>) jsonUtils.getDTOList(updateRecords, Meeting_Time.class);
				// 保存修改过的信息
				for (Meeting_Time time : updates) {
					time.setMeeting(meeting);
				}
				meetingService.saveOrUpdateAll(updates);
			}
			
			// 返回信息
			result.setSuccess(true);
			result.setMsg(ConstantInfo.saveSuccessMsg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}

	/****
	 * 编辑时加载会议信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/eidtLoad", method = RequestMethod.POST)
	@ResponseBody
	public Object editLoad(Long id) {
		FormLoadJsonBean<Meeting> result = new FormLoadJsonBean<Meeting>();
		try {
			if (id != null && id > 0) {
				Meeting meeting = meetingService.getObject(Meeting.class, id);
				meeting.setDocList(null);
				meeting.setLeaderList(null);
				meeting.setTimeList(null);
				meeting.setPersons(null);
				result.setData(meeting);
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
				result.setMsg(ConstantInfo.noCheckedMsg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}

	/****
	 * 编辑会议信息
	 * 
	 * @param meeting
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editSave.do", method = RequestMethod.POST)
	@ResponseBody
	public Object editSave(Meeting meeting,String updateRecords,String removeRecords) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			updateTime = sdf.format(cl.getTime());
			meeting.setUpdateTime(updateTime);// 设置更新时间

			/**
			 * 系统用户+系统用户单位
			 */
			meeting.setIsDeleted(IsDeleted.No.getIndex());
			meeting.setOprstatus(OperateStatu.Edit.getIndex());// 操作状态

			meeting=meetingService.updateObject(meeting);
			
			/***
			 * 保存开会时间地址信息
			 */
			JsonUtils jsonUtils = new JsonUtils();
			logger.debug(updateRecords);
			if (updateRecords != null && updateRecords.length() > 2) {
				List<Meeting_Time> updates = (List<Meeting_Time>) jsonUtils
						.getDTOList(updateRecords, Meeting_Time.class);
				// 保存修改过的信息
				for (Meeting_Time time : updates) {
					time.setMeeting(meeting);
				}
				meetingService.saveOrUpdateAll(updates);

			}

			logger.debug(removeRecords);
			if (removeRecords != null && removeRecords.length() > 2) {// /删除列表存在数据
				List<Meeting_Time> removes = (List<Meeting_Time>) jsonUtils
						.getDTOList(removeRecords, Meeting_Time.class);
				// 删除已删除的数据
				meetingService.deleteAll(removes);
			}
			

			result.setSuccess(true);
			result.setMsg(ConstantInfo.saveSuccessMsg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}

	/****
	 * 删除会议信息
	 * 
	 * @param meeting
	 * @return
	 */
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(Long[] ids) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			if (ids != null) {
				meetingService.deleteByIds(ids);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			} else {
				result.setSuccess(false);
				result.setMsg(ConstantInfo.noCheckedMsg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}

}
