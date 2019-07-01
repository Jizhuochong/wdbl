package com.capinfo.security.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.capinfo.security.bean.User;
import com.capinfo.security.dao.UserDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository @Component
public class UserDaoImpl extends GenericDAOImpl<User,Long> implements UserDao
{
    private static final Log log = LogFactory.getLog(UserDaoImpl.class);
    
    public UserDaoImpl(){
	log.debug("UserDao has been initialized");
    }
    
}
