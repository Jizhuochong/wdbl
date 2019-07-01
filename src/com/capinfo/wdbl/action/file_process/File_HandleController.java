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
import com.capinfo.wdbl.bean.file_process.File_Handle;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
/**
 * 呈送领导
 */
@Controller  
@Scope("prototype")
@RequestMapping("/filehandle")
public class File_HandleController {   
	public static Logger log = Logger.getLogger(File_HandleController.class);
	
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
		FormLoadJsonBean<File_Handle> result = new FormLoadJsonBean<File_Handle>();
		try{
			File_Handle bean = baseService.getObject(File_Handle.class, id);
			if(bean == null){
				bean = new File_Handle(id);
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
	
	/**发送拟办*/
	@RequestMapping(value="/senddraftSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object senddraftSave(File_Handle t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			String nowDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//File_Handle sbean = baseService.getObject(File_Handle.class, t.getId());
			/*if(sbean!=null){//修改
				sbean.setOfficeLeader_start(t.getOfficeLeader_start());
				sbean.setOfficeLeader_org(t.getOfficeLeader_name());
				sbean.setOfficeLeader_name(t.getOfficeLeader_name());
				sbean.setOfficeLeader_job(t.getOfficeLeader_job());
				sbean.setOfficeLeader_action(t.getOfficeLeader_action());
				sbean.setDraft_op(t.getDraft_op());
				sbean.setDraft_remarkop(t.getDraft_remarkop());
				sbean.setDraft_person(t.getDraft_person());
				sbean.setDraft_date(nowDateString);
				baseService.updateObject(sbean);
			}else{//添加
*/				FileReg entity = baseService.getObject(FileReg.class, t.getId());
				t.setFileReg(entity);
				t.setDraft_date(nowDateString);
				t.setDraft_person(UserDetailsHelper.getUser().getName());
				baseService.saveObject(t);
				/**修改文件登记信息状态**/
				entity.setHandlestatus("呈送");
				baseService.saveObject(entity);
				ProcessRecord processRecord = new ProcessRecord();
				processRecord.setName(t.getSubmitLeader());
				processRecord.setHandlestatus("呈送");
				processRecord.setHandlecontent("呈送给"+t.getSubmitLeader());
				processRecord.setApprovalOp(entity.getLeadershipPS());
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				processRecord.setSys_date(df.format(new Date()));
				processRecord.setSys_user(UserDetailsHelper.getUser().getName());
				processRecord.setAgent(UserDetailsHelper.getUser().getName());
				processRecord.setFileReg(entity);
				baseService.saveObject(processRecord);
			/*}*/
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
	
	/**拟办*/
	@RequestMapping(value="/draftSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object draftSave(File_Handle t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			//String nowDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			File_Handle sbean = baseService.getObject(File_Handle.class, t.getId());
			FileReg fileReg = baseService.getObject(FileReg.class, t.getId());
			if(sbean!=null){
				/*sbean.setOfficeLeader_result(t.getOfficeLeader_result());
				sbean.setOfficeLeader_opinion(t.getOfficeLeader_opinion());
				sbean.setOfficeLeader_remarop(t.getOfficeLeader_remarop());
				sbean.setOfficeLeader_enter(t.getOfficeLeader_enter());
				sbean.setOfficeLeader_Approvaltime(t.getOfficeLeader_Approvaltime());
				sbean.setOfficeLeader_entertime(nowDateString);*/
				baseService.updateObject(sbean);
			}else{
				t.setFileReg(new FileReg(t.getId()));
				/*t.setOfficeLeader_entertime(nowDateString);*/
				baseService.saveObject(t);
			}
			if(fileReg.getHandlestatus().equals("发送拟办")
					|| fileReg.getHandlestatus().equals("登记")){
				/**修改文件登记信息状态**/
				fileReg.setHandlestatus("拟办");
				baseService.saveObject(fileReg);
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