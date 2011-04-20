package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;

public class Graphic implements GraphicObject {

	public Main main;  //parent
	
	//Childrens
	private Map map;
	private GUI gui;
	private Menu menu;
	private Settings settings;
	private Highscore highscore;	
	
	public boolean showmenu;
	public boolean showhighscore;
	public boolean showsettings;
	
	public Graphic(Main m){
		main = m;
		showmenu = true;
		showhighscore = false;
		showsettings = false;
		map = new Map(this);
		gui = new GUI(this);
		menu = new Menu(this);
		highscore = new Highscore(this);
		settings = new Settings(this);
	}
	
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getMain().getWidth(), getMain().getHeight());
	
		if(showmenu)
			menu.draw();
		else if(showsettings)
			settings.draw();
		else if(showhighscore)
			highscore.draw();
		else{
			getGUI().draw();
			getMap().draw();
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
			if( x  < getMap().getWidth() && y <= getMain().getHeight() ) getMap().click(x,y);
			if( x >= getMap().getWidth() && y <= getMain().getHeight() && x <= getMain().getWidth() ) getGUI().click(x,y);
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
			if( x  < getMap().getWidth() && y <= getMain().getHeight() ){ getMap().mouseOver(x, y); getGUI().mouseOut(); }
			if( x >= getMap().getWidth() && y <= getMain().getHeight() && x <= getMain().getWidth() ){ getGUI().mouseOver(x, y); getMap().mouseOut(); }
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
	
	public Main getMain(){return main;}
	public Map getMap(){return map;}
	public GUI getGUI(){return gui;}
	public Menu getMenu() {return menu;}
	public Settings getSettings() {return settings;}
	public Highscore getHighscore() {return highscore;}
	public Graphics getBackbuffer(){return getMain().getBackbuffer();}
}
