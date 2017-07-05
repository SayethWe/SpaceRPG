package sineSection.spaceRPG;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Random;

import sineSection.SineSection;
import sineSection.networking.client.Client;
import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.save.SaveReader;
import sineSection.spaceRPG.save.SaveState;
import sineSection.spaceRPG.world.ItemGenerator;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.ItemReference;
import sineSection.spaceRPG.world.item.loader.ItemLoader;
import sineSection.util.LogWriter;

public class SpaceRPG {
	public static final String TITLE = "SpaceRPG";

	private static GameUI gui;
	private static ItemGenerator itemGenerator;
	private static Random seedGenerator;
	private static SpaceRPG master; // The SPACERPG object to call for all your
									// needs.

	private static Player testPlayer;
	private Map<String, Creature> creatures; //characters the player can target with items
	private Client gameClient; //the client object this uses to talk to the server

	public static void main(String[] args) {
		loadFontFromFile("Mars_Needs_Cunnilingus");
		initRandom();
		SineSection.initialize();
		if(!ItemLoader.loadItemsFrom("items")) {
			System.err.println("Something went wrong when loading items!");
			System.exit(-1);
		}
		SpaceRPG.initialize();
		new SpaceRPG().testGame();
		// new GameUI().display()
	}

	private static void initialize() {
		itemGenerator = new ItemGenerator();
		addItemTypes();
		LogWriter.createLogger(TITLE);
	}

	private static void addItemTypes() {
		for(ItemReference ref : ItemReference.itemRefs) {
			itemGenerator.addType(ref);
		}
	}

	public static SpaceRPG getMaster() {
		return master;
	}

	/**
	 * start a new game
	 */
	public SpaceRPG() {
		seedGenerator = new Random();
		master = this;
		gui = new GameUI();
		gameClient = new Client();
	}

	/**
	 * continue a previously started game
	 * 
	 * @param save
	 */
	public SpaceRPG(File saveFile) {
		SaveState save = SaveReader.read(saveFile);
		seedGenerator = new Random(save.getSeed());
	}

	private void testGame() {
		testPlayer = new Player("Katyusha");
		gui.setPlayerToTrack(testPlayer);
		gui.display();
		Item testItem = itemGenerator.generate();
		writeToGui(testPlayer);
		testPlayer.addItem(testItem);
		writeToGui(testItem);
		testPlayer.useItem((String) testPlayer.getInventory().toArray()[0], testPlayer);
		writeToGui(testPlayer);
	}

	/**
	 * placeholder testing method for setting up the random generator
	 */
	private static void initRandom() {
		seedGenerator = new Random();
	}

	public static int getNewSeed() {
		return seedGenerator.nextInt();
	}
	
	public Player getPlayer() {
		return testPlayer;
	}

	public GameUI getGui() {
		return gui;
	}

	public void writeToGui(Object in) {
		gui.write(in);
	}

	public static void loadFontFromFile(String fontName) {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			URL url = SpaceRPG.class.getResource("/font/" + fontName + ".ttf");
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(url.toURI())));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't load font: " + fontName);
		}
	}
	
	public void sendChat(String chat) {
		if(gameClient != null) gameClient.sendChat(chat);
	}

	public Creature getCharacter(String name) {
		// TODO return a game character
		return creatures.get(name);
	}
}
