package com.capinfo.meeting.util;

public enum DayNames {
	Sun("星期日",0),Mon("星期一", 1),Tue("星期二",2),Wed("星期三",3),
	Thu("星期四",4),Fri("星期五",5),Sta("星期六",6);
	
	private String name;
	private int index;

	// 构造方法
	private DayNames() {
	}
	
	private DayNames(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
