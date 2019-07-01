package com.capinfo.util;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeJsonBean {

	private Long id;
	/** 名称 */
	private String text;
	/** 是否为叶子节点 */
	private Boolean leaf;
	/** 上级节点id */
	private Long parentid;
	private Boolean checked;
	
	private Integer disnum;
	/** 子节点 */
	private List<TreeNodeJsonBean> children = new ArrayList<TreeNodeJsonBean>();

	// constructors
	public TreeNodeJsonBean() {
		super();
	}

	public TreeNodeJsonBean(Long id, String text,Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.checked = checked;
		this.leaf = false;
	}
	public TreeNodeJsonBean(Long id, String text,Boolean leaf,Boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.checked = checked;
		this.leaf = leaf;
	}

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public List<TreeNodeJsonBean> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNodeJsonBean> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public boolean isExpanded() {
		return !getLeaf();
	}

	public Integer getDisnum() {
		return disnum;
	}

	public void setDisnum(Integer disnum) {
		this.disnum = disnum;
	}

}
