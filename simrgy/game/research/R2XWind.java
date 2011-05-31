package simrgy.game.research;

import java.awt.Image;

import simrgy.game.Game;
import simrgy.game.Research;
import simrgy.game.ResearchAbstract;
import static simrgy.res.RessourceManager.*;

public class R2XWind extends ResearchAbstract {

	//Singleton
	private static R2XWind instance = null;
	public static R2XWind getInstance(){
		if(instance==null){
			instance = new R2XWind("2 mit einem Streich", "Halbiert Bauzeit von Windrädern", null, 300000000.0, 100000 );
		}
		return instance;
	}
	
	private R2XWind(String name, String desc, Research parent, double kosten, long forschungsdauer) {
		super(name, desc, parent, kosten, forschungsdauer);
	}
	
	public Image getImage() {return rWind;}
	
	public void researchEffect(Game g) {
		g.rWindBuildTime *= 2.0; //beschleunigt das bauen um +100%
	}

}
