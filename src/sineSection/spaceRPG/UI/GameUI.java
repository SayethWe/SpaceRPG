package sineSection.spaceRPG.UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import sineSection.spaceRPG.UI.panel.CommandBar;
import sineSection.spaceRPG.UI.panel.GameScreen;
import sineSection.spaceRPG.UI.panel.HudPanel;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.command.CommandHandler;
import sineSection.util.LogWriter;

public class GameUI extends AbstractUI {
	private static final long serialVersionUID = 8764045071574261230L;


	GameScreen gameScreen;
	CommandBar commandArea;
	HudPanel hud;

	public GameUI() {
		super();
		setMinimumSize(new Dimension(600, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
		setLocationRelativeTo(null);
	}

	/**
	 * 
	 */
	private void createLayout() {
		gameScreen = new GameScreen();
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
		gameScreen.writeScroll(in);
	}

	public void write(Object in) {
		write(in.toString());
	}

	public void display() {
		super.display();
		hud.intitalize();
	}

	public void clearScreen() {
		gameScreen.setText("");
	}

	public void decreaseFontSize() {
		gameScreen.decreaseFontSize();
	}

	public void increaseFontSize() {
		gameScreen.increaseFontSize();
	}
	
	public boolean toggleTextScroll() {
		return gameScreen.toggleScroll();
	}

	public void setFontSize(int fontSizeIndex) {
		gameScreen.setFontSize(fontSizeIndex);
	}
}
