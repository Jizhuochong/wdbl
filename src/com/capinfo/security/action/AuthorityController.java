package com.capinfo.security.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Menu;
import com.capinfo.security.bean.Resource;
import com.capinfo.security.service.AuthorityService;
import com.capinfo.security.service.MenuService;
import com.capinfo.security.service.ResourceService;
import com.capinfo.util.ComparatorMenu;
import com.capinfo.util.ComparatorTreeMenu;
import com.capinfo.util.MenuTreeModel;
import com.capinfo.util.PageBean;
import com.capinfo.util.TreeNodeJsonBean;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Controller
@RequestMapping("access/authority")
public class AuthorityController {
	private static final Log s_logger = LogFactory.getLog(AuthorityController.class);
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private MenuService menuService;
	
	/**
	 * [展示权限管理页面]
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getAuth(){
		s_logger.debug("show authotity list invoked");
		return "security/authority";
	}
	
	/**
	 * [获取权限列表数据]
	 * @param page : 分页条件
	 * @param paramMap : 查询参数
	 * @return
	 */
	@RequestMapping(value="/json",method = RequestMethod.POST)
	public @ResponseBody GridJsonBean query(PageBean page, @RequestParam Map<String,Object> paramMap){
		SearchResult<Authority> result = null;
		try {
		    s_logger.debug("query authority list data invoked");
		    Search search = new Search(Authority.class);
		    //filter
		    processParamsFilter(paramMap, search);
		    //sorting
		    if(!StringUtils.isEmpty(page.getDir()) && !StringUtils.isEmpty(page.getSort())){
		    	search.addSort(page.getSort(),page.getDir().toLowerCase().equals("desc"));
		    }else{
			search.addSort("authorityId", true);
		    }
		    //paging
			search.setFirstResult(page.getStart());
			search.setMaxResults(page.getLimit());
		    result = authorityService.getAuthorities(search);
		} catch (Exception e) {
		    s_logger.error(e.getMessage(),e);
		}
		
		return new GridJsonBean().valueOf(result);
	}
	
