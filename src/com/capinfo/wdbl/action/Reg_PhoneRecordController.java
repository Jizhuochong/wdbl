package com.capinfo.wdbl.action;

import java.util.ArrayList;
import java.util.Collection;
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
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.api.SerialNumberManager;
import com.capinfo.wdbl.bean.file_process.File_PhoneRecord;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
/**
 * 电话记录登记
 */


@Controller  
@Scope("prototype")
@RequestMapping("/regphonerecord")
public class Reg_PhoneRecordController {   
	public static Logger log = Logger.getLogger(Reg_PhoneRecordController.class);
	
	@Autowired
	private BaseService baseService;
	@Autowired
	private SerialNumberManager serialNumberManager;
	
	/**跳转到列表*/
	@RequestMapping(value="/toList.do",method = RequestMethod.GET) 
	public String toList(){
		return "file_reg/reg_PhoneRecordList";
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
	
	/**表单保存*/
	@RequestMapping(value="/addSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object addSave(File_PhoneRecord t1){
		SaveJsonBean result = new SaveJsonBean();
		try{
			t1.setSn(serialNumberManager.generateSN());
			baseService.saveObject(t1);
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
	
	/**表单更新*/
	@RequestMapping(value="/editSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object editSave(File_PhoneRecord t){
		SaveJsonBean result = new SaveJsonBean();
		try{
			baseService.updateObject(t);
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
	
	/**加载表单数据*/
	@RequestMapping(value="/loadBean.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadBean(@RequestParam(value="id",required=true) Long id){
		FormLoadJsonBean<File_PhoneRecord> result = new FormLoadJsonBean<File_PhoneRecord>();
		try{
			File_PhoneRecord bean = baseService.getObject(File_PhoneRecord.class, id);
			result.setSuccess(true);
			result.setData(bean);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
		}
        return result; 
    } 
	
	
	/**列表删除*/
	@RequestMapping(value="/delete.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object delete(long[] ids){
		SaveJsonBean  result = new SaveJsonBean();
		try{
			if(ids!=null&&ids.length>0){
				Collection<File_PhoneRecord> list=new ArrayList<File_PhoneRecord>(); 
				for(int i=0;i<ids.length;i++){
					File_PhoneRecord t = new File_PhoneRecord();
					t.setId(ids[i]);
					list.add(t);
				}
				baseService.deleteAll(list);
				
				result.setSuccess(true);
				result.setMsg(ConstantInfo.delSuccessMsg);
			}else{
				result.setSuccess(true);
				result.setMsg(ConstantInfo.noCheckedMsg);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMsg(ConstantInfo.delErrorMsg);
		}
        return result; 
    } 
	
	
	
	
}  