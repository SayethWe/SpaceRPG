package sineSection.spaceRPG.command;

public class Command {
	private String[] args;
	private CommandString command;

	public Command(CommandString activator, String[] args) {
		command = activator;
		this.args = args;
	}

	public CommandString getCommand() {
		return command;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public boolean isValid() {
		return command == CommandString.UNKNOWN;
	}
	
	public boolean hasArgs() {
		return args.length > 0;
	}
	
	public int argsNum() {
		return args.length;
	}
}
