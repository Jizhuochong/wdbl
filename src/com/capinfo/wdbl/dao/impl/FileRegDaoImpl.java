package com.capinfo.wdbl.dao.impl;

import org.springframework.stereotype.Repository;

import com.capinfo.wdbl.bean.FileReg;
import com.capinfo.wdbl.dao.FileRegDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository("fielRegDao")
public class FileRegDaoImpl extends GenericDAOImpl<FileReg,Long> implements FileRegDao{
}
