package sineSection.spaceRPG.world.map.node;

import sineSection.spaceRPG.world.map.room.RoomCorridor;
import sineSection.spaceRPG.world.map.room.RoomPowerDistributer;
import sineSection.spaceRPG.world.map.room.RoomReactor;

public class NodeIndustrial extends Node {

	public NodeIndustrial(int size) {
		super(size);
	}

	@Override
	protected void addRoomTypes() {
		addRoomType(RoomCorridor.class);
		addRoomType(RoomReactor.class);
		addRoomType(RoomPowerDistributer.class);
	}

}
