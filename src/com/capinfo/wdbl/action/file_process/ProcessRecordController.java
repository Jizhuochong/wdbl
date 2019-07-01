package com.capinfo.wdbl.action.file_process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.bean.file_process.File_Handle;
import com.capinfo.wdbl.bean.file_process.File_Leader;
import com.capinfo.wdbl.service.ProcessReocordService;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
/**
 * 注办信息
 */
@Controller  
@Scope("prototype")
@RequestMapping("/processrecord")
public class ProcessRecordController {   
	public static Logger log = Logger.getLogger(ProcessRecordController.class);
	
	@Autowired
	private ProcessReocordService processReocordService;
	
	@Autowired
	private BaseService baseService;
	
	/**根据文件id加载单条操作记录数据*/
	@RequestMapping(value="/loadProcess.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadProcess(@RequestParam(value="fid",required=true) Long fileid){
		FormLoadJsonBean<File_Leader> result = new FormLoadJsonBean<File_Leader>();
		try{
			ProcessRecord pr = processReocordService.loadProcess(fileid);
			if(pr == null){
				pr = new ProcessRecord();
			}
			File_Leader leader = new File_Leader();
			leader.setId(fileid);
			leader.setLeader(pr.getName());
			result.setSuccess(true);
			result.setData(leader);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
        return result; 
    }
	
	/**根据文件id加载表单数据*/
	@RequestMapping(value="/loadBeanByFileid.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadBeanByFileid(@RequestParam(value="id",required=true) Long fileid){
		GridJsonBean result = new GridJsonBean();
		try{
			FileReg info = baseService.getObject(FileReg.class, fileid);
			List<ProcessRecord> list = info.getProcessRecord();
			List<ProcessRecord> newList = new ArrayList<ProcessRecord>();
			for(ProcessRecord processRecord : list) {
				if(processRecord.getHandlestatus() != null && !processRecord.getHandlestatus().equals("修改")) {
					newList.add(processRecord);
				}
			}
			Collections.sort(newList);
			result.setTotalrows(newList.size());
			result.setRows(newList);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
        return result; 
    }
	
	/**
	 * 加载文件登记信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/loadBean.do", method = RequestMethod.POST)
	public @ResponseBody
	FormLoadJsonBean<ProcessRecord> loadBean(@RequestParam(value = "id", required = true) Long id, String sessionID,HttpSession session) {
		FormLoadJsonBean<ProcessRecord> result = new FormLoadJsonBean<ProcessRecord>();
		try {
			ProcessRecord bean = processReocordService.getObject(ProcessRecord.class, id);
			result.setSuccess(true);
			result.setData(bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**保存*/
	@RequestMapping(value="/save.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object save(ProcessRecord t,Long fileid){
		SaveJsonBean result = new SaveJsonBean();
		String nowDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try{
			if(t.getId()!=null){
				ProcessRecord ubean = processReocordService.getObject(ProcessRecord.class,t.getId());
				ubean.setName(t.getName());
				ubean.setDuty(t.getDuty());
				ubean.setAgent(t.getAgent());
				ubean.setApprovalResult(t.getApprovalResult());
				ubean.setApprovalOp(t.getApprovalOp());
				ubean.setRemarkOp(t.getRemarkOp());
//				ubean.setSys_user(UserDetailsHelper.getUser().getName()==null?"":UserDetailsHelper.getUser().getName());
				ubean.setSys_date(nowDateString);
				
				processReocordService.updateObject(ubean);
			}else{
				FileReg FileReg = processReocordService.getObject(FileReg.class, fileid);
				t.setFileReg(FileReg);
				t.setHandlestatus("领导批示");
				t.setSys_date(nowDateString);
				processReocordService.saveObject(t);
				/**修改文件登记信息状态**/
				FileReg.setHandlestatus("领导批示");
				processReocordService.saveObject(FileReg);
			}
			
			result.setSuccess(true);
			result.setMsg(ConstantInfo.saveSuccessMsg);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.saveErrorMsg);
		}
        return result; 
    }
	
	/**修改*/
	@RequestMapping(value="/update.do")  
	@ResponseBody 
	public Object update(ProcessRecord entity){
		SaveJsonBean result = new SaveJsonBean();
		try{
			ProcessRecord newentity = processReocordService.getObject(ProcessRecord.class, entity.getId());
			newentity.setAgent(entity.getAgent());
			newentity.setName(entity.getName());
			newentity.setHandlecontent(entity.getHandlecontent());
			newentity.setHandlestatus(entity.getHandlestatus());
			newentity.setApprovalOp(entity.getApprovalOp());
			newentity.setSys_date(entity.getSys_date());
			newentity.setJishouren(entity.getJishouren());
			processReocordService.updateObject(newentity);
			result.setSuccess(true);
			result.setMsg(ConstantInfo.saveSuccessMsg);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	    return result; 
	}  

}  