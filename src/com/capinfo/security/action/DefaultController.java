package com.capinfo.security.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capinfo.base.service.BaseService;
import com.capinfo.security.access.intercept.CapinfoLoginAuthenticationFilter;
import com.capinfo.security.bean.Menu;
import com.capinfo.security.userdetails.UserDetailsHelper;
import com.capinfo.util.ComparatorMenu;
import com.capinfo.util.MenuTreeModel;

/**
 * 
 * DefaultController
 *
 */
@Controller
@RequestMapping("/")
public class DefaultController {
	private static final Log s_logger = LogFactory.getLog(DefaultController.class);
	
	@Autowired
	CapinfoLoginAuthenticationFilter loginAuthenticationFilter;
	
	@Autowired
	private BaseService baseService;
	
	private Map<String, String> orderMap = new HashMap<String, String>();
	
	/**
	 * [系统主页面]
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showIndex() {
		
		s_logger.debug("show index invoked");
		return "index";
	}
	
	/**
	 * 加载用户菜单
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/menu/json.do",method = RequestMethod.POST) 
	@ResponseBody 
	public List<MenuTreeModel> getMyMenus(HttpServletResponse response){
		List<MenuTreeModel> treeMenus =new ArrayList<MenuTreeModel>();
		
		try {
			List<Menu> menus = (List<Menu>) UserDetailsHelper.getMenus();
			ComparatorMenu comparator = new ComparatorMenu(); 
			Collections.sort(menus, comparator);
			for (Menu m : menus) {
				MenuTreeModel mtm = new MenuTreeModel(m.getMenuId(), m.getMenuName(), m.getUrl(), m.isLeaf(), 
						m.getParent()==null ? null:m.getParent().getMenuId(),convert(m.getChildren()));
				treeMenus.add(mtm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			s_logger.error(e.getMessage(), e);
		}
		return treeMenus;
	}


	private List<MenuTreeModel> convert(List<Menu> children) {
		if(children != null){
			ComparatorMenu comparator = new ComparatorMenu(); 
			Collections.sort(children, comparator);
		}
		List<MenuTreeModel> treeMenus = new ArrayList<MenuTreeModel>();
		for (Menu m : children) {
			MenuTreeModel mtm = new MenuTreeModel(m.getMenuId(), m.getMenuName(), m.getUrl(), m.isLeaf(), 
					m.getParent()==null  ? null:m.getParent().getMenuId(),convert(m.getChildren()));
			treeMenus.add(mtm);
		}
		return treeMenus;
	}
	
	
	/**
	 * [展示登录]
	 * @return
	 */
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String showLogin(ModelMap map){
		map.addAttribute("allowEmptyValidateCode", loginAuthenticationFilter.isAllowEmptyValidateCode());
		s_logger.debug("show login invoked");
		
		return "login";
	}
	
}
