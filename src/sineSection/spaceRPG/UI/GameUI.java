package sineSection.spaceRPG.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sineSection.spaceRPG.UI.panel.CommandBar;
import sineSection.spaceRPG.UI.panel.HudPanel;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.command.CommandHandler;
import sineSection.util.LogWriter;

public class GameUI extends AbstractUI {
	private static final long serialVersionUID = 8764045071574261230L;

	private static final int DEFAULT_FONT_SIZE = 6;
	private static final float[] FONT_SIZES = new float[] { 8f, 9f, 10f, 11f, 12f, 14f, 16f, 18f, 20f, 22f, 24f };

	public static final Font GAME_SCREEN_FONT = new Font("VT323", Font.PLAIN, (int) FONT_SIZES[DEFAULT_FONT_SIZE]);

	private int fontSize = DEFAULT_FONT_SIZE;

	JTextArea gameScreen;
	CommandBar commandArea;
	HudPanel hud;

	public GameUI() {
		super();
		setMinimumSize(new Dimension(600, 500));
		setTitle("SpaceRPG");
		createLayout();
		setLocationRelativeTo(null);
	}

	private void createLayout() {
		gameScreen = new JTextArea();
		gameScreen.setAlignmentX(LEFT_ALIGNMENT);
		gameScreen.setEditable(false);
		gameScreen.setLineWrap(true);
		gameScreen.setWrapStyleWord(true);
		gameScreen.setFont(GAME_SCREEN_FONT);
		gameScreen.setBackground(Color.BLACK);
		gameScreen.setForeground(Color.GREEN);
		JScrollPane scrollable = new JScrollPane(gameScreen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollable, constraints);

		hud = new HudPanel();
		hud.setAlignmentX(CENTER_ALIGNMENT);
		constraints.weightx = 0;
		constraints.gridheight = 2;
		constraints.gridx = 1;
		add(hud, constraints);
		addWindowListener(CreateWindowAdapter());

		commandArea = new CommandBar(this);
		commandArea.setEditable(true);
		constraints.weightx = 0.5;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(commandArea, constraints);

		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
				commandArea.disposeCommandList();
			}

			public void windowActivated(WindowEvent e) {
			}
		});
	}

	public void commandSent(String text) {
		LogWriter.print("Command Sent: " + text);
		gameScreen.append(text);
		gameScreen.append("\n");
		CommandHandler.sendCommand(text);
	}

	private WindowAdapter CreateWindowAdapter() {
		return new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				hud.unintitalize();
			}
		};
	}

	/**
	 * Makes this GameUI track a player.<br>
	 * Calls {@link #HudPanel.setPlayerToTrack(Player p)}.
	 * 
	 * @param p
	 *            The player to track. If null, do not track a player.
	 * @author Richard Abbott
	 */
	public void setPlayerToTrack(Player p) {
		hud.setPlayerToTrack(p);
	}

	private void write(String in) {
		gameScreen.append(in);
		gameScreen.append("\n");
		gameScreen.setCaretPosition(gameScreen.getDocument().getLength());
	}

	public void write(Object in) {
		write(in.toString());
	}

	public void display() {
		super.display();
		hud.intitalize();
	}

	/**
	 * Sets the <b>INDEX</b> of the {@link #FONT_SIZES} to use.
	 * 
	 * @param fontSizeIndex
	 */
	public void setFontSize(int fontSizeIndex) {
		if (fontSizeIndex > FONT_SIZES.length)
			fontSizeIndex = FONT_SIZES.length;
		if (fontSizeIndex < 0)
			fontSizeIndex = 0;
		fontSize = fontSizeIndex;
		if (fontSize == DEFAULT_FONT_SIZE) {
			gameScreen.setFont(GAME_SCREEN_FONT);
		} else {
			gameScreen.setFont(GAME_SCREEN_FONT.deriveFont(FONT_SIZES[fontSize]));
		}
		gameScreen.append("Font size set to " + FONT_SIZES[fontSize] + "!\n(" + (fontSize + 1) + "/" + FONT_SIZES.length + ")\n");
	}

	public void increaseFontSize() {
		if (fontSize < FONT_SIZES.length - 1) {
			fontSize++;
			if (fontSize == DEFAULT_FONT_SIZE) {
				gameScreen.setFont(GAME_SCREEN_FONT);
			} else {
				gameScreen.setFont(GAME_SCREEN_FONT.deriveFont(FONT_SIZES[fontSize]));
			}
			gameScreen.repaint();
			gameScreen.append("Font size increased!\n(" + (fontSize + 1) + "/" + FONT_SIZES.length + ")\n");
		} else {
			gameScreen.append("Can't increase font size any more!\n");
		}
	}

	public void decreaseFontSize() {
		if (fontSize > 0) {
			fontSize--;
			if (fontSize == DEFAULT_FONT_SIZE) {
				gameScreen.setFont(GAME_SCREEN_FONT);
			} else {
				gameScreen.setFont(GAME_SCREEN_FONT.deriveFont(FONT_SIZES[fontSize]));
			}
			gameScreen.append("Font size decreased!\n(" + (fontSize + 1) + "/" + FONT_SIZES.length + ")\n");
		} else {
			gameScreen.append("Can't decrease font size any more!\n");
		}
	}
}
