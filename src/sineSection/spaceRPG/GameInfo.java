package sineSection.spaceRPG;

import java.io.File;

public class GameInfo {

	public static final String TITLE = "Greenshift";
	public static final boolean IS_OSX = System.getProperty("os.name").toUpperCase().contains("MAC");
	public static final String APPDATA_FOLDER_NAME = "greenshift";
	
	public static String getAppdataFolderPath() {
		String OS = (System.getProperty("os.name")).toUpperCase();
		if (OS.contains("WIN")) {
			return System.getenv("AppData") + File.separator + APPDATA_FOLDER_NAME;
		} else {
			return System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + APPDATA_FOLDER_NAME;
		}
	}
	
	

}
