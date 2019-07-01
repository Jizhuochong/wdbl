package com.capinfo.wdbl.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * @title: 返回当前日期
	 * @return
	 */
	public static String getCurrentDate() {
		String temp_str = "";   
	    Date dt = new Date();   
	    //yyyy-MM-dd HH:mm:ss aa最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    temp_str=sdf.format(dt);   
	    
	    return temp_str;
	}

}
