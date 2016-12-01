package com.concordia.dist.asg1.UnitTest;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.concordia.dist.asg1.Client.auto.BaseServer;
import com.concordia.dist.asg1.Client.auto.BaseServerServiceLocator;

public class ServiceCall {
	public static void main(String[] args) throws ServiceException, RemoteException {
		
		
		System.out.println("----------------------------Montreal---------------------------------");
		BaseServerServiceLocator locator  = new BaseServerServiceLocator("Montreal");
		BaseServer service  =  locator.getMontrealServer();
		//
		// System.out.println("Creating Flight : ");
		// String reply= service.editFlightRecord("1:MTL1114", "createFlight",
		// "10:10:10:2016/11/25:14;30:Washington");
		// System.out.println("reply : \r\n "+reply);
		//
		// System.out.println("Booking Flight : ");
		// reply= service.bookFlight("abc", "asda", "asdm 2o asd", "1234567890",
		// "Washington", "2016/11/25", "Economy");
		// System.out.println("reply : \r\n "+reply);

		// MontrealService MontrealService = new MontrealService();
		// Montreal service = MontrealService.getMontreal();

		System.out.println("Creating Flight : ");
		String reply = service.editFlightRecord("1:MTL1114", "createFlight", "10:10:10:2016/11/25:14;30:Washington");
		System.out.println("reply : \r\n " + reply);

		System.out.println("Booking Flight : ");
		reply = service.bookFlight("abc", "asda", "asdm 2o asd", "1234567890", "Washington", "2016/11/25", "Economy");
		System.out.println("reply : \r\n " + reply);
		
		System.out.println("----------------------------Washington---------------------------------");

		locator = new BaseServerServiceLocator("Washington");
		service = locator.getMontrealServer();
		
		System.out.println("Creating Flight : ");		
		reply= service.editFlightRecord("1:MTL1114", "createFlight", "10:10:10:2016/11/25:14;30:Montreal");
		System.out.println("reply : \r\n "+reply);
		
		System.out.println("Booking Flight : ");
		reply= service.bookFlight("abc", "asda", "asdm 2o asd", "1234567890", "Montreal", "2016/11/25", "Economy");
		System.out.println("reply : \r\n "+reply);
		
		System.out.println("----------------------------NewDelhi---------------------------------");
		
		locator = new BaseServerServiceLocator("NewDelhi");
		service = locator.getMontrealServer();
		
		System.out.println("Creating Flight : ");		
		reply= service.editFlightRecord("1:MTL1114", "createFlight", "10:10:10:2016/11/25:14;30:Washington");
		System.out.println("reply : \r\n "+reply);
		
		System.out.println("Booking Flight : ");
		reply= service.bookFlight("abc", "asda", "asdm 2o asd", "1234567890", "Washington", "2016/11/25", "Economy");
		System.out.println("reply : \r\n "+reply);
		
		System.out.println("Get Flight Count : ");		
		reply= service.getBookedFlightCount("All:NDL1114");
		System.out.println("reply : \r\n "+reply);
	}
}
