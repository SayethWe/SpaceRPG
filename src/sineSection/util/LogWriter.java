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
			File file = new File(fileLocation);
			if (file.createNewFile()) {
				System.out.println("Log file created: " + fileLocation);
			} else {
				System.out.println("Log file already exists.");
			}
			PrintStream out = new PrintStream(file);
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error creating log!");
			e.printStackTrace();
			return null;
		}
	}
}
