package com.capinfo.wdbl.service;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.Document;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.bean.file_process.FileLeader;
import com.capinfo.wdbl.dao.FileRegDao;
import com.capinfo.wdbl.enums.DocType;
import com.capinfo.wdbl.util.BarCodeUtil;
import com.capinfo.wdbl.util.WordOperator;

@Service
public class FileRegExportServiceImpl implements FileRegExportService {
	private static final Log log = LogFactory.getLog(FileRegExportServiceImpl.class);

	protected static final String TPL_QUERY_SQL = "select t.* from doc_export_tpl t where t.doc_type_val=?";
	private static final Object TPL_FIELD_NAME = "TPL_FILENAME";
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	FileRegDao fielRegDao;
	
	
	
	/**
	 * 生成密码电报word导出文件
	 */
	public File getGenerateTelegraphExportFile(int docTypeVal, Long id) {
		FileReg fr = fielRegDao.find(id);
		File tpl = null, gen = null;
		
		// 获取模板文件
		tpl = getTplPathByDocTypeVal(docTypeVal);
		gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
		
		// 获取导出内容数据
		Map<String, String> map = getFilledUpTplDataModel(docTypeVal, id);
		
		try {
			if(map.get("DOC_NUMBER") != null && map.get("DOC_NUMBER").equals("﹝﹞")) {
				map.put("DOC_NUMBER", "");
			}
			if(map.get("RECEIVE_TIME") != null){
				String v = map.get("RECEIVE_TIME");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				DateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
				Date d = df.parse(v);
				map.put("RECEIVE_TIME", df2.format(d));
			}
			if("无".equals(map.get("SECURITY_GRADE")) || map.get("SECURITY_GRADE") == null){
				map.put("SECURITY_GRADE", "");
			}
			if("无".equals(map.get("URGENCY_DEGREE")) || map.get("URGENCY_DEGREE") == null){
				map.put("URGENCY_DEGREE", "");
			}
			WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map);
			
			// 替换模板中的标记内容
			oper.processText();
			
			// 模板标记位置插入图片
			oper.processTelegraphPicture("barCode", BarCodeUtil.createBarCode(fr), Document.PICTURE_TYPE_BMP, 0d, 0d);
			oper.outFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gen;
	}
	
	/**
	 * 生成电话记录word导出文件
	 */
	public File getGeneratePhoneRecordExportFile(int docTypeVal, Long id) {
		FileReg fr = fielRegDao.find(id);
		File tpl = null, gen = null;
		
		// 获取模板文件
		tpl = getTplPathByDocTypeVal(docTypeVal);
		gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
		
		// 获取导出内容数据
		Map<String, String> map = getFilledUpTplDataModel(docTypeVal, id);
		
		try {
			if(map.get("RECEIVE_TIME") != null){
				String v = map.get("RECEIVE_TIME");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				DateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
				Date d = df.parse(v);
				map.put("RECEIVE_TIME", df2.format(d));
			}
			if("无".equals(map.get("SECURITY_GRADE")) || map.get("SECURITY_GRADE") == null){
				map.put("SECURITY_GRADE", "");
			}
			if("无".equals(map.get("URGENCY_DEGREE")) || map.get("URGENCY_DEGREE") == null){
				map.put("URGENCY_DEGREE", "");
			}
			WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map);
			
			// 替换模板中的标记内容
			oper.processText();
			
			// 模板标记位置插入图片
			oper.processPhonePicture("barCode", BarCodeUtil.createBarCode(fr), Document.PICTURE_TYPE_BMP, 0d, 0d);
			oper.outFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gen;
	}
	
	// 公文阅批单
	public File getGenerateExportFile(int docTypeVal, Long id) {
		FileReg fr = fielRegDao.find(id);
		File tpl = null, gen = null;
		tpl = getTplPathByDocTypeVal(docTypeVal);
		gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
		Map<String, String> map = getFilledUpTplDataModel(docTypeVal, id);
		Map<String, String> draftMap = getDraftOp(id);
		
		try {
			if(map.get("DOC_NUMBER") != null && map.get("DOC_NUMBER").equals("﹝﹞")) {
				map.put("DOC_NUMBER", "");
			}
			if(map.get("RECEIVE_TIME") != null){
				String v = map.get("RECEIVE_TIME");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				v = StringUtils.split(v, " ")[0];
				DateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");
				Date d = df.parse(v);
				map.put("RECEIVE_TIME", df2.format(d));
			}
			if("无".equals(map.get("SECURITY_GRADE")) || map.get("SECURITY_GRADE") == null){
				map.put("SECURITY_GRADE", "");
			}
			if("无".equals(map.get("URGENCY_DEGREE")) || map.get("URGENCY_DEGREE") == null){
				map.put("URGENCY_DEGREE", "");
			}
			if(draftMap != null && draftMap.get("DRAFT_OP") != null){
				map.put("DRAFT_OP", draftMap.get("DRAFT_OP"));
			} else {
				map.put("DRAFT_OP", "");
			}
			WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map);
			oper.processText();
			oper.processPicture("barCode", BarCodeUtil.createBarCode(fr), Document.PICTURE_TYPE_BMP, 0d, 0d);
			oper.outFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gen;
	}
	
