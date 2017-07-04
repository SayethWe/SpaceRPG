package sineSection.spaceRPG.world.item.loader;

import java.util.ArrayList;

public class ItemReference {
	
	public static final ArrayList<ItemReference> itemRefs = new ArrayList<ItemReference>();
	
	private String name, description;
	private ArrayList<ItemAttribute> attribs;
	
	public ItemReference(String name, String description, ArrayList<ItemAttribute> attribs) {
		this.name = name;
		this.description = description;
		this.attribs = attribs;
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
		return null;
	}
	
	public static void registerItemRef(ItemReference ref) {
		itemRefs.add(ref);
	}
}
