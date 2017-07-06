package sineSection.spaceRPG.UI.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.command.CommandStrings;
import sineSection.util.GraphicsUtils;

public class CommandList extends JFrame {
	private static final long serialVersionUID = -8961495941379728359L;

	private static final Font COMMAND_LIST_FONT = GameUI.GAME_SCREEN_FONT.deriveFont(18f);

	private static final Color COMMAND_LIST_TEXT_COLOR = new Color(0, 255, 0);
	private static final Color COMMAND_LIST_BG_COLOR = new Color(0, 0, 0);
	private static final Color COMMAND_LIST_BORDER_COLOR = new Color(0, 255, 0);

	private static final int COMMAND_BAR_MARGIN = 3;

	private static final int COMMAND_BAR_MIN_WIDTH = 50;

	private List<String> shownCommands = new ArrayList<String>();

	public CommandList(JFrame parent) {
		setUndecorated(true);
		setAutoRequestFocus(false);
		setVisible(false);
		setType(Type.UTILITY);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		update("", false);
	}

	public void update(String cmd, boolean selected) {
		shownCommands.clear();
		if (!cmd.isEmpty() && selected) {
			for (String s : CommandStrings.getCommandCalls()) {
				if (s.toLowerCase().startsWith(cmd.toLowerCase()))
					shownCommands.add(s);
			}
		}
		setSize(Math.max(COMMAND_BAR_MARGIN * 2 + GraphicsUtils.getLargestWidth(COMMAND_LIST_FONT, shownCommands), COMMAND_BAR_MIN_WIDTH), (shownCommands.size() * GraphicsUtils.getFontHeight(COMMAND_LIST_FONT)) + COMMAND_BAR_MARGIN * 2);
		if (!shownCommands.isEmpty()) {
			setVisible(true);
			repaint();
		} else {
			setVisible(false);
		}

	}

	public void paint(Graphics g) {
		g.setFont(COMMAND_LIST_FONT);
		g.setColor(COMMAND_LIST_BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(COMMAND_LIST_BORDER_COLOR);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

		g.setColor(COMMAND_LIST_TEXT_COLOR);
		for (int i = 0; i < shownCommands.size(); i++) {
			String cmd = shownCommands.get(i);
			int x = COMMAND_BAR_MARGIN;
			int y = ((i + 1) * GraphicsUtils.getFontHeight(g)) + COMMAND_BAR_MARGIN - (3 * (i + 1));
			g.drawString(cmd, x, y);
		}
	}

}
