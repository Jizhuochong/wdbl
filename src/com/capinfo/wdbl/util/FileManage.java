package com.capinfo.wdbl.util;

import java.io.File;


public class FileManage {
	
	public static void delFile(String[] fileurl){
		for(int i=0;i<fileurl.length;i++){
			File file = new File(fileurl[i]);
			if (file.exists()) {
				file.delete(); 
			}
		}
	}
	
	
}
