package com.capinfo.wdbl.util;

import java.util.List;

import com.googlecode.genericdao.search.SearchResult;

public class GridJsonBean {
	private int totalrows;
	private List<?> rows;
	
	
	public GridJsonBean() {
		super();
	}

	public GridJsonBean(List<?> rows, int totalrows) {
		this.rows = rows;
		this.totalrows = totalrows;
	}
	
	public int getTotalrows() {
		return totalrows;
	}
	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public GridJsonBean valueOf(SearchResult<?> result) {
		GridJsonBean bean = new GridJsonBean();
		bean.setRows(result.getResult());
		bean.setTotalrows(result.getTotalCount());
		return bean;
	}
}
