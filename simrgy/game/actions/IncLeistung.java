package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.game.buildings.AKW;

public class IncLeistung implements Action {

	//Singleton
	private IncLeistung(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new IncLeistung();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			AKW akw = (AKW) b;
			akw.setLeistung(akw.getLeistung()+0.05);
		}
	}

	
	public String getName(Building b){
		return "+";
	}
	
	public boolean isPossible(Building b){
		return b instanceof AKW;
	}
}
