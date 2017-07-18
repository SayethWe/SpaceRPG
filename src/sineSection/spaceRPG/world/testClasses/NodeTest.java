package sineSection.spaceRPG.world.testClasses;

import sineSection.spaceRPG.world.map.node.Node;

public class NodeTest extends Node {
	private static final int NORMAL_WEIGHT = 4;

	public NodeTest() {
	}

	@Override
	protected void addRoomTypes() {
		addRoomType(RoomTest.class, NORMAL_WEIGHT);
		addRoomType(RoomTestDanger.class);
	}

	@Override
	protected void addDoorTypes() {
		addDoorType(DoorwayTest.class);
	}

}
