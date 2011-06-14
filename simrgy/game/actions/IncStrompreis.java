package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.game.buildings.HQ;

public class IncStrompreis implements Action {

	//Singleton
	private IncStrompreis(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new IncStrompreis();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			b.getGame().setStrompreis(b.getGame().strompreis+0.005);
		}
	}

	
	public String getName(Building b){
		return "+";
	}
	
	public boolean isPossible(Building b){
		return b instanceof HQ;
	}
}
