package sineSection.spaceRPG.character;

public class ComfortStat {
	private static final double EXPONENTIAL_BASE = 1.2;
	private static final double MIN_DIVS = 0.1; // prevents div0 errors

	// private final int defaultValue;
	private final int posTolerance;
	private final int negTolerance;
	private final int nativeDamping;

	private int currentValue;

	public ComfortStat(int defaultValue, int posTolerance, int negTolerance) {
		this(defaultValue, posTolerance, negTolerance, 0);
	}

	public ComfortStat(int defaultValue, int posTolerance, int negTolerance, int nativeDamping) {
		this.currentValue = defaultValue;
		this.posTolerance = defaultValue + posTolerance;
		this.negTolerance = defaultValue - negTolerance;
		this.nativeDamping = nativeDamping;
	}

	/**
	 * calculate the damage this character should take based upon the related
	 * environmental value
	 * 
	 * @param envValue
	 *            the world value to change upon
	 * @return
	 */
	public int envCalc(int envValue, int damping, int resistance) {
		int result = 0;
		updateCurrent(envValue, damping);
		if (currentValue < negTolerance) {
			result = negTolerance - currentValue;
		} else if (currentValue > posTolerance) {
			result = currentValue - posTolerance;
		}

		return (int) ((Math.pow(EXPONENTIAL_BASE, result) / Math.max(resistance, MIN_DIVS)) + 0.5);
	}

	private void updateCurrent(int envValue, int damping) {
		currentValue += envValue;
		currentValue /= (2 * (Math.max(damping + nativeDamping, MIN_DIVS)));
	}

}
