package com.capinfo.meeting.util;

public enum MeetStatu {
	
	Prepare("拟办中",1),Ready("已确定",2),Cancel("已取消",3);
	
	private String name;
	private Integer index;
	
	private MeetStatu(String name,Integer index) {
		this.name=name;
		this.index=index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
