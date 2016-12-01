/**
 * MontrealServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.concordia.dist.asg1.Client.auto;

public interface BaseServer extends java.rmi.Remote {
    public java.lang.String getBookedFlightCount(java.lang.String recordType) throws java.rmi.RemoteException;
    public java.lang.String transferReservation(java.lang.String passengerID, java.lang.String currentCity, java.lang.String otherCity) throws java.rmi.RemoteException;
    public java.lang.String bookFlight(java.lang.String firstName, java.lang.String lastName, java.lang.String address, java.lang.String phone, java.lang.String destination, java.lang.String date, java.lang.String flightClass) throws java.rmi.RemoteException;
    public java.lang.String editFlightRecord(java.lang.String recordID, java.lang.String fieldName, java.lang.String newValue) throws java.rmi.RemoteException;
}
