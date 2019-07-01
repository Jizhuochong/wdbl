package com.capinfo.base.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.capinfo.base.dao.BaseDao;
import com.capinfo.base.service.BaseService;
import com.capinfo.util.PageBean;


@Service("baseService")
public class BaseServiceImpl implements BaseService {
	@Autowired
	private BaseDao baseDao;


	/*** 保存实体*/
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public <T> T saveObject(T t) {
		return this.baseDao.saveObject(t);
	}
	/**更新实体*/
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public <T> T updateObject(T t) {
		return this.baseDao.updateObject(t);
	}
	/***保存/更新实体*/
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public <T> T saveOrUpdateObject(T t) {
		return this.baseDao.saveOrUpdateObject(t);
	}
	/*** 批量保存/更新实体*/
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public <T> void saveOrUpdateAll(Collection<T> c) {
		this.baseDao.saveOrUpdateAll(c);
	}
	/*** 删除实体*/
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public <T> T deleteObject(T t) {
		return this.baseDao.deleteObject(t);
	}
	/*** 批量删除*/
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public <T> void deleteAll(Collection<T> c) {
		this.baseDao.deleteAll(c);
	}
	/*** 根据id获取实体*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED) 
	public <T> T getObject(Class<T> c, Serializable id) {
		return this.baseDao.getObject(c, id);
	}
	/*** 加载所有*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED) 
	public <T> List<T> loadAll(Class<T> c) {
		return this.baseDao.loadAll(c);
	}
	
	/**分页查询*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED)  
	public  <T> List<T> findByPage(PageBean pageBean,Class<T> c,Map<String, Object> conditionMap,Map<String, String> orderMap){
		return this.baseDao.findByPage(pageBean, c, conditionMap, orderMap);
	}
	
	/**分页查询*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED)  
	public  <T> List<T> findByPage(PageBean pageBean,Class<T> c,T c_t,Map<String, String> orderMap){
		return this.baseDao.findByPage(pageBean, c, c_t, orderMap);
	}
	
	/**条件查询*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> List<T> getByProperty(Class<T> c,Map<String, Object> conditionMap,Map<String, String> orderMap) {
		return this.baseDao.getByProperty(c, conditionMap, orderMap);
	}
	
	/**条件查询*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED) 
	public <T> List<T> getByProperty(Class<T> c,T c_t,Map<String, String> orderMap){
		return this.baseDao.getByProperty(c, c_t, orderMap);
	}
	/**分页查询*/
	@Transactional(readOnly = true,propagation=Propagation.NOT_SUPPORTED) 
	public  <T> List<T> queryByPage(final PageBean pageBean,final Class<T> c, T bean){
		return this.baseDao.queryByPage(pageBean, c,bean);
	}
	public <T> T getObjectWithProperties(Class<T> clazz, Serializable paramSerializable, String... properties) {
		return this.baseDao.getObjectWithProperties(clazz,paramSerializable,properties);
	}
	/*根据文件id获取列表*/
	public  <T> List<T> getListByFileId(final Class<T> c,final Long id){
		return  this.baseDao.getListByFileId(c, id);
	}
	public <T> List<T> findByPage(PageBean pageBean, T t, String sort,
			String dir, Criterion... criterions) {
		return this.baseDao.findByPage(pageBean, t, sort, dir, criterions);
	}

	public boolean execute(String hql) {
		return ((BaseService) this.baseDao).execute(hql);
	}
	
	/**通过hql语句进行分页查询 lish*/
	public <T> List<T> findByPageUseHql(PageBean pageBean, String hql) {
		return ((BaseServiceImpl) this.baseDao).findByPageUseHql(pageBean, hql);
	}

}
