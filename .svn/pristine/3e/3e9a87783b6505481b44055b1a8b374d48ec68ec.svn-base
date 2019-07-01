package com.capinfo.wdbl.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.capinfo.base.dao.impl.BaseDaoImpl;
import com.capinfo.util.BeanRefUtil;
import com.capinfo.util.PageBean;
import com.capinfo.wdbl.bean.ProcessRecord;
import com.capinfo.wdbl.dao.ProcessRecordDao;

@Repository("processRecordDao")
public class ProcessRecordDaoImpl extends BaseDaoImpl implements ProcessRecordDao{
	
	/**
	 *	根据文件id 分页查询 
	 */
	public  List<ProcessRecord> getListByPageAndFileId(final PageBean pageBean,final ProcessRecord bean,final Long fileid){
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session){
				StringBuffer sqlBuffer = new StringBuffer();
				StringBuffer countBuffer = new StringBuffer();
				sqlBuffer.append(" from ProcessRecord where fileReg.id = ? ");
				countBuffer.append(" select count(*) from processrecord p where p.fileregid = ? ");
				
				/**查询条件
				 * 注： 只有当实体中各属性与数据库字段一样时可以使用此方法
				 * */
				BeanRefUtil beanreutil = new BeanRefUtil();
				Class<?> cls = bean.getClass();                                                                    
				Method[] methods = cls.getDeclaredMethods();                                                       
				Field[] fields = cls.getDeclaredFields();                                                          
			                                                                                                      
				for (Field field : fields) {                                                                       
					try {                                                                                          
						String fieldGetName = beanreutil.parGetName(field.getName());                                         
						if (!beanreutil.checkGetMet(methods, fieldGetName)) {                                                 
							continue;                                                                              
						}                                                                                          
						Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});     
						Object f_v = fieldGetMet.invoke(bean, new Object[] {});
			            String fieldVal = null;    
			            if(f_v!=null){
			            	fieldVal = String.valueOf(f_v);
			            }
			            
			            if(fieldVal!=null&&fieldVal.length()>0){
			    			String[] s_f = fieldVal.split("##");
			    			if(s_f.length == 2 &&s_f[0]!=null&&s_f[0].trim().length()>0&&s_f[1]!=null&&s_f[1].trim().equals("true")){
			    				sqlBuffer.append(" and ").append(field.getName()).append(" like '%").append(s_f[0].trim()).append("%'");
			    				countBuffer.append(" and ").append(field.getName()).append(" like '%").append(s_f[0].trim()).append("%'");
			    			}else{
			    				if(s_f[0]!=null&&s_f[0].trim().length()>0){
			    					sqlBuffer.append(" and ").append(field.getName()).append(" = '").append(s_f[0].trim()).append("'");
			    					countBuffer.append(" and ").append(field.getName()).append(" = '").append(s_f[0].trim()).append("'");
			    				}
			    			}
			    		}
			            
					} catch (Exception e) {                                                                        
						continue;                                                                                  
			       	}                                                                                              
				}    
				
				
				/** 排序*/
				if(pageBean!=null&&pageBean.getSort()!=null){
					if(pageBean.getDir()!=null&&pageBean.getDir().toLowerCase().equals("desc")){
	        			sqlBuffer.append(" order by ").append(pageBean.getSort()).append(" desc ");
	        		}else{
	        			sqlBuffer.append(" order by ").append(pageBean.getSort()).append(" asc ");
	        		}
				}
				
				/**查询总记录条数*/
				if(pageBean!=null){
					SQLQuery countquery = session.createSQLQuery(countBuffer.toString());
					countquery.setLong(0, fileid);
					String totalItems = countquery.list().get(0).toString();
					pageBean.setTotal(Integer.parseInt(totalItems));
				}
				
				Query  query = session.createQuery(sqlBuffer.toString());
				query.setLong(0, fileid);
				
				/**分页信息*/
				if(pageBean!=null){
					query.setFirstResult(pageBean.getStart());
					query.setMaxResults(pageBean.getLimit());
				}
			
				List list = query.list(); 
				
				
				return list;
			}
		};
		return getHibernateTemplate().executeFind(callback);
	}

	@Override
	public ProcessRecord loadProcess(final Long fileid) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) {
				String sqlString = "FROM ProcessRecord PR WHERE (handlestatus = '呈送' OR handlestatus = '已投箱') AND fileReg.id = ? ORDER BY sys_date DESC";
				Query query = session.createQuery(sqlString);
				query.setLong(0, fileid);
				query.setFirstResult(0);
				query.setMaxResults(1);
				return query.list();
			}
		};
		List list = getHibernateTemplate().executeFind(callback);
		if (list != null && list .size() > 0)
			return (ProcessRecord) list.get(0);
		else 
			return null;
	}
	
}
