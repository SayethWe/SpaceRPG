package sineSection.spaceRPG.UI.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map.Entry;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.character.Stat;

/**
 * Graphical status area for stats and inventory, maybe other things.
 * @author Richard Abbott
 */
public class StatPanel extends Canvas {
	private static final long serialVersionUID = 7542272538407966916L;

	private static final Color PANEL_BG = Color.WHITE;
	
	private static final Color PANEL_PLAYER_NAME_COLOR = Color.GRAY;
	private static final Font PANEL_PLAYER_NAME_FONT = new Font("Arial", Font.BOLD, 24);
	
	private static final Color PANEL_PLAYER_STAT_COLOR = Color.LIGHT_GRAY;
	private static final Font PANEL_PLAYER_STAT_FONT = new Font("Arial", Font.ITALIC, 16);
	
	private static final Color PANEL_HEALTH_TEXT_COLOR = Color.BLACK;
	private static final Font PANEL_HEALTH_TEXT_FONT = new Font("Arial", Font.PLAIN, 18);
	
	private static final Color PANEL_HEALTH_BAR_BG_COLOR = Color.LIGHT_GRAY;
	private static final Color PANEL_HEALTH_BAR_FILL_COLOR = Color.RED.brighter().brighter();
	private static final Color PANEL_HEALTH_BAR_BORDER_COLOR = Color.BLACK;

	private Player player;
	
	/**
	 * Graphical status area for stats and inventory, maybe other things.
	 * @param p <code>Player</code> object to track
	 * @author Richard Abbott
	 */
	public StatPanel(Player p) {
		setSize(200, 800);
		player = p;
	}
	
	/**
	 * Call this to update the <code>StatPanel</code> and to redraw it.
	 * @author Richard Abbott
	 */
	public void onPlayerStatsChanged() {
		repaint();
	}
	
	/**
	 * Draw the <code>StatPanel</code>
	 * @param g Graphics object to draw with.
	 * @author Richard Abbott<br><i>(Also Oracle :P)</i> 
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(PANEL_BG);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(PANEL_PLAYER_NAME_COLOR);
		g.setFont(PANEL_PLAYER_NAME_FONT);
		int y = 25;
		g.drawString(player.getName(), 5, y);
		y += 20;
		g.setColor(PANEL_PLAYER_STAT_COLOR);
		g.setFont(PANEL_PLAYER_STAT_FONT);
		for(Entry<String, Stat> s : player.getStatsAsMap().entrySet()) {
			g.drawString(getStatNameAbrv(s.getKey()) + ": " + s.getValue().currentVal(), 5, y);
			y += 16;
		}
		y += 10;
		
		g.setColor(PANEL_HEALTH_TEXT_COLOR);
		g.setFont(PANEL_HEALTH_TEXT_FONT);
		g.drawString("+" + player.getHealth(), 5, y);
		y += 2;
		
		g.setColor(PANEL_HEALTH_BAR_BG_COLOR);
		g.fillRect(5, y, getWidth() - 10, 10);
		g.setColor(PANEL_HEALTH_BAR_FILL_COLOR);
		g.fillRect(5, y, (int) ((float)player.getHealth() * ( (float)(getWidth() - 10) / (float)player.getMaxHealth())), 10);
		g.setColor(PANEL_HEALTH_BAR_BORDER_COLOR);
		g.drawRect(5, y, getWidth() - 10, 10);
		y += 10;
	}
	
	/**
	 * Abbreviate the name of a stat.<br>
	 * Example:<br>
	 * <br>
	 * <code>
	 * "Intelligence" => "Int"<br>
	 * </code>
	 * <br>
	 * Simple <code>String.substring(0, 3)</code> call.
	 * @param statName The stat name to abbreviate
	 * @author Richard Abbott
	 */
	public String getStatNameAbrv(String statName) {
		return statName.substring(0, 3);
	}
	
}
