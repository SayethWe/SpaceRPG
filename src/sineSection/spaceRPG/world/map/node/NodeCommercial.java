package sineSection.spaceRPG.world.map.node;

import sineSection.spaceRPG.world.map.room.RoomDiner;
import sineSection.spaceRPG.world.map.room.RoomStore;

public class NodeCommercial extends Node {

	public NodeCommercial(int size) {
		super(size);
	}

	@Override
	protected void addRoomTypes() {
		addRoomType(RoomDiner.class);
		addRoomType(RoomStore.class);
	}
	

}
