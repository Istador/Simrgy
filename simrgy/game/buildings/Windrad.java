package simrgy.game.buildings;

import javax.swing.*;
import simrgy.game.*;

public class Windrad extends BuildingAbstract implements Building {

	public Windrad(String name){
		super(name);
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/windrad.png")).getImage();
	}
}