	/**
	 * [添加权限 ].
	 * @param authority
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean add(@Valid  @ModelAttribute("authority")Authority authority,BindingResult result){
		try {
			s_logger.debug("add authority invoked");
			if(!result.hasErrors()){
				if(authorityService.add(authority)){
					return new SaveJsonBean(true,"保存成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}

		return new SaveJsonBean(false, "保存失败，请稍候再试。");
	}
	/**
	 * [修改权限].
	 * @param id
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean update(@PathVariable Long id,@Valid  @ModelAttribute("authorityform")Authority authority,BindingResult result){
		try {
			s_logger.debug("update authority invoked");
			if(!result.hasErrors()) {
				if(authorityService.edit(authority)){
					return new SaveJsonBean(true,"修改成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "修改失败，请稍候再试。");
	}
	
	/**
	 * [删除权限]
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean delete(Long[] ids){
		try{
		    s_logger.debug("delete authority invoked");
		    if(authorityService.delete(ids)){
		    	return new SaveJsonBean(true,"删除成功。");
		    }
		}catch (Exception e){
		    s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "删除失败。");
	}
	
	/**
	 * [获取权限详情]
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<Authority> showDetail(@PathVariable Long id){
		s_logger.debug("show authority invoked");
		FormLoadJsonBean<Authority> result = new FormLoadJsonBean<Authority>();
		try {
			Authority a = authorityService.getAuthorityById(id);
			Assert.notNull(a);
			result.setSuccess(true);
			result.setData(a);
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * [获取可用的资源]
	 * @param id :资源编号
	 * @return
	 */
	@RequestMapping(value="/{id}/resource/json", method=RequestMethod.POST)
	public @ResponseBody List<TreeNodeJsonBean> getAvailableRes(@PathVariable Long id){
		Search search;
		SearchResult<Resource> result = null;
		List<TreeNodeJsonBean> avaliableRes = new ArrayList<TreeNodeJsonBean>();
		ComparatorTreeMenu menutreemodel = new ComparatorTreeMenu();
		Collections.sort(avaliableRes, menutreemodel);
		try {
			s_logger.debug("get availabe authority resourece invoked");
			/***1.获取系统所用资源*/
			search = new Search(Resource.class);
			//filter
			search.addFilterEqual("enabled", true);
			//sorting
			search.addSort("resourceId", false);
			result = resourceService.getResources(search);
			/***2.根据权限编号获取该权限可以访问的系统资源*/
			List<Resource> resources = new ArrayList<Resource>();
			Authority authority = authorityService.getAuthorityByIdWithResource(id);
			for (Iterator<Resource> iterator = authority.getResources().iterator(); iterator.hasNext();) {
				Resource resource = (Resource) iterator.next();
				resources.add(resource);
			}
			for(Resource r: result.getResult()){
				TreeNodeJsonBean node = new TreeNodeJsonBean(r.getResourceId(),r.getResourceName(),resources.contains(r));
				avaliableRes.add(node);
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return avaliableRes;
	}
	
	/**
	 * [保存权限资源映射]
	 * @param id
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/resource/mapping/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean doAuthResMapping(@PathVariable Long id,Long[] ids){
		try {
			s_logger.debug("save authority resource mapping invoked");
			s_logger.debug(id);
			s_logger.debug(Arrays.toString(ids));
			if(authorityService.addResMapping(id,ids)){
				return new SaveJsonBean(true,"权限资源映射保存成功。");
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "权限资源映射保存失败，请稍候再试。 ");
	}
	
	
	/**
	 * [获取可用的菜单]
	 * @param id:Long 资源编号
	 * @return
	 */
	@RequestMapping(value="/{id}/menu/json", method=RequestMethod.POST)
	public @ResponseBody List<TreeNodeJsonBean> getAvailableMenu(@PathVariable Long id){
		Search search;
		SearchResult<Menu> result = null;
		List<TreeNodeJsonBean> avaliableMenus = new ArrayList<TreeNodeJsonBean>();
		ComparatorTreeMenu menutreemodel = new ComparatorTreeMenu();
		Collections.sort(avaliableMenus, menutreemodel);
		try {
			s_logger.debug("get availabe authority menu invoked");
			/***1.获取系统所用菜单*/
			search = new Search(Menu.class);
			//filter
			search.addFilter(Filter.isNull("parent"));
			//sorting
			search.addSort("menuId", false);
			result = menuService.getMenus(search);
			/***2.根据权限编号获取该权限可以访问的系统菜单*/
			List<Menu> menus = new ArrayList<Menu>();
			Authority authority = authorityService.getAuthorityByIdWithMenu(id);
			for (Iterator<Menu> iterator = authority.getMenus().iterator(); iterator.hasNext();) {
				Menu menu = (Menu) iterator.next();
				menus.add(menu);
			}
			for(Menu m: result.getResult()){
				TreeNodeJsonBean node = new TreeNodeJsonBean(m.getMenuId(),m.getMenuName(),m.isLeaf(),menus.contains(m));
				addChildren(m, node, menus);
				avaliableMenus.add(node);
			}
			
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return avaliableMenus;
	}
	/**
	 * 递归遍历选中菜单及其子子孙孙菜单
	 */
	private void addChildren(Menu m,TreeNodeJsonBean n,List<Menu> checkedMenus){
		if(m.getChildren() != null && m.getChildren().size() > 0){
			List<TreeNodeJsonBean> children = new ArrayList<TreeNodeJsonBean>();
			ComparatorTreeMenu compartormenu = new ComparatorTreeMenu();
			Collections.sort(children, compartormenu);
			for (Menu c : m.getChildren()) {
				TreeNodeJsonBean c_node = new TreeNodeJsonBean(c.getMenuId(),c.getMenuName(),c.isLeaf(),checkedMenus.contains(c));
				children.add(c_node);
				addChildren(c, c_node, checkedMenus);
			}
			n.setChildren(children);
		}
	}
	
	/**
	 * [保存权限资源映射]
	 * @param id
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/menu/mapping/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean doAuthMenuMapping(@PathVariable Long id,Long[] ids){
		try {
			s_logger.debug("save authority menu mapping invoked");
			s_logger.debug(id);
			s_logger.debug(Arrays.toString(ids));
			if(authorityService.addMenuMapping(id,ids)){
				return new SaveJsonBean(true,"权限菜单映射保存成功。");
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "权限菜单映射保存失败，请稍候再试。 ");
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
		if(paramMap.containsKey(property="authorityName") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterLike(property, "%"+value1+"%");/*模糊匹配*/
		}
	}
}
