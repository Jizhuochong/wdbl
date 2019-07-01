package com.capinfo.wdbl.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.wdbl.api.LeaderNumberManager;
import com.capinfo.wdbl.bean.LeNumber;

@Component
public class LeaderNumberManagerImpl implements LeaderNumberManager{
	 private static final Log log =LogFactory.getLog(LeaderNumberManagerImpl.class);
	 @Autowired
	 private HibernateTemplate hibernateTemplate;
	 @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	 public synchronized String generateLN(){
		 LeNumber l =new LeNumber();
		 hibernateTemplate.save(l);
			Long lead = l.getId();
			String leadhao="小洪"+lead+"号";
			String le = String.format(leadhao);
		 l.setfileNumber(le);
		 hibernateTemplate.update(l);
		 log.debug("生成领导文号:"+le);
		 return le;
	 }
 
}
