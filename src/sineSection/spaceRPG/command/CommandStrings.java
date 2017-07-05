package sineSection.spaceRPG.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sineSection.spaceRPG.SpaceRPG;

public class CommandStrings {
	public static final String DEBUG_COMMAND_PREFIX = "db_";

	private static Map<String, CommandString> validCommands = addCommands();

	private static Map<String, CommandString> addCommands() {
		Map<String, CommandString> result = new HashMap<>();
		for (CommandString command : CommandString.values()) {
			if (command != CommandString.UNKNOWN && !result.containsKey(command.getCall()) && (command.getCall().startsWith(DEBUG_COMMAND_PREFIX) ? SpaceRPG.DEBUG : true))
				result.put(command.getCall(), command);
		}
		return result;
	}

	public static CommandString getCommandString(String commandString) {
		CommandString res = CommandString.UNKNOWN;
		for (Entry<String, CommandString> e : validCommands.entrySet()) {
			if (e.getKey().equalsIgnoreCase(commandString)) {
				res = e.getValue();
				break;
			}
		}
		return res;
	}

	public static boolean isCommand(String commandString) {
		return validCommands.containsKey(commandString.toLowerCase());
	}

	public static String getCommands() {
		StringBuilder result = new StringBuilder("Commands: ");
		validCommands.keySet().forEach((command) -> result.append(command + "; "));
		return result.toString();
	}

}
