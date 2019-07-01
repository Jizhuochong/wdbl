package com.capinfo.wdbl.action.file_process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.service.ProcessReocordService;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
/**
 * 市委市政府正式文件-领导审批
 */
@Controller  
@Scope("prototype")
@RequestMapping("/fcgoapproval")
public class F_CGO_ApprovalController {   
	public static Logger log = Logger.getLogger(F_CGO_ApprovalController.class);
	
	@Autowired
	private ProcessReocordService processReocordService;
	
	/**跳转到列表*/
	@RequestMapping("/toList.do")
	public ModelAndView toList(Long fileid){
		ModelAndView modelAndView = new ModelAndView();
		FileReg file = processReocordService.getObject(FileReg.class, fileid);
		modelAndView.addObject("filebean", file);
		modelAndView.addObject("fileid",fileid);
		modelAndView.setViewName("file_process/f_CGO_ApprovalList");
		
		return modelAndView;
	}
	
	/**列表加载数据*/
	@RequestMapping(value="/listLoad.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object listLoad(PageBean page, ProcessRecord bean,long fileid){
		GridJsonBean grid = null;
		try{
			bean.setHandlestatus("领导批示");
			List<ProcessRecord> list = processReocordService.getListByPageAndFileId(page, bean, fileid);
			for (ProcessRecord processRecord : list) {
				processRecord.setFileReg(null);
			}
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
    public Object loadBean(long id){
		FormLoadJsonBean<ProcessRecord> result = new FormLoadJsonBean<ProcessRecord>();
		try{
			ProcessRecord bean = processReocordService.getObject(ProcessRecord.class, id);
			if(bean!=null){
				bean.setFileReg(null);
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
				ubean.setSys_date(nowDateString);
				
				processReocordService.updateObject(ubean);
			}else{//添加
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
	/**删除*/
	@RequestMapping(value="/delete.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object delete(Long[] ids){
		SaveJsonBean result = new SaveJsonBean();
		try{
			if(ids!=null&&ids.length>0){
				Collection<ProcessRecord> c = new ArrayList<ProcessRecord>();
				for(Long id:ids){
					ProcessRecord processRecord = new ProcessRecord(id);
					c.add(processRecord);
				}
				processReocordService.deleteAll(c);
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