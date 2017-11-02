package greenShift.world.room;

import java.util.Map;

import greenShift.world.Direction;
import greenShift.world.Doorway;
import greenShift.world.GeneratorValue;

public class Room {
	private final GeneratorValue heatValue; //The value range an a temperature map for this type of room to generate
	private final GeneratorValue humidValue; //the value range on a humitidy map for the type of room to generate
	private final GeneratorValue machineryValue; //the value range on a machinery amount map
	
	Map<Direction,Doorway> doors; 
}
