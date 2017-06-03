package sineSection.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class LogWriter {
	private final static String DEFAULT_TITLE = "Default Logger";
	private static Logger logger;
	
	public static void createLogger(String title) {
			logger = Logger.getLogger(title);
			logger.addHandler(new StreamHandler(createLogFile(), new SimpleFormatter()));
	}
	
	public static Logger getLogger() {
		if (logger == null) createLogger(DEFAULT_TITLE);
		return logger;
	}
	
	
	private static PrintStream createLogFile() {
		String sep = File.separator;
		Date today = new Date();
		String fileLocation = System.getProperty("user.dir") + sep + "logs" + sep + "Log " + today.toString() + ".sineLog";
		File log = new File(fileLocation);
		PrintStream out = System.out;
		try {
			out = new PrintStream(log);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return out;
	}

}
