package com.capinfo.wdbl.dao;

import java.util.List;

import com.capinfo.base.dao.BaseDao;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.ProcessRecord;

public interface ProcessRecordDao extends BaseDao{
	/**
	 *	根据文件id 分页查询 
	 */
	public  List<ProcessRecord> getListByPageAndFileId( PageBean pageBean, ProcessRecord bean, Long fileid);

	public ProcessRecord loadProcess(Long fileid);
}
