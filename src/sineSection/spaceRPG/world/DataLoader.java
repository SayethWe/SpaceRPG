package sineSection.spaceRPG.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.util.Utils;

public class DataLoader {

	/**
	 * 
	 * @return <code>true</code> if this ran without err
	 */
	public static boolean loadAllFiles() {
		int errors = 0;
		File homeFolder = new File(SpaceRPG.resDirectory + "/data");
		String[] data = homeFolder.list();
		for(String datumString : data) {
			File datum = new File(datumString);
			if(datum.isDirectory()) {
				String[] files = datum.list();
				for (String file : files) {
					File loadFrom = new File(file);
					if(loadFrom.isFile()) {
						//run a loader
					} else {
						errors++;
					}
				}
			}
		}
		System.out.println(homeFolder);
		return errors == 0;
	}
	
	public static boolean loadAllFiles(int thisSupressesCompilerComplaints) {
		int errors = 0;
		final InputStream resources = SpaceRPG.class.getResourceAsStream("/data");
		InputStreamReader reader = new InputStreamReader(resources);
		BufferedReader bReader = new BufferedReader(reader);
		List<String> folders  = Utils.toList(bReader.lines());
		return errors == 0;
	}
	
	private static void loadFilesFromFolder(File folder) {}

}
