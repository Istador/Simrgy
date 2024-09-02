package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class RSolarPlus extends ResearchAbstract {

	//Singleton
	private static RSolarPlus instance = null;
	public static RSolarPlus getInstance(){
		if(instance==null){
			instance = new RSolarPlus("Mehr Solarpanel", "doppelte Modulanzahl pro Feld", null,  500000000.0, 90000 );
		}
		return instance;
	}
	
	private RSolarPlus(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rPlusSolar;}
	
	public void researchEffect(Game g) {
		//effekt über game.isResearchDone(this)
	}

}