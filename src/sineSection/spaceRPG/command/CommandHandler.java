package sineSection.spaceRPG.command;

import sineSection.spaceRPG.SpaceRPG;

public class CommandHandler {
	private static final String FAILURE_MESSAGE = "That's not a valid command";
	private static final String NYI_MESSAGE = "Not yet implemented!";
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
			break;
		case HELP:
			if(args.length == 0) {
				SpaceRPG.getMaster().writeToGui(CommandStrings.getCommands());
			} else {
				for (String helpText : args) {
					master.writeToGui(helpText + ": " + CommandParser.parseCommand(helpText).getCommand().getDescription());
				}
			}
			break;
		case GO:
			if(args.length > 0) {
				//move by arg direction, then do room.onRoomEnter();
			} else {
				master.writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		case USE:
		case TAKE:
		case SAVE:
		case QUIT:
		case LISTEN:
		case INSPECT:
		case INFO:
			SpaceRPG.getMaster().writeToGui(NYI_MESSAGE);
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
						SpaceRPG.getMaster().getPlayer().damage(dmg);
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
		case DEBUG_HEAL:
			if(args.length > 0) {
				try {
					int heal = Integer.parseInt(args[0]);
					if(heal < 1) {
						SpaceRPG.getMaster().writeToGui("You feel normal.");
						break;
					}
					if(heal >= SpaceRPG.getMaster().getPlayer().getHealth() && heal >= SpaceRPG.getMaster().getPlayer().getMaxHealth() / 2) {
						SpaceRPG.getMaster().writeToGui("You feel the best!\n(999 Healed)");
						SpaceRPG.getMaster().getPlayer().heal(heal);
						break;
					}
					SpaceRPG.getMaster().getPlayer().heal(heal);
					SpaceRPG.getMaster().writeToGui("You feel better for no reason!\n(" + heal + " Healed)");
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
