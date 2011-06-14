package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.game.buildings.AKW;

public class IncPersonal implements Action {

	//Singleton
	private IncPersonal(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new IncPersonal();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			AKW akw = (AKW) b;
			akw.setPersonal(akw.getPersonal()+5);
		}
	}

	
	public String getName(Building b){
		return "+";
	}
	
	public boolean isPossible(Building b){
		return b instanceof AKW;
	}
}
