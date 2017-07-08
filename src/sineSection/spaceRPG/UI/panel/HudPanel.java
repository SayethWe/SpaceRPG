package sineSection.spaceRPG.UI.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Map.Entry;

import javax.swing.Box;

import sineSection.AnimatedSineSection;
import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.character.Stat;
import sineSection.util.GraphicsUtils;

/**
 * the Hud. Displays your inventory, stats, health, etc. Maybe update w/ a
 * command
 * 
 * @author geekman9097
 * @author Richard Abbott
 *
 */
public class HudPanel extends AbstractPanel implements Runnable {
	private static final long serialVersionUID = -2435708821319951292L;

	private static final int PANEL_WIDTH = 180;
	private static final int PANEL_PADDING = 10;

	public static final Color PANEL_BG = new Color(0, 0, 0);

	public static final Color PANEL_PLAYER_NAME_BG = new Color(0, 60, 0);
	public static final Color PANEL_PLAYER_NAME_COLOR = new Color(0, 255, 0);
	public static final Font PANEL_PLAYER_NAME_FONT = new Font("Mars Needs Cunnilingus", Font.BOLD, 24);

	public static final Font PANEL_DEBUG_MODE_FONT = new Font("Mars Needs Cunnilingus", Font.BOLD, 10);

	public static final Color PANEL_PLAYER_STAT_COLOR = new Color(0, 150, 0);
	public static final Color PANEL_PLAYER_STAT_VALUE_COLOR = new Color(0, 255, 0);
	public static final Font PANEL_PLAYER_STAT_FONT = new Font("Mars Needs Cunnilingus", Font.PLAIN, 16);

	public static final Color PANEL_HEALTH_TEXT_COLOR = new Color(0, 150, 0);
	public static final Color PANEL_HEALTH_VALUE_COLOR = new Color(0, 255, 0);
	public static final Font PANEL_HEALTH_TEXT_FONT = new Font("Mars Needs Cunnilingus", Font.PLAIN, 18);

	public static final int PANEL_BAR_HEIGHT = 20;

	public static final Color PANEL_HEALTH_BAR_BG_COLOR = new Color(0, 60, 0);
	public static final Color PANEL_HEALTH_BAR_FILL_COLOR = new Color(0, 255, 0);
	public static final Color PANEL_HEALTH_BAR_BORDER_COLOR = new Color(0, 60, 0);

	private Canvas canvas;
	private BufferStrategy bfr;
	private boolean running;
	private Thread thread;

	private Player player;
	
	private AnimatedSineSection ASS = new AnimatedSineSection(8, 200, Color.GREEN, 1); // 8? Dayum that ass is huge! It's green though...

	public HudPanel() {
		super();
		Component horizontalStrut = Box.createHorizontalStrut(PANEL_WIDTH);
		constraints.weighty = 0;
		constraints.gridy = -1;
		add(horizontalStrut, constraints);
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		constraints.weighty = 1;
		constraints.gridy = 1;
		add(canvas, constraints);
	}

	public synchronized void intitalize() {
		canvas.createBufferStrategy(2);
		bfr = canvas.getBufferStrategy();
		running = true;
		thread = new Thread(this);
		thread.start();
		ASS.start();
	}

	public synchronized void unintitalize() {
		running = false;
		bfr.dispose();
		if (thread.isAlive()) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Makes this HudPanel track a player.
	 * 
	 * @param p
	 *            the player to track. if <code>null</code>, do not track a
	 *            player.
	 * @author Richard Abbott
	 */
	public void setPlayerToTrack(Player p) {
		player = p;
	}

	public void run() {
		while (running) {
			render();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Uses {@link #bfr} to get a <code>Graphics2D</code> object, then calls
	 * {@link #draw(Graphics2D)}.
	 * 
	 * @author Richard Abbott
	 */
	private void render() {
		Graphics2D g = (Graphics2D) bfr.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		draw(g);
		ASS.draw(40, getHeight() - 25, g);
		g.dispose();
		bfr.show();
	}

	/**
	 * Takes a <code>{@link #Graphics2D}</code> object and draws with it.
	 * 
	 * @author Richard Abbott
	 */
	private void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		g.setColor(PANEL_BG);
		g.fillRect(0, 0, getWidth(), getHeight());

		if (player != null) {
			g.setColor(PANEL_PLAYER_NAME_COLOR);
			g.setFont(PANEL_PLAYER_NAME_FONT);
			int y = 25;
			g.setColor(PANEL_PLAYER_NAME_BG);
			g.fillRect(0, 0, getWidth(), 32);
			g.setColor(PANEL_PLAYER_NAME_COLOR);
			g.drawString(player.getName(), (getWidth() / 2) - (GraphicsUtils.getStringWidth(g, player.getName()) / 2), y);
			y += 25;
			g.setFont(PANEL_PLAYER_STAT_FONT);
			for (Entry<String, Stat> s : player.getStatsAsMap().entrySet()) {
				g.setColor(PANEL_PLAYER_STAT_COLOR);
				g.drawString(getStatNameAbrv(s.getKey()) + ": ", PANEL_PADDING, y);
				g.setColor(PANEL_PLAYER_STAT_VALUE_COLOR);
				g.drawString("" + s.getValue().currentVal(), 40 + PANEL_PADDING, y);
				y += 16;
			}
			y += 6;

			int x = PANEL_PADDING;
			g.setFont(PANEL_HEALTH_TEXT_FONT);
			g.setColor(PANEL_HEALTH_TEXT_COLOR);
			g.drawString("+", x, y);
			x += 15;
			g.setColor(PANEL_HEALTH_VALUE_COLOR);
			g.drawString("" + player.getHealth(), x, y);
			x += GraphicsUtils.getStringWidth(g, "" + player.getHealth()) + 2;
			g.setColor(PANEL_HEALTH_TEXT_COLOR);
			g.drawString("/", x, y);
			x += 13;
			g.setColor(PANEL_HEALTH_VALUE_COLOR);
			g.drawString("" + player.getMaxHealth(), x, y);
			y += 2;

			int barWidth = getWidth() - (PANEL_PADDING * 2);

			GraphicsUtils.drawBar(g, PANEL_PADDING, y, barWidth, PANEL_BAR_HEIGHT, (float) player.getHealth() / (float) player.getMaxHealth(), GraphicsUtils.BAR_NO_BORDER, PANEL_HEALTH_BAR_BG_COLOR, PANEL_HEALTH_BAR_FILL_COLOR, PANEL_HEALTH_BAR_BORDER_COLOR);
		} else {
			g.setColor(PANEL_PLAYER_NAME_COLOR);
			g.setFont(PANEL_PLAYER_NAME_FONT);
			g.drawString("No player", 30, getHeight() / 2 - 15);
		}
		if (SpaceRPG.DEBUG) {
			g.setColor(new Color(0.0f, 0.3f, 0.0f));
			g.setFont(PANEL_DEBUG_MODE_FONT);
			g.drawString("DEBUG MODE", (getWidth() / 2) - (GraphicsUtils.getStringWidth(g, "DEBUG MODE") / 2), getHeight() - 5);
		}
	}

	/**
	 * Abbreviate the name of a stat.<br>
	 * Example:<br>
	 * <br>
	 * <code>
	 * "Intelligence" => "Int"<br>
	 * </code> <br>
	 * Simple <code>{@link String#substring(int, int)}</code> call. (for now.)
	 * 
	 * @param statName
	 *            the stat name to abbreviate
	 * @author Richard Abbott
	 */
	public String getStatNameAbrv(String statName) {
		return statName.substring(0, 3);
	}

}
