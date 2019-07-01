package com.capinfo.security.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Menu;
import com.capinfo.security.bean.Role;
import com.capinfo.security.bean.User;

public class UserDetailsImpl implements UserDetails
{
	private static final long serialVersionUID = -9096607721989831832L;

	private User user;
	
	private Collection<Menu> menus;

	private Collection<? extends GrantedAuthority> grantedAuthorities;

	public User getUser()
	{
		return user;
	}
	public Collection<Menu> getMenus() {
		return menus;
	}
	/**
	 * 获取用户详细信息(含权限信息)
	 * @param user
	 */
	public UserDetailsImpl(User user,Collection<Menu> menus)
	{
		super();
		this.user = user;
		this.menus = menus;
		List<SimpleGrantedAuthority> grantedAuthoritiesList = new ArrayList<SimpleGrantedAuthority>();
		//1.用户特殊权限
		Set<Authority> userAuthorities = user.getAuthorities();
		if (userAuthorities != null)
		{
			for (Authority authority : userAuthorities)
			{
				grantedAuthoritiesList.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
			}
		}
		//2.用户角色权限
		Set<Role> roles = user.getRoles();
		if(null != roles){
			for (Role role : roles)
			{
				Set<Authority> roleAuthorities = role.getAuthorities();
				if(roleAuthorities != null){
					for (Authority authority : roleAuthorities)
					{
						grantedAuthoritiesList.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
					}
				}
			}
		}
		this.grantedAuthorities = grantedAuthoritiesList;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.grantedAuthorities;
	}

	public String getPassword()
	{
		return user.getPassword();
	}

	public String getUsername()
	{
		return user.getUsername();
	}
	/**
	 * 是否为非过期账号
	 */
	public boolean isAccountNonExpired()
	{
		return true;
	}
	
	/**
	 * 是否为非锁定状态账号
	 */
	public boolean isAccountNonLocked()
	{
		return true;
	}
	/**
	 * 是否为非过期账号证书
	 */
	public boolean isCredentialsNonExpired()
	{
		return true;
	}
	/**
	 * 是否为启用状态账号
	 */
	public boolean isEnabled()
	{
		return true;
	}

}
