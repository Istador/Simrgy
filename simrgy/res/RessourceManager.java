package simrgy.res;

import java.awt.Image;

import javax.swing.ImageIcon;

public class RessourceManager {

	public static final Image map = new ImageIcon(RessourceManager.class.getResource("img/map.png")).getImage();
	
	public static final Image rcl = new ImageIcon(RessourceManager.class.getResource("img/rcl.jpg")).getImage();
	
	public static final Image none = new ImageIcon(RessourceManager.class.getResource("img/buildings/none.png")).getImage();
	
	public static final Image hq = new ImageIcon(RessourceManager.class.getResource("img/buildings/hq.png")).getImage();
	
	public static final Image akw_0 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_0.png")).getImage();
	public static final Image akw_1 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_1.png")).getImage();
	public static final Image akw_2 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_2.png")).getImage();
	public static final Image akw_3 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_3.png")).getImage();
	
	public static final Image windrad = new ImageIcon(RessourceManager.class.getResource("img/buildings/windrad.png")).getImage();
	
	public static final Image kohle = new ImageIcon(RessourceManager.class.getResource("img/buildings/kohle.png")).getImage();
	
	public static final Image solar = new ImageIcon(RessourceManager.class.getResource("img/buildings/solar.png")).getImage();
	
	public static final Image staudamm = new ImageIcon(RessourceManager.class.getResource("img/buildings/staudamm.png")).getImage();
	
	//TODO: Fonts und eigene Farben sammeln
}
