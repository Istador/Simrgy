package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.game.buildings.AKW;

public class ALequidatoren implements Action {

	//Singleton
	private ALequidatoren(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new ALequidatoren();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			b.getGame().money -= 1000000.0;
			((AKW)b).unfall = false;
			((AKW)b).unfall_tick = 0;
			((AKW)b).unfall_time = 0;
			b.getGame().getMain().getGraphic().removeOverlay();
			b.getGame().pause();
		}
	}

	
	public String getName(Building b){
		return "Lequidatoren einsetzen (-1.000.000€)";
	}
	
	public boolean isPossible(Building b){
		if(!(b instanceof AKW)) return false;
		if(!b.getActions().contains(instance)) return false;
		if(b.isDeploying()) return false;
		//if(!b.getGame().moneySubValid(1000000.0)) return false;
		return ((AKW)b).unfall;
	}
}
