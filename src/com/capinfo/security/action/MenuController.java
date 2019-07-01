package com.capinfo.security.action;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.security.bean.Menu;
import com.capinfo.security.service.MenuService;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Controller
@RequestMapping("access/menu")
public class MenuController {
	private static final Log s_logger = LogFactory.getLog(MenuController.class);
	@Autowired
	private MenuService menuService;
	/**
	 * [展示菜单管理页面]
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getMenus() {
		s_logger.debug("show menu list invoked");
		return "security/menu";
	}
	
	/**
	 * [获取菜单列表数据]
	 * @param page : 分页查询条件
	 * @param paramMap : 查询参数
	 * @return
	 */
	@RequestMapping(value="/json",method = RequestMethod.POST)
	public @ResponseBody List<Menu> query(PageBean page, @RequestParam Map<String,Object> paramMap){
		
		Search search;
		List<Menu> menus = null;
		try {
			s_logger.debug("query menu list data invoked");
			
			search = new Search(Menu.class);
			//filter
			search.addFilter(Filter.isNull("parent"));
			processParamsFilter(paramMap, search);
			if(!StringUtils.isEmpty(page.getDir()) && !StringUtils.isEmpty(page.getSort())){
				search.addSort(page.getSort(), page.getDir().toLowerCase().equals("desc"));
			}else{
				search.addSort("disnum", false);
			}
			//paging
			search.setFirstResult(page.getStart());
			search.setMaxResults(page.getLimit());

			SearchResult<Menu> result = menuService.getMenus(search);
			menus = result.getResult();
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return menus;
	}
	
	/**
	 * [添加菜单 ]
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean add(@Valid  @ModelAttribute("menu")Menu menu,BindingResult result){
		try {
			s_logger.debug("add menu invoked");
			if(!result.hasErrors()){
				if(menuService.add(menu)){
					return new SaveJsonBean(true,"保存成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}

		return new SaveJsonBean(false, "保存失败，请稍候再试。");
	}
	
	/**
	 * [修改菜单]
	 * @param id
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean update(@PathVariable Long id,@Valid  @ModelAttribute("menu")Menu menu,BindingResult result,ModelMap model){
		try {
			s_logger.debug("update menu invoked");
			 if(!result.hasErrors()) {
					if(menuService.edit(menu)){
						return new SaveJsonBean(true,"修改成功。");
					}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return new SaveJsonBean(false, "修改失败，请稍候再试。");
	}
	
	/**
	 * [删除菜单]
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean delete(@PathVariable Long id){
		try {
			s_logger.debug("delete menu invoked");
			if(menuService.delete(id)){
				return new SaveJsonBean(true,"删除成功。");
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}

		return new SaveJsonBean(false, "删除失败。");
	}
	
	/**
	 * [获取菜单详情]
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<Menu> showDetail(@PathVariable Long id){
		s_logger.debug("show menu detail invoked");
		FormLoadJsonBean<Menu> result = new FormLoadJsonBean<Menu>();
		try {
			Menu m = menuService.getMenuById(id);
			Assert.notNull(m);
			result.setSuccess(true);
			result.setData(m);
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
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
		if(paramMap.containsKey(property="menuName") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterLike(property, "%"+value1+"%");/*模糊匹配*/
		}
	}
}
