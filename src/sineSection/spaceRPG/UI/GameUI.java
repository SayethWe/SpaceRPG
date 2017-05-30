package sineSection.spaceRPG.UI;

import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sineSection.util.Utils;

public class GameUI extends AbstractUI {
	private static final long serialVersionUID = 8764045071574261230L;
	private static final String DEFAULT_TEXT = "Enter Command Here";
	private static final int TEXT_HISTORY = 9;
	private static final int DEFAULT_WIDTH = 10;
	
	JTextArea gameScreen;
	JTextField commandArea;
	
	public GameUI() {
		super();
		createLayout();
	}
	
	private void createLayout() {
		gameScreen = new JTextArea(DEFAULT_WIDTH,TEXT_HISTORY);
		gameScreen.setAlignmentX(LEFT_ALIGNMENT);
		constraints.fill = GridBagConstraints.BOTH;
		JScrollPane scrollable = new JScrollPane(gameScreen);
		add(scrollable, constraints);
		
		commandArea = new JTextField(DEFAULT_TEXT);
		commandArea.addActionListener((e) -> commandSent());
		commandArea.addKeyListener(CreateFocusListener());
		commandArea.setEditable(true);
		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(commandArea, constraints);
	}
	
	private void commandSent() {
		Utils.getLogger().info("Command Sent: " + commandArea.getText());
		gameScreen.append(commandArea.getText());
		gameScreen.append("\n");
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
}
