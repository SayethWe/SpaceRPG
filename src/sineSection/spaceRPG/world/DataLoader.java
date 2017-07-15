package sineSection.spaceRPG.world;

import java.io.File;

import sineSection.spaceRPG.GameInfo;

public class DataLoader {

	/**
	 * 
	 * @return <code>true</code> if this ran without err
	 */
	public static boolean loadAllFiles() {
		int errors = 0;
		File homeFolder = new File(GameInfo.APPDATA_FOLDER_NAME + "/data");
		homeFolder.mkdirs();
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
	
//	public static boolean loadAllResourceFiles() {
//		int errors = 0;
//		InputStream resources = SpaceRPG.class.getResourceAsStream("/data");
//		BufferedReader reader = new BufferedReader(new InputStreamReader(resources));
//		List<String> folders  = Utils.toList(bReader.lines());
//		return errors == 0;
//	}
//	
//	private static void loadFilesFromFolder(File folder) {}
}
