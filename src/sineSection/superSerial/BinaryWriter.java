package sineSection.superSerial;

import java.util.ArrayList;
import java.util.List;

public class BinaryWriter {
	
	private List<Byte> buffer;
	
	public BinaryWriter() {
		buffer = new ArrayList<Byte>();
	}
	
	public BinaryWriter(int size) {
		buffer = new ArrayList<Byte>(size);
	}
	
	public byte[] getBuffer() {
		Byte[] array = new Byte[buffer.size()];
		buffer.toArray(array);
		byte[] result = new byte[buffer.size()];
		for(int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}
	
	public void write(byte data) {
		buffer.add(data);
	}
	
	public void write(byte[] data) {
		for(int i = 0; i < data.length; i++)
			buffer.add(data[i]);
	}
	
	public void write(short data) {
		byte[] bytes = new byte[SSType.SSDataType.SHORT.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}
	
	public void write(char data) {
		byte[] bytes = new byte[SSType.SSDataType.CHARACTER.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}
	
	public void write(int data) {
		byte[] bytes = new byte[SSType.SSDataType.INTEGER.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}
	
	public void write(float data) {
		byte[] bytes = new byte[SSType.SSDataType.FLOAT.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}
	
	public void write(long data) {
		byte[] bytes = new byte[SSType.SSDataType.LONG.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}
	
	public void write(double data) {
		byte[] bytes = new byte[SSType.SSDataType.DOUBLE.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}

	public void write(boolean data) {
		byte[] bytes = new byte[SSType.SSDataType.BOOLEAN.getSize()];
		SSSerialization.write(bytes, 0, data);
		write(bytes);
	}

}
