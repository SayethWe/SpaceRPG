package sineSection.spaceRPG.world.map.nodes;

import sineSection.spaceRPG.world.rooms.RoomDiner;
import sineSection.spaceRPG.world.rooms.RoomQuarters;

public class ResidentialNode extends Node {
	
	
	public ResidentialNode(int size) {
		super(size);
		addRoomTypes();
	}
	
	private void addRoomTypes() {
		addRoomType(RoomQuarters.class);
		addRoomType(RoomDiner.class);
	}

}
