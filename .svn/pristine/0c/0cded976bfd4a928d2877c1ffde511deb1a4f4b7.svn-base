package com.capinfo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.capinfo.security.bean.Menu;
import com.capinfo.security.dao.MenuDao;
import com.capinfo.security.service.MenuService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
@Service("menuService")
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;
	
	public SearchResult<Menu> getMenus(ISearch search) {
		return menuDao.searchAndCount(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(Long id) {
		if(id!=null){
			Menu menu = getMenuByIdWithChildren(id);
			Assert.isTrue(menu.getChildren().isEmpty(),"该菜单下还有子菜单，不允许删除。");
		 	menuDao.remove(menu);
		 	/* 更新父节点是否叶子节点标志  */
		 	if(menu != null && menu.getParent() != null){
				Menu parent = getMenuByIdWithChildren(menu.getParent().getMenuId());
				if(parent.getChildren().size()==0){
					parent.setLeaf(true);
					menuDao.save(parent);
				}
			}
		 	return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean edit(Menu menu) {
		Menu m = menuDao.find(menu.getMenuId());
		if(m != null){
			m.setMenuName(menu.getMenuName());
			m.setUrl(menu.getUrl());
			m.setDisnum(menu.getDisnum());
			m.setMenuIcon(menu.getMenuIcon());
			menuDao.save(m);
			return true;
		}
		return false;
	}

	public Menu getMenuById(Long id) {
		return menuDao.find(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean add(Menu menu) {
		Menu m = new Menu();
		m.setMenuName(menu.getMenuName());
		m.setUrl(menu.getUrl());
		m.setMenuIcon(menu.getMenuIcon());
		m.setDisnum(menu.getDisnum());
		m.setLeaf(true);
		
		if(menu.getParent()!=null && menu.getParent().getMenuId()!=null){
			Menu parent = menuDao.find(menu.getParent().getMenuId());
			if(parent != null){
				m.setParent(parent);
				/* 更新父节点是否叶子节点标志  */
				if(parent.isLeaf()){
					parent.setLeaf(false);
					menuDao.save(parent);
				}
			}
		}
		return menuDao.save(m);
	}

	/**
	 * [根据菜单编号获取菜单及其所有子菜单]
	 * @param menuId
	 * @return
	 */
	private Menu getMenuByIdWithChildren(Long menuId){
		if(menuId != null){
			Search search = new Search(Menu.class);
			search.addFetch("children");
			search.addFilterEqual("menuId", menuId);
			return menuDao.searchUnique(search );
		}
		return null;
	}
	
}
