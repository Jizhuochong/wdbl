package com.capinfo.security.service;

import com.capinfo.security.bean.Menu;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface MenuService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract SearchResult<Menu> getMenus( ISearch search);
	/**
	 * 删除
	 * @param id
	 */
	public abstract boolean delete(Long id);
	/**
	 * 修改 
	 * @param menu
	 */
	public abstract boolean edit(Menu menu);
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	public abstract Menu getMenuById(Long id);
	/**
	 * 添加
	 * @param menu
	 */
	public abstract boolean add(Menu menu);

}
