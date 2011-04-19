package simrgy.game.buildings;

import java.awt.Image;

import javax.swing.ImageIcon;
import simrgy.game.*;

public class HQ extends BuildingAbstract implements Building {

	public HQ(){
		super("Wattenfail");
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/hq.png")).getImage();
	}

}
