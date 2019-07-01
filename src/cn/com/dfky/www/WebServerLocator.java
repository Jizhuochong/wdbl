/**
 * WebServerLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.dfky.www;

public class WebServerLocator extends org.apache.axis.client.Service implements cn.com.dfky.www.WebServer {

    public WebServerLocator() {
    }


    public WebServerLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebServerLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebServerSoap
    private java.lang.String WebServerSoap_address = "http://10.8.27.115/webservice/webserver.asmx";

    public java.lang.String getWebServerSoapAddress() {
        return WebServerSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebServerSoapWSDDServiceName = "WebServerSoap";

    public java.lang.String getWebServerSoapWSDDServiceName() {
        return WebServerSoapWSDDServiceName;
    }

    public void setWebServerSoapWSDDServiceName(java.lang.String name) {
        WebServerSoapWSDDServiceName = name;
    }

    public cn.com.dfky.www.WebServerSoap getWebServerSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebServerSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebServerSoap(endpoint);
    }

    public cn.com.dfky.www.WebServerSoap getWebServerSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.com.dfky.www.WebServerSoapStub _stub = new cn.com.dfky.www.WebServerSoapStub(portAddress, this);
            _stub.setPortName(getWebServerSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebServerSoapEndpointAddress(java.lang.String address) {
        WebServerSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.com.dfky.www.WebServerSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.com.dfky.www.WebServerSoapStub _stub = new cn.com.dfky.www.WebServerSoapStub(new java.net.URL(WebServerSoap_address), this);
                _stub.setPortName(getWebServerSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WebServerSoap".equals(inputPortName)) {
            return getWebServerSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.dfky.com.cn/", "WebServer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.dfky.com.cn/", "WebServerSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebServerSoap".equals(portName)) {
            setWebServerSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
