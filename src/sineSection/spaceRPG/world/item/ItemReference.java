package sineSection.spaceRPG.world.item;

import java.util.ArrayList;

import sineSection.spaceRPG.world.item.loader.ItemAttribute;

public class ItemReference {

	public static final ArrayList<ItemReference> itemRefs = new ArrayList<ItemReference>();

	private final String name, alias, description, scriptLang;
	private final ArrayList<ItemAttribute> attribs;

	public ItemReference(String name, String description, ArrayList<ItemAttribute> attribs, String scriptLang) {
		this.name = name;
		alias = name.split(" ")[0];
		this.description = description;
		this.attribs = attribs;
		this.scriptLang = scriptLang;
	}

	public String getName() {
		return name;
	}
	
	public String getAlias() {
		return alias;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<ItemAttribute> getAttribs() {
		return attribs;
	}

	public Item createItem() {
		return new Item(this);
	}
	
	public String getScriptLanguage() {
		return scriptLang;
	}
	
	public String toString() {
		return getName();
	}

	public static void registerItemRef(ItemReference ref) {
		itemRefs.add(ref);
	}

	public static ItemReference getFromName(String refName) {
		for(ItemReference ref : itemRefs) {
			if(ref.getName().equalsIgnoreCase(refName))
				return ref;
		}
		return null;
	}
	
}
