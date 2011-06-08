package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;

public class ACancel implements Action {

	//Singleton
	private ACancel(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new ACancel();
		return instance;
	}
	
	public void run(Building b) {
		b.getGame().getMain().getGraphic().removeOverlay();
		b.getGame().pause();
	}
	public String getName(Building b){ return "Abbrechen"; }
	public boolean isPossible(Building b){ return true; }
}
