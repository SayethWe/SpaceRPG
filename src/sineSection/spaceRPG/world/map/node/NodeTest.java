package sineSection.spaceRPG.world.map.node;

import sineSection.spaceRPG.world.map.room.RoomTest;

public class NodeTest extends Node {

	public NodeTest() {
	}

	@Override
	protected void addRoomTypes() {
		addRoomType(RoomTest.class);
	}

}
