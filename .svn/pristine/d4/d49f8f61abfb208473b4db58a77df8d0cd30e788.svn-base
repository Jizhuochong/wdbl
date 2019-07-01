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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * User entity. [用户]
 */
@Entity
@Table(name = "T_USER")
public class User implements java.io.Serializable
{
	private static final long serialVersionUID = -7409741029480727540L;

	// Fields
	/**
	 * 用户编号
	 */
	private Long userId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 职务
	 */
	private String position;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户状态[1:正常,2:锁定,3:已删除]
	 */
	private String state;
	/**
	 * 绑定IP
	 */
	private String bindedIp;
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
	private boolean enabled;
	/**
	 * 用户特权
	 */
	private Set<Authority> authorities = new HashSet<Authority>(0);
	/**
	 * 用户角色
	 */
	private Set<Role> roles = new HashSet<Role>(0);
	
	private Long departmentId; //部门id


	@Id
	@GenericGenerator(name = "userIdGen", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "SEQ_USER_ID") })
	@GeneratedValue(generator = "userIdGen")
	@Column(name = "USER_ID", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STATE")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "ENABLED")
	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "T_USER_AUTHORITY", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID", updatable = false) })
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	public Set<Authority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) })
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Column(name = "BINDED_IP")
	public String getBindedIp() {
		return bindedIp;
	}

	public void setBindedIp(String bindedIp) {
		this.bindedIp = bindedIp;
	}

	@Column(name = "DEPARTMENT_ID")
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "UNIT")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "POSITION")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "DEPARTMENT")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	

}