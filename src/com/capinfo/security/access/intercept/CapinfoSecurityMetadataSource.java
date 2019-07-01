package com.capinfo.security.access.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Resource;
import com.capinfo.security.dao.AuthorityDao;
import com.capinfo.security.tool.AntUrlPathMatcher;
import com.capinfo.security.tool.UrlMatcher;

public class CapinfoSecurityMetadataSource implements FilterInvocationSecurityMetadataSource
{
	private static final Logger logger = Logger.getLogger(CapinfoSecurityMetadataSource.class);
	
	private AuthorityDao authorityDao;
	
	private Collection<ConfigAttribute> configAttributes = null;
	
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	
	public AuthorityDao getAuthorityDao()
	{
		return authorityDao;
	}

	public void setAuthorityDao(AuthorityDao authorityDao)
	{
		this.authorityDao = authorityDao;
	}

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public CapinfoSecurityMetadataSource(AuthorityDao authorityDao)
	{
		super();
		this.authorityDao = authorityDao;
		loadResourceDefine();
	}

	/**
	 * 根据URL找到相关的权限配置。
	 */
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException
	{
		String url = ((FilterInvocation) obj).getRequestUrl();
//		logger.debug(String.format("requestUrl is %s", url));

		if (resourceMap == null)
		{
			loadResourceDefine();
		}
		
		Iterator<String> resUrls = resourceMap.keySet().iterator();
		while (resUrls.hasNext())
		{
			String resUrl = resUrls.next();
			if(urlMatcher.pathMatchesUrl(resUrl, url)){
				return resourceMap.get(resUrl);
			}
			
		}

		return null;
	}
	/**
	 * 获取系统所有权限配置集合
	 */
	public Collection<ConfigAttribute> getAllConfigAttributes()
	{
		return this.configAttributes;
	}

	public boolean supports(Class<?> paramClass)
	{
		return true;
	}

	private void loadResourceDefine()
	{
		if (resourceMap == null)
		{
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Authority> authorityList = this.authorityDao.getAllAuthorities();
			for (Authority authority : authorityList)
			{
				ConfigAttribute ca = new SecurityConfig(authority.getAuthorityName());
				Set<Resource> resources = authority.getResources();
				for (Resource resource : resources)
				{
					/*
					 * 判断资源文件和权限的对应关系
					 * 如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中
					 */
					if (resourceMap.containsKey(resource.getResourceString()))
					{
						Collection<ConfigAttribute> configAttributes = resourceMap.get(resource.getResourceString());
						configAttributes.add(ca);
						resourceMap.put(resource.getResourceString(), configAttributes);
					} else
					{
						Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
						atts.add(ca);
						resourceMap.put(resource.getResourceString(), atts);
					}

				}

			}
		}
		
		if(this.configAttributes == null){
			List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
			List<String> authorityNames  =  authorityDao.getAllAuthorityNames();
			if(authorityNames != null && authorityNames.size() >0){
				for (String authorityName : authorityNames)
				{
					list.add(new SecurityConfig(authorityName));
				}
			}
			configAttributes = list;
		}

	}

}