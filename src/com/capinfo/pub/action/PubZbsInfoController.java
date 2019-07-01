package com.capinfo.pub.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capinfo.base.service.BaseService;
import com.capinfo.pub.bean.PubZbsInfo;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
@Controller  
@Scope("prototype")
@RequestMapping("/pubZbsInfo")
public class PubZbsInfoController {
	public static Logger log = Logger.getLogger(PubZbsInfoController.class);
	@Autowired
	private BaseService baseService;
	
	
	private Map<String, String> orderMap = new HashMap<String, String>();
	private Map<String, Object> searchMap = new HashMap<String, Object>();
	
	/**跳转到列表*/
	@RequestMapping(value="/toList.do",method = RequestMethod.GET) 
	public ModelAndView toPubZbsInfoList(){
		return new ModelAndView("pub/pubZbsInfoList");
	}
	/**列表加载数据*/
	@RequestMapping(value="/list.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object list(PageBean page,PubZbsInfo p){
		GridJsonBean grid = null;
		try{
			searchMap.put("name",p.getName());
			if(page.getProperty()==null){
				orderMap.put("id","asc");
			}else{
				orderMap.put(page.getProperty(), page.getDirection());
			}
			
			List<PubZbsInfo> list = baseService.findByPage(page, PubZbsInfo.class, searchMap, orderMap);
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
	@RequestMapping(value="/add.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object addSave(PubZbsInfo t1){
		SaveJsonBean result = new SaveJsonBean();
		try{
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
	@RequestMapping(value="/update.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object editSave(PubZbsInfo t){
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
		FormLoadJsonBean<PubZbsInfo> result = new FormLoadJsonBean<PubZbsInfo>();
		try{
			PubZbsInfo bean = baseService.getObject(PubZbsInfo.class, id);
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
				Collection<PubZbsInfo> list=new ArrayList<PubZbsInfo>(); 
				for(int i=0;i<ids.length;i++){
					PubZbsInfo t = new PubZbsInfo();
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
