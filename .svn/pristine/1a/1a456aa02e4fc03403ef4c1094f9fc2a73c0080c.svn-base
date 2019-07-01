package com.capinfo.util;




/**
 * 
 * [分页参数对象]
 *
 */
public class PageBean {
	
	private static final int DEFAULT_PAGE_OFFSET = 0;
	private static final int DEFAULT_PAGE_SIZE = 20;
	
	/**
	 * 开始记录
	 */
	private int start = DEFAULT_PAGE_OFFSET;
	/**
	 * 每页记录数
	 */
	private int limit = DEFAULT_PAGE_SIZE;
	/**
	 * 排序字段
	 */
	private String sort;
	/**
	 * 排序指令（升序或降序）
	 */
	private String dir;
	/**
	 * 临时
	 */
	private String property;    

	private String direction;
	
	private int parseInt;
	/**
	 * 总条数
	 */
	private int total = 0;
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public PageBean() {
		super();
	}

	public PageBean(Integer start, Integer limit) {
		super();
		this.start = start;
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
