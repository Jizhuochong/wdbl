package com.capinfo.security.tool;

import java.util.Comparator;

import com.capinfo.security.bean.Menu;

public class MenuComparator implements Comparator<Menu>{
	
	public int compare(Menu m1, Menu m2) {
		int val1 = m1.getMenuId().intValue();  
    	int val2 = m2.getMenuId().intValue();  
		return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));
	}

}
