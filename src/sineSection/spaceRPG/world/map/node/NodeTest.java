package sineSection.spaceRPG.world.map.node;

import sineSection.spaceRPG.world.map.room.RoomTest;
import sineSection.spaceRPG.world.map.room.RoomTestDanger;

public class NodeTest extends Node {

	public NodeTest() {
	}

	@Override
	protected void addRoomTypes() {
		addRoomType(RoomTest.class);
//		addRoomType(RoomTestDanger.class);
	}

}
