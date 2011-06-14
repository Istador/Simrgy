package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;

public class ASurrender implements Action {

	//Singleton
	private ASurrender(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new ASurrender();
		return instance;
	}
	
	public void run(Building b) {
		b.getGame().stop();
		b.getGame().getMain().getGraphic().removeOverlay();
		b.getGame().getMain().getGraphic().showMenu();
	}
	public String getName(Building b){ return "Spiel Aufgeben"; }
	public boolean isPossible(Building b){ return true; }
}
