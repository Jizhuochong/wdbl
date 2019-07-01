package com.capinfo.wdbl.api;

import org.springframework.web.multipart.MultipartFile;

import com.capinfo.wdbl.enums.DocType;
/**
 * 
 * [文件管理器]
 *
 */
public interface FileManager {
	/**
	 * 保存原文件
	 * @param file 文件
	 * @param docType 文件类型
	 * @return path 存储文件的文件路径
	 */
	public String  saveDocOriginalFile(MultipartFile file, DocType docType);
	
	
	/**
	 * 保存原文件
	 * @param file 文件
	 * @return path 存储文件的文件路径
	 */
	public String saveDocOriginalFile(MultipartFile file);
	
	/**
	 * 获取文件存储根路径
	 * @return base path
	 */
	public String getDocBasePath();
	/**
	 * 根据文件类型获取原文件所在目录
	 * @param docType
	 * @return directory path
	 */
	public String getDocLocationPath(DocType docType);
	/**
	 * 删除原文件
	 * @param filePath
	 * @return
	 */
	public boolean removeDocOriginalFile(String filePath);
}
