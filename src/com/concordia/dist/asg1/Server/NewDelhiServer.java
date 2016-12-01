package com.concordia.dist.asg1.Server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.concordia.dist.asg1.Models.Enums;

@WebService(endpointInterface = "com.concordia.dist.asg1.Server.IFlightService")
@SOAPBinding(style=Style.RPC)
public class NewDelhiServer  implements IFlightService{
	//FlightImplementation newDelhiServer;
	BaseFlightServer newDelhiServer;
	
	public NewDelhiServer(){
//		newDelhiServer =  new  FlightImplementation(Enums.FlightCities.NewDelhi.toString(), 
//				Enums.UDPPort.NewDelhi.getNumVal());		
		newDelhiServer = new BaseFlightServer(Enums.FlightCities.NewDelhi.toString());
	}
	
	@Override
	public String bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String flightClass) {
		return newDelhiServer.bookFlight(firstName, lastName, address, phone, destination, date, flightClass);
	}

	@Override
	public String getBookedFlightCount(String recordType) {
		return newDelhiServer.getBookedFlightCount(recordType);
	}

	@Override
	public String editFlightRecord(String recordID, String fieldName, String newValue) {
		return newDelhiServer.editFlightRecord(recordID, fieldName, newValue);
	}

	@Override
	public String transferReservation(String passengerID, String currentCity, String otherCity) {
		return newDelhiServer.transferReservation(passengerID, currentCity, otherCity);
	}

}
