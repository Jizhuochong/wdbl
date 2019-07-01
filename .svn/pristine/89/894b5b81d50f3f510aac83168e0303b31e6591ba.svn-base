package com.capinfo.base.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.capinfo.util.PageBean;
/**
 *service
 */
public interface BaseService {
	/**保存实体*/
	public <T> T saveObject(T paramT);
	
	/***更新实体*/
	public <T> T updateObject(T t);
	
	/***保存或更新实体*/
	public <T> T saveOrUpdateObject(T t) ;

	/***批量保存更新实体*/
	public <T> void saveOrUpdateAll(Collection<T> paramCollection);
	
	/***删除实体*/
	public <T> T deleteObject(T paramT);
	
	/***批量删除实体*/
	public <T> void deleteAll(Collection<T> paramCollection);
	
	/**根据主键获取实体*/
	public <T> T getObject(Class<T> paramClass,Serializable paramSerializable);
	
	/**加载所有实体*/
	public <T> List<T> loadAll(Class<T> paramClass);
	
	/**分页查询*/
	public  <T> List<T> findByPage(PageBean pageBean,Class<T> c,Map<String, Object> conditionMap,Map<String, String> orderMap);		
	
	/**分页查询*/
	public  <T> List<T> findByPage(PageBean pageBean,Class<T> c,T c_t,Map<String, String> orderMap);	
	
	/**条件查询*/
	public <T> List<T> getByProperty(Class<T> c,Map<String, Object> conditionMap,Map<String, String> orderMap);
	
	/**条件查询*/
	public <T> List<T> getByProperty(Class<T> c,T c_t,Map<String, String> orderMap);
	
	/**分页查询*/
	public  <T> List<T> queryByPage(final PageBean pageBean,final Class<T> c, T bean);
	/*** 执行hql */
	public boolean execute(String hql);
	
	/**
	 * 根据主键获取实体并获取其级联属性
	 * @param clazz
	 * @param id
	 * @param properties
	 * @return
	 */
	public <T> T getObjectWithProperties(Class<T> clazz, Serializable paramSerializable,
			String ... properties);
	/*根据文件id获取列表*/
	public  <T> List<T> getListByFileId(final Class<T> c,final Long id);
	/**
	 * 分页获取含过滤及排序条件的数据列表
	 * @param pageBean 分页参数
	 * @param t	数据实体
	 * @param sort	排序字段
	 * @param dir	排序指令
	 * @param criterions 查询条件数组
	 * @return
	 */
	public <T> List<T> findByPage(final PageBean pageBean,final T t,final String sort,final String dir,final Criterion ... criterions);
}