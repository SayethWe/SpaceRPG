package sineSection.spaceRPG.world.item.loader;

import sineSection.spaceRPG.script.Script;

public class ItemAttribute {

	public enum ItemAttribType {
		INIT_FUNC("init"), USE_FUNC("use");
		
		private final String xmlElementName;
		private ItemAttribType(String xmlElementName) {
			this.xmlElementName = xmlElementName;
		}
		
		public String getXmlElementName() {
			return xmlElementName;
		}
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
