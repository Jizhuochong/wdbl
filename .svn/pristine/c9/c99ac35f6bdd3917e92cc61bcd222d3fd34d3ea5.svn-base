package com.capinfo.wdbl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.base.dao.BaseDao;
import com.capinfo.util.InitConfig;
import com.capinfo.wdbl.bean.processConfig.ProcessInfo;
import com.capinfo.wdbl.service.ProcessInfoService;


@Service("processInfoService")
public class ProcessInfoServiceImpl implements ProcessInfoService {
	@Autowired
	private BaseDao baseDao;


	/**获取流程信息放入流程信息变量中*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED) 
	public  void initProcessConfig(){
		 List<ProcessInfo> list = baseDao.loadAll(ProcessInfo.class);
		 if(list==null||list.size()==0){
			 return;
		 }
		 for(int i=0;i<list.size();i++){
			 InitConfig.processMap.put(list.get(i).getName(), list.get(i).getProcessNodeList());
		 }
	}
}
