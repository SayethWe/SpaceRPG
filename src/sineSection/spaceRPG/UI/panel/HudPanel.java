package sineSection.spaceRPG.UI.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Map.Entry;

import javax.swing.Box;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.character.Stat;
import sineSection.spaceRPG.sound.SoundPlayer;
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
	
	public static final String NO_HUD_TEXT = "NO HUD SYSTEM DETECTED";

	private Canvas canvas;
	private boolean running;
	private Thread thread;

	private Player player;

	private static final int NONE_IDLE = 0;
	private static final int HUD_ATTAINED_ANIM = 1;
	private static final int HUD_IDLE = 2;
	private static final int HUD_LOST_ANIM = 3;

	private int state = NONE_IDLE;
	private boolean animating = false;
	private int animTimer = 0;

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
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void unintitalize() {
		running = false;
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
			if(animating)
			animTimer++;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void hudAttained() {
		if (state == NONE_IDLE) {
			state = HUD_ATTAINED_ANIM;
			animating = true;
			SoundPlayer.play("hudAttained");
		}
	}

	public void hudLost() {
		if (state == HUD_IDLE) {
			state = HUD_LOST_ANIM;
			animating = true;
			SoundPlayer.play("hudLost");
		}
	}

	/**
	 * Uses {@link #bfr} to get a <code>Graphics2D</code> object, then calls
	 * {@link #draw(Graphics2D)}.
	 * 
	 * @author Richard Abbott
	 */
	private void render() {
		if (canvas.getBufferStrategy() == null) {
			canvas.createBufferStrategy(2);
			return;
		}
		BufferStrategy bfr = canvas.getBufferStrategy();
		Graphics2D g = (Graphics2D) bfr.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		draw(g);
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

		switch(state) {
		default:
		case NONE_IDLE:
			drawNoneIdle(g);
			break;
		case HUD_ATTAINED_ANIM:
			drawAttainedAnim(g);
			break;
		case HUD_IDLE:
			drawIdle(g);
			break;
		case HUD_LOST_ANIM:
			drawLostAnim(g);
			break;
		}
		
		if (SpaceRPG.DEBUG) {
			g.setColor(new Color(0.0f, 0.3f, 0.0f));
			g.setFont(PANEL_DEBUG_MODE_FONT);
			g.drawString("DEBUG MODE", (getWidth() / 2) - (GraphicsUtils.getStringWidth(g, "DEBUG MODE") / 2), getHeight() - 5);
		}
	}

	private void drawNoneIdle(Graphics g) {
		g.setColor(PANEL_BG);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(PANEL_DEBUG_MODE_FONT);
		g.setColor(PANEL_PLAYER_NAME_BG);
		int x = getWidth() / 2 - GraphicsUtils.getStringWidth(g, NO_HUD_TEXT) / 2;
		int y = getHeight() / 2 - GraphicsUtils.getFontHeight(g) / 2;
		g.drawString(NO_HUD_TEXT, x, y);
	}

	private void drawAttainedAnim(Graphics g) {
		g.setColor(PANEL_BG);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GREEN);
		int w = animTimer / 2;
		int x = getWidth() / 2 - w / 2;
		g.drawRect(x, 0, w, getHeight());
		if(w >= getWidth()) {
			animating = false;
			state = HUD_IDLE;
		}
	}

	private void drawIdle(Graphics g) {
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
	}

	private void drawLostAnim(Graphics g) {
		g.setColor(PANEL_BG);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.GREEN);
		int w = getWidth() - (animTimer / 2);
		int x = getWidth() / 2 - w / 2;
		g.drawRect(x, 0, w, getHeight());
		if(w <= 0) {
			animating = false;
			state = HUD_IDLE;
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

	public void updateHud() {
		if(player == null) return;
		if(player.hasHud()) {
			hudAttained();
		} else {
			hudLost();
		}
	}

}
