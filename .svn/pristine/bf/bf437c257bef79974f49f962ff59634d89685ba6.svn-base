package com.capinfo.security.service;

import com.capinfo.security.bean.Role;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface RoleService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract SearchResult<Role> getRoles( ISearch search);
	/**
	 * 删除
	 * @param ids
	 */
	public abstract boolean delete(Long[] ids);
	/**
	 * 修改 
	 * @param user
	 */
	public abstract boolean edit(Role role);
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	public abstract Role getRoleById(Long id);
	/**
	 * 添加
	 * @param role
	 */
	public abstract boolean add(Role role);
	/**
	 * 保存角色权限映射
	 * @param id
	 * @param ids
	 * @return
	 */
	public abstract boolean addAuthMapping(Long id, Long[] ids);
	
}
