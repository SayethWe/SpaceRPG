package sineSection.spaceRPG.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import sineSection.spaceRPG.SpaceRPG;

public class CommandStrings {

	private static final Map<String, CommandString> validCommands = addCommands();
	private static final Set<String> primaryCalls = getPrimaryCalls();

	private static Map<String, CommandString> addCommands() {
		Map<String, CommandString> result = new HashMap<>();
		for (CommandString command : CommandString.values()) {
			String[] calls = command.getCall().split(";");
			for (int i = 0; i < calls.length; i++) {
				if (command != CommandString.UNKNOWN && !result.containsKey(calls[i]) && (command.isDebugCommand() ? SpaceRPG.getMaster().isDebugMode() : true))
					result.put(calls[i], command);
			}

		}
		return result;
	}
	
	private static Set<String> getPrimaryCalls() {
		Set<String> result = new HashSet<>();
		for (CommandString cmd : CommandString.values()) {
			result.add(cmd.getCall().split(";")[0]);
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

//	public static boolean isCommand(String commandString) {
//		return getCommandString(commandString) != CommandString.UNKNOWN;
//	}
	
	public static String getCommands() {
		StringBuilder result = new StringBuilder("Commands: ");
		validCommands.keySet().forEach((command) -> result.append(command + "; "));
		return result.toString();
	}

	public static String getPrimaryCommands() {
		StringBuilder result = new StringBuilder("Commands: ");
		primaryCalls.forEach((command) -> result.append(command + "; "));
		return result.toString();
	}

	public static List<String> getCommandNames() {
		return new LinkedList<>(primaryCalls);
	}
	
	public static List<String> getCommandCalls() {
		return new LinkedList<>(validCommands.keySet());
	}

}
