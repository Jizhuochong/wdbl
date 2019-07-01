package com.capinfo.wdbl.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;

import com.capinfo.base.service.BaseService;
import com.capinfo.wdbl.bean.CodeInfo;
import com.mchange.v1.io.InputStreamUtils;

/**
 * 
 * 文件校验工具类 [包含从数据库获取系统允许上传文件的MimeType（定时刷新）、以及一些验证文件上传类型是否为可上传类型、错误提示等方法。]
 * 
 */
@Component
public class FileValidationUtils {

	private static final Log log = LogFactory.getLog(FileValidationUtils.class);

	/**
	 * 允许上传文件MimeType
	 */
	private static final String SYSTEM_FILE_ALLOWED_MIMETYPES_DICT_CODE = "system.uploadfile.allowed.mimetypes";
	/**
	 * 刷新数据库周期（单位：分钟）
	 */
	private static final int REFRESH_TIME_PERIOD = 30;

	/**
	 * 排序条件
	 */
	private Map<String, String> orderMap;
	/**
	 * 系统允许上传文件类型MimeType列表
	 */
	private List<CodeInfo> allowedMimeTypeCodeInfoList;
	/**
	 * 数据最后一次更新时间
	 */
	private long lastRefreshTimeMillis = 0;

	@Autowired
	private BaseService baseService;

	// Constructor
	public FileValidationUtils() {
		super();
	}

	/**
	 * 验证文件是否为允许上传类型
	 * 
	 * @param file
	 *            : org.springframework.web.multipart.MultipartFile
	 * @return
	 */
	public boolean isAcceptType(MultipartFile multipartFile) {
		try {
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY,multipartFile.getOriginalFilename());
			Parser parser = new AutoDetectParser();
			ParseContext context = new ParseContext();
			ContentHandler contenthandler = new BodyContentHandler();
			InputStream is = new ByteArrayInputStream(multipartFile.getBytes());//ByteArrayInputStream's close is empty
			parser.parse(is, contenthandler, metadata,context);
			String mimetype = metadata.get(Metadata.CONTENT_TYPE);
			log.debug("the validating file's mimetype is : " + mimetype);
			return Arrays.asList(getAllowedExtensions()).contains(
					FilenameUtils.getExtension(multipartFile.getOriginalFilename()))//校验文件后缀名
					&& Arrays.asList(getAllowedMimeType()).contains(mimetype);//校验文件MimeType
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return false;
	}

	/**
	 * 验证文件是否为允许上传类型
	 * 
	 * @param file
	 *            : java.io.File
	 * @return
	 */
	public boolean isAcceptType(File file) {
		FileInputStream is = null;
		try {
			if (file != null) {
				is = new FileInputStream(file);
				Metadata metadata = new Metadata();
				metadata.set(Metadata.RESOURCE_NAME_KEY,file.getName());
				Parser parser = new AutoDetectParser();
				ParseContext context = new ParseContext();
				ContentHandler contenthandler = new BodyContentHandler();
				parser.parse(is, contenthandler, metadata,context);
				String mimetype = metadata.get(Metadata.CONTENT_TYPE);
				log.debug("the validating file's mimetype is : "+mimetype);
				return Arrays.asList(getAllowedMimeType()).contains(mimetype) //校验MimeType
						 && Arrays.asList(getAllowedExtensions()).contains(FilenameUtils.getExtension(file.getName()));//校验文件后缀名
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			InputStreamUtils.attemptClose(is);
		}
		return false;
	}

	/**
	 * 获取允许上传的文件类型
	 * 
	 * @return
	 */
	public String[] getAllowedExtensions() {
		List<String> allowedExtensionList = new ArrayList<String>();
		for (CodeInfo item : getAllowedMimeTypeCodeInfoList()) {
			allowedExtensionList.add(item.getNode());
		}
		return allowedExtensionList.toArray(new String[] {});
	}

	/**
	 * 获取允许上传文件类型MimeType
	 * 
	 * @return
	 */
	public String[] getAllowedMimeType() {
		List<String> allowedMimeTypeList = new ArrayList<String>();
		for (CodeInfo item : getAllowedMimeTypeCodeInfoList()) {
			allowedMimeTypeList.add(item.getCode());
		}
		return allowedMimeTypeList.toArray(new String[] {});
	}

	public String getAcceptTypeErrorMsg() {
		String errorMsg = "文件上传失败，只允许上传";
		String[] allowedExtensions = getAllowedExtensions();
		String tmp = "";
		for (int i = 0; i < allowedExtensions.length; i++) {
			tmp += allowedExtensions[i] + "、";
		}
		errorMsg += tmp.substring(0, tmp.lastIndexOf("、")) + "格式的文件";
		return errorMsg;
	}

	/**
	 * 获取系统允许MimeType列表
	 * 
	 * @return
	 */
	public List<CodeInfo> getAllowedMimeTypeCodeInfoList() {
		if (allowedMimeTypeCodeInfoList == null
				|| System.currentTimeMillis() - this.lastRefreshTimeMillis > 1000 * 60 * REFRESH_TIME_PERIOD) {
			refresh();// 刷新数据
		}
		return allowedMimeTypeCodeInfoList;
	}

	/**
	 * 从数据库加载系统允许上传的文件类型列表
	 * 
	 * @return
	 */
	private void refresh() {
		CodeInfo example = new CodeInfo();
		example.setParent_code(SYSTEM_FILE_ALLOWED_MIMETYPES_DICT_CODE);
		this.allowedMimeTypeCodeInfoList = baseService.getByProperty(
				CodeInfo.class, example, getOrderMap());
		Assert.notEmpty(this.allowedMimeTypeCodeInfoList,
				"系统允许上传文件类型MimeType未定义!!!");
		this.lastRefreshTimeMillis = System.currentTimeMillis();
	}

	/**
	 * 获取排序条件
	 * 
	 * @return
	 */
	private Map<String, String> getOrderMap() {
		if (orderMap == null) {
			orderMap = new LinkedHashMap<String, String>();
		}
		if (orderMap.isEmpty()) {
			orderMap.put("dis_num", "ASC");// 显示顺序
		}
		return orderMap;
	}

}
