package sineSection.spaceRPG.world.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sineSection.util.LogWriter;

public class ItemReference {

	public static final Map<String, ItemReference> itemRefs = new HashMap<String, ItemReference>();

	private final String referenceID, name, alias, description, scriptLang;
	private final ArrayList<ItemAttribute> attribs;

	public ItemReference(String referenceID, String name, String description, ArrayList<ItemAttribute> attribs, String scriptLang) {
		this.referenceID = referenceID;
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
		if(!itemRefs.containsKey(ref.referenceID))
			itemRefs.put(ref.referenceID, ref);
		else {
			String err = "Duplicate item reference ID!\nNew: " + ref.referenceID + " (" + ref.name + "), Existing: (" + itemRefs.get(ref.referenceID).name + ")";
			LogWriter.printErr(err);
			System.exit(-1);
		}
	}

	@Deprecated
	public static ItemReference getFromName(String refName) {
		for(ItemReference ref : itemRefs.values()) {
			if(ref.getName().equalsIgnoreCase(refName))
				return ref;
		}
		return null;
	}
	
	public static ItemReference getFromItemReferenceID(String refID) {
		return itemRefs.get(refID.toUpperCase());
	}
	
}
