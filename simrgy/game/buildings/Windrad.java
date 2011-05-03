package simrgy.game.buildings;

import java.awt.Image;

import simrgy.game.*;
import simrgy.res.RessourceManager;

public class Windrad extends BuildingAbstract implements Building {

	public static int underground = 1; //Land benötigt
	
	static double baukosten = 3570000.0; //pro Rad
	static int personalProRad = 1;
		
	private double grad = 90.0; //90°=N, 0°=E
	private int anzahl = 1;
	
	public Windrad(Game g, String name){
		super(g, name);
	}
	
	public Image getImage(){ return RessourceManager.windrad; }
	
	public double getMoneyPerSecond(){
		return getPowerPerSecond() * getGame().getStrompreis() - getPersonal() * getGame().getPersonalkosten();
		}
	
	public double getPowerPerSecond(){
		//Pro Windrad: 2-6 MW/h
		double pro = 2.0 + 4.0 * getGame().getWindpower(this) * getGradFaktor();
		return pro * anzahl;
		}
	
	public int getPersonal(){
		return personalProRad * anzahl;
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
}