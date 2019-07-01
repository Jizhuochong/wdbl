package com.capinfo.security.bean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.capinfo.security.tool.MenuToStringSerializer;

@Entity
@Table(name = "T_MENU")
public class Menu implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8495697195713558329L;
	/**
	 * 菜单编号
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 显示顺序
	 */
	private Integer disnum;
	/**
	 * 菜单URL
	 */
	private String url;
	/**
	 * 上级菜单
	 */
	@JsonProperty("_parentId")
	@JsonSerialize(using = MenuToStringSerializer.class)
	private Menu parent;
	/**
	 * 图标
	 */
	private String menuIcon;
	/**
	 * 是否为叶子节点
	 */
	@JsonProperty("leaf")
	private boolean isLeaf;

	/**
	 * 拥有菜单的权限
	 */
	@JsonIgnore
	private Set<Authority> authorities = new HashSet<Authority>(0);
	/**
	 * 当前菜单的子菜单
	 */
	private List<Menu> children = new ArrayList<Menu>(0);

	@Id
	@GenericGenerator(name = "menuIdGen", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_MENU_ID") })
	@GeneratedValue(generator = "menuIdGen")
	@Column(name = "MENU_ID", nullable = false)
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MENU_NAME")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "MENU_URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "MENU_ICON")
	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	@Column(name = "IS_LEAF", length = 1)
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "DIS_NUM")
	public Integer getDisnum() {
		return disnum;
	}

	public void setDisnum(Integer disnum) {
		this.disnum = disnum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_MENU_ID")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "menus")
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	@Transient
	public boolean isExpanded() {
		return !isLeaf();
	}

}