package com.capinfo.meeting.util;

public enum TypeJuge {
	Deal("处理",1),Trun("转走",2);
	
	private String name;
	private Integer index;
	
	private TypeJuge(String name,Integer index) {
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
