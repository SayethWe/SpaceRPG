package sineSection.superSerial.containers;

import sineSection.superSerial.SSSerialization;
import sineSection.superSerial.SSType;
import sineSection.superSerial.SSType.SSContainerType;
import sineSection.superSerial.SSType.SSDataType;

public class SSArray extends SSContainer {

	private SSDataType dataType;
	private int count;
	
	private byte[] byteData;
	private short[] shortData;
	private char[] characterData;
	private int[] integerData;
	private long[] longData;
	private float[] floatData;
	private double[] doubleData;
	private boolean[] booleanData;

	private SSArray() {
		super(SSContainerType.ARRAY);
		this.size += SSDataType.BYTE.getSize() + SSDataType.INTEGER.getSize();
	}
	
	private SSArray(String name, SSDataType dataType) {
		this();
		setName(name);
		this.dataType = dataType;
	}

	public int writeBytes(byte[] dest, int pointer) {
		pointer = SSSerialization.write(dest, pointer, containerType.getType());
		pointer = SSSerialization.write(dest, pointer, nameLength);
		pointer = SSSerialization.write(dest, pointer, name);
		pointer = SSSerialization.write(dest, pointer, size);
		pointer = SSSerialization.write(dest, pointer, dataType.getType());
		pointer = SSSerialization.write(dest, pointer, count);
		switch (dataType) {
		case BYTE:
			pointer = SSSerialization.write(dest, pointer, byteData);
			break;
		case SHORT:
			pointer = SSSerialization.write(dest, pointer, shortData);
			break;
		case CHARACTER:
			pointer = SSSerialization.write(dest, pointer, characterData);
			break;
		case INTEGER:
			pointer = SSSerialization.write(dest, pointer, integerData);
			break;
		case LONG:
			pointer = SSSerialization.write(dest, pointer, longData);
			break;
		case FLOAT:
			pointer = SSSerialization.write(dest, pointer, floatData);
			break;
		case DOUBLE:
			pointer = SSSerialization.write(dest, pointer, doubleData);
			break;
		case BOOLEAN:
			pointer = SSSerialization.write(dest, pointer, booleanData);
			break;
		case UNKNOWN:
			break;
		}
		return pointer;
	}

	private int getArrayLength() {
		switch (dataType) {
		case BYTE:
			return byteData.length;
		case SHORT:
			return shortData.length;
		case CHARACTER:
			return characterData.length;
		case INTEGER:
			return integerData.length;
		case LONG:
			return longData.length;
		case FLOAT:
			return floatData.length;
		case DOUBLE:
			return doubleData.length;
		case BOOLEAN:
			return booleanData.length;
		default:
		case UNKNOWN:
			return 0;
		}
	}
	
	public byte[] getBytes() {
		return byteData;
	}
	
	public short[] getShorts() {
		return shortData;
	}
	
	public char[] getCharacters() {
		return characterData;
	}
	
	public int[] getIntegers() {
		return integerData;
	}
	
	public long[] getLongs() {
		return longData;
	}
	
	public float[] getFloats() {
		return floatData;
	}
	
	public double[] getDoubles() {
		return doubleData;
	}
	
	public boolean[] getBooleans() {
		return booleanData;
	}

	public static SSArray Deserialize(byte[] data, int pointer) {
		SSArray result = new SSArray();
		byte containerType = SSSerialization.readByte(data, pointer);
		pointer += SSDataType.BYTE.getSize();
		assert (containerType == result.containerType.getType());
		result.nameLength = SSSerialization.readShort(data, pointer);
		pointer += SSDataType.SHORT.getSize();
		result.setName(SSSerialization.readString(data, pointer, result.nameLength));
		pointer += result.nameLength;
		result.size = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		result.dataType = SSDataType.getTypeFromByte(SSSerialization.readByte(data, pointer));
		pointer += SSDataType.BYTE.getSize();
		result.count = SSSerialization.readInteger(data, pointer);
		pointer += SSDataType.INTEGER.getSize();
		switch (result.dataType) {
		case BYTE:
			result.byteData = new byte[result.count];
			SSSerialization.readBytes(data, pointer, result.byteData);
			break;
		case SHORT:
			result.shortData = new short[result.count];
			SSSerialization.readShorts(data, pointer, result.shortData);
			break;
		case CHARACTER:
			result.characterData = new char[result.count];
			SSSerialization.readCharacters(data, pointer, result.characterData);
			break;
		case INTEGER:
			result.integerData = new int[result.count];
			SSSerialization.readIntegers(data, pointer, result.integerData);
			break;
		case LONG:
			result.longData = new long[result.count];
			SSSerialization.readLongs(data, pointer, result.longData);
			break;
		case FLOAT:
			result.floatData = new float[result.count];
			SSSerialization.readFloats(data, pointer, result.floatData);
			break;
		case DOUBLE:
			result.doubleData = new double[result.count];
			SSSerialization.readDoubles(data, pointer, result.doubleData);
			break;
		case BOOLEAN:
			result.booleanData = new boolean[result.count];
			SSSerialization.readBooleans(data, pointer, result.booleanData);
			break;
		case UNKNOWN:
			break;
		}
		pointer += result.dataType.getSize() * result.count;
		return result;
	}

	public static SSArray Byte(String name, byte[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.BYTE);
		array.count = data.length;
		array.byteData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Short(String name, short[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.SHORT);
		array.count = data.length;
		array.shortData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Character(String name, char[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.CHARACTER);
		array.count = data.length;
		array.characterData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Integer(String name, int[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.INTEGER);
		array.count = data.length;
		array.integerData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Long(String name, long[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.LONG);
		array.count = data.length;
		array.longData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Float(String name, float[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.FLOAT);
		array.count = data.length;
		array.floatData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Double(String name, double[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.DOUBLE);
		array.count = data.length;
		array.doubleData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

	public static SSArray Boolean(String name, boolean[] data) {
		SSArray array = new SSArray(name, SSType.SSDataType.BOOLEAN);
		array.count = data.length;
		array.booleanData = data;
		array.size += array.getArrayLength() * array.dataType.getSize();
		return array;
	}

}
