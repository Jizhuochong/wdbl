package com.capinfo.wdbl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.capinfo.base.service.impl.BaseServiceImpl;
import com.capinfo.wdbl.api.FileManager;
import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.OriginalFile;
import com.capinfo.wdbl.bean.file_process.FileDispense;
import com.capinfo.wdbl.bean.file_process.File_Leader;
import com.capinfo.wdbl.dao.FileDao;
import com.capinfo.wdbl.dao.FileLeaderDao;
import com.capinfo.wdbl.dao.FileRegDao;
import com.capinfo.wdbl.service.FileRegService;
import com.capinfo.wdbl.service.WsService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

@Service
public class FileRegServiceImpl extends BaseServiceImpl implements FileRegService{
	/**
	 * 允许删除原文件
	 */
	private static final boolean ALLOWD_REMOVE_ORIGINAL_FILES = true;
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private FileManager fileManager;
	
	@Autowired
	private FileRegDao fileRegDao;
	
	@Autowired
	private FileLeaderDao fielLeaderDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private WsService wsService;

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public FileReg updateFileReg(FileReg fileReg) {
		@SuppressWarnings("unchecked")
		List<OriginalFile> ofList = hibernateTemplate.find("select o from com.capinfo.wdbl.bean.OriginalFile as o where o.fileReg=?", fileReg);
		for (OriginalFile originalFile : ofList) {
			hibernateTemplate.delete(originalFile);
		}
		
		FileReg reg = hibernateTemplate.get(FileReg.class, fileReg.getId());
		Assert.notNull(reg, "未找到文件注册信息");
		
		reg.setDocNumber(fileReg.getDocNumber());
		reg.setReceiveTime(fileReg.getReceiveTime());
		reg.setSecurityGrade(fileReg.getSecurityGrade());
		reg.setUrgencyDegree(fileReg.getUrgencyDegree());
		reg.setFormUnit(fileReg.getFormUnit());
		reg.setTitle(fileReg.getTitle());
		reg.setCopies(fileReg.getCopies());
		reg.setNumberRangeStart(fileReg.getNumberRangeStart());
		reg.setNumberRangeEnd(fileReg.getNumberRangeEnd());
		reg.setKeyword(fileReg.getKeyword());
		reg.setSummary(fileReg.getSummary());
		reg.setDeadline(fileReg.getDeadline());
		reg.setRemark(fileReg.getRemark());
		reg.setOriginalFiles(fileReg.getOriginalFiles());
		hibernateTemplate.update(reg);
		return reg;
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean deleteFileReg(Long[] ids) {
		boolean rs = false;
		for (int i = 0; i < ids.length; i++) {
			if(ALLOWD_REMOVE_ORIGINAL_FILES){
				/**1.删除上传的原文件**/
				@SuppressWarnings("unchecked")
				List<OriginalFile> ofList = hibernateTemplate.find("select o from com.capinfo.wdbl.bean.OriginalFile as o where o.fileReg.id=?", ids[i]);
				for (OriginalFile originalFile : ofList) {
					fileManager.removeDocOriginalFile(originalFile.getFilePath());
				}
			}
			/**2.删除数据库记录**/
			FileReg f = hibernateTemplate.load(FileReg.class, ids[i]);
			//wsService.wsDelFile(f.getBarNo());
			hibernateTemplate.delete(f);
			rs = true;
		}
		return rs;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public boolean toSendFile(Long[] ids) {
		boolean rs = false;
		for (int i = 0; i < ids.length; i++) {
			FileReg fr = hibernateTemplate.load(FileReg.class, ids[i]);
			fr.setHandlestatus("送阅");
			if(fr != null){
				hibernateTemplate.update(fr);
			}
		}
		rs = true;
		return rs;
	}
	
	
	
	public SearchResult<FileReg> listData(ISearch search){
		return fileRegDao.searchAndCount(search);
	}
	
	public SearchResult<File_Leader> listLeaderData(ISearch search){
		return fielLeaderDao.searchAndCount(search);
	}

	public boolean delFile(Long id) {
		return fileDao.removeById(id);
	}

	public OriginalFile getFile(Long id) {
		return fileDao.find(id); 
	}

}