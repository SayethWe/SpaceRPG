package sineSection.spaceRPG.UI;

import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sineSection.spaceRPG.UI.panel.HudPanel;
import sineSection.spaceRPG.command.CommandHandler;
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
		createLayout();
	}
	
	private void createLayout() {
		gameScreen = new JTextArea(TEXT_HISTORY,DEFAULT_WIDTH);
		gameScreen.setAlignmentX(LEFT_ALIGNMENT);
		gameScreen.setEditable(false);
		constraints.fill = GridBagConstraints.BOTH;
		JScrollPane scrollable = new JScrollPane(gameScreen);
		add(scrollable, constraints);
		
		hud = new HudPanel();
		hud.setAlignmentX(CENTER_ALIGNMENT);
		constraints.weightx = 0.25;
		constraints.gridheight = 2;
		constraints.gridx = 1;
		JScrollPane hudPane = new JScrollPane(hud);
		add(hudPane, constraints);
		
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
		String text = commandArea.getText();
		LogWriter.getLogger().info("Command Sent: " + text);
		gameScreen.append(text);
		gameScreen.append("\n");
		CommandHandler.sendCommand(text);
		commandArea.setText(DEFAULT_TEXT);
	}
	
	private KeyListener CreateFocusListener() {
		return new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				if (commandArea.getText().equals(DEFAULT_TEXT)) commandArea.setText(" ");
			}

			@Override public void keyPressed(KeyEvent e) {}

			@Override public void keyReleased(KeyEvent e) {}
		};
	}
	
	public HudPanel getHud() {
		return hud;
	}
	
	private void write(String in) {
		gameScreen.append(in);
		gameScreen.append("\n");
	}
	
	public void write(Object in) {
		write(in.toString());
	}
}
