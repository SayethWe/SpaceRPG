package sineSection.spaceRPG.character;

public class Stat {
	private int value;
	private int maxValue;
	private final int BaseMaxValue;
	private final int minValue;

	public Stat(int minValue, int maxValue) {
		this.maxValue = BaseMaxValue = maxValue;
		this.minValue = minValue;
	}

	public int maxVal() {
		return BaseMaxValue;
	}

	public int minVal() {
		return minValue;
	}

	public void addToMax(int incrementNum) {
		maxValue += incrementNum;
	}

	public int currentVal() {
		return value;
	}

	public boolean increment(int amt) {
		boolean result = incrementAllowed(amt);
		if (result) {
			value += amt;
		}
		return result;
	}

	public boolean incrementAllowed(int amt) {
		return ((value + amt) > maxValue) || (value + amt < minValue) ? false : true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");
		result.append("current: ");
		result.append(value);
		result.append(" max: ");
		result.append(maxValue);
		result.append(" min: ");
		result.append(minValue);
		return result.toString();
	}

	public void topOff() {
		value = BaseMaxValue;
	}

	public void empty() {
		value = minValue;
	}
}
