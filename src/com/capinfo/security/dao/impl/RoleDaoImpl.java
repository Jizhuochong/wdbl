package com.capinfo.security.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.capinfo.security.bean.Role;
import com.capinfo.security.dao.RoleDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class RoleDaoImpl extends GenericDAOImpl<Role,Long> implements RoleDao
{
    private static final Log log = LogFactory.getLog(RoleDaoImpl.class);
    
    public RoleDaoImpl(){
	log.debug("RoleDao has been initialized");
    }

}
