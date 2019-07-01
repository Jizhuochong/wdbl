package com.capinfo.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.capinfo.base.dao.BaseDao;
import com.capinfo.util.BeanRefUtil;
import com.capinfo.util.PageBean;

@Repository("baseDao")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	/*** 保存实体 */
	public <T> T saveObject(T t) {
		this.getHibernateTemplate().save(t);
		return t;
	}

	/** 更新实体 */
	public <T> T updateObject(T t) {
		this.getHibernateTemplate().update(t);
		return t;
	}

	/*** 保存/更新实体 */
	public <T> T saveOrUpdateObject(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
		return t;
	}

	/*** 批量保存/更新实体 */
	public <T> void saveOrUpdateAll(Collection<T> c) {
		getHibernateTemplate().saveOrUpdateAll(c);
	}

	/*** 删除实体 */
	public <T> T deleteObject(T t) {
		this.getHibernateTemplate().delete(t);
		return t;
	}

	/*** 批量删除 */
	public <T> void deleteAll(Collection<T> c) {
		getHibernateTemplate().deleteAll(c);
	}

	/*** 根据id获取实体 */
	public <T> T getObject(Class<T> c, Serializable id) {
		return this.getHibernateTemplate().get(c, id);
	}

	/*** 加载所有 */
	public <T> List<T> loadAll(Class<T> c) {
		return (List<T>) getHibernateTemplate().loadAll(c);
	}

	/** 分页查询 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByPage(final PageBean pageBean, final Class<T> c,
			final Map<String, Object> conditionMap,
			final Map<String, String> orderMap) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				Criteria cri = session.createCriteria(c);
				/** 查询条件 */
				if (conditionMap != null) {
					Set<String> keySet = conditionMap.keySet();
					for (Iterator it = keySet.iterator(); it.hasNext();) {
						String key = (String) it.next();
						Object value = (Object) conditionMap.get(key);
						if (value != null && value instanceof String) {
							value = "%" + ((String) value).trim() + "%";
						}
						if (value != null) {
							cri.add(Restrictions.like(key, value));
						}
					}
				}
				/** 排序 */
				if (orderMap != null) {
					Set<String> keySet_o = orderMap.keySet();
					for (Iterator it = keySet_o.iterator(); it.hasNext();) {
						String key = (String) it.next();
						String value = (String) orderMap.get(key);
						if (value != null) {
							value = value.trim().toLowerCase();
							if (value.equals("desc")) {
								cri.addOrder(Property.forName(key).desc());
							} else {
								cri.addOrder(Property.forName(key).asc());
							}
						}
					}
				}

				/** 查询总记录条数 */
				String totalItems = cri.setProjection(Projections.rowCount()).uniqueResult().toString();
				cri.setProjection(null);
				pageBean.setTotal(Integer.parseInt(totalItems));

				/** 分页信息 */
				if (pageBean != null) {
					cri.setFirstResult(pageBean.getStart());
					cri.setMaxResults(pageBean.getLimit());
				}
				List<T> list = cri.list();

				return list;
			}
		};
		return getHibernateTemplate().executeFind(callback);

	}

	/**
	 * 分页查询 cri.add(Example.create(c_t).enableLike(MatchMode.ANYWHERE))
	 * 模糊查询加上上面的 使用Criteria 查询的时候 int char等类型不能为空 有缺陷
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findByPage(final PageBean pageBean, final Class<T> c,
			final T c_t, final Map<String, String> orderMap) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				Criteria cri = session.createCriteria(c);
				/** 查询条件 */
				if (c_t != null) {
					cri.add(Example.create(c_t).enableLike(MatchMode.ANYWHERE));
				}
				/** 排序 */
				if (orderMap != null) {
					Set<String> keySet_o = orderMap.keySet();
					for (Iterator it = keySet_o.iterator(); it.hasNext();) {
						String key = (String) it.next();
						String value = (String) orderMap.get(key);
						if (value != null) {
							value = value.trim().toLowerCase();
							if (value.equals("desc")) {
								cri.addOrder(Property.forName(key).desc());
							} else {
								cri.addOrder(Property.forName(key).asc());
							}
						}
					}
				}

				/** 查询总记录条数 */
				Projection original = ((CriteriaImpl) cri).getProjection();// 获取原有投影信息
				String totalItems = cri.setProjection(Projections.rowCount())
						.uniqueResult().toString();
				pageBean.setTotal(Integer.parseInt(totalItems));
				cri.setProjection(original);// 完成统计条数后恢复原投影信息
				if (original == null) {
					cri.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				}

				/** 分页信息 */
				if (pageBean != null) {
					cri.setFirstResult(pageBean.getStart());
					cri.setMaxResults(pageBean.getLimit());
				}

				List<T> list = cri.list();

				return list;
			}
		};
		return getHibernateTemplate().executeFind(callback);
	}

	/** 条件查询 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getByProperty(final Class<T> c,
			final Map<String, Object> conditionMap,
			final Map<String, String> orderMap) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				Criteria cri = session.createCriteria(c);

				/** 查询条件 */
				if (conditionMap != null) {
					Set<String> keySet = conditionMap.keySet();
					for (Iterator it = keySet.iterator(); it.hasNext();) {
						String key = (String) it.next();
						Object value = (Object) conditionMap.get(key);
						if (value != null && value instanceof String) {
							value = ((String) value).trim();
						}
						if (value != null) {
							cri.add(Restrictions.eq(key, value));
						}
					}
				}
				/** 排序 */
				if (orderMap != null) {
					Set<String> keySet_o = orderMap.keySet();
					for (Iterator it = keySet_o.iterator(); it.hasNext();) {
						String key = (String) it.next();
						String value = (String) orderMap.get(key);
						if (value != null) {
							value = value.trim().toLowerCase();
							if (value.equals("desc")) {
								cri.addOrder(Property.forName(key).desc());
							} else {
								cri.addOrder(Property.forName(key).asc());
							}
						}
					}
				}
				List<T> list = cri.list();
				return list;
			}
		};
		return getHibernateTemplate().executeFind(callback);
	}

	/** 条件查询 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getByProperty(final Class<T> c, final T c_t,
			final Map<String, String> orderMap) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				Criteria cri = session.createCriteria(c);
				/** 查询条件 */
				if (c_t != null) {
					cri.add(Example.create(c_t).enableLike(MatchMode.ANYWHERE));
				}
				/** 排序 */
				if (orderMap != null) {
					Set<String> keySet_o = orderMap.keySet();
					for (Iterator it = keySet_o.iterator(); it.hasNext();) {
						String key = (String) it.next();
						String value = (String) orderMap.get(key);
						if (value != null) {
							value = value.trim().toLowerCase();
							if (value.equals("desc")) {
								cri.addOrder(Property.forName(key).desc());
							} else {
								cri.addOrder(Property.forName(key).asc());
							}
						}
					}
				}
				List<T> list = cri.list();
				return list;
			}
		};
		return getHibernateTemplate().executeFind(callback);
	}

	/**
	 * 分页查询 通过##区分是否模糊 *
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryByPage(final PageBean pageBean, final Class<T> c,
			final T bean) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				Criteria cri = session.createCriteria(c);
				/** 查询条件 */
				BeanRefUtil beanreutil = new BeanRefUtil();
				Class<?> cls = bean.getClass();
				Method[] methods = cls.getDeclaredMethods();
				Field[] fields = cls.getDeclaredFields();

				for (Field field : fields) {
					try {
						String fieldGetName = beanreutil.parGetName(field
								.getName());
						if (!beanreutil.checkGetMet(methods, fieldGetName)) {
							continue;
						}
						Method fieldGetMet = cls.getMethod(fieldGetName,
								new Class[] {});
						Object f_v = fieldGetMet.invoke(bean, new Object[] {});
						String fieldVal = null;
						if (f_v != null) {
							fieldVal = String.valueOf(f_v);
						}

						if (fieldVal != null && fieldVal.length() > 0) {
							String[] s_f = fieldVal.split("##");
							if (s_f.length == 2 && s_f[0] != null
									&& s_f[0].trim().length() > 0
									&& s_f[1] != null
									&& s_f[1].trim().equals("true")) {
								cri.add(Restrictions.like(field.getName(), "%"
										+ s_f[0].trim() + "%"));
							} else {
								if (s_f[0] != null
										&& s_f[0].trim().length() > 0) {
									cri.add(Restrictions.eq(field.getName(),
											s_f[0].trim()));
								}
							}
						}

					} catch (Exception e) {
						continue;
					}
				}

				/** 排序 */
				if (pageBean != null && pageBean.getSort() != null) {
					if (pageBean.getDir() != null
							&& pageBean.getDir().toLowerCase().equals("desc")) {
						cri.addOrder(Property.forName(pageBean.getSort())
								.desc());
					} else {
						cri.addOrder(Property.forName(pageBean.getSort()).asc());
					}
				}

				/** 查询总记录条数 */
				String totalItems = cri.setProjection(Projections.rowCount())
						.uniqueResult().toString();
				cri.setProjection(null);
				pageBean.setTotal(Integer.parseInt(totalItems));

				/** 分页信息 */
				if (pageBean != null) {
					cri.setFirstResult(pageBean.getStart());
					cri.setMaxResults(pageBean.getLimit());
				}
				List<T> list = cri.list();

				return list;
			}
		};

		return getHibernateTemplate().executeFind(callback);
	}

	public <T> T getObjectWithProperties(final Class<T> clazz,
			final Serializable paramSerializable, final String... properties) {
		HibernateCallback<T> action = new HibernateCallback<T>() {

			@SuppressWarnings("unchecked")
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria c = session.createCriteria(clazz);
				c.add(Restrictions.idEq(paramSerializable));
				T t = (T) c.uniqueResult();
				for (String property : properties) {
					Hibernate.initialize(property);
				}
				return t;
			}
		};
		return getHibernateTemplate().execute(action);
	}

	/* 根据文件id获取列表 */
	public <T> List<T> getListByFileId(final Class<T> c, final Long id) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				String sqlString = "from " + c.getSimpleName()
						+ " fileReg where fileReg.id = ?";
				Query query = session.createQuery(sqlString);
				query.setLong(0, id);
				List list = query.list();
				return list;
			}
		};
		List list = getHibernateTemplate().executeFind(callback);
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByPage(final PageBean pageBean, final T t,
			final String sort, final String dir, final Criterion... criterions) {
		HibernateCallback<List<T>> callback = new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) {
				Criteria cri = session.createCriteria(t.getClass());
				/** 查询条件 */
				if (criterions != null && criterions.length > 0) {
					for (int i = 0; i < criterions.length; i++) {
						cri.add(criterions[i]);
					}
				}
				/** 排序 */
				if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(dir)) {
					if (dir.toLowerCase().equals("desc")) {
						cri.addOrder(Order.desc(sort));
					} else {
						cri.addOrder(Order.asc(sort));
					}
				}
				/** 查询总记录条数 */
				Projection original = ((CriteriaImpl) cri).getProjection();// 获取原有投影信息
				String totalItems = cri.setProjection(Projections.rowCount())
						.uniqueResult().toString();
				pageBean.setTotal(Integer.parseInt(totalItems));
				cri.setProjection(original);// 完成统计条数后恢复原投影信息
				if (original == null) {
					cri.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				}

				/** 分页信息 */
				if (pageBean != null) {
					cri.setFirstResult(pageBean.getStart());
					cri.setMaxResults(pageBean.getLimit());
				}
				return cri.list();
			}
		};
		return getHibernateTemplate().executeFind(callback);
	}

}