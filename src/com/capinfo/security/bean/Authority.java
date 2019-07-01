package com.capinfo.security.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Parameter;

/**
 * Authority entity. [权限]
 */
@Entity
@Table(name = "T_AUTHORITY")
public class Authority implements java.io.Serializable
{
	private static final long serialVersionUID = 6076999498676322469L;

	// Fields
	/**
	 * 权限编号
	 */
	private Long authorityId;
	/**
	 * 权限名称
	 */
	private String authorityName;
	/**
	 * 权限描述
	 */
	private String authorityDesc;
	/**
	 * 是否禁用
	 */
	private Boolean enabled;
	/**
	 * 拥有该权限的角色
	 */
	private Set<User> users = new HashSet<User>(0);
	/**
	 * 该权限拥有的资源
	 */
	private Set<Resource> resources = new HashSet<Resource>(0);
	/**
	 * 拥有该权限的角色
	 */
	private Set<Role> roles = new HashSet<Role>(0);
	/**
	 * 该权限拥有的菜单
	 */
	private Set<Menu> menus = new HashSet<Menu>(0); 

	
	
	// Property accessors
	@Id
	@GenericGenerator(name = "authorityIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_AUTHORITY_ID") })
	@GeneratedValue(generator = "authorityIdGen")
	@Column(name = "AUTHORITY_ID",  nullable = false)
	public Long getAuthorityId()
	{
		return this.authorityId;
	}

	public void setAuthorityId(Long authorityId)
	{
		this.authorityId = authorityId;
	}

	@Column(name = "AUTHORITY_NAME", length = 50)
	public String getAuthorityName()
	{
		return this.authorityName;
	}

	public void setAuthorityName(String authorityName)
	{
		this.authorityName = authorityName;
	}
	@Column(name = "AUTHORITY_DESC", length = 100)
	public String getAuthorityDesc()
	{
		return this.authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc)
	{
		this.authorityDesc = authorityDesc;
	}

	@Column(name = "ENABLED", length = 1)
	public Boolean getEnabled()
	{
		return this.enabled;
	}

	public void setEnabled(Boolean enabled)
	{
		this.enabled = enabled;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
	@JsonIgnore public Set<User> getUsers()
	{
		return this.users;
	}

	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "T_AUTHORITY_RESOURCE", joinColumns = { @JoinColumn(name = "AUTHORITY_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID", updatable = false) })
	@JsonIgnore public Set<Resource> getResources()
	{
		return this.resources;
	}

	public void setResources(Set<Resource> resources)
	{
		this.resources = resources;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
	@JsonIgnore public Set<Role> getRoles()
	{
		return this.roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_MENU_AUTHORITY", joinColumns = { @JoinColumn(name = "AUTHORITY_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "MENU_ID", updatable = false) })
	@JsonIgnore public Set<Menu> getMenus()
	{
		return menus;
	}

	public void setMenus(Set<Menu> menus)
	{
		this.menus = menus;
	}
	
	

}
