package com.capinfo.security.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.capinfo.security.bean.Menu;
import com.capinfo.security.dao.MenuDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
@Repository
public class MenuDaoImpl extends GenericDAOImpl<Menu, Long> implements MenuDao{
    private static final Log log = LogFactory.getLog(MenuDaoImpl.class);
    public MenuDaoImpl(){
	log.debug("MenuDao has been initialized");
    }
}

