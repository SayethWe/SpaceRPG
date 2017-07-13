package sineSection.spaceRPG.world.item.aura;

public abstract class AbilityAura extends Aura {

	protected final String effectDesc;
	
	public AbilityAura(String effectDesc) {
		this.effectDesc = effectDesc;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("Aura: ");
		result.append(effectDesc);
		return result.toString();
	}

}
