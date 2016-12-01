package com.concordia.dist.asg1.Client.auto;

public class BaseServerProxy implements com.concordia.dist.asg1.Client.auto.BaseServer {
  private String _endpoint = null;
  private com.concordia.dist.asg1.Client.auto.BaseServer montrealServer = null;
  
  public BaseServerProxy() {
    _initMontrealServerProxy();
  }
  
  public BaseServerProxy(String endpoint) {
    _endpoint = endpoint;
    _initMontrealServerProxy();
  }
  
  private void _initMontrealServerProxy() {
    try {
      montrealServer = (new com.concordia.dist.asg1.Client.auto.BaseServerServiceLocator()).getMontrealServer();
      if (montrealServer != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)montrealServer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)montrealServer)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (montrealServer != null)
      ((javax.xml.rpc.Stub)montrealServer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.concordia.dist.asg1.Client.auto.BaseServer getMontrealServer() {
    if (montrealServer == null)
      _initMontrealServerProxy();
    return montrealServer;
  }
  
  public java.lang.String getBookedFlightCount(java.lang.String recordType) throws java.rmi.RemoteException{
    if (montrealServer == null)
      _initMontrealServerProxy();
    return montrealServer.getBookedFlightCount(recordType);
  }
  
  public java.lang.String transferReservation(java.lang.String passengerID, java.lang.String currentCity, java.lang.String otherCity) throws java.rmi.RemoteException{
    if (montrealServer == null)
      _initMontrealServerProxy();
    return montrealServer.transferReservation(passengerID, currentCity, otherCity);
  }
  
  public java.lang.String bookFlight(java.lang.String firstName, java.lang.String lastName, java.lang.String address, java.lang.String phone, java.lang.String destination, java.lang.String date, java.lang.String flightClass) throws java.rmi.RemoteException{
    if (montrealServer == null)
      _initMontrealServerProxy();
    return montrealServer.bookFlight(firstName, lastName, address, phone, destination, date, flightClass);
  }
  
  public java.lang.String editFlightRecord(java.lang.String recordID, java.lang.String fieldName, java.lang.String newValue) throws java.rmi.RemoteException{
    if (montrealServer == null)
      _initMontrealServerProxy();
    return montrealServer.editFlightRecord(recordID, fieldName, newValue);
  }
  
  
}