	// 批示抄清
	public File getGenerateInstructionsExportFiles(int docTypeVal, Long id) {
		File tpl = null, gen = null;
		tpl = getTplPathByDocTypeVal(docTypeVal);
		gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
		Map<String, String> map = getFilledUpTplDataModel(docTypeVal, id);
		Map<String, String> leaderMap = getLeader(id);
		
		try {
			if(leaderMap != null && leaderMap.get("LEADER") != null){
				map.put("LEADER", leaderMap.get("LEADER"));
			} else {
				map.put("LEADER", "");
			}
			if(leaderMap != null && leaderMap.get("LEADEROPINION") != null){
				map.put("LEADEROPINION", leaderMap.get("LEADEROPINION"));
			} else {
				map.put("LEADEROPINION", "");
			}
			if(leaderMap != null && leaderMap.get("JOB") != null){
				map.put("JOB", leaderMap.get("JOB"));
			} else {
				map.put("JOB", "");
			}
			WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map);
			oper.processText();
			oper.outFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gen;
	}

	private File getTplPathByDocTypeVal(int docTypeVal) {
		File tpl = null;
		String tplPath = Thread.currentThread().getContextClassLoader().getResource("templates").getPath() + getTplFilenameByDocTypeVal(docTypeVal);
		tpl = FileUtils.getFile(tplPath);
		return tpl;
	}

