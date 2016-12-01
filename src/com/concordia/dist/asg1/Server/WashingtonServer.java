package com.concordia.dist.asg1.Server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.concordia.dist.asg1.Models.Enums;

@WebService(endpointInterface = "com.concordia.dist.asg1.Server.IFlightService")
@SOAPBinding(style=Style.RPC)
public class WashingtonServer  implements IFlightService{
	//FlightImplementation washingtonServer;
	BaseFlightServer washingtonServer;
	
	public WashingtonServer(){
//		washingtonServer =  new  FlightImplementation(Enums.FlightCities.Washington.toString(), 
//				Enums.UDPPort.Washington.getNumVal());
		
		washingtonServer = new BaseFlightServer(Enums.FlightCities.Washington.toString());
	}
	
	@Override
	public String bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String flightClass) {
		return washingtonServer.bookFlight(firstName, lastName, address, phone, destination, date, flightClass);
	}

	@Override
	public String getBookedFlightCount(String recordType) {
		return washingtonServer.getBookedFlightCount(recordType);
	}

	@Override
	public String editFlightRecord(String recordID, String fieldName, String newValue) {
		return washingtonServer.editFlightRecord(recordID, fieldName, newValue);
	}

	@Override
	public String transferReservation(String passengerID, String currentCity, String otherCity) {
		return washingtonServer.transferReservation(passengerID, currentCity, otherCity);
	}

}
