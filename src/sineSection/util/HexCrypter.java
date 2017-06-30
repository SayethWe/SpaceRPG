package sineSection.util;

public class HexCrypter {
	private static final char DELIMITER = ' ';
	private static final char PADDING = '=';
	private static final int SPLIT_LENGTH = 3;
	
	public static String encrypt(String readable) {
		StringBuilder result = new StringBuilder("");
		readable = padLength(readable, PADDING, nextMultipleFrom(readable.length(), SPLIT_LENGTH));
		for(String split : splitByLength(readable, SPLIT_LENGTH)){
			StringBuilder hex = new StringBuilder("");
			for(char ch : split.toCharArray()) {
				hex.append(toHexValue(ch));
			}
			result.append(Integer.parseInt(hex.toString(), 16)).append(DELIMITER);
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		String[] testStrings = {"One Two Three", "SpaceRPG Central"};
		for (String test : testStrings) {
			System.out.println(test);
			String i;
			System.out.println(i = HexCrypter.encrypt(test));
			System.out.println(HexCrypter.decrypt(i));
		}
	}
	
	public static String decrypt(String unreadable) {
		StringBuilder result = new StringBuilder("");
		for(String split : unreadable.split(String.valueOf(DELIMITER))) {
			split = decToHex(split);
			if(split.length() % 2 != 0) split = "0" + split;
			for (String charrable : splitByLength(split, 2)) {
				result.append(toAsciiChar(charrable));
			}
		}
		return result.toString().replaceAll(String.valueOf(PADDING), "");
	}
	
	private static String toHexValue(char text) {
		return Integer.toHexString((int) text);
	}
	
	private static String padLength(String in, char pad, int desLen) {
		StringBuilder result = new StringBuilder(in);
		for(int i = 0; i < desLen - in.length(); i++) {
			result.append(pad);
		}
		return result.toString();
	}
	
	private static String[] splitByLength(String in, int splitLength) {
		int resSize = (in.length()/splitLength);
		String[] result = new String[resSize];
		for (int i = 0; i < resSize; i++) {
			result[i] = in.substring(i*splitLength,(i+1)*splitLength);
		}
		return result;
	}
	
	private static int nextMultipleFrom(int start, int mod) {
		while (start % mod != 0) {
			start++;
		}
		return start;
	}
	
	private static char toAsciiChar(String hex) {
		return (char) Integer.parseInt(hex, 16);
	}
	
	private static String decToHex(String dec) {
		return Integer.toHexString(Integer.parseInt(dec));
	}

}
