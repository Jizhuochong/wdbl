package com.capinfo.meeting.util;

public enum IsDeleted {

	No("未删除",false),Yes("删除",true);
	
	private String name;
	private Boolean index;
	
	private IsDeleted(String name,Boolean index) {
		this.name=name;
		this.index=index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIndex() {
		return index;
	}

	public void setIndex(Boolean index) {
		this.index = index;
	}
	
	
}
