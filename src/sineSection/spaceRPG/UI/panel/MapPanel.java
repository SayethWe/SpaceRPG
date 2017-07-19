package sineSection.spaceRPG.UI.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.node.Node;
import sineSection.spaceRPG.world.map.room.Room;

/**
 * 
 * @author geekman9097
 * TODO have this be added to the hud by a cartography upgrade to the arctan hud system.
 */
public class MapPanel extends AbstractPanel {
	private static final long serialVersionUID = 1876374043517764378L;


	private static final Color TEXT_COLOR = Color.GREEN;

	private static final Color VISITED_ROOM_COLOR = Color.WHITE;
	private static final Color UNVISITED_ROOM_COLOR = Color.GRAY;
	private static final Color UNMAPPED_COLOR = Color.BLACK;

	private static final Color ENEMY_COLOR = Color.RED;
	private static final Color FRIENDLY_COLOR =Color.PINK;
	private static final Color PLAYER_COLOR = Color.CYAN;

	private static final Color ITEM_COLOR = Color.ORANGE;

	private static final int ROOM_SIZE = 15; //TODO make this zoomable

	private static final int PANEL_WIDTH = 150;


	private static final int MARK_SIZE = 3;

	private Canvas canvas;
	private Player player;
	private Room[][] map;

	public MapPanel(Node n) {
		Component horizontalStrut = Box.createHorizontalStrut(PANEL_WIDTH);
		constraints.weighty = 0;
		constraints.gridy = 0;
		add(horizontalStrut, constraints);
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		constraints.weighty = 1;
		constraints.gridy = 1;
		add(canvas, constraints);
		map = new Room[n.getWidth()][n.getHeight()];
	}

	public void setPlayerToTrack(Player p) {
		player = p;
	}

	public void addRoom(Pos p, Room r) {
		map[p.getX()][p.getY()] = r;
	}

	private void draw(Graphics2D g) {
		g.setBackground(UNMAPPED_COLOR);
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[x].length; y++) {
				Room r = map[x][y];
				g.setColor(getColorFromRoomState(r));
				int roomX = x * ROOM_SIZE;
				int roomY = y * ROOM_SIZE;
				g.drawRect(roomX, roomY, ROOM_SIZE, ROOM_SIZE);

				if(r.hasBeenEntered()) {
					//TODO draw doors.

					//Draw Creature and Item Markers
					List<Color> markers = new ArrayList<>();
					r.getRawCreatures().forEach((c) -> markers.add(getColorFromCreatureType(c)));
					r.getRawItems().forEach((i) -> markers.add(getColorFromItemType(i)));

					int markCols = (int)Math.ceil(Math.sqrt(markers.size()));
					int markRows = (int)Math.ceil(markers.size()/markCols);

					int horPad = (int)((ROOM_SIZE - (markCols*MARK_SIZE))/markCols + 1);
					int verPad = (int)((ROOM_SIZE - (markRows*MARK_SIZE))/markRows + 1);
					for(int mark = 0; mark < markers.size(); mark ++) {
						int markCol = mark % markCols;
						int markRow = (mark - markCol)/markRows;

						int markX = (markCol * MARK_SIZE) + ((markCol + 1) * horPad);
						int markY = (markRow * MARK_SIZE) + ((markRow + 1) * verPad);

						g.setColor(markers.get(mark));
						g.fillOval(markX, markY, MARK_SIZE, MARK_SIZE);
					}
				}
			}
		}
	}

	private Color getColorFromItemType(Item i) {
		return ITEM_COLOR;
		//will eventually keep track of quest items, weapons, etc.
	}

	private Color getColorFromCreatureType(Creature c) {
		Color result;
		if(c.equals(player)) {
			result = PLAYER_COLOR;
		} else if(c.isFriend()) {
			result = FRIENDLY_COLOR;
		} else {
			result = ENEMY_COLOR;
		}
		return result;
	}

	private Color getColorFromRoomState(Room r) {
		Color result;
		if(r == null) {
			result = UNMAPPED_COLOR;
		} else if (r.hasBeenEntered()) {
			result = VISITED_ROOM_COLOR;
		} else {
			result = UNVISITED_ROOM_COLOR;
		}
		//TODO: find way to keep track of visited rooms. 
		return result;
	}

	public void update() {

	}

	public void attained() {

	}

	public void lost() {

	}

}
