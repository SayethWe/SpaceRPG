package sineSection.spaceRPG.world.map;

import sineSection.util.HashCoder;

/**
 * the absolute position of a room. Effectively a Pos two-tuple
 * 
 * @author geekman9097
 *
 */
public class WorldPos {
	private final Pos nodePos; // the position of the node within the ship
	private final Pos roomPos; // the position of the room within the node

	public WorldPos(Pos node, Pos room) {
		nodePos = node;
		roomPos = room;
	}

	public WorldPos(int nx, int ny, int rx, int ry) {
		nodePos = new Pos(nx, ny);
		roomPos = new Pos(rx, ry);
	}

	public Pos getRoom() {
		return roomPos;
	}

	public Pos getNode() {
		return nodePos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WorldPos)) {
			return false;
		}
		WorldPos wp = (WorldPos) o;
		return (wp.hashCode() == hashCode());
	}

	@Override
	public int hashCode() {
		return new HashCoder(11, 23).append(nodePos).append(roomPos).getHash();
	}

}
