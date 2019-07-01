package com.capinfo.meeting.util;

public enum OperateStatu {
	Add("新增",1),Edit("修改",2),Delete("删除",3);
	
	private String name;
	private Integer index;
	
	private OperateStatu() {
	}
	private OperateStatu(String name,Integer index) {
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
