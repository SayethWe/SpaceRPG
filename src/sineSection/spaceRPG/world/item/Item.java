package sineSection.spaceRPG.world.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.script.Script;
import sineSection.spaceRPG.script.Scriptable;
import sineSection.spaceRPG.script.TriConsumer;
import sineSection.spaceRPG.script.TriFunction;
import sineSection.spaceRPG.world.item.ItemAttribute.ItemAttribType;
import sineSection.spaceRPG.world.item.aura.AbilityAura;
import sineSection.spaceRPG.world.item.aura.Aura;
import sineSection.spaceRPG.world.item.aura.StatAura;
import sineSection.util.LogWriter;
import sineSection.util.ScriptHelper;
import sineSection.util.Utils;

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
		if (this.sEng == null) {
			System.err.println(ref.getScriptLanguage() + " is not a valid language name!");
		}
		init();
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
		} catch (Exception e) {
			if (e instanceof ScriptException)
				LogWriter.printErr(getName() + ": SCRIPT EXCEPTION!\n");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * use this item to get the active effect.
	 * 
	 * @return if the item was used on the target(s) successfully
	 */
	public boolean use(Creature user, List<Creature> targets) {
		if (user == null) {
			System.err.println("Item.use(): Argument \"user\" must not be null!");
			return false;
		}
		user.addAuras(auras);
		try {
			for (ItemAttribute attrib : this.ref.getAttribs()) {
				if (attrib.getType() == ItemAttribType.USE_FUNC) {
					Script s = attrib.getScript();
					s.addScriptable(this);
					sEng.put("user", user);
					sEng.put("targets", targets);
					s.run(sEng);
					return true;
				}
			}
		} catch (Exception e) {
			if (e instanceof ScriptException)
				LogWriter.printErr(getName() + ": SCRIPT EXCEPTION!\n" + e.toString());
			else
				e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ease of use function to turn args into creatures
	 * @param user
	 * @param args
	 * @return
	 */
	public boolean use(Creature user, String[] args) {
		List<Creature> targets = new ArrayList<>();
		for(String arg : args) {
			targets.add(SpaceRPG.getMaster().getWorld().getRoomAt(user.getPos()).getCreature(arg));
		}
		return(use(user, targets));
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

	/**
	 * use this item to get the active effect.
	 * 
	 * @return if the item was used successfully
	 */
	public boolean use(Creature user) {
		return use(user, new ArrayList<>());
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public void addStatAura(String statName, int value) {
		auras.add(new StatAura(statName, value));
	}
	
	public void addAbilityAura(String auraDesc, Consumer<Creature> affect, Consumer<Creature> unaffect) {
		auras.add(new AbilityAura(auraDesc) {
			public void unaffect(Creature creature) {
				affect.accept(creature);
			}
			
			public void affect(Creature creature) {
				unaffect.accept(creature);
			}
		});
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
		String s = ScriptHelper.replaceAllFormattersInString(ref.getName());
		if (ScriptHelper.hasVarsInString(sEng, s)) {
			return ScriptHelper.putVarsInString(sEng, s);
		} else {
			return ScriptHelper.removeAllVarsFromString(s);
		}
	}
	
	public String getAlias() {
		return ref.getAlias();
	}

	public String getDescription() {
		String s = ScriptHelper.replaceAllFormattersInString(ref.getDescription());
		if (ScriptHelper.hasVarsInString(sEng, s)) {
			return ScriptHelper.putVarsInString(sEng, s);
		} else {
			return ScriptHelper.removeAllVarsFromString(s);
		}
	}

	public void used() {
		if (uses > NO_DURABILITY) {
			uses--;
			canUse = true;
		} else {
			canUse = false;
		}
	}

	public boolean canUse() {
		return canUse;
	}

	public int getMinTargets() {
		return 0;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("Item:");
		result.append("\n").append(getName()).append("\n").append("Alias: ").append(getAlias()).append("\n").append(getDescription()).append("\n");
		if (auras.size() > 0) {
			result.append("Passives:").append("\n");
			auras.forEach((aura) -> result.append(aura.toString() + "\n"));
		}
		return result.toString();
	}

	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("uses", uses);
		return ret;
	}

	public HashMap<String, Supplier<?>> getScriptSuppliers() {
		HashMap<String, Supplier<?>> ret = new HashMap<>();
		ret.put("getAuras", (Supplier<List<Aura>>) this::getAuras);
		ret.put("getName", (Supplier<String>) this::getName);
		ret.put("getDescription", (Supplier<String>) this::getDescription);
		ret.put("hasAuraEffect", (Supplier<Boolean>) this::hasAuraEffect);
		ret.put("canUse", (Supplier<Boolean>) this::canUse);
		ret.put("toString", (Supplier<Boolean>) this::canUse);
		return ret;
	}

	public HashMap<String, Consumer<?>> getScriptConsumers() {
		HashMap<String, Consumer<?>> ret = new HashMap<>();
		ret.put("setUses", (Consumer<Integer>) this::setUses);
		ret.put("log", (Consumer<String>) LogWriter::print);
		ret.put("logErr", (Consumer<String>) LogWriter::printErr);
		ret.put("write", (Consumer<? extends Object>) SpaceRPG.getMaster().getGui()::write);
		ret.put("addAura", (Consumer<Aura>) this::addAura);
		return ret;
	}

	public HashMap<String, Runnable> getScriptRunnables() {
		HashMap<String, Runnable> ret = new HashMap<>();
		ret.put("used", this::used);
		return ret;
	}

	public HashMap<String, BiConsumer<?, ?>> getScriptBiConsumers() {
		HashMap<String, BiConsumer<?, ?>> ret = new HashMap<>();
		ret.put("addStatAura", (BiConsumer<String, Integer>) this::addStatAura);
		return ret;
	}
	
	public HashMap<String, TriConsumer<?, ?, ?>> getScriptTriConsumers() {
		HashMap<String, TriConsumer<?, ?, ?>> ret = new HashMap<>();
		ret.put("addAbilityAura", (TriConsumer<String, Consumer<Creature>, Consumer<Creature>>) this::addAbilityAura);
		return ret;
	}

	public HashMap<String, Function<?, ?>> getScriptFunctions() {
		HashMap<String, Function<?, ?>> ret = new HashMap<>();
		ret.put("getListOfNamesFromCreatures", (Function<List<Creature>, List<String>>) Utils::getListOfNamesFromCreatures);
		return ret;
	}

	public HashMap<String, BiFunction<?, ?, ?>> getScriptBiFunctions() {
		return null;
	}
	
	// JEsus chrisT the ARGS and genERICS
	public HashMap<String, TriFunction<?, ?, ?, ?>> getScriptTriFunctions() {
		HashMap<String, TriFunction<?, ?, ?, ?>> ret = new HashMap<>();
		ret.put("showSelectionDialouge", (TriFunction<Integer, String, List<String>, Boolean>) SpaceRPG.getMaster().getGui()::showSelectionDialogue);
		return ret;
	}
}
