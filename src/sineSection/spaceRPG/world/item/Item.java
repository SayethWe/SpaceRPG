package sineSection.spaceRPG.world.item;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.script.Script;
import sineSection.spaceRPG.script.Scriptable;
import sineSection.spaceRPG.world.item.effect.Aura;
import sineSection.spaceRPG.world.item.loader.ItemAttribute;
import sineSection.spaceRPG.world.item.loader.ItemAttribute.ItemAttribType;
import sineSection.util.ScriptHelper;

/**
 * Class for Item
 * 
 * @Author William Black
 */
public class Item implements Scriptable {
	private final static int NO_DURABILITY = 0;

	private final ItemReference ref;

	private int uses = 0;
	private boolean canUse = true;

	private ScriptEngine sEng;

	private List<Aura> auras;

	/**
	 * create an item instance of <code>itemRef</code>
	 * 
	 * @param itemRef
	 */
	public Item(ItemReference itemRef) {
		this.ref = itemRef;
		this.auras = new ArrayList<Aura>();
		ScriptEngineManager enMgr = new ScriptEngineManager();
		this.sEng = enMgr.getEngineByName(ref.getScriptLanguage());
	}

	public boolean init() {
		try {
			for (ItemAttribute attrib : this.ref.getAttribs()) {
				if (attrib.getType() == ItemAttribType.INIT_FUNC) {
					Script s = attrib.getScript();
					s.addScriptable(this);
					s.run(sEng);
					return true;
				}
			}
		} catch (ScriptException e) {
			System.err.println(getName() + ": SCRIPT EXCEPTION!\n" + e.toString());
		}
		return false;
	}

	public boolean effect(Creature user) {
		try {
			for (ItemAttribute attrib : this.ref.getAttribs()) {
				if (attrib.getType() == ItemAttribType.EFFECT_FUNC) {
					if (user == null) {
						System.err.println("Item.use(): Argument \"User\" must not be null!");
						return false;
					}
					Script s = attrib.getScript();
					s.addScriptable(this);
					s.addScriptable(user);
					s.run(sEng);
					return true;
				}
			}
		} catch (ScriptException e) {
			System.err.println(getName() + ": SCRIPT EXCEPTION!\n" + e.toString());
		}
		return false;
	}

	/**
	 * use this item to get the active effect.
	 * 
	 * @return if the item was used on the target(s) successfully
	 */
	public boolean use(Creature user, ArrayList<Creature> targets) {
		try {
			for (ItemAttribute attrib : this.ref.getAttribs()) {
				if (attrib.getType() == ItemAttribType.USE_FUNC) {
					if (user == null) {
						System.err.println("Item.use(): Argument \"User\" must not be null!");
						return false;
					}
					
					Script s = attrib.getScript();
					s.addScriptable(this);
					s.addScriptable(user);
					targets.forEach((target)->s.addScriptable(target));
					s.run(sEng);
					return true;
				}
			}
		} catch (ScriptException e) {
			System.err.println(getName() + ": SCRIPT EXCEPTION!\n" + e.toString());
		}
		return false;
	}
	
	/**
	 * use this item to get the active effect.
	 * 
	 * @return if the item was used on the target(s) successfully
	 */
	public boolean use(Creature user, Creature target) {
		ArrayList<Creature> targets = new ArrayList<>();
		targets.add(target);
		return use(user, targets);
	}

	public void addAura(Aura aura) {
		auras.add(aura);
	}

	public List<Aura> getAuras() {
		return auras;
	}

	public boolean hasAuraEffect() {
		return (auras.size() > 0);
	}

	public String getName() {
		if(ScriptHelper.hasVarsInString(sEng, ref.getName())) {
			return ScriptHelper.putVarsInString(sEng, ref.getName());
		} else {
			return ScriptHelper.removeAllVarsFromString(ref.getName());
		}
	}
	
	public String getDescription() {
		if(ScriptHelper.hasVarsInString(sEng, ref.getDescription())) {
			return ScriptHelper.putVarsInString(sEng, ref.getDescription());
		} else {
			return ScriptHelper.removeAllVarsFromString(ref.getDescription());
		}
	}

	protected void used() {
		if (uses > NO_DURABILITY) {
			uses--;
			canUse = false;
		} else {
			canUse = true;
		}
	}

	public boolean canUse() {
		return canUse;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Item:");
		result.append("\n").append(getName()).append("\n").append(getDescription()).append("\n").append("Passives:").append("\n");
		auras.forEach((aura) -> result.append(aura.toString() + "\n"));
		return result.toString();
	}

	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("uses", uses);
		ret.put("canUse", canUse);
		ret.put("auras", auras);
		return ret;
	}

	public ArrayList<Method> getScriptFunctions() {
		ArrayList<Method> ret = new ArrayList<>();
		try {
			ret.add(getClass().getMethod("addAura", new Class[] {Aura.class}));
			ret.add(getClass().getMethod("getAuras", new Class[] {}));
			ret.add(getClass().getMethod("hasAuraEffect", new Class[] {}));
			ret.add(getClass().getMethod("removeItem", new Class[] {String.class}));
			ret.add(getClass().getMethod("getName", new Class[] {}));
			ret.add(getClass().getMethod("getDescription", new Class[] {}));
			ret.add(getClass().getMethod("used", new Class[] {}));
			ret.add(getClass().getMethod("canUse", new Class[] {}));
			ret.add(getClass().getMethod("toString", new Class[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
