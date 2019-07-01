package com.capinfo.security.bean;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Role entity. [角色]
 */
@Entity
@Table(name = "T_ROLE")
public class Role implements java.io.Serializable
{
	private static final long serialVersionUID = -6107177279044828859L;

	// Fields
	
	/**
	 * 角色编号
	 */
	private Long roleId;
	/**
	 * 角色名称 
	 */
	private String roleName;
	/**
	 * 角色描述
	 */
	private String roleDesc;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否启用
	 */
	private Boolean enabled;
	/**
	 * 当前角色拥有的权限
	 */
	private Set<Authority> authorities = new HashSet<Authority>(0);
	/**
	 * 拥有该权限的角色
	 */
	private Set<User> users = new HashSet<User>(0);

	// Constructors

	/** default constructor */
	public Role()
	{
	}

	public Role(Long roleId)
	{
		this.roleId = roleId;
	}
	
	/** minimal constructor */
	public Role(String roleName, Date createTime)
	{
		this.roleName = roleName;
		this.createTime = createTime;
	}

	/** full constructor */
	public Role(String roleName, String roleDesc, Date createTime, Date updateTime, Boolean enabled, Set<Authority> authorities, Set<User> users)
	{
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.enabled = enabled;
		this.authorities = authorities;
		this.users = users;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "roleIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_ROLE_ID") })
	@GeneratedValue(generator = "roleIdGen")
	@Column(name = "ROLE_ID",  nullable = false)
	public Long getRoleId()
	{
		return this.roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME", nullable = false, length = 50)
	public String getRoleName()
	{
		return this.roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	@Column(name = "ROLE_DESC", length = 100)
	public String getRoleDesc()
	{
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc)
	{
		this.roleDesc = roleDesc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false)
	public Date getCreateTime()
	{
		return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime()
	{
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
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

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "T_ROLE_AUTHORITY", joinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID", updatable = false) })
	@JsonIgnore public Set<Authority> getAuthorities()
	{
		return this.authorities;
	}

	public void setAuthorities(Set<Authority> authorities)
	{
		this.authorities = authorities;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	@JsonIgnore public Set<User> getUsers()
	{
		return this.users;
	}

	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

}
