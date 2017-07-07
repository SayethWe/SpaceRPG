package sineSection.spaceRPG.world.map.node;

import sineSection.spaceRPG.world.map.room.RoomTest;
import sineSection.spaceRPG.world.map.room.RoomTestDanger;

public class NodeTest extends Node {
	private static final int NORMAL_WEIGHT = 4;

	public NodeTest() {
	}

	@Override
	protected void addRoomTypes() {
		addRoomType(RoomTest.class, NORMAL_WEIGHT);
		addRoomType(RoomTestDanger.class);
	}

}
