/**
 * MontrealServerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.concordia.dist.asg1.Client.auto;

public class BaseServerServiceLocator extends org.apache.axis.client.Service implements com.concordia.dist.asg1.Client.auto.BaseServerService {
	 // The WSDD service name defaults to the port name.
	private java.lang.String MontrealServerWSDDServiceName = "";
	
	// Use to get a proxy class for MontrealServer
    private java.lang.String MontrealServer_address = "http://localhost/FlightManagerWebService/services/"+MontrealServerWSDDServiceName;
    public BaseServerServiceLocator() {
    	this.MontrealServerWSDDServiceName="MontrealServer";
    	this.MontrealServer_address = "http://localhost/FlightManagerWebService/services/"+this.MontrealServerWSDDServiceName;
    	
    }

    public BaseServerServiceLocator(String serverName) {
    	this.MontrealServerWSDDServiceName = serverName+"Server";
    	this.MontrealServer_address = "http://localhost/FlightManagerWebService/services/"+this.MontrealServerWSDDServiceName;
    }

    public BaseServerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BaseServerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    

    public java.lang.String getMontrealServerAddress() {
        return MontrealServer_address;
    }

   
    

    public java.lang.String getMontrealServerWSDDServiceName() {
        return MontrealServerWSDDServiceName;
    }

    public void setMontrealServerWSDDServiceName(java.lang.String name) {
        MontrealServerWSDDServiceName = name;
    }

    public com.concordia.dist.asg1.Client.auto.BaseServer getMontrealServer() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MontrealServer_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMontrealServer(endpoint);
    }

    public com.concordia.dist.asg1.Client.auto.BaseServer getMontrealServer(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.concordia.dist.asg1.Client.auto.BaseServerSoapBindingStub _stub = new com.concordia.dist.asg1.Client.auto.BaseServerSoapBindingStub(portAddress, this);
            _stub.setPortName(getMontrealServerWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMontrealServerEndpointAddress(java.lang.String address) {
        MontrealServer_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.concordia.dist.asg1.Client.auto.BaseServer.class.isAssignableFrom(serviceEndpointInterface)) {
                com.concordia.dist.asg1.Client.auto.BaseServerSoapBindingStub _stub = new com.concordia.dist.asg1.Client.auto.BaseServerSoapBindingStub(new java.net.URL(MontrealServer_address), this);
                _stub.setPortName(getMontrealServerWSDDServiceName());
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
        if ("MontrealServer".equals(inputPortName)) {
            return getMontrealServer();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://Server.asg1.dist.concordia.com", this.MontrealServerWSDDServiceName+"Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://Server.asg1.dist.concordia.com", this.MontrealServerWSDDServiceName));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MontrealServer".equals(portName)) {
            setMontrealServerEndpointAddress(address);
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
