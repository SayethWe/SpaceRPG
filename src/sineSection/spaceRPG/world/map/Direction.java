package sineSection.spaceRPG.world.map;

public enum Direction {
	FORE("Fore", 0, 1),
	AFT("Aft", 0, -1),
	PORT("Port", -1, 0),
	STARBOARD("Starboard", 1, 0);

	final String call;
	final int delX;
	final int delY;

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
	
	public static Direction getOpposite(Direction d) {
		Direction result;
		switch(d) {
		case AFT:
			result = FORE;
			break;
		case FORE:
			result = AFT;
			break;
		case PORT:
			result = STARBOARD;
			break;
		case STARBOARD:
			result = PORT;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}
}
