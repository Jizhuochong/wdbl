package com.capinfo.wdbl.service;

import java.util.List;

import com.capinfo.base.service.BaseService;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.ProcessRecord;

public interface ProcessReocordService extends BaseService{
	/**
	 *	根据文件id 分页查询 
	 */
	public  List<ProcessRecord> getListByPageAndFileId(PageBean pageBean,ProcessRecord bean,Long fileid);

	public ProcessRecord loadProcess(Long fileid);
}
