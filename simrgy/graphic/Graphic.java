package simrgy.graphic;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import simrgy.applet.Main;

public class Graphic implements GraphicObject {

	
	private Map map;
	private GUI gui;
	private Menu menu;
	private Settings settings;
	private Highscore highscore;

	public Main main;
	
	public boolean showmenu;
	public boolean showhighscore;
	public boolean showsettings;
	
	//Singleton
	private static Graphic instance = null;
	private Graphic(){}
	public static Graphic getInstance(){
		if(instance == null){
			instance = new Graphic();
			instance.map = Map.getInstance();
			instance.gui = GUI.getInstance();
			instance.menu = Menu.getInstance();
			instance.highscore = Highscore.getInstance();
			instance.settings = Settings.getInstance();
		}
		return instance;
	}
	
	public void init(Main m){
		this.main = m;
		showmenu = true;
		showhighscore = false;
		showsettings = false;
		menu.init(m.backg);
		highscore.init(m.backg);
		settings.init(m.backg);
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
	
		if(showmenu)
			menu.draw(g);
		else if(showsettings)
			settings.draw(g);
		else if(showhighscore)
			highscore.draw(g);
		else{
			gui.draw(g);
			map.draw(g);
			}
	}
	
	public void click(int x, int y) {
		if(showmenu)
			menu.click(x,y);
		else if(showsettings)
			settings.click(x,y);
		else if(showhighscore)
			highscore.click(x,y);
		else{
			if( x  < map.getWidth() && y <= 600 ) map.click(x,y);
			if( x >= map.getWidth() && y <= 600 && x <= 800 ) gui.click(x,y);
		}
	}

	public void mouseOver(int x, int y) {
		if(showmenu)
			menu.mouseOver(x,y);
		else if(showsettings)
			settings.mouseOver(x,y);
		else if(showhighscore)
			highscore.mouseOver(x,y);
		else{
			if( x  < map.getWidth() && y <= 600 ){ map.mouseOver(x, y); gui.mouseOut(); }
			if( x >= map.getWidth() && y <= 600 && x <= 800 ){ gui.mouseOver(x, y); map.mouseOut(); }
		}
	}
	public void mouseOut() {
		if(showmenu)
			menu.mouseOut();
		else if(showsettings)
			settings.mouseOut();
		else if(showhighscore)
			highscore.mouseOut();
		else{
			map.mouseOut();
			gui.mouseOut();
		}
	}
	
}
