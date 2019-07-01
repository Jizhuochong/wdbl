package com.capinfo.security.tool;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntUrlPathMatcher implements UrlMatcher
{
	private boolean requestsLowerCaseUrl;
	private PathMatcher pathMatcher;

	public AntUrlPathMatcher()
	{
		this(true);
	}

	public AntUrlPathMatcher(boolean requestsLowerCaseUrl)
	{
		super();
		this.requestsLowerCaseUrl = requestsLowerCaseUrl;
		this.pathMatcher = new AntPathMatcher();
	}

	public void setRequestsLowerCaseUrl(boolean requestsLowerCaseUrl)
	{
		this.requestsLowerCaseUrl = requestsLowerCaseUrl;
	}

	// implements

	public Object compile(String path)
	{
		if (this.requestsLowerCaseUrl)
		{
			return path.toLowerCase();
		}
		return path;
	}

	public boolean pathMatchesUrl(Object path, String url)
	{
		if (("/**".equals(path)) || ("**".equals(path)))
		{
			return true;
		}
		return this.pathMatcher.match((String) path, url);
	}

	public String getUniversalMatchPattern()
	{
		return "/**";
	}

	public boolean requestsLowerCaseUrl()
	{
		return this.requestsLowerCaseUrl;
	}

	@Override
	public String toString()
	{
		return "AntUrlPathMatcher [requestsLowerCaseUrl=" + requestsLowerCaseUrl + ", pathMatcher=" + pathMatcher + "]";
	}

}
