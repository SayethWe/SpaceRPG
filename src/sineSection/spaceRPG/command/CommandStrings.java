package sineSection.spaceRPG.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.util.Utils;

public class CommandStrings {

	private static Map<String, CommandString> validCommands = addCommands();

	private static Map<String, CommandString> addCommands() {
		Map<String, CommandString> result = new HashMap<>();
		for (CommandString command : CommandString.values()) {
			String[] calls = command.getCall().split(";");
			for (int i = 0; i < calls.length; i++) {
				if (command != CommandString.UNKNOWN && !result.containsKey(calls[i]) && (command.isDebugCommand() ? SpaceRPG.DEBUG : true))
					result.put(calls[i], command);
			}

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
		return getCommandString(commandString) != CommandString.UNKNOWN;
	}

	public static String getCommands() {
		StringBuilder result = new StringBuilder("Commands: ");
		validCommands.keySet().forEach((command) -> result.append(command + "; "));
		return result.toString();
	}

	public static List<String> getCommandCalls() {
		return Utils.toList(validCommands.keySet());
	}

}
