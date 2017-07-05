package sineSection.util;

public class Utils {
	
	/**
	 * Splits a string at a certain position.
	 * @param s input string to split
	 * @param index where to split the string (&lt; 0 and &gt; <code>s.length()</code>)
	 * @return an array of length 2 containing the string section before and after <code>index</code>.
	 * @author Richard Abbott
	 */
	public static String[] splitString(String s, int index) {
		if(index < 0) return null;
		else if(index >= s.length()) return null;
		String[] ret = new String[2];
		ret[0] = s.substring(0, index);
		ret[1] = s.substring(index);
		return ret;
	}

}