package sineSection.spaceRPG.command;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.Ship;
import sineSection.spaceRPG.world.map.WorldPos;
import sineSection.spaceRPG.world.map.room.Room;

public class CommandHandler {
	private static final String FAILURE_MESSAGE = "That's not a valid command";
	private static final String NYI_MESSAGE = "Not yet implemented!";
	private static final String INSUFFICIENT_ARGUMENTS_MESSAGE = "You need to give me more arguments, bucko";
	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Thats... not the argument I needed.";
	
	private static SpaceRPG doctor = SpaceRPG.getMaster(); // Everyone knows the Doctor IS the master.

	public static void sendCommand(String command) {
		Command comm = CommandParser.parseCommand(command);
		CommandString c = comm.getCommand();
		String[] args = comm.getArgs();
		Ship sulaco = doctor.getWorld();
		WorldPos tenForward = doctor.getPlayer().getPos();
		Room halCortex = sulaco.getNode(tenForward.getNode()).getRoom(tenForward.getRoom());

		switch (c) {
		case UNKNOWN:
			doctor.writeToGui(FAILURE_MESSAGE);
			break;
		case HELP:
			if (args.length == 0) {
				doctor.writeToGui(CommandStrings.getCommands());
			} else {
				if (args[0].equalsIgnoreCase("all")) {
					for (CommandString helpCommand : CommandString.values()) {
						doctor.writeToGui(helpCommand.getHelpString());
					}
				} else {
					for (String helpText : args) {
						CommandString cmd = CommandParser.parseCommand(helpText).getCommand();
						doctor.writeToGui(cmd.getHelpString());
					}
				}
			}
			break;
		case GO:
			if (args.length > 0) {
				Direction dir = Ship.getDir(args[0]);
				if (dir != null || !halCortex.getExits().contains(dir)) {
					doctor.getPlayer().move(dir);
				} else {
					doctor.writeToGui(ILLEGAL_ARGUMENT_MESSAGE);
				}
				// move by arg direction, then do room.onRoomEnter();
			} else {
				doctor.writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		// case ALIAS:
		case USE:
			if (args.length >= 2) {
				Creature target = doctor.getCharacter(args[1]);
				if (target != null) {
					doctor.getPlayer().useItem(args[0], target);
				} else {
					doctor.writeToGui(ILLEGAL_ARGUMENT_MESSAGE + " You had an invalid target name.");
				}
			} else {
				doctor.writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		case DECREASE_FONT_SIZE:
			doctor.getGui().decreaseFontSize();
			break;
		case INCREASE_FONT_SIZE:
			doctor.getGui().increaseFontSize();
			break;
		case RESET_FONT_SIZE:
			doctor.getGui().setFontSize(GameUI.DEFAULT_FONT_SIZE);;
			break;
		case CLEAR_SCREEN:
			doctor.getGui().clearScreen();
			break;
		case TAKE:
		case SAVE:
		case QUIT:
		case LISTEN:
		case INSPECT:
			doctor.writeToGui(NYI_MESSAGE);
			break;
		case INFO:
			doctor.writeToGui(halCortex.getExitString());
		case CHAT:
			StringBuilder send = new StringBuilder(doctor.getPlayer().getName() + " says: ");
			if (args.length > 0) {
				for (String word : args) {
					send.append(word).append(" ");
				}
				doctor.sendChat(send.toString());
			} else {
				doctor.writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		case DEBUG_DAMAGE:
			if (args.length > 0) {
				try {
					int dmg = Integer.parseInt(args[0]);
					if (dmg < 1) {
						doctor.writeToGui("A speck of dust lands on you, dealing no damage.");
						break;
					}
					if (dmg >= doctor.getPlayer().getHealth() && dmg >= doctor.getPlayer().getMaxHealth() / 2) {
						doctor.writeToGui("You decide you cannot take this cruel world anymore, and you then snap your neck.\n(999 Damage)");
						doctor.getPlayer().damage(dmg);
						doctor.getPlayer().makeCharacterDie();
						break;
					}
					doctor.getPlayer().damage(dmg);
					doctor.writeToGui("You feel pain for no reason!\n(" + dmg + " Damage)");
				} catch (NumberFormatException e) {
					doctor.writeToGui(ILLEGAL_ARGUMENT_MESSAGE);
				}
			} else {
				doctor.writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;
		case DEBUG_HEAL:
			if (args.length > 0) {
				try {
					int heal = Integer.parseInt(args[0]);
					if (heal < 1) {
						doctor.writeToGui("You feel normal.");
						break;
					}
					if (heal >= doctor.getPlayer().getHealth() && heal >= doctor.getPlayer().getMaxHealth() / 2) {
						doctor.writeToGui("You feel the best!\n(999 Healed)");
						doctor.getPlayer().heal(heal);
						break;
					}
					doctor.getPlayer().heal(heal);
					doctor.writeToGui("You feel better for no reason!\n(" + heal + " Healed)");
				} catch (NumberFormatException e) {
					doctor.writeToGui(ILLEGAL_ARGUMENT_MESSAGE);
				}
			} else {
				doctor.writeToGui(INSUFFICIENT_ARGUMENTS_MESSAGE);
			}
			break;

		}
		doctor.writeToGui("");
	}

}
