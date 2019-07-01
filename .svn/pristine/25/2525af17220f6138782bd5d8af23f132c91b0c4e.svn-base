package com.capinfo.security.service;


import com.capinfo.security.bean.Authority;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public interface AuthorityService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public SearchResult<Authority> getAuthorities( ISearch search);
	/**
	 * 删除
	 * @param ids
	 */
	public boolean delete(Long[] ids);
	/**
	 * 修改 
	 * @param user
	 */
	public boolean edit(Authority authority);
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	public Authority getAuthorityById(Long id);
	/**
	 * 添加
	 * @param role
	 */
	public boolean add(Authority authority);
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public Authority queryAuthority(Search search);
	public int count(Search search);
	/**
	 * 获取权限及其附属资源
	 * @param id 权限编号
	 * @return
	 */
	public Authority getAuthorityByIdWithResource(Long id);
	/**
	 * 
	 * @param id 权限编号
	 * @param ids 资源编号集合
	 * @return
	 */
	public boolean addResMapping(Long id, Long[] ids);
	/**
	 * 获取权限及其附属菜单
	 * @param id 权限编号
	 * @return
	 */
	public Authority getAuthorityByIdWithMenu(Long id);
	/**
	 * 
	 * @param id 权限编号
	 * @param ids 菜单编号集合
	 * @return
	 */
	public boolean addMenuMapping(Long id, Long[] ids);
}
