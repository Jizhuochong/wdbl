package com.capinfo.security.service;


import com.capinfo.security.bean.User;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface UserService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract SearchResult<User> getUsers( ISearch search);
	/**
	 * 查询所有
	 * @param search
	 * @return
	 */
	public abstract SearchResult<User> get( ISearch search);
	/**
	 * 删除
	 * @param id
	 */
	public abstract boolean delete(Long[] ids);
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	public abstract User getUserById(Long id);
	/**
	 * 根据用户编号获取用户及其角色权限
	 * @param id
	 * @return
	 */
	public abstract User getUserByIdWithDetail(Long id);
	/**
	 * 添加
	 * @param user
	 */
	public abstract boolean add(User user);
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public abstract boolean doEditPwd(User user);
	
	/**
	 * 用户添加特权
	 * @param id
	 * @param ids
	 * @return
	 */
	public abstract boolean addAuthMapping(Long id, Long[] ids);
	/**
	 * 用户添加角色
	 * @param id
	 * @param ids
	 * @return
	 */
	public abstract boolean addRoleMapping(Long id, Long[] ids);
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public abstract User findUserByName(String username);
}
