package sineSection.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.character.Creature;

public class Utils {

	/**
	 * Splits a string at a certain position.
	 * 
	 * @param s
	 *            input string to split
	 * @param index
	 *            where to split the string (&lt; 0 and &gt;
	 *            <code>s.length()</code>)
	 * @return an array of length 2 containing the string section before and
	 *         after <code>index</code>.
	 * @author Richard Abbott
	 */
	public static String[] splitString(String s, int index) {
		if (index < 0)
			return new String[] { s };
		else if (index >= s.length())
			return new String[] { s };
		String[] ret = new String[2];
		ret[0] = s.substring(0, index);
		ret[1] = s.substring(index);
		return ret;
	}

	public static double log(double base, double num) {
		double result = Math.log(num);
		result /= Math.log(base);
		return result;
	}

	public static <T> List<T> toList(T[] set) {
		List<T> ret = new ArrayList<>();
		for(T element : set) {
			ret.add(element);
		}
		return ret;
	}

	public static List<String> removeStringFromListIgnoreCase(List<String> list, String s) {
		list.forEach((str) -> {
			if (str.equalsIgnoreCase(s))
				list.remove(s);
		});
		return list;
	}

	public static BufferedImage loadImageResource(String path) {
		try {
			return ImageIO.read(Utils.class.getResourceAsStream(path));
		} catch (Exception e) {
			LogWriter.printErr("Can't load image: " + path);
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getListOfNamesFromCreatures(List<Creature> creatures) {
		ArrayList<String> ret = new ArrayList<>();
		creatures.forEach((c) -> ret.add(c.getName()));
		return ret;
	}

	public static <T> List<T> toList(Stream<T> in) {
		List<T> ret = new ArrayList<>();
		in.forEach((element) -> ret.add(element));
		return ret;
	}
	
	public static void setMacIcon() {
		Image image = Utils.loadImageResource("/image/logo.png");
		try {
			String className = "com.apple.eawt.Application";
			Class<?> claas = Class.forName(className);
			Object application = claas.getMethod("getApplication").invoke(null);
			application.getClass().getMethod("setDockIconImage", java.awt.Image.class).invoke(application, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadFontFromFile(String fontName) {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			InputStream f = SpaceRPG.class.getResourceAsStream("/font/" + fontName + ".ttf");
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
		} catch (Exception e) {
			e.printStackTrace();
			LogWriter.print("Can't load font: " + fontName);
		}
	}

}
