package com.capinfo.wdbl.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.capinfo.wdbl.bean.FileSign;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface FileSignService {
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public SearchResult<FileSign> list(ISearch search);
	
	public boolean daoChu(String backTime, ISearch search,OutputStream os);
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public FileSign load(Long id);
	
	/**
	 * 修改文件登记信息
	 * @param FileSign
	 * @return
	 */
	public FileSign add(FileSign fileSign);
	
	/**
	 * 修改文件登记信息
	 * @param FileSign
	 * @return
	 */
	public void update(FileSign fileSign);
	/**
	 * 删除文件登记信息
	 * @param ids
	 */
	public void delete(Long[] ids);

}
