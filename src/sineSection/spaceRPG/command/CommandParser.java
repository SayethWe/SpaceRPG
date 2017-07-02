package sineSection.spaceRPG.command;

import java.util.Arrays;

public class CommandParser {

	public static Command parseCommand(String parse) {
		String[] split = parse.split(" ");
		String word1 = split[0];
		String[] args = Arrays.copyOfRange(split, 1, split.length);

		return new Command(CommandStrings.getCommandString(word1), args);
	}

	public static String getCommands() {
		return CommandStrings.getCommands();
	}
}
