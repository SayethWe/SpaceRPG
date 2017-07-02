package sineSection.util;

import java.io.File;
import java.io.IOException;
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
		PrintStream log = createLogFile();
		if (log != null)
			logger.addHandler(new StreamHandler(log, new SimpleFormatter()));
	}

	public static Logger getLogger() {
		if (logger == null)
			createLogger(DEFAULT_TITLE);
		return logger;
	}

	// TODO Fix-a this-a thing-a
	private static PrintStream createLogFile() {
		try {
			String sep = File.separator;
			Date today = new Date();
			String fileLocation = System.getProperty("user.dir") + sep + "logs" + sep + "Log " + today.toString() + ".sineLog";
			System.out.println("Creating log file at: " + fileLocation);
			File log = new File(fileLocation);
			if (!log.exists()) {
				log.createNewFile();
			}
			PrintStream out = System.out;
			out = new PrintStream(log);
			return out;
		} catch (IOException e) {
			System.err.println("Error creating log!");
			e.printStackTrace();
			return null;
		}
	}

}
