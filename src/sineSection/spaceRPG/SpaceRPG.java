package sineSection.spaceRPG;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;
import java.util.Random;

import sineSection.SineSection;
import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.save.SaveReader;
import sineSection.spaceRPG.save.SaveState;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.PArmorItem;
import sineSection.spaceRPG.world.item.TestItem;
import sineSection.spaceRPG.world.item.loader.ItemLoader;
import sineSection.util.LogWriter;

public class SpaceRPG {
	public static final String TITLE = "SpaceRPG";

	private static GameUI gui;
	private static Generator<Item> itemGenerator;
	private static Random seedGenerator;
	private static SpaceRPG master; // The SPACERPG object to call for all your
									// needs.

	private static Player testPlayer;

	public static void main(String[] args) {
		loadFontFromFile("Mars_Needs_Cunnilingus");
		initRandom();
		SpaceRPG.initialize();
		SineSection.initialize();
		if(!ItemLoader.loadItemsFrom("items")) {
			System.err.println("Something went wrong when loading items!");
			System.exit(-1);
		}
		new SpaceRPG().testGame();
		// new GameUI().display()
	}

	private static void initialize() {
		itemGenerator = new Generator<>();
		addItemTypes();
		LogWriter.createLogger(TITLE);
	}

	private static void addItemTypes() {
		itemGenerator.addType(TestItem.class);
		itemGenerator.addType(PArmorItem.class);
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
}
