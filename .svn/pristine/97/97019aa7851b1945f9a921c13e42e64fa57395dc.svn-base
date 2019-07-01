package com.capinfo.meeting.action;

import javax.servlet.http.HttpServletRequest;

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
import com.capinfo.meeting.bean.Meeting_Time;
import com.capinfo.meeting.service.MeetingService;
import com.capinfo.meeting.util.Tools;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;

@Controller
@Scope("prototype")
@RequestMapping(value="meetingDetail")
public class MeetingDetailController {

	@Autowired
	private MeetingService meetingService;
	private Logger log=Logger.getLogger(MeetingDetailController.class);
	
	/***
	 * 页面跳转
	 * @return
	 */
	@RequestMapping(value = "/toList.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView toList(Long id, HttpServletRequest request) {
		//返回结果
		FormLoadJsonBean<Meeting> result=new FormLoadJsonBean<Meeting>();
		
		//错误请求页面
		ModelAndView error=new ModelAndView("meeting/error");
		
		//如果请求id不存在
		if(id==0||id==null){
			result.setSuccess(false);
			result.setMsg("不存在该会议信息！");
			error.addObject("result",result);
			return error;
		}
		
		//通过id取得会议信息
		Meeting meeting=meetingService.getObject(Meeting.class, id);
		
		//如果会议信息不存在
		if (meeting==null) {
			result.setSuccess(false);
			result.setMsg("不存在该会议信息！");
			error.addObject("result",result);
			return error;
		}
		
		meeting.setDocList(null);
		meeting.setPersons(null);
		meeting.setLeaderList(null);
		meeting.setTimeList(null);
		
		meeting.setContent(Tools.htmlToCode(meeting.getContent()));
		meeting.setRemark(Tools.htmlToCode(meeting.getRemark()));
		
		result.setData(meeting);
		//正确请求返回页面
		ModelAndView modelAndView=new ModelAndView("meeting/meetingDetail");
		modelAndView.addObject("result", result);
		log.debug(result);
		return modelAndView;
	}
		
	/***
	 * 会议信息详情加载
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public FormLoadJsonBean getDetail(Long id) {
		FormLoadJsonBean<Meeting> loadresult=new FormLoadJsonBean<Meeting>();
		try {
			/***
			 * 验证用户身份信息合理性
			 */
			if (id!=null&&id>0) {
				Meeting meeting=meetingService.getObject(Meeting.class, id);
				if (meeting.getIsDeleted()) {
					loadresult.setSuccess(false);
					loadresult.setMsg("该会议信息已删除！");
					return loadresult;
				}
				for (Meeting_Leader leader : meeting.getLeaderList()) {
					leader.setMeeting(null);
				}
				for (Meeting_Person person : meeting.getPersons()) {
					person.setMeeting(null);
				}
				for (Meeting_Time time : meeting.getTimeList()) {
					time.setMeeting(null);
				}
				for (Meeting_Doc doc : meeting.getDocList()) {
					doc.setMeeting(null);
				}
				loadresult.setData(meeting);
				loadresult.setSuccess(true);
			}
			else {
				loadresult.setSuccess(false);
				loadresult.setMsg("加载失败，不存在该会议信息！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			loadresult.setSuccess(false);
			loadresult.setMsg("加载失败！未知异常！");
		}
		return loadresult;
	}
	
	/***
	 * 与会领导信息保存
	 */
	@RequestMapping(value="/leaderSave.do",method=RequestMethod.POST)
	@ResponseBody
	public Object leaderSave(Meeting_Leader leader,String jionTimeHour, Long meetingid){
		SaveJsonBean result=new SaveJsonBean();
		try {
			if (meetingid!=null) {
				leader.setJionTime(leader.getJionTime()+" "+ jionTimeHour);//日期拼接
				
				Meeting meeting=meetingService.getObject(Meeting.class, meetingid);
				leader.setMeeting(meeting);
				meetingService.saveOrUpdateObject(leader);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			}else {
				result.setSuccess(false);
				result.setMsg(ConstantInfo.saveErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}
	
	/***
	 * 与会人员信息保存
	 */
	@RequestMapping(value="/personSave.do",method=RequestMethod.POST)
	@ResponseBody
	public Object personSave(Meeting_Person person,Long meid){
		SaveJsonBean result=new SaveJsonBean();
		try {
			if (meid!=null&&meid>0) {
				Meeting meeting=meetingService.getObject(Meeting.class, meid);
				person.setMeeting(meeting);
				meetingService.saveOrUpdateObject(person);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}
	
	/***
	 * 文档信息保存
	 */
	@RequestMapping(value="/medocSave.do",method=RequestMethod.POST)
	@ResponseBody
	public Object medocSave(Meeting_Doc doc,Long meetingid){
		SaveJsonBean result=new SaveJsonBean();
		try {
			if (meetingid!=null&&meetingid>0) {
				Meeting meeting=meetingService.getObject(Meeting.class, meetingid);
				doc.setMeeting(meeting);
				meetingService.saveOrUpdateObject(doc);
				result.setSuccess(true);
				result.setMsg(ConstantInfo.saveSuccessMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
		return result;
	}
	
	/***
	 * 加载与会领导信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadLeader.do",method=RequestMethod.POST)
	@ResponseBody
	public Object loadLeader(Long id) {
		FormLoadJsonBean<Meeting_Leader> result=new FormLoadJsonBean<Meeting_Leader>();
		try {
			if (id!=null&&id>0) {
				Meeting_Leader leader=meetingService.getObject(Meeting_Leader.class, id);
				leader.setMeeting(null);
				result.setData(leader);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
		
	}

	/***
	 * 加载与会人员信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadPerson.do",method=RequestMethod.POST)
	@ResponseBody
	public Object loadPerson(Long id) {
		FormLoadJsonBean<Meeting_Person> result=new FormLoadJsonBean<Meeting_Person>();
		try {
			if (id!=null&&id>0) {
				Meeting_Person person=meetingService.getObject(Meeting_Person.class, id);
				person.setMeeting(null);
				result.setData(person);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
		
	}

	/***
	 * 加载会议文档信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadDoc.do",method=RequestMethod.POST)
	@ResponseBody
	public Object loadDoc(Long id) {
		FormLoadJsonBean<Meeting_Doc> result=new FormLoadJsonBean<Meeting_Doc>();
		try {
			if (id!=null&&id>0) {
				Meeting_Doc doc=meetingService.getObject(Meeting_Doc.class, id);
				doc.setMeeting(null);
				result.setData(doc);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
		
	}
}
