package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.Game;
import simrgy.graphic.gui.GUI;
import simrgy.graphic.map.Map;
import simrgy.graphic.menu.*;
import simrgy.graphic.menu.Menu;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Graphic implements GraphicObject {

	public Main main;  //parent
	
	//Childrens
	private Map map;
	private GUI gui;
	private Menu menu;
	private Settings settings;
	private Highscore highscore;	
	private About about;
	private Intro intro;
	
	private GraphicObject overlay = null;
	
	private GraphicObject show;
	
	public Graphic(Main m){
		main = m;
		
		init();
		
		menu = new Menu(this);
		highscore = new Highscore(this);
		settings = new Settings(this);
		about = new About(this);
		intro = new Intro(this);

		show = menu;
	}
	
	public void init(){
		map = new Map(this);
		gui = new GUI(this);
		
	}
	
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(c_menu_rand);
		g.fillRect(0, 0, getMain().width, getMain().height);
	
		if(show!=null)
			show.draw();
		else{
			getGUI().draw();
			getMap().draw();
			}
		
		if(overlay != null) overlay.draw();
	}
	
	public void click(int x, int y) {
		if(overlay != null)
			overlay.click(x,y);
		else if(show!=null)
			show.click(x,y);
		else{
			if( x  < getMap().getWidth() && y <= getMain().getHeight() ) getMap().click(x,y);
			if( x >= getMap().getWidth() && y <= getMain().getHeight() && x <= getMain().getWidth() ) getGUI().click(x,y);
		}
	}

	public void mouseOver(int x, int y) {
		if(overlay != null)
			overlay.mouseOver(x,y);
		else if(show!=null)
			show.mouseOver(x,y);
		else{
			if( x  < getMap().getWidth() && y <= getMain().getHeight() ){ getMap().mouseOver(x, y); getGUI().mouseOut(); }
			if( x >= getMap().getWidth() && y <= getMain().getHeight() && x <= getMain().getWidth() ){ getGUI().mouseOver(x, y); getMap().mouseOut(); }
		}
	}
	public void mouseOut() {
		if(overlay != null)
			overlay.mouseOut();
		else if(show!=null)
			show.mouseOut();
		else{
			map.mouseOut();
			gui.mouseOut();
		}
	}
	public void setOverlay(GraphicObject go){
		overlay=go;
		Game game = getMain().getGame(); 
		if(game.running) game.pause();
	}
	public void removeOverlay(){
		overlay=null;
		Game game = getMain().getGame(); 
		if(game.started && !game.running) game.pause();
	}
	public boolean isOverlay(){ return overlay!=null; }
	
	public Main getMain(){return main;}
	public Map getMap(){return map;}
	public GUI getGUI(){return gui;}
	public Menu getMenu() {return menu;}
	public Settings getSettings() {return settings;}
	public Highscore getHighscore() {return highscore;}
	public Graphics getBackbuffer(){return getMain().getBackbuffer();}
	public void keyPress(KeyEvent ke){
		if(overlay != null)
			overlay.keyPress(ke);
		else if(show!=null)
			show.keyPress(ke);
		else{
			//TODO: beim drücken von Escape Menü anzeigen, für Einstellungen, und Aufgeben.
			if(ke.getKeyCode()==KeyEvent.VK_ESCAPE)
				RechtsklickTab.getInstance().run(getMain().getGame().hq);
				//setOverlay(getSettings());
			else if( ke.getKeyCode()==KeyEvent.VK_PAUSE && getMain().getGame().time>0 && !isOverlay())
				getMain().getGame().pause();
		}
	}
	
	public void showMenu(){ show = menu; }
	public void showHighscore(){ show = highscore; }
	public void showSettings(){ show = settings; }
	public void showAbout(){ show = about; }
	public void showIntro(){ show = intro; }
	public void showGame(){ show = null; }
}
