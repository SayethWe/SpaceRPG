package sineSection.spaceRPG.world.map.node;

import sineSection.spaceRPG.world.map.room.RoomDiner;
import sineSection.spaceRPG.world.map.room.RoomQuarters;

public class ResidentialNode extends Node {
	
	
	public ResidentialNode(int size) {
		super(size);
	}
	
	@Override
	protected void addRoomTypes() {
		addRoomType(RoomQuarters.class);
		addRoomType(RoomDiner.class);
	}

}
