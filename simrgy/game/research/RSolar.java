package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RSolar extends ResearchAbstract {

	//Singleton
	private static RSolar instance = null;
	public static RSolar getInstance(){
		if(instance==null){
			instance = new RSolar("Verbessertes Photovoltaic", "+10% Energieerzeugung", RSolarPlus.getInstance(),  1200000000.0, 90000 );
		}
		return instance;
	}
	
	private RSolar(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rSolar;}
	
	public void researchEffect(Game g) {
		g.rSolarEnergy *= 1.10; //erhöhe die erzeugte Energie durch Photovoltaik um 10%
	}

}
