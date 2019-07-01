package com.capinfo.wdbl.service.impl;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.capinfo.wdbl.api.FileManager;
import com.capinfo.wdbl.bean.CodeInfo;
import com.capinfo.wdbl.enums.DocType;

@Component
public class DefaultFileManagerImpl implements FileManager{
	private static final Log log = LogFactory.getLog(DefaultFileManagerImpl.class);
	/**
	 * 系统上传文件默认存储根路径
	 */
	private static final String SYSTEM_UPLOADFILE_BASE_PATH = "system.uploadfile.base.path";
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public String saveDocOriginalFile(MultipartFile file, DocType docType) {
		String path = "";
		try {
			path = getDocLocationPath(docType);
			File dir = new File(path);
			if(!dir.exists()){ 
				FileUtils.forceMkdir(dir);
			}
			path += file.getOriginalFilename();
			file.transferTo(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存文件失败!!!");
		}
		return path;
	}
	
	public String saveDocOriginalFile(MultipartFile file) {
		String path = "";
		try {
			path = getDocLocationPath();
			File dir = new File(path);
			if(!dir.exists()){ 
				FileUtils.forceMkdir(dir);
			}
			path += file.getOriginalFilename();
			file.transferTo(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存文件失败!!!");
		}
		return path;
	}

	public String getDocBasePath() {
		String basePath = null;
		try {
			basePath = getBasePathInfo().getNode();
			Assert.hasLength(basePath);
		} catch (Exception e) {
			basePath = "D:/data/file";
			log.warn("从数据库获取默认上传根路径失败，设置默认上传根路径为“D:/data/file”。", e);
		}
		return basePath;
	}
	
	public String getDocLocationPath(DocType docType) {
		String path = getDocBasePath();
		switch (docType) {
		case CITY_GOV_DOC:
			path += "市委市政府正式文件";
			break;
		case WIDE_POST_DOC:
			path += "大范围分发";
			break;
		case TEL_RCD_DOC:
			path += "电话记录";
			break;
		case MPS_DOC:
			path += "公安部正式文件";
			break;
		case CHIEF_APRV_DOC:
			path += "局长批示件";
			break;
		case BUR_INSIDE_DOC:
			path += "局内文件";
			break;
		case LEADER_ASND_DOC:
			path += "领导交办件";
			break;
		case PT_LEADER_DOC:
			path += "领导兼职";
			break;
		case DIRECT_POST_DOC:
			path += "直接转文";
			break;
		case PRIVATE_DOC:
			path += "亲启件";
			break;
		case OTHER_DOC:
			path += "其他";
			break;
		case MAIN_LEARDER_DOC:
			path += "主要领导文件";
			break;
		case CIPHER_TELEGRAPH_DOC:
			path += "密码电报";
			break;
		}
		
		return String.format("%s/%d/", path,System.currentTimeMillis());
	}
	
	public String getDocLocationPath() {
		String path = getDocBasePath();
		return String.format("%s/%d/", path,System.currentTimeMillis());
	}

	public boolean removeDocOriginalFile(String filePath) {
		File file = new File(filePath);
		boolean flag = false;
		if(file.isFile())
			flag = FileUtils.deleteQuietly(file.getParentFile());
		log.info(String.format("文件 “%s” 删除%s", filePath,flag?"成功":"失败"));
		return flag;
	}

	private CodeInfo getBasePathInfo() {
		CodeInfo basePathInfo = null;
		if(basePathInfo == null){
			HibernateCallback<CodeInfo> action = new HibernateCallback<CodeInfo>() {
				public CodeInfo doInHibernate(Session session) throws HibernateException,
						SQLException {
					Query q = session.createQuery("from com.capinfo.wdbl.bean.CodeInfo c where c.code=:code");
					q.setString("code", SYSTEM_UPLOADFILE_BASE_PATH);
					return (CodeInfo) q.uniqueResult();
				}
			};
			basePathInfo = hibernateTemplate.execute(action );
		}
		return basePathInfo;
	}

}
