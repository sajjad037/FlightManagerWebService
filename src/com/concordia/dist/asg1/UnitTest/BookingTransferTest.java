package com.concordia.dist.asg1.UnitTest;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.rpc.ServiceException;

import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.concordia.dist.asg1.Client.auto.BaseServer;
import com.concordia.dist.asg1.Client.auto.BaseServerServiceLocator;
import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.ServerConfig;
import com.concordia.dist.asg1.Server.IFlightService;
import com.concordia.dist.asg1.StaticContent.StaticContent;

/**
 * 
 * @author SajjadAshrafCan
 *
 */
public class BookingTransferTest {
	//IFlightService flgOpImp;
	private BaseServer flgOpImp  =  null;
	//final String[] args = new String[] { "-ORBInitialPort", "1050", "-ORBInitialHost", "localhost" };
	@Test
	/**
	 * Sample Transfer Test.
	 */
	public void transferTest() throws ServiceException, RemoteException
	//public void transfer()
			 {
		String serverName = Enums.FlightCities.Montreal.toString();
		String managerID = "MTL1114";

		// Connect to Montreal Server
		flgOpImp = connectToServer(serverName);
		// Create Flight Destination Washington
		System.out.println("Server:" + serverName + ", Create Flight Destination Washington.");
		String newValues = "" + 10 + ":" + 15 + ":" + 20 + ":2016/12/06:13;12:"
				+ Enums.FlightCities.Washington.toString();
		String response = flgOpImp.editFlightRecord("-1:" + managerID, Enums.FlightFileds.createFlight.toString(),
				newValues);
		System.out.println(response);

		// Book a reservation for Montreal to Washington.
		System.out.println("Server:" + serverName + ", Book a reservation for Montreal to Washington.");
		response = flgOpImp.bookFlight("FTestCase", "LTestCase", "abc 123", "1234567890",
				Enums.FlightCities.Washington.toString(), "2016/12/06", Enums.Class.Economy.toString());
		System.out.println(response);
		int bookingID = getBookingID(response);

		// Connect to NewDehli
		serverName = Enums.FlightCities.NewDelhi.toString();
		flgOpImp = connectToServer(serverName);
		// Create Flight Destination Washington
		System.out.println("Server:" + serverName + ", Create Flight Destination Washington.");
		newValues = "" + 10 + ":" + 15 + ":" + 20 + ":2016/12/06:13;12:" + Enums.FlightCities.Washington.toString();
		response = flgOpImp.editFlightRecord("-1:" + managerID, Enums.FlightFileds.createFlight.toString(), newValues);
		System.out.println(response);

		// Connect to Montreal Server
		serverName = Enums.FlightCities.Montreal.toString();
		flgOpImp = connectToServer(serverName);
		// Transfer booking to NewDehli
		System.out.println("Server:" + serverName + ", Transfer booking to NewDehli.");
		response = flgOpImp.transferReservation("" + bookingID, Enums.FlightCities.Montreal.toString(),
				Enums.FlightCities.NewDelhi.toString());
		System.out.println(response);

		if (response != null && response.length() > 0 && !response.toString().contains("false")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}

	}
	
	
	@Test
	/**
	 * Sample Transfer Test.
	 */
	public void concurentTransferTest() throws InterruptedException, InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, ServiceException {
		
		int threadCount =3;
		String response ="";
		Thread[] threads = new Thread[threadCount];
		BookingTransferThread[] bookingTransferThread = new BookingTransferThread[threadCount];
		
		
		
		//Connect to Server
		String serverName = Enums.FlightCities.Montreal.toString();
		flgOpImp = connectToServer(serverName);
		saveDummyData(serverName);
		
		serverName = Enums.FlightCities.Washington.toString();
		flgOpImp = connectToServer(serverName);
		saveDummyData(serverName);
		
		serverName = Enums.FlightCities.NewDelhi.toString();
		flgOpImp = connectToServer(serverName);
		saveDummyData(serverName);
		
		
		// Connect to Montreal Server
		serverName = Enums.FlightCities.Montreal.toString();
		//Connect to Server
		flgOpImp = connectToServer(serverName);
		//saveDummyData(serverName);
		bookingTransferThread[0] = new BookingTransferThread(flgOpImp, "1", Enums.FlightCities.Washington.toString(), Enums.FlightCities.NewDelhi.toString());
		threads[0] = new Thread(bookingTransferThread[0]);
		threads[0].start();
		
		// Connect to Washington Server
		serverName = Enums.FlightCities.Washington.toString();
		//Connect to Server
		flgOpImp = connectToServer(serverName);
		//saveDummyData(serverName);
		bookingTransferThread[1] = new BookingTransferThread(flgOpImp, "2", Enums.FlightCities.NewDelhi.toString(), Enums.FlightCities.Montreal.toString());
		threads[1] = new Thread(bookingTransferThread[1]);
		threads[1].start();
		
		// Connect to NewDelhi Server
		serverName = Enums.FlightCities.NewDelhi.toString();
		//Connect to Server
		flgOpImp = connectToServer(serverName);
		//saveDummyData(serverName);
		bookingTransferThread[2] = new BookingTransferThread(flgOpImp, "1", Enums.FlightCities.Montreal.toString(), Enums.FlightCities.Washington.toString());
		threads[2] = new Thread(bookingTransferThread[2]);
		threads[2].start();
		
		
		// Start threads
		for (int i = 0; i < threadCount; i++) {
			threads[i].join();
			response = bookingTransferThread[i].getReply();
			System.out.println("Thread "+(i+1) +": "+response);
			
			if (response != null && response.length() > 0 && !response.toString().contains("false")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		}		

	}

	/**
	 * Create an instance of the server
	 * 
	 * @param serverName
	 * @return
	 * @throws InvalidName
	 * @throws org.omg.CosNaming.NamingContextPackage.InvalidName
	 * @throws CannotProceed
	 * @throws NotFound
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	private BaseServer connectToServer(String serverName) throws ServiceException {
		BaseServer _flgOpImp = null;
		BaseServerServiceLocator locator  = new BaseServerServiceLocator(serverName);
		_flgOpImp  =  locator.getMontrealServer();
		return _flgOpImp;
	}
	
	/**
	 * Extract or parse Booking Id from mesasge 
	 * @param msg
	 * @return
	 */
	private int getBookingID(String msg) {
		int bookingId = -1;
		String pattern = "(.*)(\\d+)(.*)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(msg);
		if (m.find()) {
			// System.out.println("Found value: " + m.group(0) );
			// System.out.println("Found value: " + m.group(1) );
			// System.out.println("Found value: " + m.group(2) );
			bookingId = Integer.parseInt(m.group(2).toString());
		} else {
			System.out.println("NO MATCH");
		}

		return bookingId;
	}
	
	/**
	 * 
	 * @author SajjadAshrafCan
	 *
	 */
	public class BookingTransferThread implements Runnable {

		StringBuilder _sb = new StringBuilder();
		BaseServer _flgOpImp;
		String passengerID , currentCity, otherCity;

		public BookingTransferThread(BaseServer _flgOpImp, String passengerID, String currentCity, String otherCity) {
			// store parameter for later user
			this._flgOpImp = _flgOpImp;
			this.passengerID = passengerID;
			this.currentCity = currentCity;
			this.otherCity = otherCity;
		}

		public void run() {				
			try {
				_sb.append(flgOpImp.transferReservation(passengerID, currentCity, otherCity));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		public String getReply() {
			return _sb.toString();
		}
	}
	
	private void saveDummyData(String ServerName) {	
		try {
			//clogger.log("saving some dummy data.");

			int count = StaticContent.getServersList().serverConfigList.size();
			for (int i = 0; i < count; i++) {
				ServerConfig serverConfig = StaticContent.getServersList().serverConfigList.get(i);
				if (!serverConfig.serverName.equals(ServerName)) 
				{

					// Save Flight
					flgOpImp.editFlightRecord("1:system", "createFlight", "10:10:10:2016/11/28:14;30:"+serverConfig.serverName);

					// Book Fight
					flgOpImp.bookFlight("FDummy", "LDummay", "Saint Marc", "1234567890", serverConfig.serverName, "2016/11/28",
							"Economy");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
