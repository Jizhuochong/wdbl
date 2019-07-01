package com.capinfo.wdbl.service;

import com.capinfo.base.service.BaseService;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.OriginalFile;
import com.capinfo.wdbl.bean.file_process.FileDispense;
import com.capinfo.wdbl.bean.file_process.File_Leader;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public interface FileRegService extends BaseService {
	/**
	 * 修改文件登记信息
	 * @param fileReg
	 * @return
	 */
	FileReg updateFileReg(FileReg fileReg);
	/**
	 * 删除文件登记信息
	 * @param ids
	 */
	boolean deleteFileReg(Long[] ids);
	/**
	 * 送阅
	 * @param ids
	 */
	public boolean toSendFile(Long[] ids);
	
	/**
	 * 查询
	 * @param search
	 * @return
	 */
	public SearchResult<FileReg> listData(ISearch search);
	
	/**
	 * 查询批示领导记录
	 * @param search
	 * @return
	 */
	public SearchResult<File_Leader> listLeaderData(ISearch search);
	
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	public boolean delFile(Long id);
	
	public OriginalFile getFile(Long id);

}
