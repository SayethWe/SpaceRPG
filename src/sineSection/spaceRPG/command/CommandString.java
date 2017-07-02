package sineSection.spaceRPG.command;

public enum CommandString {
	INSPECT("Look", ""), LISTEN("Listen", ""), USE("Use", ""), TAKE("Take", ""), INFO("Info", "Refresh all information in the HUD"), HELP("Help", "See A list of Commands, or More Detail About a Command \n Args: None, or Command To Learn About"), GO("move", "Move Between Rooms \n Args: Exit You Want To Go Through"), QUIT("Quit", "Leave the Game Without Saving \n No Args"), SAVE("Save", "Save your progress so far \n No Args"), DEBUG_DAMAGE("debug_dmg", "!DEBUG!\nDamage the player by <Number>\n Args: Number of hit points"), UNKNOWN("?", "Not A Valid Command");

	private final String call;
	private final String description;

	CommandString(String call, String description) {
		this.call = call;
		this.description = description;
	}

	public String getCall() {
		return call;
	}

	public String getDescription() {
		return description;
	}
}
