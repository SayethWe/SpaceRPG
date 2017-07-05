package sineSection.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.logging.Level;
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

	private static Logger getLogger() {
		if (logger == null)
			createLogger(DEFAULT_TITLE);
		return logger;
	}

	private static PrintStream createLogFile() {
		Date today = new Date();
		String fileLocation = System.getProperty("user.dir") + File.separator + "logs" + File.separator + "Log " + today.toString() + ".sineLog";
		try {
			File file = new File(fileLocation);
			PrintStream out = null;
			if (file.canRead() && file.canWrite()) {
				file.mkdirs();
				if (file.createNewFile()) {
					System.out.println("Log file created: " + fileLocation);
				} else {
					System.out.println("Log file already exists, Or cannot be created.");
				}
				if(file.exists()) {
					out = new PrintStream(file);
				}
			} else {
				System.err.println("Can't read or write to: " + fileLocation);
			}
			return out;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error creating log at: " + fileLocation);
			e.printStackTrace();
			return null;
		}
	}
	
	public static void print(String msg) {
		LogWriter.getLogger().logp(Level.INFO, getCallerClassName(), getCallerMethodName(), msg);
	}

	public static void printErr(String error) {
		LogWriter.getLogger().logp(Level.WARNING, getCallerClassName(), getCallerMethodName(), error);
	}
	
	private static String getCallerClassName() { 
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(LogWriter.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return "";
     }
	
	private static String getCallerMethodName() { 
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(LogWriter.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getMethodName() + "()";
            }
        }
        return "";
     }
}
