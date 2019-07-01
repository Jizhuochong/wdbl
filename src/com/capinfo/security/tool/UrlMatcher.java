package com.capinfo.security.tool;

public interface UrlMatcher
{
	public Object compile(String path);
	public boolean pathMatchesUrl(Object path, String url);
	public String getUniversalMatchPattern();
	public boolean requestsLowerCaseUrl();

}
