package simrgy.game.buildings;

import javax.swing.*;
import simrgy.game.*;

public class Solar extends BuildingAbstract implements Building {

	public Solar(String name){
		super(name);
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/solar.png")).getImage();
	}
}