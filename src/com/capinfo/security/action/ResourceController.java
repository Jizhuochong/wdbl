package com.capinfo.security.action;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.security.bean.Resource;
import com.capinfo.security.service.ResourceService;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Controller
@RequestMapping("access/resource")
public class ResourceController {
	private static final Log s_logger = LogFactory.getLog(ResourceController.class);
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getResources() {
		
		s_logger.debug("show resource list invoked");
		return "security/resource";
	}
	
	/**
	 * [获取用户列表数据]
	 * @param p: 分页对象
	 * @param paramMap : 查询参数
	 * @return
	 */
	@RequestMapping(value="/json",method = RequestMethod.POST)
	public @ResponseBody GridJsonBean query(PageBean p, @RequestParam Map<String,Object> paramMap){
		
		Search search = null;
		SearchResult<Resource> result = null;
		try {
			s_logger.debug("query resource list data invoked");
			
			search = new Search(Resource.class);
			//filter
			processParamsFilter(paramMap, search);
			//sorting
			if(!StringUtils.isEmpty(p.getDir()) && !StringUtils.isEmpty(p.getSort())){
				search.addSort(p.getSort(), p.getDir().toLowerCase().equals("desc"));
			}else{
				search.addSort("resourceId", true);
			}
			//paging
			search.setFirstResult(p.getStart());
			search.setMaxResults(p.getLimit());
			
			result = resourceService.getResources(search);
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return new GridJsonBean().valueOf(result);
	}
	
	/**
	 * [添加资源].
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean add(@Valid  @ModelAttribute("resource")Resource resource,BindingResult result){
		try {
			s_logger.debug("add resource invoked");
			if(!result.hasErrors()){
				if(resourceService.add(resource)){
					return new SaveJsonBean(true,"保存成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "保存失败，请稍候再试。");
	}
	
	/**
	 * [修改资源].
	 * @param id
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean update(@PathVariable Long id,@Valid  @ModelAttribute("resourceform")Resource resource,BindingResult result){
		try {
			s_logger.debug("update resource invoked");
			 if(!result.hasErrors()) {
				if(resourceService.edit(resource)){
					return new SaveJsonBean(true,"修改成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "修改失败，请稍候再试。");
	}
	
	/**
	 * [删除资源].
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean delete(Long[] ids){
		try {
			s_logger.debug("delete resource invoked");
			if(resourceService.delete(ids)){
				return new SaveJsonBean(true,"删除成功。");
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "删除失败。");
	}
	
	/**
	 * [获取资源详情].
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<Resource> showDetail(@PathVariable Long id){
		s_logger.debug("show resource detail invoked");
		FormLoadJsonBean<Resource> result = new FormLoadJsonBean<Resource>();
		try {
			Resource r = resourceService.getResourceById(id);
			Assert.notNull(r);
			result.setData(r);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(),e);
			result.setSuccess(false);
		}
		return result;
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
		if(paramMap.containsKey(property="enabled") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterEqual(property, value1.equals("1"));/*精确匹配*/
		}
		if(paramMap.containsKey(property="resourceName") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterLike(property, "%"+value1+"%");/*模糊匹配*/
		}
	}
}
