package com.capinfo.security.access.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.code.kaptcha.Constants;

public class CapinfoLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{

	private boolean allowEmptyValidateCode;
	private boolean allowAllIpVisit;
	private boolean postOnly = true;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException
	{
		if((this.postOnly) && (!(request.getMethod().equals("POST")))) {
		     throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		   }
		
		   String username = obtainUsername(request);
		   String password = obtainPassword(request);
		
		   if (username.isEmpty()) {
			   throw new AuthenticationServiceException("用户名不能为空");   
		   }
		
		   if (password.isEmpty()) {
			   throw new AuthenticationServiceException("密码不能为空"); 
		   }
		
		   username = username.trim();
		   
		   UserDetails u = userDetailsService.loadUserByUsername(username);
		   if(u == null || !u.getPassword().equals(md5PasswordEncoder.encodePassword(password, username))) {  
			            throw new AuthenticationServiceException("用户名或者密码不正确");   
		   }
		   //检查验证码
		   if (!isAllowEmptyValidateCode()){
				  checkValidateCode(request);  
		   }
		  /* //IP限制检查
		   if(!allowAllIpVisit){
			   String requestIp = this.getIpAddr(request);
			    Sys_User = userService.findUserByName(username);
			   if(!requestIp.equals(user.getBindedIp())){
				   throw new AuthenticationServiceException(String.format("当前IP[%s]不允许访问", requestIp));
			   }
		   }*/
		
		   UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		   setDetails(request, authRequest);
		
		   return getAuthenticationManager().authenticate(authRequest);
	}
	protected void checkValidateCode(HttpServletRequest request) {
		String sessionCaptcha = obtainSessionCaptcha(request);
		String requestCaptcha = obtainCaptchaParameter(request);
		if(StringUtils.isEmpty(requestCaptcha)){
			throw new AuthenticationServiceException("验证码不能为空");
		}
		if (!sessionCaptcha.equalsIgnoreCase(requestCaptcha)) {
			throw new AuthenticationServiceException("验证码不正确");
		}
	}
	public boolean isAllowEmptyValidateCode()
	{
		return allowEmptyValidateCode;
	}
	public void setAllowEmptyValidateCode(boolean allowEmptyValidateCode)
	{
		this.allowEmptyValidateCode = allowEmptyValidateCode;
	}
	
	public boolean isAllowAllIpVisit() {
		return allowAllIpVisit;
	}
	public void setAllowAllIpVisit(boolean allowAllIpVisit) {
		this.allowAllIpVisit = allowAllIpVisit;
	}
	
	private String obtainCaptchaParameter(HttpServletRequest request)
	{
		String captcha = "";
		if(request != null){
			captcha = (String) request.getParameter("captcha");  
		}
		return captcha;
	}
	private String obtainSessionCaptcha(HttpServletRequest request)
	{
		String sessionCaptcha = "";
		if(request != null && request.getSession() != null){
			sessionCaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		}
		return sessionCaptcha;
	}
	
	/*private final String getIpAddr(final HttpServletRequest request){
		if (request == null) {
			return "unknown";
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		if(ipString != null){
			// 多个路由时，取第一个非unknown的ip
			final String[] arr = ipString.split(",");
			for (final String str : arr) {
				if (!"unknown".equalsIgnoreCase(str)) {
					ipString = str;
					break;
				}
			}
		}
		ipString = ipString.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ipString;
		return ipString;
	}*/
}
