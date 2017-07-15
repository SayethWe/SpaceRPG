package sineSection.superSerial.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sineSection.superSerial.SSSerialization;
import sineSection.superSerial.SSType.SSContainerType;
import sineSection.superSerial.SSType.SSDataType;

public class SSDatabase extends SSContainer {

	public static final String FILE_EXTENTION = ".ssd";
	public static final byte[] HEADER = "SSDB".getBytes();
	public static final short VERSION = 0x0010;
	private short objectCount;
	public List<SSObject> objects = new ArrayList<SSObject>();

	private SSDatabase() {
		super(SSContainerType.DATABASE);
		this.size += (SSDataType.SHORT.getSize() * 2) + HEADER.length;
	}

	public SSDatabase(String name) {
		this();
		setName(name);
	}

	public int writeBytes(byte[] dest, int pointer) {
		pointer = SSSerialization.write(dest, pointer, HEADER);
		pointer = SSSerialization.write(dest, pointer, VERSION);
		pointer = SSSerialization.write(dest, pointer, containerType.getType());
		pointer = SSSerialization.write(dest, pointer, nameLength);
		pointer = SSSerialization.write(dest, pointer, name);
		pointer = SSSerialization.write(dest, pointer, size);
		
		pointer = SSSerialization.write(dest, pointer, objectCount);
		for (SSObject object : objects) {
			pointer = object.writeBytes(dest, pointer);
		}
		return pointer;
	}

	public void addObject(SSObject object) {
		objects.add(object);
		size += object.getSize();
		objectCount = (short) objects.size();
	}
	
	public SSObject getObject(String name) {
		for (SSObject object : objects)
			if(object.getName().equals(name)) return object;
		System.err.println("Object does not exist: " + name);
		return null;
	}

	public static SSDatabase Deserialize(byte[] data) {
		int pointer = 0;
		if(!isValidData(data)) {
			System.err.println("[ERROR]Invalid data!");
			return null;
		}
		SSDatabase result = new SSDatabase();
		pointer += HEADER.length;
		short version = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		if(version != VERSION) {
			System.err.println("[ERROR]Invalid SSDatabase version! Read: " + version + ", Expected: " + VERSION);
			return null;
		}
		byte containerType = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();	
		assert(containerType == result.containerType.getType());
		result.nameLength = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.setName(SSSerialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		result.objectCount = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		for(int i = 0; i < result.objectCount; i++) {
			SSObject object = SSObject.Deserialize(data, pointer);
			result.objects.add(object);
			pointer += object.getSize();
		}
		return result;
	}
	
	public static boolean isValidData(byte[] data) {
		byte[] header = new byte[HEADER.length];
		SSSerialization.readBytes(data, 0, header);
		return Arrays.equals(header, HEADER);
	}
	
	public String toString() {
		String out = new String();
		out += "DATABASE:\n";
		out += "\tName: " + getName() + "\n";
		out += "\tSize: " + getSize() + " bytes\n";
		out += "\n";
		out += "\tOBJECTS: " + objectCount + "\n";
		for(SSObject o : objects) {
			out += "\t\tOBJECT:\n";
			out += "\t\tName: " + o.getName() + "\n";
			out += "\t\tSize: " + o.getSize() + " bytes\n";
		}
		return out;
	}
}
