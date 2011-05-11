package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;

public class Deploy implements Action {

	//Singleton
	private Deploy(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new Deploy();
		return instance;
	}
	
	public void run(Building b) {
		if( b.getActions().contains(instance) ){
			//TODO: nachfragen: Sicher? ja/nein 
			b.deploy();
			b.removeAction(instance);
		}
	}

	
	public String getName(){
		return "abreiﬂen";
	}
	
}
