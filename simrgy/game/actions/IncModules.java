package simrgy.game.actions;


import simrgy.game.Action;
import simrgy.game.Building;

public class IncModules implements Action {

	//Singleton
	private IncModules(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new IncModules();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			b.newModule();
		}
	}

	
	public String getName(){
		return "weiteres Modul";
	}
	
	public boolean isPossible(Building b){
		if(!b.getActions().contains(instance)) return false;
		if(b.isDeploying()) return false;
		if(!b.moreModulesPossible()) return false;
		return true;
	}
}
