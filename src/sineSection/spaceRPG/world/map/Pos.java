package sineSection.spaceRPG.world.map;

import sineSection.util.HashCoder;

/**
 * a position far a node in the world, ar a room in a node. Effectively, a
 * two-tuple of an int
 * 
 * @author geekman9097
 *
 */
public class Pos {
	private final int x;
	private final int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pos)) {
			return false;
		}

		Pos pos = (Pos) o;

		return (pos.hashCode() == hashCode());
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return new HashCoder(11, 23).append(x).append(y).getHash();
	}
}
