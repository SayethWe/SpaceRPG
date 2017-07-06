package sineSection.spaceRPG.world.item.loader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.script.Script;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.ItemReference;
import sineSection.spaceRPG.world.item.loader.ItemAttribute.ItemAttribType;
import sineSection.util.LogWriter;

public class ItemLoader {

	public static ArrayList<Item> items;

	public static boolean loadItemsFrom(String xmlSheet) {
		int errorFlag = 0;

		items = new ArrayList<Item>();

		InputStream f = SpaceRPG.class.getResourceAsStream("/data/" + xmlSheet + ".xml");
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			Element base = doc.getDocumentElement();
			base.normalize();

			NodeList itemList = doc.getElementsByTagName("item");

			for (int i = 0; i < itemList.getLength(); i++) {
				errorFlag += loadItemFromNode(itemList.item(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
			errorFlag += 1;
		}
		return errorFlag == 0;
	}

	private static int loadItemFromNode(Node n) {
		int errorFlag = 0;
		if (n.getNodeType() == Node.ELEMENT_NODE) {

			Element e = (Element) n;

			String name = e.getElementsByTagName("name").item(0).getTextContent();
			String desc = e.getElementsByTagName("desc").item(0).getTextContent();
			String scriptLang = e.getAttribute("scriptLang");
			ArrayList<ItemAttribute> attribs = new ArrayList<ItemAttribute>();

			for (ItemAttribType type : ItemAttribType.values()) {
				NodeList typeList = e.getElementsByTagName(type.getXmlElementName());
				if (typeList.getLength() > 0)
					attribs.addAll(loadItemAttrib(type, typeList));
			}
			LogWriter.print("Loading Item: " + name);
			ItemReference.registerItemRef(new ItemReference(name, desc, attribs, scriptLang));
		} else {
			LogWriter.printErr("loadItemFromNode(Node n): Node is not correct type! Expected: " + Node.ELEMENT_NODE + ", Received: " + n.getNodeType());
			errorFlag += 1;
		}
		return errorFlag;
	}

	private static ArrayList<ItemAttribute> loadItemAttrib(ItemAttribType type, NodeList attribList) {
		ArrayList<ItemAttribute> attribs = new ArrayList<ItemAttribute>();
		for (int i = 0; i < attribList.getLength(); i++) {
			Node n = attribList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				Script script = new Script(e.getTextContent());
				attribs.add(new ItemAttribute(type, script));
			}
		}
		return attribs;
	}
}
