package com.capinfo.wdbl.service.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.sys.ConfigINFO;
import com.capinfo.wdbl.api.SerialNumberManager;
import com.capinfo.wdbl.bean.SeqNum;
import com.capinfo.wdbl.bean.Sn;
import com.capinfo.wdbl.dao.SeqNumDao;
import com.googlecode.genericdao.search.Search;

@Component
public class SerialNumberManagerImpl implements SerialNumberManager{
	
	private static final Log log = LogFactory.getLog(SerialNumberManagerImpl.class);
	private static final int SN_LENGTH = 8;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private SeqNumDao seqNumDao;
	
	@Autowired
	private ConfigINFO configINFO;
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized String generateSN() {
		Sn s = new Sn();
		hibernateTemplate.save(s);
		String prefix = DateFormatUtils.format(System.currentTimeMillis(), "yy");//年月日(6位)
		Long suffix = s.getId();
		String format = "%s%0"+(configINFO.getFileSnLength()-prefix.length())+"d";
		String sn = String.format(format, prefix,suffix);
		s.setSn(sn);
		hibernateTemplate.update(s);
		log.debug("生成文件流水号:"+sn);
		return sn;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized String genFileSN() {
		String year = DateFormatUtils.format(System.currentTimeMillis(), "yy");//年
		Search search = new Search(SeqNum.class);
		search.addFilterEqual("seqType", "FILE_SN");
		search.addFilterEqual("year",year);
		SeqNum sn = seqNumDao.searchUnique(search);
		if(sn == null){
			sn = new SeqNum();
			sn.setSeqNum(1L);
			sn.setSeqType("FILE_SN");
			sn.setYear(year);
		}else{
			sn.setSeqNum(sn.getSeqNum()+1);
		}
		seqNumDao.save(sn);
		Long suffix = sn.getSeqNum();
		String format = "%s%0"+(configINFO.getFileSnLength()-year.length())+"d";
		String strSn = String.format(format, year,suffix);
		return strSn;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized String genFileBarNo() {
		String year = DateFormatUtils.format(System.currentTimeMillis(), "yy");//年
		Search search = new Search(SeqNum.class);
		search.addFilterEqual("seqType", "FILE_BAR_NO");
		search.addFilterEqual("year",year);
		SeqNum sn = seqNumDao.searchUnique(search);
		if(sn == null){
			sn = new SeqNum();
			sn.setSeqNum(1L);
			sn.setSeqType("FILE_BAR_NO");
			sn.setYear(year);
		}else{
			sn.setSeqNum(sn.getSeqNum()+1);
		}
		seqNumDao.save(sn);
		Long suffix = sn.getSeqNum();
		//GA+部门code+标识字母+6位顺号
		String format = "GA"+configINFO.getWdDeptCode()+"%s"+configINFO.getBarCodeChar()+"%0"+configINFO.getBarCodeLength()+"d";
		String strSn = String.format(format, year,suffix);
		log.debug("生成文件barCode号:"+sn);
		return strSn;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public synchronized String genLeaderSN() {
		String value = configINFO.getLeaderChar();
		try {
			value =new String(value.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Search search = new Search(SeqNum.class);
		search.addFilterEqual("seqType", "Leader_SN");
		search.addFilterEqual("year",value);
		SeqNum sn = seqNumDao.searchUnique(search);
		if(sn == null){
			sn = new SeqNum();
			sn.setSeqNum(1L);
			sn.setSeqType("Leader_SN");
			sn.setYear(value);
		}else{
			sn.setSeqNum(sn.getSeqNum()+1);
		}
		seqNumDao.save(sn);
		
		return value+sn.getSeqNum()+"号";
	}
	
}
