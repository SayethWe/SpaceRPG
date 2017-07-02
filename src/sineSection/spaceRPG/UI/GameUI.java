package sineSection.spaceRPG.UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import sineSection.spaceRPG.UI.panel.HudPanel;
import sineSection.spaceRPG.character.Player;
import sineSection.util.LogWriter;

public class GameUI extends AbstractUI {
	private static final long serialVersionUID = 8764045071574261230L;
	private static final String DEFAULT_TEXT = "Enter Command Here";
	private static final int TEXT_HISTORY = 9;
	private static final int DEFAULT_WIDTH = 10;

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
		constraints.fill = GridBagConstraints.BOTH;
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
		commandArea.addKeyListener(CreateFocusListener());
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
		LogWriter.getLogger().info("Command Sent: " + commandArea.getText());
		gameScreen.append(commandArea.getText());
		gameScreen.append("\n");
		commandArea.setText(DEFAULT_TEXT);
	}

	private KeyListener CreateFocusListener() {
		return new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (commandArea.getText().equals(DEFAULT_TEXT))
					commandArea.setText(" ");
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
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
	}

	public void write(Object in) {
		write(in.toString());
	}

	public void display() {
		super.display();
		hud.intitalize();
	}
}
