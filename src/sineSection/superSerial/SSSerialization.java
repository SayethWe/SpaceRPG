package sineSection.superSerial;

import java.nio.ByteBuffer;

import sineSection.superSerial.SSType.SSDataType;

public class SSSerialization {
	
	// WRITING ==================================================================================
	
	public static int write(byte[] dest, int pointer, byte value) {
		assert(dest.length >= pointer + SSDataType.BYTE.getSize());
		dest[pointer++] = value;
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, short value) {
		assert(dest.length >= pointer + SSDataType.SHORT.getSize());
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, char value) {
		assert(dest.length >= pointer + SSDataType.CHARACTER.getSize());
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, int value) {
		assert(dest.length >= pointer + SSDataType.INTEGER.getSize());
		dest[pointer++] = (byte) ((value >> 24) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, long value) {
		assert(dest.length >= pointer + SSDataType.LONG.getSize());
		dest[pointer++] = (byte) ((value >> 56) & 0xff);
		dest[pointer++] = (byte) ((value >> 48) & 0xff);
		dest[pointer++] = (byte) ((value >> 40) & 0xff);
		dest[pointer++] = (byte) ((value >> 32) & 0xff);
		dest[pointer++] = (byte) ((value >> 24) & 0xff);
		dest[pointer++] = (byte) ((value >> 16) & 0xff);
		dest[pointer++] = (byte) ((value >> 8) & 0xff);
		dest[pointer++] = (byte) ((value >> 0) & 0xff);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, float value) {
		assert(dest.length >= pointer + SSDataType.FLOAT.getSize());
		int data = Float.floatToIntBits(value);
		return write(dest, pointer, data);
	}
	
	public static int write(byte[] dest, int pointer, double value) {
		assert(dest.length >= pointer + SSDataType.DOUBLE.getSize());
		long data = Double.doubleToLongBits(value);
		return write(dest, pointer, data);
	}
	
	public static int write(byte[] dest, int pointer, boolean value) {
		assert(dest.length >= pointer + SSDataType.BOOLEAN.getSize());
		dest[pointer++] = (byte) (value ? 1 : 0);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, String string) {
		assert(dest.length >= pointer + SSDataType.SHORT.getSize() + (string.length() * SSDataType.BYTE.getSize()));
		pointer = write(dest, pointer, (short) string.length());
		return write(dest, pointer, string.getBytes());
	}
	
	public static int write(byte[] dest, int pointer, byte[] src) {
		assert(dest.length >= pointer + (SSDataType.BYTE.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, short[] src) {
		assert(dest.length >= pointer + (SSDataType.SHORT.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, char[] src) {
		assert(dest.length >= pointer + (SSDataType.CHARACTER.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, int[] src) {
		assert(dest.length >= pointer + (SSDataType.INTEGER.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, long[] src) {
		assert(dest.length >= pointer + (SSDataType.LONG.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, float[] src) {
		assert(dest.length >= pointer + (SSDataType.FLOAT.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, double[] src) {
		assert(dest.length >= pointer + (SSDataType.DOUBLE.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	public static int write(byte[] dest, int pointer, boolean[] src) {
		assert(dest.length >= pointer + (SSDataType.BOOLEAN.getSize() * src.length));
		for(int i = 0; i < src.length; i++)
			pointer = write(dest, pointer, src[i]);
		return pointer;
	}
	
	// READING ==================================================================================
	
	public static byte readByte(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.BYTE.getSize());
		return src[pointer];
	}
	
	public static short readShort(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.SHORT.getSize());
		return ByteBuffer.wrap(src, pointer, SSDataType.SHORT.getSize()).getShort();
	}
	
	public static char readCharacter(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.CHARACTER.getSize());
		return ByteBuffer.wrap(src, pointer, SSDataType.CHARACTER.getSize()).getChar();
	}
	
	public static int readInteger(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.INTEGER.getSize());
		return ByteBuffer.wrap(src, pointer, SSDataType.INTEGER.getSize()).getInt();
	}
	
	public static long readLong(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.LONG.getSize());
		return ByteBuffer.wrap(src, pointer, SSDataType.LONG.getSize()).getLong();
	}
	
	public static float readFloat(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.FLOAT.getSize());
		return Float.intBitsToFloat(readInteger(src, pointer));
	}
	
	public static double readDouble(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.DOUBLE.getSize());
		return Double.longBitsToDouble(readLong(src, pointer));
	}
	
	public static boolean readBoolean(byte[] src, int pointer) {
		assert(src.length >= pointer + SSDataType.BOOLEAN.getSize());
		assert(src[pointer] == 0 || src[pointer] == 1);
		return src[pointer] != 0;
	}
	
	public static String readString(byte[] src, int pointer, int length) {
		assert(src.length >= pointer + length);
		return new String(src, pointer, length);
	}
	
	public static void readBytes(byte[] src, int pointer, byte[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.BYTE.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readByte(src, pointer + (i * SSDataType.BYTE.getSize()));
	}
	
	public static void readShorts(byte[] src, int pointer, short[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.SHORT.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readShort(src, pointer + (i * SSDataType.SHORT.getSize()));
	}
	
	public static void readCharacters(byte[] src, int pointer, char[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.CHARACTER.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readCharacter(src, pointer + (i * SSDataType.CHARACTER.getSize()));
	}
	
	public static void readIntegers(byte[] src, int pointer, int[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.INTEGER.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readInteger(src, pointer + (i * SSDataType.INTEGER.getSize()));
	}
	
	public static void readLongs(byte[] src, int pointer, long[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.LONG.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readLong(src, pointer + (i * SSDataType.LONG.getSize()));
	}
	
	public static void readFloats(byte[] src, int pointer, float[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.FLOAT.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readFloat(src, pointer + (i * SSDataType.FLOAT.getSize()));
	}
	
	public static void readDoubles(byte[] src, int pointer, double[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.DOUBLE.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readDouble(src, pointer + (i * SSDataType.DOUBLE.getSize()));
	}
	
	public static void readBooleans(byte[] src, int pointer, boolean[] dest) {
		assert(src.length >= pointer + (dest.length * SSDataType.BOOLEAN.getSize()));
		for(int i = 0; i < dest.length; i++)
			dest[i] = readBoolean(src, pointer + (i * SSDataType.BOOLEAN.getSize()));
	}

}
