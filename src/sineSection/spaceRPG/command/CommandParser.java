package sineSection.spaceRPG.command;

import java.util.Arrays;

public class CommandParser {

	private CommandStrings commands;
	
	public CommandParser() {
		commands = new CommandStrings();
	}
	
	public Command parseCommand(String parse) {
		String[] split = parse.split(" ");
		String word1 = split[0];
		String[] args = Arrays.copyOfRange(split, 1, split.length);


		return new Command(commands.getCommandString(word1), args);
	}
	
	public String getCommands() {
		return commands.getCommands();
	}
}
