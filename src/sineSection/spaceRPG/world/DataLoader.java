package sineSection.spaceRPG.world;

import java.net.URL;

import sineSection.spaceRPG.SpaceRPG;

public class DataLoader {

	public static boolean loadAllFiles() {
		int errors = 0;
		final URL homeFolder = SpaceRPG.class.getResource("/data");
		System.out.println(homeFolder);
		return errors == 0;
	}

}
