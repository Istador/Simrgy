package simrgy.game.buildings;

import java.awt.*;
import simrgy.game.*;
import simrgy.game.actions.Rename;
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
	
	protected AKW(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
		setPersonal(personal);
	}
	
	public static AKW newAKW(Game g, String name, int module){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		return ret;
	}
	
	public static AKW newFinishedAKW(Game g, String name, int module, double mw){
		AKW ret = new AKW(g, name);
		ret.modules=module;
		ret.bauzeit_so_far = AKW.bauzeit_per_module * ret.modules;
		ret.mw_module=mw;
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
		return baukosten_per_module;
	}
	
	protected int activeModules(){
		return (int) (bauzeit_so_far / bauzeit_per_module) ;
	}
	
	public boolean newModule(){
		if(modules+1>max_modules) return false;
		getGame().money-=getBaukosten();
		modules++;
		setPersonal(personal); //Personalanpassung, damit min_personal
		return true;
	}
	
	public void tick(long miliseconds){
		//bau im gange
		long max_bauzeit = modules * bauzeit_per_module; 
		if( max_bauzeit > bauzeit_so_far ){
			bauzeit_so_far += miliseconds;
			bauzeit_so_far = (bauzeit_so_far > max_bauzeit ? max_bauzeit : bauzeit_so_far) ; 
		}	
	}

	public double getBaustatus() {
		return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module );
	}
	
	public int getUnderground(){return underground;}
	
}
