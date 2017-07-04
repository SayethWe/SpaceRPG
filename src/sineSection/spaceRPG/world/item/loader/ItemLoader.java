package sineSection.spaceRPG.world.item.loader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.script.ItemScript;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.loader.ItemAttribute;
import sineSection.spaceRPG.world.item.loader.ItemAttribute.ItemAttribType;

public class ItemLoader {

	public static ArrayList<Item> items;

	public static boolean loadItemsFrom(String xmlSheet) {
		int errorFlag = 0;

		items = new ArrayList<Item>();

		URL url = SpaceRPG.class.getResource("/data/" + xmlSheet + ".xml");
		try {
			File f = new File(url.toURI());
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
			print(ItemLoader.class, "Loading Item: ");
			Element e = (Element) n;

			String name = e.getElementsByTagName("name").item(0).getTextContent();
			printLn(null, name);
			String desc = e.getElementsByTagName("desc").item(0).getTextContent();

			ArrayList<ItemAttribute> attribs = new ArrayList<ItemAttribute>();

			NodeList initList = e.getElementsByTagName("init");
			if (initList.getLength() > 0)
				attribs.addAll(loadItemAttribs(ItemAttribType.INIT_FUNC, initList));
			NodeList effectsList = e.getElementsByTagName("effects");
			if (effectsList.getLength() > 0)
				attribs.addAll(loadItemAttribs(ItemAttribType.EFFECT_FUNC, effectsList));
			NodeList useList = e.getElementsByTagName("use");
			if (useList.getLength() > 0)
				attribs.addAll(loadItemAttribs(ItemAttribType.USE_FUNC, useList));

			ItemReference.registerItemRef(new ItemReference(name, desc, attribs));
		} else {
			printErrLn(ItemLoader.class, "loadItemFromNode: Node is not correct type! Expected: " + Node.ELEMENT_NODE + ", Received: " + n.getNodeType());
			errorFlag += 1;
		}
		return errorFlag;
	}

	private static ArrayList<ItemAttribute> loadItemAttribs(ItemAttribType type, NodeList attribList) {
		ArrayList<ItemAttribute> attribs = new ArrayList<ItemAttribute>();
		for (int i = 0; i < attribList.getLength(); i++) {
			Node n = attribList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				ItemScript script = new ItemScript(e.getTextContent());
				script.parse();
				attribs.add(new ItemAttribute(type, script));
			}
		}
		return attribs;
	}

	private static void print(Class<?> c, String msg) {
		System.out.print((c == null ? "" : "[" + c.getName() + "] ") + msg);
	}

	private static void printLn(Class<?> c, String msg) {
		print(c, msg + "\n");
	}

	private static void printErr(Class<?> c, String error) {
		System.err.print((c == null ? "" : "[" + c.getName() + "] ") + error);
	}

	private static void printErrLn(Class<?> c, String error) {
		printErr(c, error + "\n");
	}
}
