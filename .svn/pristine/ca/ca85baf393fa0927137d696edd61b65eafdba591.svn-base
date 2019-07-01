package com.capinfo.security.service;

import com.capinfo.security.bean.Resource;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface ResourceService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract SearchResult<Resource> getResources( ISearch search);
	/**
	 * 删除
	 * @param ids
	 */
	public abstract boolean delete(Long[] ids);
	/**
	 * 修改 
	 * @param resource
	 */
	public abstract boolean edit(Resource resource);
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	public abstract Resource getResourceById(Long id);
	/**
	 * 添加
	 * @param resource
	 */
	public abstract boolean add(Resource resource);
	
}
