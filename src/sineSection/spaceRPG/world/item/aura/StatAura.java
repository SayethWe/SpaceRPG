package sineSection.spaceRPG.world.item.aura;

import sineSection.spaceRPG.character.Creature;

public class StatAura extends Aura {
	
	private final String stat;
	private final int effectAmt;
	
	public StatAura(String stat, int effectAmt) {
		this.stat = stat;
		this.effectAmt = effectAmt;
	}

	public int getAmount() {
		return effectAmt;
	}

	public String getStat() {
		return stat;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Aura: ");
		result.append("Affects ");
		result.append(stat);
		result.append(" by ");
		if (effectAmt > 0)
			result.append("+");
		result.append(effectAmt);
		return result.toString();
	}

	public void affect(Creature creature) {
		
	}

	public void unaffect(Creature creature) {
		
	}

}
