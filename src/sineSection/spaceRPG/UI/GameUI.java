package sineSection.spaceRPG.UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import sineSection.spaceRPG.UI.panel.HudPanel;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.command.CommandHandler;
import sineSection.util.LogWriter;

public class GameUI extends AbstractUI {
	private static final long serialVersionUID = 8764045071574261230L;
	private static final String DEFAULT_TEXT = "Enter Command Here";
	private static final int TEXT_HISTORY = 9;
	private static final int DEFAULT_WIDTH = 10;
	
	private static final Font GAME_SCREEN_FONT = new Font("VT323" , Font.PLAIN, 16);

	JTextArea gameScreen;
	JTextField commandArea;
	HudPanel hud;

	public GameUI() {
		super();
		updateLookAndFeel();
		setMinimumSize(new Dimension(600, 500));
		setTitle("SpaceRPG");
		createLayout();
		setLocationRelativeTo(null);
	}
	
	private void updateLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void createLayout() {
		gameScreen = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		gameScreen.setAlignmentX(LEFT_ALIGNMENT);
		gameScreen.setEditable(false);
		gameScreen.setWrapStyleWord(true);
		gameScreen.setFont(GAME_SCREEN_FONT);
		JScrollPane scrollable = new JScrollPane(gameScreen);
		add(scrollable, constraints);

		hud = new HudPanel();
		hud.setAlignmentX(CENTER_ALIGNMENT);
		constraints.weightx = 0;
		constraints.gridheight = 2;
		constraints.gridx = 1;
		add(hud, constraints);
		addWindowListener(CreateWindowAdapter());

		commandArea = new JTextField(DEFAULT_TEXT);
		commandArea.addActionListener((e) -> commandSent());
		commandArea.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	commandArea.setText("");
            }
        });
		commandArea.setEditable(true);
		constraints.weightx = 0.5;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(commandArea, constraints);
	}

	private void commandSent() {
		String text = commandArea.getText();
		LogWriter.getLogger().info("Command Sent: " + text);
		gameScreen.append(text);
		gameScreen.append("\n");
		CommandHandler.sendCommand(text);
		commandArea.setText("");
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
}
