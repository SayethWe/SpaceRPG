package sineSection.spaceRPG.command;

import sineSection.spaceRPG.SpaceRPG;

public class CommandHandler {
	private static final String FAILURE_MESSAGE = "That's not a valid command";
	private static final String INSUFFICIENT_ARGUMENTS_MESSAGE = "You need to give me more arguments, bucko";
	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Thats... not the argument I needed.";

	public static void sendCommand(String command) {
		Command comm = CommandParser.parseCommand(command);
		CommandString c = comm.getCommand();
		String[] args = comm.getArgs();

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
			if(args.length == 0) {
				SpaceRPG.getMaster().writeToGui(CommandStrings.getCommands());
			} else {
				
			}
			break;
		case GO:
		case DEBUG_DAMAGE:
			if(args.length > 0) {
				try {
					SpaceRPG.getMaster().getPlayer().damage(Integer.parseInt(args[0]));
				} catch (NumberFormatException e) {
					SpaceRPG.getMaster().writeToGui(ILLEGAL_ARGUMENT_MESSAGE);
				}
			} else {
				SpaceRPG.getMaster().writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		}
	}

}
