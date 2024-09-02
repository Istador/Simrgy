package simrgy.game;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import simrgy.graphic.map.GridObject;
import simrgy.res.RessourceManager;

public abstract class BuildingAbstract implements Building {

	public static int underground = 0;
	
	protected Game game;
	
	protected String name;
	protected GridObject gridObject;
	
	
	protected int modules = 1;
	protected long bauzeit_so_far = 0;
	
	//überschreibe in child
	protected int max_modules = 1;
	protected long bauzeit_per_module = 1;
	protected double baukosten_per_module = 0.0;
	
	
	protected List<Action> actions;
	
	protected boolean building = true; //bauen oder abreißen

	protected BuildingAbstract(Game g, String n){
		game = g;
		name = n;
		actions = new ArrayList<Action>();
	}

	public double getMoneyCostH(){ return getPersonal() * getGame().getPersonalkosten() ;}
	public Image getImage() {return RessourceManager.none;}
	public String getName() {return name;}
	public void setName(String n) {name=n;}
	public void attachGridObject(GridObject o) {gridObject=o;}

	public String getPersonalText(){
		return String.valueOf(getPersonal());
	}
	
	public String getModulesText(){
		String ret = "";
		if(modules>activeModules())
			ret += activeModules()+"+"+(modules-activeModules());
		else
			ret += activeModules();

		ret += "/"+max_modules;
		return ret;
	}
	
	public List<Action> getActions(){
		return new ArrayList<Action>(actions);
	}
	
	public void removeAction(Action a){
		actions.remove(a);
	}

	public Game getGame(){return game;}
	
	public boolean enoughMoney(){
		return game.moneySubValid(getBaukosten());
		//return game.money >= getBaukosten();
	}
	
	public void deploy(){
		building = false;
	}
	public boolean isDeploying(){
		return !building;
	}
	
	public boolean drawModules(){return true;}
	
	public int activeModules(){
		return (int) (bauzeit_so_far / bauzeit_per_module) ;
	}
	
	public int modules(){
		return modules;
	}
	
	public double getBaustatus() {
		return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module );
	}
	
	public void tick(long miliseconds){
		//bau im gange
		long max_bauzeit = modules * bauzeit_per_module; 
		if( building && max_bauzeit > bauzeit_so_far ){
			bauzeit_so_far += miliseconds;
			bauzeit_so_far = (bauzeit_so_far > max_bauzeit ? max_bauzeit : bauzeit_so_far) ;
		}	
		else if(!building){
			bauzeit_so_far -= miliseconds*2;
			bauzeit_so_far = (bauzeit_so_far < 0 ? 0 : bauzeit_so_far) ;
			if(bauzeit_so_far == 0) game.removeBuilding(this);
		}
	}
	
	public boolean moreModulesPossible(){
		if(!game.moneySubValid(baukosten_per_module)) return false;
		return modules+1<=max_modules;
	}
	
	public boolean newModule(){
		if(!moreModulesPossible()) return false;
		getGame().money-=baukosten_per_module;
		modules++;
		return true;
	}
	
	public long getBauzeit() {
		return bauzeit_per_module * (long)modules;
	}

	public double getBaukosten() {
		return baukosten_per_module * (double)modules;
	}
	
	public double getBaukostenPerModule() {
		return baukosten_per_module;
	}
	
	public int getUnderground(){return underground;}
}
