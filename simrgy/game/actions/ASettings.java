package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;

public class ASettings implements Action {

	//Singleton
	private ASettings(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new ASettings();
		return instance;
	}
	
	public void run(Building b) {
		b.getGame().getMain().getGraphic().setOverlay(b.getGame().getMain().getGraphic().getSettings());
	}
	public String getName(Building b){ return "Einstellungen..."; }
	public boolean isPossible(Building b){ return true; }
}
