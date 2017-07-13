package sineSection.spaceRPG.UI.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.command.CommandStrings;
import sineSection.spaceRPG.sound.SoundPlayer;
import sineSection.util.GraphicsUtils;

public class CommandBar extends JTextField {
	private static final long serialVersionUID = 1L;

	private boolean selected = false;

	public static final Font COMMAND_BAR_FONT = GameScreen.GAME_SCREEN_FONT.deriveFont(18f);
	private static final Font COMMAND_BAR_INFO_FONT = HudPanel.PANEL_PLAYER_NAME_FONT.deriveFont(10f);

	private static final Color COMMAND_BAR_TEXT_COLOR = new Color(0, 255, 0);
	private static final Color COMMAND_BAR_SELECT_COLOR = new Color(0, 150, 0);
	private static final Color COMMAND_BAR_DEFAULT_TEXT_COLOR = new Color(0, 150, 0);
	private static final Color COMMAND_BAR_BG_COLOR = new Color(0, 30, 0);

	private static final String DEFAULT_TEXT = "Enter Command Here";
	private static final int BLINK_INTERVAL = 2500;
	private static final int BLINK_LENGTH = 250;
	private static final int MAX_COMMAND_HISTORY = 50;

	private int blinkTimer = 0;
	private boolean drawCursor = true;
	private LinkedList<String> commandHistory = new LinkedList<String>();
	private int commandHistoryIndex = -1, autoCompleteIndex = -1;

	private int caretPos = 0, markerPos = 0;

	private List<String> autoCompleteCommands = new ArrayList<String>();

	private CommandList commandList;
	private GameUI ui;

	public CommandBar(GameUI ui) {
		this("", ui);
	}

	public CommandBar(String defaultText, GameUI ui) {
		super(defaultText);
		commandList = new CommandList(ui);
		commandList.setLocationRelativeTo(this);
		this.ui = ui;

		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());

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
					if (getText().isEmpty() && autoCompleteIndex < 0)
						return;
					if (autoCompleteIndex > -1) {
						setText(autoCompleteCommands.get(autoCompleteIndex) + " ");
						commandHistoryIndex = -1;
						autoCompleteIndex = -1;
						break;
					}
					ui.commandSent(getText());
					addCommandToHistory(getText());
					updateCommandList();
					setText("");
					commandHistoryIndex = -1;
					autoCompleteIndex = -1;
					break;
				case KeyEvent.VK_UP:
					if (commandHistory.size() > 0) {
						commandHistoryIndex++;
						if (commandHistoryIndex >= commandHistory.size()) {
							commandHistoryIndex = commandHistory.size() - 1;
						}
						autoCompleteIndex = -1;
						setText(commandHistory.get(commandHistoryIndex));
					}
					updateCommandList();
					break;
				case KeyEvent.VK_DOWN:
					if (commandHistory.size() > 0) {
						commandHistoryIndex--;
						if (commandHistoryIndex < 0) {
							setText("");
							if (commandHistoryIndex < -1) {
								commandHistoryIndex = -1;
							}
						}
						if (commandHistoryIndex > -1) {
							autoCompleteIndex = -1;
							setText(commandHistory.get(commandHistoryIndex));
						}
					}
					updateCommandList();
					break;
				case KeyEvent.VK_TAB:
					commandHistoryIndex = -1;
					autoCompleteIndex++;
					if (autoCompleteIndex > autoCompleteCommands.size() - 1) {
						autoCompleteIndex = -1;
					}
					updateCommandList();
					break;
				}
			}
		});

		addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				caretPos = e.getDot();
				markerPos = e.getMark();
			}
		});

		getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				updateCommandList();
				SoundPlayer.play("keyStroke");
			}
			
			public void insertUpdate(DocumentEvent e) {
				updateCommandList();
				SoundPlayer.play("keyStroke");
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});

		this.ui.addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent e) {
				if(autoCompleteCommands.size() > 0) {
					commandList.setVisible(true);
				}
			}

			public void componentResized(ComponentEvent e) {
				updateCommandList();
			}

			public void componentMoved(ComponentEvent e) {
				updateCommandList();
			}

			public void componentHidden(ComponentEvent e) {
				commandList.setVisible(false);
			}
		});
	}

	public void disposeCommandList() {
		commandList.dispose();
	}

	private void updateCommandList() {
		autoCompleteCommands.clear();
		if (!getText().isEmpty() && selected) {
			for (String s : CommandStrings.getCommandNames()) {
				if (s.toLowerCase().startsWith(getText().toLowerCase()) && !s.equalsIgnoreCase(getText()))
					autoCompleteCommands.add(s);
			}
		}
		commandList.update(autoCompleteCommands, autoCompleteIndex);
		repaint();
		if (commandList.isVisible()) {
			Point loc = getLocationOnScreen();
			commandList.setLocation((int) loc.getX(), (int) loc.getY() - commandList.getHeight());
		}
		requestFocus();
		requestFocusInWindow();
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
		super.paint(g);
		blinkTimer++;
		if ((blinkTimer > BLINK_INTERVAL && drawCursor) || (blinkTimer > BLINK_LENGTH && !drawCursor)) {
			drawCursor = !drawCursor;
			blinkTimer = 0;
		}
		g.setColor(COMMAND_BAR_BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(COMMAND_BAR_FONT);
		int offs = 0;
		int endSpace = SpaceRPG.DEBUG ? 25 : 3;
		if (!getText().isEmpty()) {
			if (GraphicsUtils.getStringWidth(g, getText()) + endSpace > getWidth()) {
				offs = GraphicsUtils.getStringWidth(g, getText().substring(0, caretPos)) + endSpace - getWidth();
				if (offs < 0)
					offs = 0;
			}
			g.setColor(COMMAND_BAR_TEXT_COLOR);
			g.drawString(getText(), 3 - offs, getHeight() - 3);
		} else if (!selected) {
			g.setColor(COMMAND_BAR_DEFAULT_TEXT_COLOR);
			int x = (getWidth() / 2) - (GraphicsUtils.getStringWidth(g, DEFAULT_TEXT) / 2);
			g.drawString(DEFAULT_TEXT, x, getHeight() - 3);
		}
		if (selected) {
			if (caretPos == markerPos) {
				if (drawCursor) {
					g.setColor(COMMAND_BAR_TEXT_COLOR);
					int x = GraphicsUtils.getStringWidth(g, getText().substring(0, caretPos)) + 2 - offs;
					g.drawLine(x, 2, x, getHeight() - 2);
				}
			} else {
				g.setColor(COMMAND_BAR_SELECT_COLOR);
				int beginPos = Math.min(markerPos, caretPos);
				int endPos = Math.max(markerPos, caretPos);
				int x = GraphicsUtils.getStringWidth(g, getText().substring(0, beginPos)) + 2 - offs;
				int w = GraphicsUtils.getStringWidth(g, getText().substring(beginPos, endPos));
				g.drawRect(x, 2, w, getHeight() - 4);
			}
		}

		if (offs > 0) {
			g.fillRect(0, 0, 5, getHeight());
		}
		if (SpaceRPG.DEBUG) {
			g.setColor(Color.GREEN);
			g.setFont(COMMAND_BAR_INFO_FONT);
			g.drawString("" + commandHistoryIndex, getWidth() - GraphicsUtils.getStringWidth(g, "" + commandHistoryIndex) - 3, 8);
			g.drawString("" + autoCompleteIndex, getWidth() - GraphicsUtils.getStringWidth(g, "" + autoCompleteIndex) - 3, 18);
		}
		repaint();
	}

	public boolean isManagingFocus() {
		return false;
	}

}
