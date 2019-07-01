package com.capinfo.wdbl.dao.impl;

import org.springframework.stereotype.Repository;

import com.capinfo.wdbl.bean.SeqNum;
import com.capinfo.wdbl.dao.SeqNumDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository("seqNumDao")
public class SeqNumDaoImpl extends GenericDAOImpl<SeqNum,Long> implements SeqNumDao{
}