	private String getTplFilenameByDocTypeVal(final int docTypeVal) {
		HibernateCallback<Map<String, Object>> action = new HibernateCallback<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			public Map<String, Object> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.createSQLQuery(TPL_QUERY_SQL);
				q.setInteger(0, docTypeVal);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return (Map<String, Object>) q.uniqueResult();
			}
		};
		Map<String, Object> rs = hibernateTemplate.execute(action);
		return (String) rs.get(TPL_FIELD_NAME);
	}

	public Map<String, String> getFilledUpTplDataModel(final int docTypeVal, final Long id) {
		final String sqlQueryA = "select t.SN,t.FORM_UNIT,t.DOC_NUMBER,t.COPIES,t.TITLE,t.RECEIVE_TIME,T.SECURITY_GRADE,T.URGENCY_DEGREE from file_reg t  where t.id=?";
		final String sqlQueryB = "select t.SN,t.FORM_UNIT,t.HANDLE_UNIT,t.TELEPHONE,t.PHONE_PERSON," +
				"t.HANDLE_OPERATOR,t.TITLE,t.RECEIVE_TIME,t.HANDLE_SITUATION from file_reg t where t.id=?";
		HibernateCallback<Map<String, String>> action = new HibernateCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public Map<String, String> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				if(docTypeVal==DocType.TEL_RCD_DOC.ordinal()+1){
					q = session.createSQLQuery(sqlQueryB);
				}else{
					q = session.createSQLQuery(sqlQueryA);
				}
				q.setLong(0, id);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return (Map<String, String>) q.uniqueResult();
			}
		};
		return hibernateTemplate.execute(action);
	}
	
	public Map<String, String> getDraftOp(final Long id) {
		final String sqlQueryA = "SELECT FH.DRAFT_OP FROM FILE_HANDLE FH WHERE FH.FILE_REG_ID = ? ORDER BY FH.DRAFT_DATE DESC";
		HibernateCallback<Map<String, String>> action = new HibernateCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public Map<String, String> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				q = session.createSQLQuery(sqlQueryA);
				q.setLong(0, id);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setFirstResult(0);
				q.setMaxResults(1);
				return (Map<String, String>) q.uniqueResult();
			}
		};
		return hibernateTemplate.execute(action);
	}
	
	/**
	 * 获取领导批示
	 * @param id
	 * @return
	 */
	public Map<String, String> getLeader(final Long id) {
		final String sqlQueryA = "SELECT FL.LEADER, PJ.JOB, FL.LEADEROPINION FROM FILE_LEADER FL LEFT JOIN PUB_JLEADERINFO PJ ON FL.LEADER = PJ.NAME WHERE FL.FILE_REG_ID = ? ORDER BY FL.RECORDDATE DESC";
		HibernateCallback<Map<String, String>> action = new HibernateCallback<Map<String, String>>() {
			@SuppressWarnings("unchecked")
			public Map<String, String> doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				q = session.createSQLQuery(sqlQueryA);
				q.setLong(0, id);
				q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setFirstResult(0);
				q.setMaxResults(1);
				return (Map<String, String>) q.uniqueResult();
			}
		};
		return hibernateTemplate.execute(action);
	}
	
	public File getGenerateInstructionsExportFile(int docTypeVal, Long id) {
		File tpl = null, gen = null;
		tpl = getTplPathByDocTypeVal(docTypeVal);
		gen = FileUtils.getFile(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".docx");
		Map<String, String> map = getFilledUpTplDataModel(docTypeVal, id);
		String[] split = getFileLeader(id).toString().substring(1, getFileLeader(id).toString().length()-1).split("],");
		String leader = "",job = "",leaderOpinion = "";
		for (int i = 0; i < split.length; i++) {
			if(!split[i].isEmpty()){
				if(i!=split.length-1 && i!=0){
					String sub = split[i].substring(2).trim();
					String[] fileleaders = sub.split(",\\s");
					leader += fileleaders[0]+"\r\n";
					if(!"null".equals(fileleaders[1])){
						job += fileleaders[1]+"\r\n";
					}else{
						job += " "+"\r\n";
					}
					if(!"null".equals(fileleaders[2])){
						leaderOpinion += fileleaders[2]+"\r\n";
					}else{
						leaderOpinion += " "+"\r\n";
					}
				}else if(i==0){
					String sub = split[i].substring(1);
					String[] fileleaders = sub.split(",\\s");
					leader += fileleaders[0]+"\r\n";
					if(!"null".equals(fileleaders[1])){
						job += fileleaders[1]+"\r\n";
					}else{
						job += " "+"\r\n";
					}
					if(!"null".equals(fileleaders[2])){
						leaderOpinion += fileleaders[2]+"\r\n";
					}else{
						leaderOpinion += " "+"\r\n";
					}
				}else if(i==split.length-1){
					String sub = split[i].substring(2,split[i].length()-1).trim();
					String[] fileleaders = sub.split(",\\s");
					leader += fileleaders[0]+"\r\n";
					if(!"null".equals(fileleaders[1])){
						job += fileleaders[1]+"\r\n";
					}else{
						job += " "+"\r\n";
					}
					if(!"null".equals(fileleaders[2])){
						leaderOpinion += fileleaders[2]+"\r\n";
					}else{
						leaderOpinion += " "+"\r\n";
					}
				}
			}
		}
		try {
			String result ="";
			for (int i = 0; i < leader.split("\r\n").length; i++) {
				result += leader.split("\r\n")[i]+job.split("\r\n")[i]+"在"+(map.get("FORM_UNIT")==null?"":map.get("FORM_UNIT"))+"“"+(map.get("TITLE")==null?"":map.get("TITLE"))+"”上的批示："+leaderOpinion.split("\r\n")[i]+"\r\n";
			}
			map.put("RESULT",result);
			WordOperator oper = new WordOperator(tpl.getAbsolutePath(),gen.getAbsolutePath(),map);
			oper.processText();
			oper.outFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gen;
	}
	
	@SuppressWarnings({ "rawtypes"})
	public List getFileLeader(final Long id) {
		final String sqlQueryA = "SELECT FL.LEADER, PJ.JOB, FL.LEADEROPINION FROM FILE_LEADER FL LEFT JOIN PUB_JLEADERINFO PJ ON FL.LEADER = PJ.NAME WHERE FL.FILE_REG_ID = ? ORDER BY FL.RECORDDATE DESC";
		HibernateCallback<List> action = new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = null;
				q = session.createSQLQuery(sqlQueryA);
				q.setLong(0, id);
				q.setResultTransformer(Transformers.TO_LIST);
				return q.list();
//				return (Map<String, String>) q.uniqueResult();
			}
		};
		return hibernateTemplate.execute(action);
	}
}
