package cn.com.dfky.www;

public class WebServerSoapProxy implements cn.com.dfky.www.WebServerSoap {
  private String _endpoint = null;
  private cn.com.dfky.www.WebServerSoap webServerSoap = null;
  
  public WebServerSoapProxy() {
    _initWebServerSoapProxy();
  }
  
  public WebServerSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebServerSoapProxy();
  }
  
  private void _initWebServerSoapProxy() {
    try {
      webServerSoap = (new cn.com.dfky.www.WebServerLocator()).getWebServerSoap();
      if (webServerSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webServerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webServerSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webServerSoap != null)
      ((javax.xml.rpc.Stub)webServerSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cn.com.dfky.www.WebServerSoap getWebServerSoap() {
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap;
  }
  
  public boolean fileSave(java.lang.String filecode, java.lang.String cometime, java.lang.String comedept, java.lang.String comeno, java.lang.String filename, java.lang.String filetitle, java.lang.String filesecret, java.lang.String fileemerg, java.lang.String filelimittime, java.lang.String oldlabelinfo, java.lang.String deptname, java.lang.String deptcode, java.lang.String username) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.fileSave(filecode, cometime, comedept, comeno, filename, filetitle, filesecret, fileemerg, filelimittime, oldlabelinfo, deptname, deptcode, username);
  }
  
  public boolean updateFile(java.lang.String filecode, java.lang.String cometime, java.lang.String comedept, java.lang.String comeno, java.lang.String filename, java.lang.String filetitle, java.lang.String filesecret, java.lang.String fileemerg, java.lang.String filelimittime, java.lang.String oldlabelinfo, java.lang.String deptname, java.lang.String deptcode, java.lang.String username) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.updateFile(filecode, cometime, comedept, comeno, filename, filetitle, filesecret, fileemerg, filelimittime, oldlabelinfo, deptname, deptcode, username);
  }
  
  public boolean deleteFile(java.lang.String filecode) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.deleteFile(filecode);
  }
  
  public boolean fileRangeSave(java.lang.String filecode, java.lang.String getdeptlist, java.lang.String deptname, java.lang.String username) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.fileRangeSave(filecode, getdeptlist, deptname, username);
  }
  
  public boolean addUser(java.lang.String username, java.lang.String usercode, java.lang.String password, java.lang.String userdept, java.lang.String userorder) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.addUser(username, usercode, password, userdept, userorder);
  }
  
  public boolean updateUser(java.lang.String username, java.lang.String usercode, java.lang.String password, java.lang.String userdept, java.lang.String userorder) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.updateUser(username, usercode, password, userdept, userorder);
  }
  
  public boolean deleteUser(java.lang.String usercode) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.deleteUser(usercode);
  }
  
  public boolean addDeptIn(java.lang.String deptName, java.lang.String deptCode, java.lang.String deptOrder, java.lang.String remark) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.addDeptIn(deptName, deptCode, deptOrder, remark);
  }
  
  public boolean updateDeptIn(java.lang.String deptName, java.lang.String deptCode, java.lang.String deptOrder, java.lang.String remark) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.updateDeptIn(deptName, deptCode, deptOrder, remark);
  }
  
  public boolean deleteDeptIn(java.lang.String deptCode) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.deleteDeptIn(deptCode);
  }
  
  public boolean fileInBoxSave(java.lang.String filecode, java.lang.String senddept, java.lang.String getdept, java.lang.String shouldcount) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.fileInBoxSave(filecode, senddept, getdept, shouldcount);
  }
  
  public boolean deleteInBoxFile(java.lang.String filecode, java.lang.String getdept) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.deleteInBoxFile(filecode, getdept);
  }
  
  public java.lang.String searchWaitQFile(java.lang.String deptname) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.searchWaitQFile(deptname);
  }
  
  public java.lang.String searchEmerg(java.lang.String _deptName) throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.searchEmerg(_deptName);
  }
  
  public java.lang.String searchLog() throws java.rmi.RemoteException{
    if (webServerSoap == null)
      _initWebServerSoapProxy();
    return webServerSoap.searchLog();
  }
  
  
}