package com.concordia.dist.asg1.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

import com.concordia.dist.asg1.Models.Enums;
import com.concordia.dist.asg1.StaticContent.StaticContent;
import com.concordia.dist.asg1.Utilities.CLogger;

public class BaseFlightServer implements IFlightService {
	private String serverName;
	private String remoteServer = "MainServer"; 
	private CLogger clogger;
	private Logger LOGGER = Logger.getLogger(MainServer.class.getName());
	
	public BaseFlightServer(String serverName)
	{
		this.serverName = serverName;
		clogger = new CLogger(LOGGER, "WebService/" + this.serverName + ".log");
	}
	
	
	@Override
	public String bookFlight(String firstName, String lastName, String address, String phone, String destination,
			String date, String flightClass) {
		String inputs = String.format("%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"
				, firstName, lastName, address, phone, destination, date, flightClass);
		return UPDCall("localhost", Enums.UDPPort.Wrapper.getNumVal(), Enums.Operations.bookFlight.toString(), inputs);
	}

	@Override
	public String getBookedFlightCount(String recordType) {
		return UPDCall("localhost", Enums.UDPPort.Wrapper.getNumVal(), Enums.Operations.getBookedFlightCount.toString(), recordType);
	}

	@Override
	public String editFlightRecord(String recordID, String fieldName, String newValue) {
		String inputs = String.format("%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"
				, recordID, fieldName, newValue);
		return UPDCall("localhost", Enums.UDPPort.Wrapper.getNumVal(), Enums.Operations.editFlightRecord.toString(), inputs);
	}

	@Override
	public String transferReservation(String passengerID, String currentCity, String otherCity) {
		String inputs = String.format("%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"+StaticContent.VALUES_SEPARATOR_INPUTS+
				"%s"
				, passengerID, currentCity, otherCity);
		return UPDCall("localhost", Enums.UDPPort.Wrapper.getNumVal(), Enums.Operations.transferReservation.toString(), inputs);
	}
	
	private String UPDCall(String ip, int port, String operation, String augs) {
		String reply = "";
		String msg = "";
		msg = "Requesting to " + this.remoteServer + ", Server for operation " +operation+ " for augs " + augs + ".";
		System.out.println(msg);
		clogger.log(msg);
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(ip);
			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			String request = this.serverName + "," + operation + "," + augs;
			sendData = request.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String response = new String(receivePacket.getData());
			clientSocket.close();
			msg = "Reply back FROM " + this.remoteServer + " SERVER:" + response.trim();
			System.out.println(msg);
			clogger.log(msg);
			reply = response.trim();
		} catch (Exception ex) {
			reply = "Error: encouter on " + this.serverName + ", Message: " + ex.getMessage();
			clogger.logException("on starting UDP Server", ex);
			ex.printStackTrace();
		}

		return reply;
	}

}
