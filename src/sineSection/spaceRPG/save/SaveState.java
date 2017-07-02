package sineSection.spaceRPG.save;

import java.io.Serializable;

public class SaveState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8196755643718748825L;
	private int randomSeed;
//	private MapState world;
//	private PlayerState player;

	public SaveState() {
		// TODO Auto-generated constructor stub
	}

	public int getSeed() {
		return randomSeed;
	}

}
