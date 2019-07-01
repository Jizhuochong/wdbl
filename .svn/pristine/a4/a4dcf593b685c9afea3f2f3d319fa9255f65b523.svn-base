package com.capinfo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.security.dao.CodeDao;
import com.capinfo.security.service.CodeService;
import com.capinfo.wdbl.bean.CodeInfo;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
@Service("codeService")
@Transactional(readOnly = true)
public class CodeServiceImpl implements CodeService {
	@Autowired
	private CodeDao codeDao;

	public SearchResult<CodeInfo> getCodes(ISearch search) {
		return codeDao.searchAndCount(search); 
	}

	
	
}
