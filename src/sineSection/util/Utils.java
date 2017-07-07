package sineSection.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
			return new String[] {s};
		else if (index >= s.length())
			return  new String[] {s};
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

	public static <T> List<T> toList(Set<T> set) {
		List<T> ret = new ArrayList<>();
		set.forEach((item) -> ret.add(item));
		return ret;
	}

	public static List<String> removeStringFromListIgnoreCase(List<String> list, String s) {
		for (String str : list) {
			if (str.equalsIgnoreCase(s))
				list.remove(str);
		}
		return list;
	}

}
