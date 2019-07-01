package com.capinfo.meeting.action;

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
import com.capinfo.meeting.bean.Meeting_Doc;
import com.capinfo.meeting.bean.Meeting_Leader;
import com.capinfo.meeting.bean.Meeting_Person;
import com.capinfo.meeting.service.MeetingService;
import com.capinfo.meeting.util.IsDeleted;
import com.capinfo.meeting.util.Tools;
import com.capinfo.meeting.util.TypeJuge;
import com.capinfo.util.JsonUtils;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;

@Controller
@Scope("prototype")
@RequestMapping(value = "/meetingInfo")
public class MeetingInfoController {

	Logger logger = Logger.getLogger(MeetingController.class);

	@Autowired
	private MeetingService meetingService;

	// 排序方式
	private Map<String, String> orderMap = new HashMap<String, String>();

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toList.do", method = RequestMethod.GET)
	public ModelAndView toList() {
		return new ModelAndView("meeting/meetingInfo");
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
			meeting.setTypeJuge(TypeJuge.Deal.getIndex());// 处理状态

			List<Meeting> list = meetingService.findByPage(page, Meeting.class,
					meeting, orderMap);

			if (list != null) {
				for (Meeting me : list) {
					me.setDocList(null);
					me.setLeaderList(null);
					me.setPersons(null);
					me.setTimeList(null);
					//编码，去除回车符
					me.setContent(Tools.htmlToCode(me.getContent()));
					me.setRemark(Tools.htmlToCode(me.getRemark()));
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

	/***
	 * 加载参会领导信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/leaderList.do", method = RequestMethod.POST)
	@ResponseBody
	public Object leaderList(Long id) {
		GridJsonBean result = new GridJsonBean();
		try {
			if (id != null && id > 0) {
				Meeting meeting = meetingService.getObject(Meeting.class, id);// 通过id取值
				List<Meeting_Leader> leaders = meeting.getLeaderList();// 赋值
				for (Meeting_Leader meetingLeader : leaders) {
					meetingLeader.setMeeting(null);
				}
				result.setRows(leaders);
				result.setTotalrows(leaders.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/***
	 * 加载参会人员信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personList.do", method = RequestMethod.POST)
	@ResponseBody
	public Object personList(Long id) {
		GridJsonBean result = new GridJsonBean();
		try {
			if (id != null && id > 0) {
				Meeting meeting = meetingService.getObject(Meeting.class, id);// 通过id取值
				List<Meeting_Person> persons = meeting.getPersons();// 赋值
				for (Meeting_Person person : persons) {
					person.setMeeting(null);
				}
				result.setRows(persons);
				result.setTotalrows(persons.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/***
	 * 加载会议文档信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/docList.do", method = RequestMethod.POST)
	@ResponseBody
	public Object docList(Long id) {
		GridJsonBean result = new GridJsonBean();
		try {
			if (id != null && id > 0) {
				Meeting meeting = meetingService.getObject(Meeting.class, id);// 通过id取值
				List<Meeting_Doc> docs = meeting.getDocList();// 赋值
				for (Meeting_Doc doc : docs) {
					doc.setMeeting(null);
				}
				result.setRows(docs);
				result.setTotalrows(docs.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 保存参会领导信息
	 * 
	 * @param meeting
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editSave.do", method = RequestMethod.POST)
	@ResponseBody
	public Object editSave(String updateRecords, String removeRecords, Long meid) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			if (meid != null && meid > 0) {
				JsonUtils jsonUtils = new JsonUtils();
				if (updateRecords != null && updateRecords.length() > 2) {
					List<Meeting_Leader> updates = (List<Meeting_Leader>) jsonUtils
							.getDTOList(updateRecords, Meeting_Leader.class);
					// 保存修改过的信息
					Meeting meeting = meetingService.getObject(Meeting.class,
							meid);
					for (Meeting_Leader meetingLeader : updates) {
						meetingLeader.setMeeting(meeting);
					}
					meetingService.saveOrUpdateAll(updates);

				}
				if (removeRecords != null && removeRecords.length() > 2) {// /删除列表存在数据
					List<Meeting_Leader> removes = (List<Meeting_Leader>) jsonUtils
							.getDTOList(removeRecords, Meeting_Leader.class);
					// 删除已删除的数据
					meetingService.deleteAll(removes);
				}

				// 返回信息
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			} else {
				result.setSuccess(true);
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

	/**
	 * 保存参会人员信息
	 * 
	 * @param meeting
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editPerson.do", method = RequestMethod.POST)
	@ResponseBody
	public Object editPerson(String updateRecords, String removeRecords, Long meid) {
		SaveJsonBean result = new SaveJsonBean();
		try {
			if (meid != null && meid > 0) {
				JsonUtils jsonUtils = new JsonUtils();
				if (updateRecords != null && updateRecords.length() > 2) {
					List<Meeting_Person> updates = (List<Meeting_Person>) jsonUtils
					.getDTOList(updateRecords, Meeting_Person.class);
					// 保存修改过的信息
					Meeting meeting = meetingService.getObject(Meeting.class,
							meid);
					for (Meeting_Person person : updates) {
						person.setMeeting(meeting);
					}
					meetingService.saveOrUpdateAll(updates);
					
				}
				if (removeRecords != null && removeRecords.length() > 2) {// /删除列表存在数据
					List<Meeting_Person> removes = (List<Meeting_Person>) jsonUtils
					.getDTOList(removeRecords, Meeting_Person.class);
					// 删除已删除的数据
					meetingService.deleteAll(removes);
				}
				
				// 返回信息
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			} else {
				result.setSuccess(true);
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
