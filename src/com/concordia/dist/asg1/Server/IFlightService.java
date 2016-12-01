package com.concordia.dist.asg1.Server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * Interface definition: FlightOperations.
 * 
 * @author OpenORB Compiler
 */
@WebService
@SOAPBinding(style=Style.RPC)
public interface IFlightService
{
    /**
     * Operation bookFlight
     */
	@WebMethod
    public String bookFlight(String firstName, String lastName, String address, String phone, String destination, String date, String flightClass);

    /**
     * Operation getBookedFlightCount
     */
	@WebMethod
    public String getBookedFlightCount(String recordType);

    /**
     * Operation editFlightRecord
     */
	@WebMethod
    public String editFlightRecord(String recordID, String fieldName, String newValue);

    /**
     * Operation transferReservation
     */
	@WebMethod
    public String transferReservation(String passengerID, String currentCity, String otherCity);

}
