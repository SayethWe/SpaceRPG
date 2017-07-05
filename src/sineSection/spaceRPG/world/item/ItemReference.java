package sineSection.spaceRPG.world.item;

import java.util.ArrayList;

import sineSection.spaceRPG.world.item.loader.ItemAttribute;

public class ItemReference {
	
	public static final ArrayList<ItemReference> itemRefs = new ArrayList<ItemReference>();
	
	private final String name, description, scriptLang;
	private final ArrayList<ItemAttribute> attribs;
	
	public ItemReference(String name, String description, ArrayList<ItemAttribute> attribs, String scriptLang) {
		this.name = name;
		this.description = description;
		this.attribs = attribs;
		this.scriptLang = scriptLang;
	}
	
	public String getName() {
		return name;
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
	
	public static void registerItemRef(ItemReference ref) {
		itemRefs.add(ref);
	}

	public String getScriptLanguage() {
		return scriptLang;
	}
}