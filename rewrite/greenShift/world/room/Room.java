package greenShift.world.room;

import java.util.HashMap;
import java.util.Map;

import greenShift.world.Direction;
import greenShift.world.Doorway;

public abstract class Room {
	public final String call;
	private final Map<Direction,Doorway> doors;
	
	public Room(String call) {
		this.call = call;
		doors = new HashMap<>();
	}
	
	public Doorway getDoor(Direction d) {
		return doors.get(d);
	}
	
	abstract void onRoomEnter();
	abstract void onRoomExit();
	
}
