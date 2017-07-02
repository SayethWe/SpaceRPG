package sineSection.spaceRPG.command;

import sineSection.spaceRPG.SpaceRPG;

public class CommandHandler {
	private static String FAILURE_MESSAGE = "That's not a valid command";

	public static void sendCommand(String command) {
		CommandString c = CommandParser.parseCommand(command).getCommand();

		switch (c) {
		case UNKNOWN:
			SpaceRPG.getMaster().writeToGui(FAILURE_MESSAGE);
			break;
		case USE:
		case TAKE:
		case SAVE:
		case QUIT:
		case LISTEN:
		case INSPECT:
		case INFO:
		case HELP:
		case GO:
			break;
		}
	}

}
