package sineSection.spaceRPG.world.items.effects;

/**
 * a native stat-changing effect in place as long as an item is held.
 * @author geekman9097
 *
 */
public class Aura implements Passive{
	private final String stat;
	private final int effectAmt;
	
	public Aura(String stat, int effectAmt) {
		this.stat = stat;
		this.effectAmt = effectAmt;
	}
	
	public int getAmount() {
		return effectAmt;
	}
	
	public String getStat() {
		return stat;
	}
}
