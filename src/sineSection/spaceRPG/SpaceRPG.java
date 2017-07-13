package sineSection.spaceRPG;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import sineSection.SineSection;
import sineSection.networking.client.Client;
import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.UI.IntroWindow;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.save.SaveReader;
import sineSection.spaceRPG.save.SaveState;
import sineSection.spaceRPG.sound.SoundPlayer;
import sineSection.spaceRPG.world.DataLoader;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.ItemGenerator;
import sineSection.spaceRPG.world.item.ItemLoader;
import sineSection.spaceRPG.world.item.ItemReference;
import sineSection.spaceRPG.world.map.Ship;
import sineSection.spaceRPG.world.map.WorldPos;
import sineSection.util.LogWriter;
import sineSection.util.Utils;

public class SpaceRPG {
	public static final String TITLE = "Greenshift";
	public static final boolean isOSX = System.getProperty("os.name").startsWith("mac");

	public static boolean DEBUG = false;

	private static GameUI gui;
	private static ItemGenerator itemGenerator;
	private static Random seedGenerator;
	private static SpaceRPG master; // The SPACERPG object to call for all your
									// needs.
	private static boolean doIntro = true;

	private static Player testPlayer;
	private Client gameClient; // the client object this uses to talk to the
								// server
	private Ship gameWorld;

	public static void main(String[] args) {
		boolean showWindow = true;
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equalsIgnoreCase("debug")) {
					System.out.println("!DEBUG MODE ENABLED!");
					DEBUG = true;
				} else if (args[i].equalsIgnoreCase("nointro")) {
					showWindow = false;
				} else if (args[i].equalsIgnoreCase("skipExpo")) {
					doIntro = false;
				}
			}
		}

		if (isOSX)
			setMacIcon();

		SpaceRPG.initialize();
		SineSection.initialize();
		if (!DEBUG && showWindow)
			new IntroWindow().start();

		new SpaceRPG().testGame();
		// new GameUI().display()
	}

	private static void initialize() {
		LogWriter.createLogger(TITLE);
		initRandom();
		itemGenerator = new ItemGenerator();
		DataLoader.loadAllFiles();
		if (!ItemLoader.loadItemsFrom("items")) {
			LogWriter.printErr("Something went wrong when loading items!");
			System.exit(-1);
		}
		addItemTypes();
		loadFontFromFile("Mars_Needs_Cunnilingus");
		loadFontFromFile("VT323");
		SoundPlayer.init();
		SoundPlayer.loadSoundsFromSoundList("/sound/loadSound.txt");
	}

	private static void addItemTypes() {
		// for (ItemReference ref : ItemReference.itemRefs) {
		// itemGenerator.addType(ref);
		// }
		// itemGenerator.addType(ItemReference.getFromName("Power Armor"));
		itemGenerator.addType(ItemReference.getFromItemReferenceID("05-HSYS"));
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

	public Ship getWorld() {
		return gameWorld;
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
		testPlayer = new Player("Katyusha", new WorldPos(0, 0, 0, 0));
		gui.setPlayerToTrack(testPlayer);
		gui.display();
		if (doIntro) {
			try {
				IntroReader.read("Intro.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Item testItem = itemGenerator.generate();
		writeToGui(testPlayer);
		testPlayer.addItem(testItem);
		writeToGui(testPlayer.getItemByAlias(testItem.getAlias()));
		testPlayer.useItem(testItem.getName(), new ArrayList<>());
		writeToGui(testPlayer);
		writeToGui(testItem);
		gameWorld = new Ship();
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
			InputStream f = SpaceRPG.class.getResourceAsStream("/font/" + fontName + ".ttf");
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
		} catch (Exception e) {
			e.printStackTrace();
			LogWriter.print("Can't load font: " + fontName);
		}
	}

	public void sendChat(String chat) {
		if (gameClient != null)
			gameClient.sendChat(chat);
	}

	private static void setMacIcon() {
		Image image = Utils.loadImageResource("/image/logo.png");
		// Application.getApplication().setDockIconImage(image);
		try {
			String className = "com.apple.eawt.Application";
			Class<?> claas = Class.forName(className);
			Object application = claas.getMethod("getApplication").invoke(null);
			application.getClass().getMethod("setDockIconImage", java.awt.Image.class).invoke(application, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
