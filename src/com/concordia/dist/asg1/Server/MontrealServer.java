package com.concordia.dist.asg1.Server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.concordia.dist.asg1.Models.Enums;

@WebService(endpointInterface = "com.concordia.dist.asg1.Server.IFlightService", serviceName = "MontrealService", portName = "MontrealPort", targetNamespace = "http://com.concordia")
@SOAPBinding(style = Style.RPC)
public class MontrealServer implements IFlightService {
	//FlightImplementation montrealService;
	BaseFlightServer montrealService;

	public MontrealServer() {
//		montrealService = new FlightImplementation(Enums.FlightCities.Montreal.toString(),
//				Enums.UDPPort.Montreal.getNumVal());
		montrealService = new BaseFlightServer(Enums.FlightCities.Montreal.toString());
	}

	@Override
	public String bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String flightClass) {
		return montrealService.bookFlight(firstName, lastName, address, phone, destination, date, flightClass);
	}

	@Override
	public String getBookedFlightCount(String recordType) {
		return montrealService.getBookedFlightCount(recordType);
	}

	@Override
	public String editFlightRecord(String recordID, String fieldName, String newValue) {
		return montrealService.editFlightRecord(recordID, fieldName, newValue);
	}

	@Override
	public String transferReservation(String passengerID, String currentCity, String otherCity) {
		return montrealService.transferReservation(passengerID, currentCity, otherCity);
	}

}
