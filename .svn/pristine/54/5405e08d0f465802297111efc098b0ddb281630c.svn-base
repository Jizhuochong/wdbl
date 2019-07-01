package com.capinfo.util;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeModel {
	private Long id;
	/** 名称 */
	private String text;
	/** URL */
	private String url;
	/** 是否为叶子节点 */
	private Boolean leaf;
	/** 显示序号 */
	private Integer disnum;
	/** 是否菜单 */
	private Integer ifMenu;
	/** 上级id */
	private Long parentid;
	/**
	 * 子菜单
	 */
	private List<MenuTreeModel> children = new ArrayList<MenuTreeModel>();

	/// constructors
	public MenuTreeModel() {
		super();
	}

	public MenuTreeModel(Long id, String text, String url, Boolean leaf,
			Long parentid, List<MenuTreeModel> children) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.leaf = leaf;
		this.parentid = parentid;
		this.children = children;
	}
	
	/// getters and setters
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public Integer getDisnum() {
		return disnum;
	}
	public void setDisnum(Integer disnum) {
		this.disnum = disnum;
	}
	public Integer getIfMenu() {
		return ifMenu;
	}
	public void setIfMenu(Integer ifMenu) {
		this.ifMenu = ifMenu;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	public List<MenuTreeModel> getChildren() {
		return children;
	}
	public void setChildren(List<MenuTreeModel> children) {
		this.children = children;
	}
	
}
