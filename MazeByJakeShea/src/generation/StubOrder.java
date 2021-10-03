package generation;

public class StubOrder implements Order
{
	Maze orderedMaze;
	Builder builder;
	int skill;
	boolean perfect;
	int seed;
	
	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return skill;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return builder;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfect;
	}

	@Override
	public int getSeed() {
		// TODO Auto-generated method stub
		return seed;
	}

	@Override
	public void deliver(Maze mazeConfig) {
		// TODO Auto-generated method stub
		orderedMaze = mazeConfig;
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}

	public void setSeed(int seedVal)
	{
		seed = seedVal;
	}
	
	public void setSkill(int skillLevel)
	{
		skill = skillLevel;
	}
	
	public void setBuilder(Builder buildVersion)
	{
		builder = buildVersion;
	}
	
	public void setPerfect(boolean isPerf)
	{
		perfect = isPerf;
	}
	
	public Maze getMaze()
	{
		return orderedMaze;
	}
}
