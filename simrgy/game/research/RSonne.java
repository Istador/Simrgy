package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RSonne extends ResearchAbstract {

	//Singleton
	private static RSonne instance = null;
	public static RSonne getInstance(){
		if(instance==null){
			instance = new RSonne("Sonnenoutput erhöhen", "Verdoppelt den Sonnenausstoß", null, 30000000000.0, 500000 );
		}
		return instance;
	}
	
	private RSonne(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rSonne;}
	
	public void researchEffect(Game g) {
		g.rSolarEnergy *= 2.0; //verdoppele die erzeugte Energie der Sonne
	}

}
