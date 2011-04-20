package simrgy.game.buildings;

import java.awt.*;
import javax.swing.*;
import simrgy.game.*;

public class AKW extends BuildingAbstract implements Building {

	private static Image img1;
	private static Image img2;
	private static Image img3;
	
	int modules = 1;
	
	protected AKW(String name){
		super(name);
		img1 = new ImageIcon(getClass().getResource("../../res/img/buildings/akw_1.png")).getImage();
		img2 = new ImageIcon(getClass().getResource("../../res/img/buildings/akw_2.png")).getImage();
		img3 = new ImageIcon(getClass().getResource("../../res/img/buildings/akw_3.png")).getImage();
	}
	
	public static AKW newAKW(String name, int module){
		AKW ret = new AKW(name);
		ret.modules=module;
		return ret;
	}
	
	public Image getImage() {
		if(modules==1) return img1;
		if(modules==2) return img2;
		if(modules==3) return img3;
		return img;
	}

}
