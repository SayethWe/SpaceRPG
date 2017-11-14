package util;

public class HashCoder {
	private int result;
	final private int increment;

	public HashCoder(int start, int increment) {
		result = start;
		this.increment = increment;
	}

	public HashCoder append(int element) {
		multiply();
		add(element);
		return this;
	}

	public HashCoder append(double element) {
		result *= increment;
		add((int) (element + 0.5));
		return this;
	}

	public HashCoder append(Object element) {
		multiply();
		add(element.hashCode());
		return this;
	}

	public int getHash() {
		return result;
	}

	private void multiply() {
		result *= increment;
	}

	private void add(int i) {
		result += i;
	}
}