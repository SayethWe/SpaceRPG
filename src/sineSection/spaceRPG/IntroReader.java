package sineSection.spaceRPG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class IntroReader {
	
	public static void read(String title) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(SpaceRPG.class.getResourceAsStream("/" + title)));
		String line;
		StringBuilder fileText = new StringBuilder("");
		
		while ((line = in.readLine()) != null) {
			fileText.append(line + "\n");
		}
		
		SpaceRPG.getMaster().writeToGui(fileText.toString());
	}

}
