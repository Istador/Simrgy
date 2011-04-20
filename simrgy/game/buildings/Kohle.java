package simrgy.game.buildings;

import javax.swing.*;
import simrgy.game.*;

public class Kohle extends BuildingAbstract implements Building {

	public Kohle(String name){
		super(name);
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/kohle.png")).getImage();
	}
}