package sineSection.spaceRPG;

import java.io.File;
import java.util.Random;

import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.save.SaveReader;
import sineSection.spaceRPG.save.SaveState;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.PArmorItem;
import sineSection.spaceRPG.world.item.TestItem;
import sineSection.util.LogWriter;

public class SpaceRPG {
	public static final String TITLE = "SpaceRPG";
	
	private static GameUI gui;
	private static Generator<Item> itemGenerator;
	private static Random seedGenerator;
	private static SpaceRPG master; //The SPACERPG object to call for all your needs.
	
	private static Player testPlayer;
	
	public static void main(String[] args) {
		initRandom();
		SpaceRPG.initialize();
		new SpaceRPG().testGame();
//		new GameUI().display();
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
		testPlayer = new Player("Katyusha");
		gui = new GameUI(testPlayer);
	}
	
	/**
	 * continue a previously started game
	 * @param save
	 */
	public SpaceRPG(File saveFile) {
		SaveState save = SaveReader.read(saveFile);
		seedGenerator = new Random(save.getSeed());
	}
	
	private void testGame() {
		gui.display();
		Item testItem = itemGenerator.generate();
		writeToGui(testPlayer);
		testPlayer.addItem(testItem);
		writeToGui(testItem);
		testPlayer.useItem((String)testPlayer.getInventory().toArray()[0],testPlayer);
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
	
	public GameUI getGui() {
		return gui;
	}
	
	public void writeToGui(Object in) {
		gui.write(in);
	}

}
