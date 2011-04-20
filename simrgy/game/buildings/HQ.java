package simrgy.game.buildings;

import javax.swing.*;
import simrgy.game.*;

public class HQ extends BuildingAbstract implements Building {

	public HQ(){
		super("Wattenfail");
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/hq.png")).getImage();
	}

}
