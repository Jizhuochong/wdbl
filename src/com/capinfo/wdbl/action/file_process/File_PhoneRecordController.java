package com.capinfo.wdbl.action.file_process;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.file_process.File_PhoneRecord;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;


/**
 * 电话记录
 */
@Controller  
@Scope("prototype")
@RequestMapping("/filephonerecord")
public class File_PhoneRecordController {   
	public static Logger log = Logger.getLogger(File_PhoneRecordController.class);
	
	@Autowired
	private BaseService baseService;
	
	
	/**跳转到列表*/
	@RequestMapping(value="/toList.do",method = RequestMethod.GET) 
	public String toList(){
		return "file_process/file_PhoneRecordList";
	}
	
	/**列表加载数据*/
	@RequestMapping(value="/listLoad.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object listLoad(PageBean page, File_PhoneRecord file){
		GridJsonBean grid = null;
		try{
			List<File_PhoneRecord> list =  baseService.queryByPage(page,File_PhoneRecord.class,file);
			grid = new GridJsonBean();
			grid.setTotalrows(page.getTotal());
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
        return grid; 
    }  
	
	
	/**领导批示保存*/
	@RequestMapping(value="/leaderSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object suggestSave(File_PhoneRecord t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			File_PhoneRecord fp = baseService.getObject(File_PhoneRecord.class,t.getId());
			fp.setLeader_name(t.getLeader_name());
			fp.setLeader_opinion(t.getLeader_opinion());
			baseService.updateObject(fp);
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
	
	/**流程状态改变*/
	@RequestMapping(value="/processStateSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object processStateSave(File_PhoneRecord t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			File_PhoneRecord fp = baseService.getObject(File_PhoneRecord.class,t.getId());
			fp.setProcess_state(t.getProcess_state());
			baseService.updateObject(fp);
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
	
}  