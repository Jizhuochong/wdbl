package com.capinfo.security.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.capinfo.security.bean.Authority;
import com.capinfo.security.dao.AuthorityDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository("authorityDao")
public class AuthorityDaoImpl extends GenericDAOImpl<Authority,Long> implements AuthorityDao{
	
	private static final Log log = LogFactory.getLog(AuthorityDaoImpl.class);	

	public AuthorityDaoImpl(){
	    log.debug("AuthorityDao has been initialized");
	}

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	public List<String> getAllAuthorityNames()
	{
		final StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select a.authority_name from T_AUTHORITY a where a.enabled=1");
		HibernateCallback<List<String>> action = new HibernateCallback<List<String>>()
		{

			public List<String> doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createSQLQuery(queryBuilder.toString());
				return query.list();
			}
		};
		return hibernateTemplate.executeFind(action );
	}
	
	@SuppressWarnings("unchecked")
	public List<Authority> getAllAuthorities(){
		return hibernateTemplate.find("select a from Authority a left join fetch a.resources as resoureces where a.enabled=true");
	}

}

