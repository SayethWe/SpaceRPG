package sineSection.spaceRPG.UI.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import javax.swing.Box;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.room.Room;

/**
 * 
 * @author geekman9097
 * TODO have this be added to the hd by a cartography upgrade to the arctan hud system.
 */
public class MapPanel extends AbstractPanel {
	private static final long serialVersionUID = 1876374043517764378L;
	
	private static final Color VISITED_ROOM_COLOR = Color.WHITE;
	private static final Color UNVISITED_ROOM_COLOR = Color.GRAY;
	private static final Color TEXT_COLOR = Color.GREEN;
	private static final Color UNMAPPED_COLOR = Color.BLACK;
	private static final Color ENEMY_COLOR = Color.RED;
	private static final Color ITEM_COLOR = Color.ORANGE;
	
	private static final int ROOM_SIZE = 15; //TODO make this zoomable
	
	private static final int PANEL_WIDTH = 150;
	
	private Canvas canvas;
	private Player player;

	public MapPanel() {
		Component horizontalStrut = Box.createHorizontalStrut(PANEL_WIDTH);
		constraints.weighty = 0;
		constraints.gridy = 0;
		add(horizontalStrut, constraints);
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		constraints.weighty = 1;
		constraints.gridy = 1;
		add(canvas, constraints);
	}
	
	public void setPlayerToTrack(Player p) {
		player = p;
	}
	
	public void addRoom(Pos p, Room r) {
		
	}
	
	private void draw(Graphics2D g) {
		
	}
	
	public void update() {
		
	}
	
	public void attained() {
		
	}
	
	public void lost() {
		
	}

}
