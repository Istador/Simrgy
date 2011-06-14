package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.game.buildings.HQ;

public class DecStrompreis implements Action {

	//Singleton
	private DecStrompreis(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new DecStrompreis();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			b.getGame().setStrompreis(b.getGame().strompreis-0.005);
		}
	}

	
	public String getName(Building b){
		return "-";
	}
	
	public boolean isPossible(Building b){
		return b instanceof HQ;
	}
}
