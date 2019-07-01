package com.capinfo.util;

import java.util.Comparator;

public class ComparatorTreeMenu implements Comparator<TreeNodeJsonBean> {

	public int compare(TreeNodeJsonBean o1, TreeNodeJsonBean o2) {
		return o1.getDisnum().compareTo(o2.getDisnum());
	}

}
