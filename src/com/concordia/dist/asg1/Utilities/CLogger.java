package com.concordia.dist.asg1.Utilities;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.concordia.dist.asg1.StaticContent.StaticContent;

/**
 * Logger
 * 
 * @author SajjadAshrafCan
 *
 */
public class CLogger {
	private Logger LOGGER = null;
	String logFilePath = "";
	static private FileHandler fileHandler;
	static private LogFormatter simpleFormatter;

	public CLogger(Logger LOGGER, String logFilePath) {
		try {
			this.LOGGER = LOGGER;
			this.logFilePath = StaticContent.BASE_PATH+ "Logs/" + logFilePath;
			//this.logFilePath =  logFilePath;
			//this.logFilePath =new File( "Logs/" + logFilePath).getAbsolutePath();
			

			LOGGER.setLevel(Level.INFO);
			LOGGER.setUseParentHandlers(false);
			fileHandler = new FileHandler(this.logFilePath, true);

			// simpleFormatter = new SimpleFormatter();
			simpleFormatter = new LogFormatter();
			fileHandler.setFormatter(simpleFormatter);
			LOGGER.addHandler(fileHandler);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Log Normal msg
	 * 
	 * @param msg
	 */
	public void log(String msg) {
		LOGGER.info(msg);
	}

	/**
	 * Log Exception
	 * 
	 * @param msg
	 * @param ex
	 */
	public void logException(String msg, Exception ex) {
		LOGGER.log(Level.SEVERE, msg.equals("") ? "an exception was thrown" : msg, ex);
	}
}
