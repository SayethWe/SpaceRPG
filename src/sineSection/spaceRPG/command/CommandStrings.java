package sineSection.spaceRPG.command;

import java.util.HashMap;
import java.util.Map;

public class CommandStrings {

	private Map<String, CommandString> validCommands;
	
	public CommandStrings() {
		validCommands = new HashMap<>();
		addCommands();
	}
	
	private void addCommands() {
		for(CommandString command : CommandString.values()) {
			validCommands.put(command.getCall(), command);
		}
	}
	
	public CommandString getCommandString(String commandString) {
		return validCommands.getOrDefault(commandString, CommandString.UNKNOWN);
	}
	
	public boolean isCommand(String commandString) {
		return validCommands.containsKey(commandString);
	}
	
	public String getCommands() {
		StringBuilder result = new StringBuilder("Commands");
		validCommands.keySet().forEach((command) -> result.append(" " + command));
		return result.toString();
	}

}
