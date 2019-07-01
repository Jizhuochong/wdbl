package com.capinfo.sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configINFO")
public class ConfigINFO {
	@Value("${sys.dug}")
	private Boolean dug;

	@Value("${sys.wdDeptCode}")
	private String wdDeptCode;
	
	@Value("${file.snLength}")
	private Integer fileSnLength;
	
	@Value("${file.barCodeLength}")
	private Integer barCodeLength;
	
	@Value("${file.barCodeChar}")
	private String barCodeChar;
	
	@Value("${ws.URL}")
	private String wsURL;
	
	@Value("${file.leaderChar}")
	private String leaderChar;

	public String getWdDeptCode() {
		return wdDeptCode;
	}

	public void setWdDeptCode(String wdDeptCode) {
		this.wdDeptCode = wdDeptCode;
	}

	public Integer getFileSnLength() {
		return fileSnLength;
	}

	public void setFileSnLength(Integer fileSnLength) {
		this.fileSnLength = fileSnLength;
	}

	public Integer getBarCodeLength() {
		return barCodeLength;
	}

	public void setBarCodeLength(Integer barCodeLength) {
		this.barCodeLength = barCodeLength;
	}

	public String getBarCodeChar() {
		return barCodeChar;
	}

	public void setBarCodeChar(String barCodeChar) {
		this.barCodeChar = barCodeChar;
	}

	public String getWsURL() {
		return wsURL;
	}

	public void setWsURL(String wsURL) {
		this.wsURL = wsURL;
	}

	public Boolean getDug() {
		return dug;
	}

	public void setDug(Boolean dug) {
		this.dug = dug;
	}

	public String getLeaderChar() {
		return leaderChar;
	}

	public void setLeaderChar(String leaderChar) {
		this.leaderChar = leaderChar;
	}
	
	
}
