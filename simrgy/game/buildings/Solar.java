package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.game.actions.Rename;
import simrgy.res.RessourceManager;

//Photovoltaikanlage
public class Solar extends BuildingAbstract implements Building {

    public static int underground = 1; //Land benötigt
    
    static double baukosten_per_module = 130000000.0; //pro Rad
    static int personal_per_module = 10; //?
    
    protected long bauzeit_so_far = 0;
    protected static long bauzeit_per_module = 12000; //2 Jahre
    
    private int modules = 1;
    private int max_modules = 5;
    protected double co2_kg = 4.0;
    protected int zufriedenheit = 9;
    
    public Solar(Game g, String name){
        super(g, name);
        actions.add(Rename.getInstance());
    }
    
    public double getMoneyCostH()
    {
        return getPersonal() * getGame().getPersonalkosten();
    }
    
    public double getMW()
    {
        //Pro Solarkraftwerk: 10-40 MW, Wetterabhängig
        double pro = 10.0 + 30.0 * getGame().getSolarPower(this);
        return pro * activeModules();
    }
    
    public double getCo2() {return co2_kg * activeModules();}
    public int getZufriedenheit() {return zufriedenheit * activeModules();}
    
    public Image getImage(){ return RessourceManager.solar; }

    public int getPersonal() 
    {
        return personal_per_module * modules; 
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
    
    public double getBaustatus() 
    { 
        return (double)bauzeit_so_far / ( (double)modules * (double)bauzeit_per_module ); 
    }
    
    public int getUnderground(){return underground;}
}