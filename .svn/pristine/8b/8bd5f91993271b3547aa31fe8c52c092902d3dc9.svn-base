package com.capinfo.security.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.security.bean.Authority;
import com.capinfo.security.bean.Menu;
import com.capinfo.security.bean.Resource;
import com.capinfo.security.dao.AuthorityDao;
import com.capinfo.security.dao.MenuDao;
import com.capinfo.security.dao.ResourceDao;
import com.capinfo.security.service.AuthorityService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
@Service("authorityService")
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService{
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private MenuDao menuDao;

	public SearchResult<Authority> getAuthorities(ISearch search) {
		return authorityDao.searchAndCount(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(Long[] ids) {
		if(ids!=null && ids.length>0){
			authorityDao.removeByIds(ids);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean edit(Authority authority) {
		Authority u = authorityDao.find(authority.getAuthorityId());
		if(u != null){
			u.setAuthorityName(authority.getAuthorityName());
			u.setAuthorityDesc(authority.getAuthorityDesc());
			u.setEnabled(authority.getEnabled());
			authorityDao.save(u);
			return true;
		}
		return false;
	}

	public Authority getAuthorityById(Long id) {
		return authorityDao.find(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean add(Authority authority) {
		return authorityDao.save(authority);
	}

	public Authority queryAuthority(Search search) {
		return null;
	}

	public int count(Search search) {
		return authorityDao.count(search);
	}

	public Authority getAuthorityByIdWithResource(Long id) {
		Search search = new Search(Authority.class);
		search.addFetch("resources");
		search.addFilterEqual("authorityId",id);
		return authorityDao.searchUnique(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean addResMapping(Long id, Long[] ids) {
		Authority authority = authorityDao.find(id);
		if(authority != null){
			Set<Resource> resources = new HashSet<Resource>();
			if(!ArrayUtils.isEmpty(ids)){
				for (int i = 0; i < ids.length; i++) {
					Resource resource = resourceDao.find(ids[i]);
					resources.add(resource);
				}
			}
			authority.setResources(resources);
			authorityDao.save(authority);
			return true;
		}
		return false;
	}

	public Authority getAuthorityByIdWithMenu(Long id) {
		Search search = new Search(Authority.class);
		search.addFetch("menus");
		search.addFilterEqual("authorityId",id);
		return authorityDao.searchUnique(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean addMenuMapping(Long id, Long[] ids) {
		Authority authority = authorityDao.find(id);
		if(authority != null){
			Set<Menu> menus = new HashSet<Menu>();
			if(!ArrayUtils.isEmpty(ids)){
				for (int i = 0; i < ids.length; i++) {
					Menu menu = menuDao.find(ids[i]);
					menus.add(menu);
				}
			}
			authority.setMenus(menus);
			authorityDao.save(authority);
			return true;
		}
		return false;
	}
	
}
