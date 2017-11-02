package greenShift.world;

public enum GeneratorValue {
	MIN(0,5),
	LOW(5,11),
	MED(11,18),
	HIGH(18,26),
	MAX(26,45);
	
	
	final float minValue, maxValue;
	
	GeneratorValue(float min, float max) {
		minValue = min;
		maxValue = max;
	}
}
