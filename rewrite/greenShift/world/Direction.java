package greenShift.world;

public enum Direction {
	FORE(0,-1),
	AFT(0,1),
	STARBOARD(-1,0),
	Port(1,0);
	
	final int delX, delY;
	
	Direction(int delX, int delY) {
		this.delX = delX;
		this.delY = delY;
	}
}
