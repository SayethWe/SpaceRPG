package sineSection.spaceRPG.command;

import java.util.Arrays;

public class CommandParser {
	
	private static final String SPLIT_ESCAPE = "\\";
	private static final String SPLIT_AT = " ";
	private static final String SPLIT_REPLACE = "."; //TODO: not use this character elsewhere in callables

	public static Command parseCommand(String command) {
		String parse = command.replaceAll(SPLIT_ESCAPE + SPLIT_AT, SPLIT_REPLACE);
		
		String[] split = parse.split(String.valueOf(SPLIT_AT));
		if(split.length < 1) return new Command(CommandString.UNKNOWN, null);
		
		for(String s : split) {
			s = s.replaceAll(SPLIT_REPLACE, SPLIT_AT);
		}
		
		String word1 = split[0];
		String[] args = Arrays.copyOfRange(split, 1, split.length);

		return new Command(CommandStrings.getCommandString(word1), args);
	}

	public static String getCommands() {
		return CommandStrings.getPrimaryCommands();
	}
}
