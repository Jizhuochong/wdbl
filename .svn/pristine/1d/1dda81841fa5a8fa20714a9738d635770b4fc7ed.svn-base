package com.capinfo.wdbl.action.file_process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.bean.file_process.File_Leader;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;

/**
 * 领导批示
 */
@Controller  
@Scope("prototype")
@RequestMapping("/fileLeader")
public class File_LeaderController {
	public static Logger log = Logger.getLogger(File_LeaderController.class);
	
	@Autowired
	private BaseService baseService;
	
	/**跳转到列表*/
	@RequestMapping("/{docTypeName}/toList.do")
	public String toList(@PathVariable String docTypeName){
		return "file_process/"+docTypeName + "List";
	}
	
	/**列表加载数据*/
	@RequestMapping(value="/listLoad.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object listLoad(PageBean page, FileReg file){
		GridJsonBean grid = null;
		try{
			/**添加默认排序条件**/
			if(null == page.getSort() || null == page.getDir()){
				page.setSort("receiveTime");
				page.setDir("DESC");
			}
			file.setOriginalFiles(null);/*去掉关联条件*/
			List<FileReg> list =  baseService.queryByPage(page,FileReg.class,file);
			grid = new GridJsonBean();
			grid.setTotalrows(page.getTotal());
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
        return grid; 
    }  
	
	/**加载表单数据*/
	@RequestMapping(value="/loadBean.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadBean(@RequestParam(value="id",required=true) Long id){
		FormLoadJsonBean<File_Leader> result = new FormLoadJsonBean<File_Leader>();
		try{
			File_Leader bean = baseService.getObject(File_Leader.class, id);
			if(bean == null){
				bean = new File_Leader(id);
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
	
	/**录入领导批示*/
	@RequestMapping(value="/leaderSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object leaderSave(File_Leader t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			String nowDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			FileReg entity = baseService.getObject(FileReg.class, t.getId());
			t.setFileReg(entity);
			t.setRecordDate(nowDateString);
			t.setRecorder(UserDetailsHelper.getUser().getName());
			baseService.saveObject(t);
			/**修改文件登记信息状态**/
			entity.setHandlestatus("领导批示");
			baseService.saveObject(entity);
			ProcessRecord processRecord = new ProcessRecord();
			processRecord.setName(t.getLeader());
			processRecord.setHandlestatus("领导批示");
			processRecord.setHandlecontent("录入了"+t.getLeader()+"批示");
			processRecord.setApprovalOp(t.getLeaderOpinion());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			processRecord.setSys_date(df.format(new Date()));
			processRecord.setSys_user(UserDetailsHelper.getUser().getName());
			processRecord.setAgent(UserDetailsHelper.getUser().getName());
			processRecord.setFileReg(entity);
			baseService.saveObject(processRecord);
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
