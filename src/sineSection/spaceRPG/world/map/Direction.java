package sineSection.spaceRPG.world.map;

public enum Direction {
	FORE("Fore",0,1),
	AFT("Aft",0,-1),
	PORT("Port",-1,0),
	STARBOARD("Starboard",1,0);
	
	String call;
	int delX;
	int delY;
	
	Direction(String refText, int xChange, int yChange) {
		call = refText;
		delX = xChange;
		delY = yChange;
	}
	
	public String getCall() {
		return call;
	}
	
	public Pos affectPos(Pos pos) {
		return new Pos(pos.getX() + delX, pos.getY() + delY);
	}
}
