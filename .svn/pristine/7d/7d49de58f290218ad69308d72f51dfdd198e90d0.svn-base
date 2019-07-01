package com.capinfo.wdbl.action;

import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.namespace.QName;

import org.apache.axis.client.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@Scope("prototype")
@RequestMapping("/Interface")
public class InterfaceController {
	@RequestMapping(value = "/listLoad.do")
	public String listLoad(){
		/*URL wsdlURL = new URL("http://10.8.27.115/webservice/webserver.asmx");
		QName serviceName = new QName("WebServer");
//		JaxWsProxyFactoryBean factory = new JaxWsPortProxyFactoryBean();
//		factory.setServiceClass(WebServerSoap.class);
//		factory.setAddress("http://10.8.27.115/webservice/webserver.asmx?wsdl");
		
		WebServer service = (WebServer) Service.create(wsdlURL,serviceName);
		System.out.println("#############Client getUserByName##############");
		Boolean entity; 
		WebServerSoap ws =service.getWebServerSoap();
		try {
			entity = ws.addUser("filecode","cometime","comedept","comeno","filename");
			System.out.println(entity);
		} catch (Exception e) {
				e.printStackTrace();
		}*/
		try {
			Service service = new Service();
			Call call = (Call)service.createCall();
			call.setOperationName(new QName("AddUser"));
			call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("usercode", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("password", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("userdept", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("userorder", XMLType.XSD_STRING, ParameterMode.IN);
			
			call.setReturnType(XMLType.XSD_BOOLEAN);
			call.setTargetEndpointAddress("http://10.8.27.115/webservice/webserver.asmx");
			Boolean result  = (Boolean) call.invoke(new Object[]{"username","usercode","password","userdept","123"});
			System.out.println("return result:"+result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "ok";
	}
}	