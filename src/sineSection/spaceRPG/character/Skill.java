package sineSection.spaceRPG.character;

public class Skill {
	private final static int BASE_LEVEL_VALUE = 0;
	private final static int BASE_LEVEL_COST = 5;
	private final static double LEVEL_COST_GROWTH = 1.5;
	
	private int level;
	private int expToNextLevel;
	private int currentExp;

	public Skill() {
		expToNextLevel = BASE_LEVEL_COST;
		level  = BASE_LEVEL_VALUE;
	}
	
	public boolean gainExperience(int gainz) {
		currentExp += gainz;
		boolean res = currentExp > expToNextLevel;
		if (res) levelUp();
		return res;
	}
	
	private void levelUp() {
		level++;
		currentExp -= expToNextLevel;
		expToNextLevel *= LEVEL_COST_GROWTH;
	}
	
	//two diff a/effect spellings here. That is intentional.
	public void affectLevels(int effect) {
		level += effect;
	}
	
	public int getLevel() {
		return level;
	}

}
