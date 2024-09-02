package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RKohleCO2 extends ResearchAbstract {

	//Singleton
	private static RKohleCO2 instance = null;
	public static RKohleCO2 getInstance(){
		if(instance==null){
			instance = new RKohleCO2("Kohlefilter", "-70% Kohle CO2-Ausstoß", RKohleVorrat.getInstance(), 650000000.0, 280000 );
		}
		return instance;
	}
	
	private RKohleCO2(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rKohleCO2;}
	
	public void researchEffect(Game g) {
		g.rKohleCO2 *= 0.3; //verringere den CO2-Ausstoß von Kohlekraftwerken auf 30%
	}

}
