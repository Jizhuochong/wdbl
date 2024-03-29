package com.capinfo.wdbl.api;
/**
 * 文件流水号管理器
 */
public interface SerialNumberManager {
	
	/**
	 * 生成文件流水号
	 * @return
	 */
	public String generateSN();
	
	public String genFileSN();
	
	public String genFileBarNo();
	
	public String genLeaderSN();
}
