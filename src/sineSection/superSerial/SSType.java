package sineSection.superSerial;

public class SSType {
	public static enum SSContainerType {
		UNKNOWN((byte) 0),
		FIELD((byte) 1),
		STRING((byte) 3),
		ARRAY((byte) 4),
		OBJECT((byte) 5),
		DATABASE((byte) 6);
		
		private final byte type;
		
		private SSContainerType(byte type) {
			this.type = type;
		}
		
		public byte getType() {
			return type;
		}
		
		public static SSContainerType getTypeFromByte(byte b) {
			for(SSContainerType ct : SSContainerType.values()) {
				if(ct.getType() == b) {
					return ct;
				}
			}
			return UNKNOWN;
		}
	}
	
	public static enum SSDataType {
		UNKNOWN((byte) 0, 0),
		BYTE((byte) 1, Byte.BYTES),
		SHORT((byte) 2, Short.BYTES),
		CHARACTER((byte) 3, Character.BYTES),
		INTEGER((byte) 4, Integer.BYTES),
		LONG((byte) 5, Long.BYTES),
		FLOAT((byte) 6, Float.BYTES),
		DOUBLE((byte) 7, Double.BYTES),
		BOOLEAN((byte) 8, 1);
		
		private final byte type;
		private final int size;
		
		private SSDataType(byte type, int size) {
			this.type = type;
			this.size = size;
		}
		
		public byte getType() {
			return type;
		}
		
		public int getSize() {
			if(this == UNKNOWN)
				assert(false);
			return size;
		}
		
		public static SSDataType getTypeFromByte(byte b) {
			for(SSDataType ct : SSDataType.values()) {
				if(ct.getType() == b) {
					return ct;
				}
			}
			return UNKNOWN;
		}
	}

}
