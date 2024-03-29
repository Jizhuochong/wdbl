package com.capinfo.wdbl.service;

import java.io.File;

/**
 * 文件导出服务接口
 *
 */
public interface FileRegExportService {

	/**
	 * 获取生成的Word导出文件
	 * @param docTypeVal
	 * @param id
	 * @return
	 */
	public File getGenerateExportFile(int docTypeVal, Long id);
	
	public File getGeneratePhoneRecordExportFile(int docTypeVal, Long id);

	/**
	 * 获取密码电报Word导出文件
	 * @param docTypeVal
	 * @param id
	 * @return
	 */
	public File getGenerateTelegraphExportFile(int docTypeVal, Long id);

	/**
	 * 获取批示抄清Word导出文件
	 * @param docTypeVal
	 * @param id
	 * @return
	 */
	public File getGenerateInstructionsExportFile(int docTypeVal, Long id);

}
