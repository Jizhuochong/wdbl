package com.capinfo.wdbl.service.impl;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.capinfo.sys.ConfigINFO;
import com.capinfo.wdbl.service.WsService;

@Service("wsService")
public class WsServiceImpl implements WsService{
	
	private static final Log log = LogFactory.getLog(WsServiceImpl.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private ConfigINFO configINFO;
	
	public boolean tranFileToUnit(String barNo,String whereAbout,String createUser,String userDept) {
		Boolean result = false;
		if(configINFO.getDug().booleanValue())
			return true;
		try{
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			Call call = (Call) service.createCall();
			call.setOperationName(new QName("FileRangeSave"));
			call.addParameter("filecode", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("getdeptlist", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("deptname", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_BOOLEAN);
			call.setTargetEndpointAddress(configINFO.getWsURL());
			result = (Boolean)call.invoke(new Object[]{barNo,whereAbout,userDept,createUser});
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean wsDelFile(String filecode){
		Boolean result = false;
		if(configINFO.getDug().booleanValue())
			return true;
		try {
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			Call call = (Call)service.createCall();
			call.setOperationName(new QName("DeleteFile"));
			call.addParameter("filecode", XMLType.XSD_STRING, ParameterMode.IN);
			
			call.setReturnType(XMLType.XSD_BOOLEAN);
			call.setTargetEndpointAddress(configINFO.getWsURL());
			result  = (Boolean) call.invoke(new Object[]{filecode});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
