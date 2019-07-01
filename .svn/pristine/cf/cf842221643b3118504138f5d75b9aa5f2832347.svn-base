package com.capinfo.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.security.bean.Resource;
import com.capinfo.security.dao.ResourceDao;
import com.capinfo.security.service.ResourceService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
@Service("resourceService")
@Transactional(readOnly = true)
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private ResourceDao resourceDao; 
	
	public SearchResult<Resource> getResources(ISearch search) {
		return resourceDao.searchAndCount(search);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean delete(Long[] ids) {
		if(ids!=null && ids.length>0){
			resourceDao.removeByIds(ids);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean edit(Resource resource) {
		Resource u = resourceDao.find(resource.getResourceId());
		if(u != null){
			u.setResourceName(resource.getResourceName());
			u.setEnabled(resource.getEnabled());
			u.setResourceDesc(resource.getResourceDesc());
			u.setResourceString(resource.getResourceString());
			u.setResourceType(resource.getResourceType());
			resourceDao.save(u);
			return true;
		}
		return false;
	}

	public Resource getResourceById(Long id) {
		return resourceDao.find(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean add(Resource resource) {
		Resource r = new Resource();
		r.setEnabled(resource.getEnabled());
		r.setResourceName(resource.getResourceName());
		r.setResourceString(resource.getResourceString());
		r.setResourceType(resource.getResourceType());
		r.setResourceDesc(resource.getResourceDesc());
		return resourceDao.save(resource);
	}

}
