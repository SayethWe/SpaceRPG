package sineSection.spaceRPG.world;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import sineSection.spaceRPG.SpaceRPG;

public class DataLoader {

	/**
	 * 
	 * @return <code>true</code> if this ran without err
	 */
	public static boolean loadAllFiles() {
		int errors = 0;
		final URL resources = SpaceRPG.class.getResource("/data");
		File homeFolder;
		try {
			homeFolder = new File(resources.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
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

}
