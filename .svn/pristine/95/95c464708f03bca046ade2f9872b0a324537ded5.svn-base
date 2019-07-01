package com.capinfo.security.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.security.service.CodeService;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.CodeInfo;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * 字典管理
 */


@Controller  
@Scope("prototype")
@RequestMapping("/code")
public class CodeInfoController {   
	public static Logger log = Logger.getLogger(CodeInfoController.class);
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private CodeService codeService;
	
	private Map<String, String> orderMap = new HashMap<String, String>();
	private Map<String, Object> searchMap = new HashMap<String, Object>();
	
	/**跳转到列表*/
	@RequestMapping(value="/toList.do",method = RequestMethod.GET) 
	public String toList(){
		return "codeInfoList";
	}
	
	/**列表加载数据*/
	@RequestMapping(value="/listLoad.do",method = RequestMethod.POST)  
	
    public @ResponseBody Object listLoad(PageBean page, @RequestParam Map<String,Object> paramMap){
		SearchResult<CodeInfo> result = null;
		try{
			Search search = new Search(CodeInfo.class);
			//filter
		    processParamsFilter(paramMap, search);
		    //sorting
		    if(!StringUtils.isEmpty(page.getDir()) && !StringUtils.isEmpty(page.getSort())){
		    	search.addSort(page.getSort(),page.getDir().toLowerCase().equals("desc"));
		    }else{
		    	search.addSort("id", true);
		    }
			
		    //paging
			search.setFirstResult(page.getStart());
			search.setMaxResults(page.getLimit());
		    result = codeService.getCodes(search);
		} catch (Exception e) {
		   e.printStackTrace();
		}
		
		return new GridJsonBean().valueOf(result);
    }  
	
	/**表单保存*/
	@RequestMapping(value="/addSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object addSave(CodeInfo t1){
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
	@RequestMapping(value="/editSave.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object editSave(CodeInfo t){
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
		FormLoadJsonBean<CodeInfo> result = new FormLoadJsonBean<CodeInfo>();
		try{
			CodeInfo bean = baseService.getObject(CodeInfo.class, id);
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
				Collection<CodeInfo> list=new ArrayList<CodeInfo>(); 
				for(int i=0;i<ids.length;i++){
					CodeInfo t = new CodeInfo();
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
	/**加载顶级列表*/
	@RequestMapping(value="/loadParentList.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadParentList(String parent){
		GridJsonBean grid = null;
		try{
			if(parent!=null&&!parent.trim().equals("")){
				searchMap.put("parent_code", parent);
				orderMap.put("dis_num", "asc");
				List<CodeInfo> list = baseService.getByProperty(CodeInfo.class, searchMap, orderMap);
				CodeInfo c = new CodeInfo();
				c.setCode("DL");
				c.setNode("大类");
				list.add(0, c);
				grid = new GridJsonBean();
				grid.setRows(list);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
        return grid; 
    }  
	
	/**加载字典数据*/
	@RequestMapping(value="/loadCode.do",method = RequestMethod.POST)  
	@ResponseBody 
    public Object loadCode(String parent){
		GridJsonBean grid = null;
		try{
			if(parent!=null&&!parent.trim().equals("")){
				searchMap.put("parent_code", parent);
				orderMap.put("dis_num", "asc");
				List<CodeInfo> list = baseService.getByProperty(CodeInfo.class, searchMap, orderMap);
				grid = new GridJsonBean();
				grid.setRows(list);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
        return grid; 
    } 
	
	/**
	 * 处理查询参数
	 * @param paramMap
	 * @param search
	 */
	private void processParamsFilter(Map<String, Object> paramMap, Search search) {
		if (paramMap.isEmpty() || search == null || search.getSearchClass() == null){
			return;
		}
		String property=null;
		String value1 = null;
		//value2 
		if(paramMap.containsKey(property="enabled") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterEqual(property, value1.equals("1"));/*精确匹配*/
		}
		if(paramMap.containsKey(property="code") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterLike(property, "%"+value1+"%");/*模糊匹配*/
		}
		
		if(paramMap.containsKey(property="parent_code") && StringUtils.isNotEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterEqual(property, value1);
		}
	}
}  