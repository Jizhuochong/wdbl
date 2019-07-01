package com.capinfo.wdbl.action.file_process;

import java.text.SimpleDateFormat;
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

import com.capinfo.base.service.BaseService;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.file_process.File_ParttimeLeader;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
/**
 * 兼职领导
 */
@Controller  
@Scope("prototype")
@RequestMapping("/parttimeleader")
public class File_ParttimeLeaderController {   
	public static Logger log = Logger.getLogger(File_ParttimeLeaderController.class);
	
	@Autowired
	private BaseService baseService;
	
	
	/**加载表单数据*/
	@RequestMapping(value="/loadBeanByFileid.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadBeanByFileid(Long fileid){
		FormLoadJsonBean<File_ParttimeLeader> result = new FormLoadJsonBean<File_ParttimeLeader>();
		try{
			List<File_ParttimeLeader> list = baseService.getListByFileId(File_ParttimeLeader.class, fileid);
			File_ParttimeLeader bean = new File_ParttimeLeader(fileid);
			if(list != null&&list.size()>0){
				bean = list.get(0);
			}
			result.setSuccess(true);
			result.setData(bean);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
        return result; 
    } 
	
	
	/**保存*/
	@RequestMapping(value="/save.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object save(File_ParttimeLeader t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			String nowDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			File_ParttimeLeader sbean = baseService.getObject(File_ParttimeLeader.class, t.getId());
			if(sbean!=null){
				sbean.setLeader_name(t.getLeader_name());
				sbean.setLeader_job(t.getLeader_job());
				sbean.setPart_job(t.getPart_job());
				sbean.setLeader_opinion(t.getLeader_opinion());
				sbean.setLeader_enter(t.getLeader_enter());
				sbean.setLeader_Approvaltime(t.getLeader_Approvaltime());
				
				sbean.setLeader_entertime(nowDateString);
				baseService.updateObject(sbean);
			}else{
				t.setFileReg(new FileReg(t.getId()));
				t.setLeader_entertime(nowDateString);
				baseService.saveObject(t);
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
	
}  