package simrgy.game.buildings;

import javax.swing.*;
import simrgy.game.*;

public class Staudamm extends BuildingAbstract implements Building {

	public Staudamm(String name){
		super(name);
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/staudamm.png")).getImage();
	}
}