/**
 * WebServerSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.dfky.www;

import javax.jws.WebService;

@WebService(targetNamespace="http://www.dfky.com.cn/")
public interface WebServerSoap extends java.rmi.Remote {
    public boolean fileSave(java.lang.String filecode, java.lang.String cometime, java.lang.String comedept, java.lang.String comeno, java.lang.String filename, java.lang.String filetitle, java.lang.String filesecret, java.lang.String fileemerg, java.lang.String filelimittime, java.lang.String oldlabelinfo, java.lang.String deptname, java.lang.String deptcode, java.lang.String username) throws java.rmi.RemoteException;
    public boolean updateFile(java.lang.String filecode, java.lang.String cometime, java.lang.String comedept, java.lang.String comeno, java.lang.String filename, java.lang.String filetitle, java.lang.String filesecret, java.lang.String fileemerg, java.lang.String filelimittime, java.lang.String oldlabelinfo, java.lang.String deptname, java.lang.String deptcode, java.lang.String username) throws java.rmi.RemoteException;
    public boolean deleteFile(java.lang.String filecode) throws java.rmi.RemoteException;
    public boolean fileRangeSave(java.lang.String filecode, java.lang.String getdeptlist, java.lang.String deptname, java.lang.String username) throws java.rmi.RemoteException;
    public boolean addUser(java.lang.String username, java.lang.String usercode, java.lang.String password, java.lang.String userdept, java.lang.String userorder) throws java.rmi.RemoteException;
    public boolean updateUser(java.lang.String username, java.lang.String usercode, java.lang.String password, java.lang.String userdept, java.lang.String userorder) throws java.rmi.RemoteException;
    public boolean deleteUser(java.lang.String usercode) throws java.rmi.RemoteException;
    public boolean addDeptIn(java.lang.String deptName, java.lang.String deptCode, java.lang.String deptOrder, java.lang.String remark) throws java.rmi.RemoteException;
    public boolean updateDeptIn(java.lang.String deptName, java.lang.String deptCode, java.lang.String deptOrder, java.lang.String remark) throws java.rmi.RemoteException;
    public boolean deleteDeptIn(java.lang.String deptCode) throws java.rmi.RemoteException;
    public boolean fileInBoxSave(java.lang.String filecode, java.lang.String senddept, java.lang.String getdept, java.lang.String shouldcount) throws java.rmi.RemoteException;
    public boolean deleteInBoxFile(java.lang.String filecode, java.lang.String getdept) throws java.rmi.RemoteException;
    public java.lang.String searchWaitQFile(java.lang.String deptname) throws java.rmi.RemoteException;
    public java.lang.String searchEmerg(java.lang.String _deptName) throws java.rmi.RemoteException;
    public java.lang.String searchLog() throws java.rmi.RemoteException;
}
