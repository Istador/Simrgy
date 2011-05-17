package simrgy.game.buildings;

import java.awt.*;
import simrgy.game.*;
import simrgy.game.actions.*;
import simrgy.res.RessourceManager;

public class AKW extends BuildingAbstract implements Building {
	
	public static int underground = 6; //Fluss oder See benötigt benötigt
	
	protected int modules = 1;
	protected int max_modules = 3;
	protected double baukosten_per_module = 5000000000.0; // 5 Mrd.
	protected long bauzeit_so_far = 0;
	protected static long bauzeit_per_module = 90000; //10-20 Jahre Bauzeit -> 1-2 Minuten -> 1:30 -> 90s
	protected int personal = 100;
	protected static int max_personal_per_module = 100; //?
	protected static int min_personal_per_module = 10; //?
	protected double leistung = 1.0;
	protected double mw_module = 1600;
	protected double co2_kg = 3.0;
	protected int zufriedenheit = 1; 
	
	protected AKW(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
		actions.add(IncModules.getInstance());
		actions.add(Deploy.getInstance());
		setPersonal(personal);
	}
	
	public static AKW newAKW(Game g, String name, int module){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		if(!ret.moreModulesPossible()) ret.removeAction(IncModules.getInstance());
		return ret;
	}
	
	public static AKW newFinishedAKW(Game g, String name, int module, double mw){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = AKW.bauzeit_per_module * ret.modules;
		ret.mw_module=mw;
		if(!ret.moreModulesPossible()) ret.removeAction(IncModules.getInstance());
		return ret;
	}
	
	public Image getImage() {
		int m = activeModules();
		if(m==0) return RessourceManager.akw_0;
		if(m==1) return RessourceManager.akw_1;
		if(m==2) return RessourceManager.akw_2;
		if(m==3) return RessourceManager.akw_3;
		return super.getImage();
	}

	public double getMoneyCostH(){ return personal * getGame().getPersonalkosten() ;}
	public double getMW(){ return activeModules() * mw_module * leistung; }
	public double getCo2() {return co2_kg * activeModules();}
	public int getZufriedenheit() {return zufriedenheit * activeModules();}
	
	//MW produzieren - und Uran verbrauchen
	public double consumeMW(){
		double mw = 0.0;
		for(int i=0; i<activeModules(); i++){
			if(game.consumeUran()) mw += mw_module * leistung; 
		}
		return mw;
	}
	
	public void setLeistung(double leistung){
		this.leistung = ( leistung>=1.0 ? 1.0 : ( leistung<=0.0 ? 0.0 : leistung ) );
	}
	
	public double getLeistung(){
		return leistung;
	}
	
	public void setPersonal(int personal){
		int min = min_personal_per_module * modules;
		int max = min_personal_per_module * modules;
		this.personal = ( personal>=max ? max : ( personal <= min ? min : personal ) );
	}
	
	public int getPersonal(){
		return personal;
	}

	public long getBauzeit() {
		return bauzeit_per_module;
	}

	public double getBaukosten() {
		return baukosten_per_module * modules;
	}
	
	protected int activeModules(){
		return (int) (bauzeit_so_far / bauzeit_per_module) ;
	}
	
	public boolean newModule(){
		if(!moreModulesPossible()) return false;
		getGame().money-=baukosten_per_module;
		modules++;
		setPersonal(personal); //Personalanpassung, damit min_personal
		return true;
	}
	
	public boolean moreModulesPossible(){
		if(baukosten_per_module>=game.money) return false;
		return modules+1<=max_modules;
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

	public double getBaustatus() {
		return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module );
	}
	
	public int getUnderground(){return underground;}
	
}
