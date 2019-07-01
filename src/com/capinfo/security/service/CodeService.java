package com.capinfo.security.service;


import com.capinfo.wdbl.bean.CodeInfo;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface CodeService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public abstract SearchResult<CodeInfo> getCodes( ISearch search);
	
}
