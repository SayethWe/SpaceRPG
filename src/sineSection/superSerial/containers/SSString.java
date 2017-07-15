package sineSection.superSerial.containers;

import sineSection.superSerial.SSSerialization;
import sineSection.superSerial.SSType.SSContainerType;
import sineSection.superSerial.SSType.SSDataType;

public class SSString extends SSContainer {

	private short length = 0;
	private char[] data;
	
	private SSString() {
		super(SSContainerType.STRING);
		this.size += SSDataType.SHORT.getSize();
	}
	
	public SSString(String name, String data) {
		this();
		setName(name);
		assert(data.length() < Short.MAX_VALUE);
		this.data = data.toCharArray();
		this.length = (short) this.data.length;
		this.size += (this.length * SSDataType.CHARACTER.getSize());
	}
	
	public int writeBytes(byte[] dest, int pointer) {
		pointer = SSSerialization.write(dest, pointer, containerType.getType());
		pointer = SSSerialization.write(dest, pointer, nameLength);
		pointer = SSSerialization.write(dest, pointer, name);
		pointer = SSSerialization.write(dest, pointer, size);
		pointer = SSSerialization.write(dest, pointer, length);
		pointer = SSSerialization.write(dest, pointer, data);
		return pointer;
	}

	public String getString() {
		return new String(data, 0, length);
	}

	public static SSString Deserialize(byte[] data, int pointer) {
		SSString result = new SSString();
		byte containerType = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();
		assert (containerType == result.containerType.getType());
		result.nameLength = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.setName(SSSerialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		result.length = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.data = new char[result.length];
		SSSerialization.readCharacters(data, pointer, result.data);
		pointer += result.length * SSDataType.CHARACTER.getSize();
		return result;
	}
	
}
