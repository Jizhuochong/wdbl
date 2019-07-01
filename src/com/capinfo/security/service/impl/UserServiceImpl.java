package com.capinfo.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Menu;
import com.capinfo.security.bean.Role;
import com.capinfo.security.bean.User;
import com.capinfo.security.dao.AuthorityDao;
import com.capinfo.security.dao.RoleDao;
import com.capinfo.security.dao.UserDao;
import com.capinfo.security.service.UserService;
import com.capinfo.security.tool.MenuComparator;
import com.capinfo.security.userdetails.UserDetailsImpl;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final Log log = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Search search = new Search(User.class);
		search.addFetch("roles");
		search.addFetch("authorities");
		search.addFilterEqual("username", username);
		User user = userDao.searchUnique(search);
		if (user == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		UserDetails userDetails = new UserDetailsImpl(user, getMenus(user));
		return userDetails;
	}

	public SearchResult<User> getUsers(ISearch search) {
		((Search)search).addFilterEqual("state", "1");
		return userDao.searchAndCount(search);
	}
	//查询所有的方法
	public SearchResult<User> get(ISearch search) {
		return userDao.searchAndCount(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(Long[] ids) {
		if(ids!=null && ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				User user = getUserById(ids[i]);
				Assert.isTrue(!user.getUsername().toLowerCase().equals("admin"), "超级管理员账户不允许删除");
				user.setState("3");
				userDao.save(user);
			}
			return true;
		}
		return false;
	}

	public User getUserById(Long id) {
		return userDao.find(id);
	}

	public User getUserByIdWithDetail(Long id) {
		Search search = new Search(User.class);
		search.addFetch("roles");
		search.addFetch("authorities");
		search.addFilterEqual("userId", id);
		return userDao.searchUnique(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean add(User user) {
		Date now = new Date();
		user.setCreateTime(now);
		user.setUpdateTime(now);
		user.setState("1");
		user.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(),
				user.getUsername()));
		return userDao.save(user);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean doEditPwd(User user) {
		User u = null;
		if (user != null && user.getUserId() != null) {
			u = userDao.find(user.getUserId());
		}
		if (u != null) {

			u.setUsername(user.getUsername());
			u.setEnabled(user.getEnabled());
			u.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(),
					user.getUsername()));
			u.setUpdateTime(new Date());
			userDao.save(u);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean addAuthMapping(Long id, Long[] ids) {
		User user = userDao.find(id);
		Assert.notNull(user);
		Set<Authority> authorities = new HashSet<Authority>();
		if (user != null) {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					Authority authority = authorityDao.find(ids[i]);
					authorities.add(authority);
				}
			}
			user.setAuthorities(authorities);
			user.setUpdateTime(new Date());
			userDao.save(user);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean addRoleMapping(Long id, Long[] ids) {
		User user = userDao.find(id);
		Assert.notNull(user);
		Set<Role> roles = new HashSet<Role>();
		if (user != null) {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					Role role = roleDao.find(ids[i]);
					roles.add(role);
				}
			}
			user.setRoles(roles);
			userDao.save(user);
			user.setUpdateTime(new Date());
			return true;
		}
		return false;
	}

	/**
	 * 获取菜单
	 * 
	 * @param user
	 * @return
	 */
	private List<Menu> getMenus(User user) {
		Set<Menu> ancestors = new HashSet<Menu>();
		Set<Menu> sons = new HashSet<Menu>();
		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuthorities()) {
				for (Menu menu : authority.getMenus()) {
					if(null == menu.getParent()){
						ancestors.add(menu);
					}else{
						sons.add(menu);
					}
				}
			}
		}
		for (Authority authority : user.getAuthorities()) {
			for (Menu menu : authority.getMenus()) {
				if(null == menu.getParent()){
					ancestors.add(menu);
				}else{
					sons.add(menu);
				}
			}
		}
		for (Menu ancestor : ancestors) {
			foundThereSons(ancestor, sons);
		}
		List<Menu> menuList = new ArrayList<Menu>(ancestors);
		Collections.sort(menuList, new MenuComparator());
		log.debug("top menu size:"+menuList.size());
		return menuList;
	}
	
	public User findUserByName(String username) {
		Search search = new Search(User.class);
		search.addFilterEqual("username",username);
		return userDao.searchUnique(search);
	}

	/**
	 * 找到他们的后代
	 * @param sons 后代
	 * @param ancestor 祖先
	 */
	private void foundThereSons(Menu ancestor, Set<Menu> sons) {
		Set<Menu> _sons = new HashSet<Menu>();
		for (Menu son : sons) {
			if(son.getParent().getMenuId().equals(ancestor.getMenuId())){
				_sons.add(son);
			}
		}
		for (Menu _son : _sons) {
			sons.remove(_son);
		}
		List<Menu> sonList = new ArrayList<Menu>(_sons);
		Collections.sort(sonList, new MenuComparator());
		ancestor.setChildren(sonList);
		
		for (Menu _ancestor : _sons) {
			foundThereSons(_ancestor, sons);
		}
	}
	
}
