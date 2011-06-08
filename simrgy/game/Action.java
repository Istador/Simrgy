package simrgy.game;

public interface Action {
	public void run(Building b);
	public boolean isPossible(Building b);
	public String getName(Building b);
	
}
