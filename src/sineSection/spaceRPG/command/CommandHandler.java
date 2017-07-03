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
		SpaceRPG master = SpaceRPG.getMaster();

		switch (c) {
		case UNKNOWN:
			SpaceRPG.getMaster().writeToGui(FAILURE_MESSAGE);
		case HELP:
			if(args.length == 0) {
				SpaceRPG.getMaster().writeToGui(CommandStrings.getCommands());
			} else {
				for (String helpText : args) {
					master.writeToGui(helpText + ": " + CommandParser.parseCommand(helpText).getCommand().getDescription());
				}
			}
			break;
		case USE:
		case TAKE:
		case SAVE:
		case QUIT:
		case LISTEN:
		case INSPECT:
		case INFO:
		case GO:
			master.writeToGui("Not Implemented");
			break;
		case DEBUG_DAMAGE:
			if(args.length > 0) {
				try {
					int dmg = Integer.parseInt(args[0]);
					if(dmg < 1) {
						SpaceRPG.getMaster().writeToGui("A speck of dust lands on you, dealing no damage.");
						break;
					}
					if(dmg >= SpaceRPG.getMaster().getPlayer().getHealth() && dmg >= SpaceRPG.getMaster().getPlayer().getMaxHealth() / 2) {
						SpaceRPG.getMaster().writeToGui("You decide you cannot take this cruel world anymore, and you then snap your neck.\n(999 Damage)");
						SpaceRPG.getMaster().getPlayer();
						SpaceRPG.getMaster().getPlayer().makeCharacterDie();
						break;
					}
					SpaceRPG.getMaster().getPlayer().damage(dmg);
					SpaceRPG.getMaster().writeToGui("You feel pain for no reason!\n(" + dmg + " Damage)");
				} catch (NumberFormatException e) {
					SpaceRPG.getMaster().writeToGui(ILLEGAL_ARGUMENT_MESSAGE);
				}
			} else {
				SpaceRPG.getMaster().writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		}
		SpaceRPG.getMaster().writeToGui("\n");
	}

}
