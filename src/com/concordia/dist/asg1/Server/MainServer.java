package com.concordia.dist.asg1.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.Models.ServerConfig;
import com.concordia.dist.asg1.StaticContent.StaticContent;
import com.concordia.dist.asg1.Utilities.CLogger;


/**
 * @author SajjadAshrafCan
 *
 */
public class MainServer {
	private final static Logger LOGGER = Logger.getLogger(MainServer.class.getName());
	private static CLogger clogger;
	static FlightImplementation[] flightImplementation;
	/**
	 * This Class Start all Servers.
	 */
	public MainServer() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// initialize logger
			clogger = new CLogger(LOGGER, "Server/server.log");
			//clogger = new CLogger(LOGGER, "C:/FlightManagerWebService/Logs/Server/server.log");

			clogger.log("Server strat Initiated.");

			// Read Configuration File
			clogger.log("Reading Server Configurations.");
			int size = StaticContent.getServersList().serverConfigList.size();
			
			flightImplementation = new FlightImplementation[size];
			
			// String Server through loops
			for (int i = 0; i < size; i++) {
				ServerConfig serverConfig = StaticContent.getServersList().serverConfigList.get(i);
				flightImplementation[i] =  new FlightImplementation(serverConfig.serverName, serverConfig.udpPort);
				flightImplementation[i].mainFunc();
			}

			clogger.log("All " + size + " Servers are started Successfully.");
			
			UDPServer();

		} catch (Exception ex) {
			clogger.logException("On Server Start.", ex);
			ex.printStackTrace();
		}
	}

	public static void UDPServer() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(Enums.UDPPort.Wrapper.getNumVal());
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			String msg = Enums.UDPPort.Wrapper.toString() + " UDP Server Is UP!";

			System.out.println(msg);
			clogger.log(msg);
			while (true) {
				// Read request
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String request = new String(receivePacket.getData());

				// Clear received buffer
				receiveData = new byte[1024];

				String[] requestArray = request.trim().split(StaticContent.VALUES_SEPARATOR_COMMA);
				String serverName, operation, inputs, replyBack = "";

				serverName = requestArray[0];
				operation = requestArray[1];				
				//inputs = requestArray[2];
				inputs= request.replace(serverName+StaticContent.VALUES_SEPARATOR_COMMA, "").replace(operation+StaticContent.VALUES_SEPARATOR_COMMA, "");
				
				
				msg = "Request RECEIVED: " + serverName + " is requesting for Operation " + operation
						+ ", with Parameters : " + inputs + ".";
				System.out.println(msg);
				clogger.log(msg);
				
				FlightImplementation server = getServer(serverName);
				
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();

				switch (operation) {
				case "bookFlight":
					replyBack = bookFlight(server, inputs);
					break;
				case "getBookedFlightCount":
					replyBack = getBookedFlightCount(server, inputs);
					break;
				case "editFlightRecord":
					replyBack = editFlightRecord(server, inputs);
					break;
				case "transferReservation":
					replyBack = transferReservation(server, inputs);
			        break;

				default:
					break;
				}

				sendData = replyBack.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);

				// Clear Send buffer
				sendData = new byte[1024];
			}
		} catch (Exception ex) {
			clogger.logException(Enums.UDPPort.Wrapper.toString() +" on starting UDP Server", ex);
			ex.printStackTrace();
		}
	}
	
	public static FlightImplementation getServer(String serverName){
		
		FlightImplementation server = null;	
		
		if(flightImplementation!=  null && flightImplementation.length > 0)
		{
			int size = flightImplementation.length;
			// String Server through loops
			for (int i = 0; i < size; i++) {
				if(flightImplementation[i].getServerName().equals(serverName))
				{
					server = flightImplementation[i];
					break;
				}
			}
		}		
		
		return server;
	}
	
	
	public static String bookFlight(FlightImplementation server, String inputs) {
		String firstName ="", lastName ="", address ="", phone ="", destination ="", date ="" , flightClass ="";
		
		String[] requestArray = inputs.trim().split(StaticContent.VALUES_SEPARATOR_INPUTS);
		
		firstName = requestArray[0];
		lastName = requestArray[1];
		address = requestArray[2];
		phone = requestArray[3];
		destination = requestArray[4];
		date = requestArray[5];
		flightClass = requestArray[6];		
		
		return server.bookFlight(firstName, lastName, address, phone, destination, date, flightClass);
	}

	
	public static String getBookedFlightCount(FlightImplementation server, String inputs) {
		return server.getBookedFlightCount(inputs);
	}

	
	public static String editFlightRecord(FlightImplementation server, String inputs) {
		String recordID= "", fieldName= "", newValue = "";
		
		String[] requestArray = inputs.trim().split(StaticContent.VALUES_SEPARATOR_INPUTS);
		
		recordID = requestArray[0];
		fieldName = requestArray[1];
		newValue = requestArray[2];
		
		return server.editFlightRecord(recordID, fieldName, newValue);
	}

	
	public static String transferReservation(FlightImplementation server, String inputs) {
		String passengerID= "", currentCity= "", otherCity= "";
		
		String[] requestArray = inputs.trim().split(StaticContent.VALUES_SEPARATOR_INPUTS);
		
		passengerID = requestArray[0];
		currentCity = requestArray[1];
		otherCity = requestArray[2];
		
		return server.transferReservation(passengerID, currentCity, otherCity);
	}
}
