package com.capinfo.security.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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

import com.capinfo.base.service.BaseService;
import com.capinfo.pub.bean.PubExternUnitInfo;
import com.capinfo.pub.bean.PubJleaderInfo;
import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Role;
import com.capinfo.security.bean.User;
import com.capinfo.security.form.PwdForm;
import com.capinfo.security.form.UserAddForm;
import com.capinfo.security.service.AuthorityService;
import com.capinfo.security.service.RoleService;
import com.capinfo.security.service.UserService;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.util.PageBean;
import com.capinfo.util.TreeNodeJsonBean;
import com.capinfo.wdbl.util.ConstantInfo;
import com.capinfo.wdbl.util.FormLoadJsonBean;
import com.capinfo.wdbl.util.GridJsonBean;
import com.capinfo.wdbl.util.SaveJsonBean;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Controller
@RequestMapping("access/user")
public class UserController {
	private static final Log s_logger = LogFactory.getLog(UserController.class);
	@Autowired
	private BaseService baseService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	/**
	 * [展示用户管理页面]
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		s_logger.debug("show user list invoked");
		return "security/user";
	}
	/**
	 * [获取用户列表数据]
	 * @param p: 分页对象
	 * @param paramMap : 查询参数
	 * @return
	 */
	@RequestMapping(value="/json",method = RequestMethod.POST)
	public @ResponseBody GridJsonBean query(PageBean p, @RequestParam Map<String,Object> paramMap){
		
		SearchResult<User> result = null;
		try {
			s_logger.debug("query user list data invoked");
			
			Search search = new Search(User.class);
			//filter
			processParamsFilter(paramMap, search);
			//sorting
			if(!StringUtils.isEmpty(p.getDir()) && !StringUtils.isEmpty(p.getSort())){
				search.addSort(p.getSort(), p.getDir().toLowerCase().equals("desc"));
			}else{
				search.addSort("createTime", true);
			}
			//paging
			search.setFirstResult(p.getStart());
			search.setMaxResults(p.getLimit());
			
			result = userService.getUsers(search);
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		
		return new GridJsonBean().valueOf(result);
	}
	/**
	 * [添加用户 ]
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean add(@Valid  @ModelAttribute("user")UserAddForm form,BindingResult result){
		try {
			s_logger.debug("add user invoked");
			User user = userService.findUserByName(form.getUsername());
			if(user!=null&&user.getUsername().trim().equals(form.getUsername())){
				return new SaveJsonBean(false, "此用户已存在!");
			}
			if(!result.hasErrors()){
				
				User u = new User();
				u.setUsername(form.getUsername());
				u.setPassword(form.getPassword());
				u.setBindedIp(form.getBindedIp());
				u.setEnabled(form.isEnabled());
				u.setName(form.getName());
				u.setUnit(form.getUnit());
				u.setDepartment(form.getDepartment());
				u.setPosition(form.getPosition());
				if(userService.add(u)){
					return new SaveJsonBean(true,"保存成功!");
				}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}

		return new SaveJsonBean(false, "保存失败，请稍候再试。");
	}
	

	/**
	 * [修改用户]
	 * @param id
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit/{id}",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean edit(@PathVariable Long id, @Valid @ModelAttribute("user")UserAddForm form, BindingResult result){
		try {
			s_logger.debug("edit user of pwd invoked");
			 if(!result.hasErrors()) {
				 User user = userService.getUserById(id);
					user.setUsername(form.getUsername());
					user.setPassword(form.getPassword());
					user.setBindedIp(form.getBindedIp());
					user.setEnabled(form.isEnabled());
					user.setName(form.getName());
					user.setUnit(form.getUnit());
					user.setDepartment(form.getDepartment());
					user.setPosition(form.getPosition());
					if(userService.doEditPwd(user)){
						return new SaveJsonBean(true,"修改成功!");
					}
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		return new SaveJsonBean(false, "修改失败，请稍候再试。");
	}
	
	@RequestMapping(value="/password/check",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean checkPwd(@Valid @ModelAttribute("pwdform")PwdForm form, BindingResult result,ModelMap model){
		try {
			s_logger.debug("check user password invoked");
			User u = UserDetailsHelper.getUser();
			User user = userService.findUserByName(u.getUsername());
			String pwd = md5PasswordEncoder.encodePassword(form.getOldPwd(), u.getUsername());
			if(pwd.equals(user.getPassword())){
				return new SaveJsonBean(true);
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		
		return new SaveJsonBean(false);
	}
	
	/**
	 * [用户修改密码]
	 * @param id
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/password/edit",method = RequestMethod.POST)
	public @ResponseBody SaveJsonBean resetPwd(@Valid @ModelAttribute("pwdform")PwdForm form, BindingResult result,ModelMap model){
		try {
			s_logger.debug("edit user password invoked");
			User u = UserDetailsHelper.getUser();
			User user = userService.findUserByName(u.getUsername());
			String pwd = md5PasswordEncoder.encodePassword(form.getOldPwd(), u.getUsername());
			if(pwd.equals(user.getPassword())){
				if(!result.hasErrors()) {
					 u.setPassword(form.getNewPwd());
						if(userService.doEditPwd(u)){
							return new SaveJsonBean(true, "修改密码成功");
						}
			    }
			}
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}
		
		return new SaveJsonBean(false, "旧密码输入错误");
	}
	
	/**
	 * [删除用户]
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean delete(Long[] ids){
		try {
			s_logger.debug("delete user invoked");
			if(userService.delete(ids)){
				return new SaveJsonBean(true,"删除成功。");
			}
		} catch (IllegalArgumentException e) {
			return new SaveJsonBean(false, "删除失败，"+e.getMessage());
		} catch (Exception e) {
			s_logger.error(e.getMessage(),e);
		}

		return new SaveJsonBean(false, "删除失败。");
	}
	/**
	 * [获取用户详情]
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public @ResponseBody FormLoadJsonBean<User> showDetail(@PathVariable Long id){
		s_logger.debug("show user invoked");
		FormLoadJsonBean<User> result = new FormLoadJsonBean<User>();
		try {
			User u = userService.getUserById(id);
			Assert.notNull(u);
			u.setPassword(null);//密码置空
			result.setSuccess(true);
			result.setData(u);
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	/*获取所有用户名称*/
	@RequestMapping(value="/alluser", method=RequestMethod.POST)
	@ResponseBody 
	public Object Alluser()
	{
		GridJsonBean grid = null;
		try{
			List<User> list = baseService.loadAll(User.class);
			
			grid = new GridJsonBean();
			grid.setRows(list);
		}catch(Exception e){
			e.printStackTrace();
		}
        return grid; 
	}
	/**
	 * [获取可用的权限]
	 * @param id
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
			User user = userService.getUserByIdWithDetail(id);
			if(user != null && user.getAuthorities()!= null){
				for (Iterator<Authority> iterator = user.getAuthorities().iterator(); iterator.hasNext();) {
					Authority auth = (Authority) iterator.next();
					auths.add(auth);
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
	 * [保存用户权限映射]
	 * @param id
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/authority/mapping/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean doUserAuthMapping(@PathVariable Long id,Long[] ids){
		try {
			s_logger.debug("save user authority mapping invoked");
			s_logger.debug(id);
			s_logger.debug(Arrays.toString(ids));
			if(userService.addAuthMapping(id,ids)){
				return new SaveJsonBean(true,"用户特权设置成功。");
			}
		}catch (Exception e) {
			return new SaveJsonBean(false, "用户特权设置失败，请稍候再试。");
		}
		return new SaveJsonBean(false, "用户特权设置失败，请稍候再试。");
	}
	
	/**
	 * [获取可用的角色]
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/role/json", method=RequestMethod.POST)
	public @ResponseBody List<TreeNodeJsonBean> getAvailableRole(@PathVariable Long id){
		List<TreeNodeJsonBean> avaliableRoles = new ArrayList<TreeNodeJsonBean>();
		Search search;
		SearchResult<Role> result = null;
		try {
			s_logger.debug("get availabe user role invoked");
			/***1.获取系统所用角色*/
			search = new Search(Role.class);
			//filter
			search.addFilterEqual("enabled", true);
			//sorting
			search.addSort("roleId", false);
			result = roleService.getRoles(search);
			/***2.根据用户编号获取该用户拥有的系统角色*/
			List<Role> roles = new ArrayList<Role>();
			User user = userService.getUserByIdWithDetail(id);
			if(user != null && user.getRoles()!= null){
				for (Iterator<Role> iterator = user.getRoles().iterator(); iterator.hasNext();) {
					Role role = (Role) iterator.next();
					roles.add(role);
				}
			}
			for(Role r: result.getResult()){
				TreeNodeJsonBean node = new TreeNodeJsonBean(r.getRoleId(),r.getRoleName(),roles.contains(r));
				avaliableRoles.add(node);
			}
			
		} catch (Exception e) {
			s_logger.error(e.getMessage(), e);
		}
		return avaliableRoles;
	}
	
	/**
	 * [保存用户角色映射]
	 * @param paramform
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/role/mapping/{id}", method=RequestMethod.POST)
	public @ResponseBody SaveJsonBean doUserRoleMapping(@PathVariable Long id,Long[] ids){
		try {
			s_logger.debug("save user role mapping invoked");
		
			if(userService.addRoleMapping(id,ids)){
				return new SaveJsonBean(true,"用户角色设置成功。");
			}
			
		}catch (Exception e) {
			return new SaveJsonBean(false, "用户角色设置失败，请稍候再试。");
		}
		return new SaveJsonBean(false, "用户角色设置失败，请稍候再试。");
	}
	
	/**
	 * [处理查询参数]
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
			search.addFilterEqual(property, Boolean.valueOf(value1));//精确匹配
		}
		if(paramMap.containsKey(property="username") && !StringUtils.isEmpty(value1=String.valueOf(paramMap.get(property)))){
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
