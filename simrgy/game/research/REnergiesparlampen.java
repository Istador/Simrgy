package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class REnergiesparlampen extends ResearchAbstract {

	//Singleton
	private static REnergiesparlampen instance = null;
	public static REnergiesparlampen getInstance(){
		if(instance==null){
			instance = new REnergiesparlampen("Energiesparlampen", "benötigt Nachts weniger Energie", null, 800000000.0, 120000 );
		}
		return instance;
	}
	
	private REnergiesparlampen(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rGlueh;}
	
	public void researchEffect(Game g) {
		g.rNightEnergy /= 2.0; //halbiere den nächtlichen Energiebedarf 
	}

}
