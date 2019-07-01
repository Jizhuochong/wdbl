package com.capinfo.security.dao;

import java.util.List;

import com.capinfo.security.bean.Authority;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface AuthorityDao extends GenericDAO<Authority,Long>
{

	List<String> getAllAuthorityNames();
	List<Authority> getAllAuthorities();
}
