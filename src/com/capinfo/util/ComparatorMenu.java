package com.capinfo.util;

import java.util.Comparator;

import com.capinfo.security.bean.Menu;

public class ComparatorMenu implements Comparator<Menu>

{

	public int compare(Menu o1, Menu o2) 
	{
		if(o2.getDisnum()  == null || o1.getDisnum() == null)
			return 1;
		return o1.getDisnum().compareTo(o2.getDisnum());
	}

}
