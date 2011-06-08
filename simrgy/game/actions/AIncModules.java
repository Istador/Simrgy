package simrgy.game.actions;


import static simrgy.res.RessourceManager.df_money;
import simrgy.game.Action;
import simrgy.game.Building;

public class AIncModules implements Action {

	//Singleton
	private AIncModules(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new AIncModules();
		return instance;
	}
	
	public void run(Building b) {
		if( isPossible(b) ){
			b.newModule();
		}
	}

	
	public String getName(Building b){
		return "Weiteres Modul(-"+df_money.format((long)b.getBaukostenPerModule())+"€)";
	}
	
	public boolean isPossible(Building b){
		if(!b.getActions().contains(instance)) return false;
		if(b.isDeploying()) return false;
		if(!b.moreModulesPossible()) return false;
		return true;
	}
}
