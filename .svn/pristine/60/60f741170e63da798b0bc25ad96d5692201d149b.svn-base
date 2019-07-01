package com.capinfo.security.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Resource entity. [资源]
 */
@Entity
@Table(name = "T_RESOURCE")
public class Resource implements java.io.Serializable
{
	private static final long serialVersionUID = 7102857215771348933L;

	// Fields
	/**
	 * 资源编号
	 */
	private Long resourceId;
	/**
	 * 资源名称
	 */
	private String resourceName;
	/**
	 * 资源描述 
	 */
	private String resourceDesc;
	/**
	 * 资源类型
	 */
	private String resourceType;
	/**
	 * 资源URL
	 */
	private String resourceString;
	/**
	 * 是否启用 
	 */
	private Boolean enabled;
	private Set<Authority> authorities = new HashSet<Authority>(0);

	// Constructors

	/** default constructor */
	public Resource()
	{
	}
	public Resource(Long resourceId) {
		this.resourceId = resourceId;
	}


	// Property accessors
	@Id
	@GenericGenerator(name = "resourceIdGen", strategy = "sequence",
	  parameters = { @Parameter(name = "sequence", value = "SEQ_RESOURCE_ID") })
	@GeneratedValue(generator = "resourceIdGen")
	@Column(name = "RESOURCE_ID",  nullable = false)
	public Long getResourceId()
	{
		return this.resourceId;
	}

	public void setResourceId(Long resourceId)
	{
		this.resourceId = resourceId;
	}

	@Column(name = "RESOURCE_NAME", length = 50)
	public String getResourceName()
	{
		return this.resourceName;
	}

	public void setResourceName(String resourceName)
	{
		this.resourceName = resourceName;
	}

	@Column(name = "RESOURCE_DESC", length = 100)
	public String getResourceDesc()
	{
		return this.resourceDesc;
	}

	public void setResourceDesc(String resourceDesc)
	{
		this.resourceDesc = resourceDesc;
	}

	@Column(name = "RESOURCE_TYPE", length = 1)
	public String getResourceType()
	{
		return this.resourceType;
	}

	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}

	@Column(name = "RESOURCE_STRING", length = 50)
	public String getResourceString()
	{
		return this.resourceString;
	}

	public void setResourceString(String resourceString)
	{
		this.resourceString = resourceString;
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

	@JsonIgnore @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
	public Set<Authority> getAuthorities()
	{
		return this.authorities;
	}

	public void setAuthorities(Set<Authority> authorities)
	{
		this.authorities = authorities;
	}

}
