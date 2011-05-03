package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.Rename;
import simrgy.res.RessourceManager;

public class Windrad extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	
	static double baukosten_per_module = 3570000.0; //pro Rad
	static int personal_per_module = 1; //?
		
	private double grad = 90.0; //90°=N, 0°=E
	
	protected long bauzeit_so_far = 0;
	protected static long bauzeit_per_module = 5000; //4 Wochen -> 1 Minuten / 12 -> 5 Sekunden
	
	private int modules = 1;
	private int max_modules = 100;
	
	public Windrad(Game g, String name){
		super(g, name);
		actions.add(Rename.getInstance());
	}
	
	public Image getImage(){ return RessourceManager.windrad; }
	
	public double getMoneyCostH(){
		return getPersonal() * getGame().getPersonalkosten();
		}
	
	public double getMW(){
		//Pro Windrad: 2-6 MW, Wetterabhängig
		double pro = 2.0 + 4.0 * getGame().getWindpower(this) * getGradFaktor();
		return pro * activeModules() ;
		}
	
	public int getPersonal(){
		return personal_per_module * modules;
	}
	
	// Optimal: Grad wie Windrichtung
	// 20% Optimal: Grad entgegen Windrichtung
	private double getGradFaktor(){
		double res = 0.8; // 20-100% Spielraum
		double windrichtung = getGame().windrichtung; 
		if( grad > windrichtung ){
			res *= ( 360.0-(grad-windrichtung) )/360.0 ;
		}
		else if( grad < windrichtung ){
			res *= ( 360.0-(windrichtung-grad) )/360.0 ;
		}
		return res+0.2;
	}
	
	public long getBauzeit() { return bauzeit_per_module; }
	public double getBaukosten() { return baukosten_per_module; }
	
	protected int activeModules(){
		return (int) (bauzeit_so_far / bauzeit_per_module) ;
	}
	
	public boolean newModule(){
		if(modules+1>max_modules) return false;
		getGame().money-=getBaukosten();
		modules++;
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