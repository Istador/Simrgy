package simrgy.game;

import java.awt.Image;

import javax.swing.ImageIcon;

import simrgy.graphic.GridObject;

public abstract class BuildingAbstract implements Building {

	protected String name;
	protected static Image img;
	protected GridObject gridObject;

	protected BuildingAbstract(String n){
		name = n;
		img = new ImageIcon(getClass().getResource("../../res/img/buildings/none.png")).getImage();
	}

	public Image getImage() {return img;}
	public String getName() {return name;}
	public void attachGridObject(GridObject o) {gridObject=o;}

}
