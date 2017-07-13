package sineSection.spaceRPG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class IntroReader {
	
	public static void read(String title) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(SpaceRPG.class.getResourceAsStream("/story/" + title)));
		String line;
		
		while ((line = in.readLine()) != null) {
			StringBuilder paragraph = new StringBuilder("");
			while(line != "") {
				paragraph.append(line + "\n");
			}
			SpaceRPG.getMaster().writeToGui(paragraph.toString());
		}
	}

}
