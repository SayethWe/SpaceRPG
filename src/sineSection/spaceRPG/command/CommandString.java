package sineSection.spaceRPG.command;

public enum CommandString {
	INSPECT("Look;!", "Inspect this room"),
	LISTEN("Listen", "",true),
	CLEAR_SCREEN("Clear;cls;clr", "Clear the screen."
			+ "\nNo Args"),
	USE("Use", "Use an item in your inventory."
			+ "\nArgs: The name of the item to use, and the name of the character to use it on"),
	TAKE("Take;Grab", ""),
	INFO("Info;Refresh;","Refresh the HUD"), //Make this draw a map of the room
	HELP("Help;?", "See A list of Commands, or More Detail About a Command, Including Args and Aliases"
			+ "\nArgs: None, 'All', or Command(s) To Learn About"),
	GO("Move;Go", "Move Between Rooms"
			+ "\nArgs: Exit You Want To Go Through"),
	QUIT("Quit;Exit", "Leave the Game Without Saving."),
	SAVE("Save", "Save your progress so far."),
	CHAT("Talk;Say", "Speak to the players around you\nArgs: Message"),
	TOGGLESCROLL("Scroll;WriteMode","Change whether the display is scrolling or not"),
	// ALIAS("Alias","Rename something locally so you can refer to it easier"),
	INCREASE_FONT_SIZE("IncrFont;ZoomIn;>", "Increases the size of the font."
			+ "\nNo Args"),
	DECREASE_FONT_SIZE("DecrFont;ZoomOut;<", "Decreases the size of the font."
			+ "\nNo Args"),
	RESET_FONT_SIZE("ResetFont;ResetZoom;^", "Resets the size of the font."
			+ "\nNo Args"),
	DEBUG_DAMAGE("dmg", "!DEBUG!\nDamage the player by <Number>"
			+ "\nArgs: Number of hit points", true),
	DEBUG_HEAL("heal", "!DEBUG!\nHeal the player by <Number>"
			+ "\nArgs: Number of hit points", true),
	GET_POS("Pos", "get yur pos", true),
	UNKNOWN("", "Not a valid Command");

	private final String call;
	private final String description;
	private final boolean debugCommand;

	CommandString(String call, String description) {
		this(call, description, false);
	}

	CommandString(String call, String description, boolean debugCommand) {
		this.call = call;
		this.description = description;
		this.debugCommand = debugCommand;
	}

	public String getCall() {
		return call;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDebugCommand() {
		return debugCommand;
	}

	public String getHelpString() {
		StringBuilder s = new StringBuilder();
		String[] calls = getCall().split(";");
		for (int i = 0; i < calls.length; i++) {
			if (i == calls.length - 1) {
				s.append(calls[i]);
			} else if (i == 0 && calls.length > 1) {
				s.append(calls[i] + ". Alias");
				if(calls.length > 2) {
					s.append("es");
				}
				s.append(": ");
			} else {
				s.append(calls[i] + ", ");
			}
		}
		s.append("\nPurpose:\n");
		s.append(getDescription());
		s.append("\n");
		return s.toString();
	}
}
