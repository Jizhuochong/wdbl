package com.capinfo.wdbl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capinfo.base.service.impl.BaseServiceImpl;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.dao.ProcessRecordDao;
import com.capinfo.wdbl.service.ProcessReocordService;

@Service("processRecordService")
public class ProcessRecordServiceImpl extends BaseServiceImpl implements ProcessReocordService{
	@Autowired
	private ProcessRecordDao processRecordDao;
	
	/**
	 *	根据文件id 分页查询 
	 */
	public  List<ProcessRecord> getListByPageAndFileId( PageBean pageBean, ProcessRecord bean, Long fileid){
		return this.processRecordDao.getListByPageAndFileId(pageBean, bean, fileid);
	}

	@Override
	public ProcessRecord loadProcess(Long fileid) {
		return processRecordDao.loadProcess(fileid);
	}
}