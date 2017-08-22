package sineSection.spaceRPG.character;

import java.util.HashMap;
import java.util.function.BiFunction;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.WorldPos;

/**
 * @Author William Black A class that stores the stats and inventory of a player
 *         character
 */
public class Player extends Creature {
	private static final int BASE_MAX_HEALTH = 20;
	private static final int INVENTORY_SIZE = 20;

	public final static String INTELLECT = "Intelligence";
	public final static String POWER = "Strength";
	public final static String RESISTANCE = "Defense";

	private static final int RESISTANCE_MAX_POSSIBLE = 20;
	private static final int INTELLECT_MAX_POSSIBLE = 10;
	private static final int POWER_MAX_POSSIBLE = 10;

	private static final int RESISTANCE_MIN = 0;
	private static final int INTELLECT_MIN = 1;
	private static final int POWER_MIN = 1;

	
	private WorldPos location;
	private Direction lastDirectionTraveled;
	private boolean hasHud = false;
	// private Map<String, ComfortStat> comfortStats;
	// TODO add in 'comfort' like warmth/hunger/thirst, and environment checks
	// for damage

	/**
	 * Initializes 'inventory' and 'stats', adds all the 'stats' into the
	 * appropriate HashMap
	 * 
	 * @Author William Black
	 */
	public Player(String name, WorldPos startingPos) {
		super(name, BASE_MAX_HEALTH, INVENTORY_SIZE);
		location = startingPos;
		
		addStat(RESISTANCE, new Stat(RESISTANCE_MIN, (int) (Math.random() * RESISTANCE_MAX_POSSIBLE + 1)));
		addStat(INTELLECT, new Stat(INTELLECT_MIN, (int) (Math.random() * INTELLECT_MAX_POSSIBLE + 1)));
		addStat(POWER, new Stat(POWER_MIN, (int) (Math.random() * POWER_MAX_POSSIBLE + 1)));
	}

	public boolean damage(int amt) {
		return super.damage(amt - getStatVal(RESISTANCE));
	}

	public void setHasHud(boolean hasHud) {
		if (hasHud != this.hasHud) {
			this.hasHud = hasHud;
			SpaceRPG.getMaster().getGui().updateHud();
		}
	}

	public boolean hasHud() {
		return hasHud;
	}

	public WorldPos getPos() {
		return location;
	}

	public void move(Direction dir) {
		SpaceRPG.getMaster().writeToGui(getName() + " moved " + dir.getCall());
		lastDirectionTraveled = dir;
		SpaceRPG.getMaster().getWorld().getRoomAt(location).onRoomExit(this);
		location = new WorldPos(location.getNode(), dir.affectPos(location.getRoom()));
		SpaceRPG.getMaster().getWorld().getRoomAt(location).onRoomEnter(this);
		SpaceRPG.getMaster().writeToGui(location.getRoom());
	}

	public Direction getLastDir() {
		return lastDirectionTraveled;
	}

	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = super.getScriptVars();
		ret.put("location", location);
		return ret;
	}

	public HashMap<String, BiFunction<?, ?, ?>> getScriptBiFunctions() {
		HashMap<String, BiFunction<?, ?, ?>> ret = super.getScriptBiFunctions();
		return ret;
	}
}