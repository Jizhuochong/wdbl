package com.capinfo.security.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Role;
import com.capinfo.security.dao.AuthorityDao;
import com.capinfo.security.dao.RoleDao;
import com.capinfo.security.service.RoleService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
@Service("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;

	public SearchResult<Role> getRoles(ISearch search) {
		return roleDao.searchAndCount(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(Long[] ids) {
		if(ids!=null && ids.length>0){
			roleDao.removeByIds(ids);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean edit(Role role) {
	    Role r = roleDao.find(role.getRoleId());
	    if(r != null){
		r.setRoleName(role.getRoleName());
		r.setRoleDesc(role.getRoleDesc());
		r.setEnabled(role.getEnabled());
		r.setUpdateTime(new Date());
		roleDao.save(r);
		return true;
	    }
	    return false;
	}

	public Role getRoleById(Long id) {
		return roleDao.find(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean add(Role role) {
	    Date now = new Date();
	    role.setCreateTime(now);
	    role.setUpdateTime(now);
	    return roleDao.save(role);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean addAuthMapping(Long id, Long[] ids) {
		Role role = roleDao.find(id);
		Set<Authority> authorities = new HashSet<Authority>();
		if(role != null){
			if(ids!=null && ids.length> 0){
				for (int i = 0; i < ids.length; i++) {
					Authority authority = authorityDao.find(ids[i]);
					authorities.add(authority);
				}
			}
			role.setAuthorities(authorities);
			role.setUpdateTime(new Date());
			roleDao.save(role);
			return true;
		}
		return false;
	}

}
