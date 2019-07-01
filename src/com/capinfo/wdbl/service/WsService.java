package com.capinfo.wdbl.service;

public interface WsService {
	
	public boolean tranFileToUnit(String barNo,String whereAbout,String createUser,String userDept);
	
	public boolean wsDelFile(String filecode);
}
