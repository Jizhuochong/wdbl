package com.capinfo.security.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Role;
import com.capinfo.security.service.AuthorityService;
import com.capinfo.security.service.RoleService;
import com.capinfo.util.PageBean;
import com.capinfo.util.TreeNodeJsonBean;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


@Controller
@RequestMapping("access/role")
public class RoleController {
	private static final Log s_logger = LogFactory.getLog(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * [展示角色管理页面]
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		
		s_logger.debug("show role list invoked");
		return "security/role";
	}
	
	/**
	 * [获取角色列表数据]
	 * @param p: 分页对象
	 * @param paramMap : 查询参数
	 * @return
	 */
	@RequestMapping(value="/json",method = RequestMethod.POST)
	public @ResponseBody() GridJsonBean query(PageBean p, @RequestParam Map<String,Object> paramMap){
		
		Search search;
		SearchResult<Role> result = null;
		try {
			s_logger.debug("query role list data invoked");
			
			search = new Search(Role.class);
			//filter
			processParamsFilter(paramMap, search);
			//sorting
			if(!StringUtils.isEmpty(p.getDir()) && !StringUtils.isEmpty(p.getSort())){
				search.addSort(p.getSort(), p.getDir().equals("desc"));
			}else{
				search.addSort("roleId", true);
			}
			//paging
			search.setFirstResult(p.getStart());
			search.setMaxResults(p.getLimit());
			
			result = roleService.getRoles(search);
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		
		return new GridJsonBean().valueOf(result);
	}
	
	/**
	 * [添加角色 ].
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean add(@Valid  @ModelAttribute("role")Role role,BindingResult result){
		try {
			s_logger.debug("add role invoked");
			if(!result.hasErrors()){
				if(roleService.add(role)){
					return new SaveJsonBean(true,"添加成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "保存失败，请稍候再试。");
	}
	
	/**
	 * [修改角色].
	 * @param id
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit/{id}",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean edit(@PathVariable Long id, @Valid @ModelAttribute("role")Role role, BindingResult result,ModelMap model){
		try {
			s_logger.debug("edit aiApplication invoked");
			 if(!result.hasErrors()) {
				if(roleService.edit(role)){
					return new SaveJsonBean(true,"修改成功。");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "修改失败，请稍候再试。");
	}
	
	/**
	 * [删除角色]
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean delete(Long[] ids){
		try{
		    s_logger.debug("delete role invoked");
		    if(roleService.delete(ids)){
				return new SaveJsonBean(true,"删除成功。");
			}
		} catch (Exception e){
		    s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "删除失败。");
	}
	
	/**
	 * [获取角色详情]
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<Role> showDetail(@PathVariable Long id){
		s_logger.debug("show role invoked");
		FormLoadJsonBean<Role> result = new FormLoadJsonBean<Role>();
		try {
			Role r = roleService.getRoleById(id);
			Assert.notNull(r);
			result.setSuccess(true);
			result.setData(r);
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * [获取可用的权限]
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/{id}/authority/json", method=RequestMethod.POST)
	public @ResponseBody List<TreeNodeJsonBean> getAvailableAuth(@PathVariable Long id){
		Search search;
		SearchResult<Authority> result = null;
		List<TreeNodeJsonBean> avaliableAuths = new ArrayList<TreeNodeJsonBean>();
		try {
			s_logger.debug("get availabe user authority invoked");
			/***1.获取系统所用权限*/
			search = new Search(Authority.class);
			//filter
			search.addFilterEqual("enabled", true);
			//sorting
			search.addSort("authorityId", false);
			result = authorityService.getAuthorities(search);
			/***2.根据用户编号获取该用户拥有的系统权限*/
			List<Authority> auths = new ArrayList<Authority>();
			Role role = roleService.getRoleById(id);
			if(role != null && role.getAuthorities()!= null){
				for (Iterator<Authority> iterator = role.getAuthorities().iterator(); iterator.hasNext();) {
					Authority authority = (Authority) iterator.next();
					auths.add(authority);
				}
			}
			for(Authority a: result.getResult()){
				TreeNodeJsonBean node = new TreeNodeJsonBean(a.getAuthorityId(),a.getAuthorityName(),auths.contains(a));
				avaliableAuths.add(node);
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return avaliableAuths;
	}
	
	/**
	 * [保存角色权限映射]
	 * @param id
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/authority/mapping/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean doUserAuthMapping(@PathVariable Long id,Long[] ids){
		try {
			s_logger.debug("save role authority mapping invoked");
			if(roleService.addAuthMapping(id,ids)){
				return new SaveJsonBean(true,"保存角色权限映射成功。");
			}
		}catch (Exception e) {
			return new SaveJsonBean(false,"保存角色权限映射失败，请稍候再试。");
		}
		return new SaveJsonBean(false, "保存角色权限映射失败，请稍候再试。");
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
		String value1,value2 = null;
		if(paramMap.containsKey(property="enabled") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterEqual(property, value1.equals("1"));//精确匹配
		}
		if(paramMap.containsKey(property="roleName") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
			search.addFilterLike(property, "%"+value1+"%");//模糊匹配
		}
		if(paramMap.containsKey(property="createTimeS") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))
				&& paramMap.containsKey(property="createTimeE") && !StringUtils.isEmpty(value2=String.valueOf(paramMap.get(property)))
				){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = null,endDate = null;
			try {
				startDate = sdf.parse(value1+" 00:00:00");
				endDate = sdf.parse(value2+" 23:59:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			search.addFilter(Filter.and(Filter.greaterOrEqual("createTime", startDate),
					Filter.lessOrEqual("createTime", endDate)));//比较匹配
		}
		
	}
}
