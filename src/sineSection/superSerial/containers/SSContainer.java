package sineSection.superSerial.containers;

import sineSection.superSerial.SSType.SSContainerType;
import sineSection.superSerial.SSType.SSDataType;

public abstract class SSContainer {
	
	protected final SSContainerType containerType;
	protected short nameLength;
	protected byte[] name;
	protected int size;
	
	public SSContainer(SSContainerType containerType) {
		this.containerType = containerType;
		this.size += SSDataType.BYTE.getSize() + SSDataType.SHORT.getSize() + SSDataType.INTEGER.getSize();
	}
	
	public void setName(String name) {
		assert(name.length() < Short.MAX_VALUE);
		if(this.name != null)
			size -= this.name.length;
		this.name = name.getBytes();
		this.nameLength = (short) this.name.length;
		size += this.name.length;
	}
	
	public String getName() {
		return new String(name, 0, nameLength);
	}
	
	public int getSize() {
		return size;
	}
	
	public abstract int writeBytes(byte[] dest, int pointer);
}
