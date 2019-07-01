package com.capinfo.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.context.SecurityContextHolder;

import com.capinfo.security.bean.Menu;
import com.capinfo.security.bean.User;

public abstract class UserDetailsHelper
{
	   public static final UserDetails getUserDetails()
	   {
	     Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     if ((o instanceof UserDetails)) {
	       return (UserDetails)o;
	     }
	     return null;
	   }
	 
	   public static final User getUser()
	   {
		   UserDetails editorDetails = getUserDetails();
	     if (editorDetails != null) {
	       return editorDetails.getUser();
	     }
	     return null;
	   }
	   public static final Collection<Menu> getMenus()
	   {
		   UserDetails editorDetails = getUserDetails();
		   if (editorDetails != null) {
			   return editorDetails.getMenus();
		   }
		   return null;
	   }

}
