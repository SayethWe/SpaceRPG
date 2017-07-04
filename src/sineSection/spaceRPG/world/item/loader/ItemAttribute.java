package sineSection.spaceRPG.world.item.loader;

import java.util.HashMap;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.script.ItemScript;

public class ItemAttribute {

	public enum ItemAttribType {
		EFFECT_FUNC, USE_FUNC, INIT_FUNC
	}

	private ItemAttribType type;
	private ItemScript script;

	public ItemAttribute(ItemAttribType type, ItemScript script) {
		this.type = type;
		this.script = script;
	}

	public ItemAttribType getType() {
		return type;
	}

	public void runScript(Creature user, Creature[] targets) {
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("user", user);
		vars.put("targets", targets);
		if (script != null)
			script.run(vars);
	}

}
