package sineSection.spaceRPG.world.item.loader;

import sineSection.spaceRPG.script.Script;

public class ItemAttribute {

	public enum ItemAttribType {
		EFFECT_FUNC, USE_FUNC, INIT_FUNC
	}

	private ItemAttribType type;
	private Script script;

	public ItemAttribute(ItemAttribType type, Script script) {
		this.type = type;
		this.script = script;
	}

	public ItemAttribType getType() {
		return type;
	}
	
	public Script getScript() {
		return script;
	}

}
