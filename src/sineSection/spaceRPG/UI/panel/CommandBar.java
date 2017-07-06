package sineSection.spaceRPG.UI.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import sineSection.spaceRPG.UI.GameUI;
import sineSection.util.GraphicsUtils;

public class CommandBar extends JTextField {
	private static final long serialVersionUID = 1L;

	private boolean selected = false;

	private static final Font COMMAND_BAR_FONT = GameUI.GAME_SCREEN_FONT.deriveFont(18f);
	private static final Font COMMAND_BAR_CARET_INDEX_FONT = GameUI.GAME_SCREEN_FONT.deriveFont(18f);
	
	private static final Color COMMAND_BAR_TEXT_COLOR = new Color(0, 255, 0);
	private static final Color COMMAND_BAR_DEFAULT_TEXT_COLOR = new Color(0, 150, 0);
	private static final Color COMMAND_BAR_BG_COLOR = new Color(0, 30, 0);
	
	private static final String DEFAULT_TEXT = "Enter Command Here";
	private static final int BLINK_INTERVAL = 2500;
	private static final int MAX_COMMAND_HISTORY = 50;

	private int blinkTimer = 0;
	private boolean drawCursor = true;
	private LinkedList<String> commandHistory = new LinkedList<String>();
	private int commandHistoryIndex = 0;
	
	private int caretPos = 0;
	
	private JFrame commandList;

	public CommandBar(GameUI ui) {
		this("", ui);
	}

	public CommandBar(String defaultText, GameUI ui) {
		super(defaultText);
		commandList = new JFrame();
		commandList.setUndecorated(true);
		commandList.setLocationRelativeTo(this);

		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				selected = false;
			}

			public void focusGained(FocusEvent e) {
				selected = true;
			}
		});
		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {

			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					if (getText().isEmpty())
						return;
					ui.commandSent(getText());
					addCommandToHistory(getText());
					setText("");
					commandHistoryIndex = 0;
					break;
				case KeyEvent.VK_UP:
					if (commandHistory.size() > 0) {
						setText(commandHistory.get(commandHistoryIndex));
						commandHistoryIndex++;
						if (commandHistoryIndex >= commandHistory.size()) {
							commandHistoryIndex = commandHistory.size() - 1;
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					if (commandHistory.size() > 0) {
						setText(commandHistory.get(commandHistoryIndex));
						commandHistoryIndex--;
						if (commandHistoryIndex < 0) {
							commandHistoryIndex = 0;
						}
					}
					break;
				}
			}
		});
		
		addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				caretPos = e.getDot();
			}
		});
	}

	private void addCommandToHistory(String c) {
		if (c == null || c.isEmpty())
			return;
		commandHistory.addFirst(c);
		if (commandHistory.size() > MAX_COMMAND_HISTORY) {
			commandHistory.removeLast();
		}
	}
	
	public void clearCommandHistory() {
		commandHistory.clear();
	}

	public void paint(Graphics g) {
		blinkTimer++;
		if (blinkTimer > BLINK_INTERVAL) {
			drawCursor = !drawCursor;
			blinkTimer = 0;
		}
		g.setColor(COMMAND_BAR_BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(COMMAND_BAR_FONT);
		int offs = 0;
		if (!getText().isEmpty()) {
			if (GraphicsUtils.getStringWidth(g, getText()) + 12 > getWidth()) {
				offs = GraphicsUtils.getStringWidth(g, getText()) + 12 - getWidth();
			}
			g.setColor(COMMAND_BAR_TEXT_COLOR);
			g.drawString(getText(), 3 - offs, getHeight() - 3);
		} else if (!selected) {
			g.setColor(COMMAND_BAR_DEFAULT_TEXT_COLOR);
			int x = (getWidth() / 2) - (GraphicsUtils.getStringWidth(g, DEFAULT_TEXT) / 2);
			g.drawString(DEFAULT_TEXT, x, getHeight() - 3);
		}
		if (selected && drawCursor) {
			g.setColor(COMMAND_BAR_TEXT_COLOR);
			int x = GraphicsUtils.getStringWidth(g, getText()) + 5 - offs;
			g.drawLine(x, 2, x, getHeight() - 2);
		}

		if (offs > 0) {
			g.fillRect(0, 0, 5, getHeight());
		}
		repaint();
	}

}
