package sineSection.spaceRPG.command;

public enum CommandString {
	INSPECT("Look", ""),
	LISTEN("Listen", ""),
	USE("Use", "Use an item in your inventory.\n Args: The name of the item to use, and the name of the character to use it on"),
	TAKE("Take", ""),
	INFO("Info", "Refresh all information in the HUD"),
	HELP("Help", "See A list of Commands, or More Detail About a Command\nArgs: None, All, or Command(s) To Learn About"),
	GO("Move", "Move Between Rooms\n Args: Exit You Want To Go Through"),
	QUIT("Quit", "Leave the Game Without Saving\nNo Args"),
	SAVE("Save", "Save your progress so far\nNo Args"),
	CHAT("Talk", "Speak to the players around you\nArgs: Message"),
//	ALIAS("Alias","Rename something locally so you can refer to it easier"),
	INCREASE_FONT_SIZE("IncrFont", "Increases the size of the font.\nNo Args"),
	DECREASE_FONT_SIZE("DecrFont", "Decreases the size of the font.\nNo Args"),
	DEBUG_DAMAGE(CommandStrings.DEBUG_COMMAND_PREFIX + "dmg", "!DEBUG!\nDamage the player by <Number>\nArgs: Number of hit points"),
	DEBUG_HEAL(CommandStrings.DEBUG_COMMAND_PREFIX + "heal", "!DEBUG!\nHeal the player by <Number>\nArgs: Number of hit points"),
	UNKNOWN("", "Not a valid Command");

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
