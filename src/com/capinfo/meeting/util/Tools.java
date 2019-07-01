package com.capinfo.meeting.util;

/***
 * 工具类
 * @author LMZ
 *
 */
public class Tools {
	
	/****
	 * 替换换行字符
	 * @param s
	 * @return
	 */
	public static final String htmlToCode(String s) {
		if (s == null) {
			return "";
		} else {
//			s = s.replace("\n\r", "<br>&nbsp;&nbsp;");
//			s = s.replace("\r\n", "<br>&nbsp;&nbsp;");
//			s = s.replace("\"", "\\" + "\"");//替换引号
			s = s.replace("\n", "<br>");
//			s="<p>"+s;
//			s = s.replace("\n", "</p><p>");
//			s=s+"</p>";
			return s;
		}
	}
}
