package simrgy.graphic;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.net.*;
import javax.swing.*;

import simrgy.applet.Main;
import simrgy.applet.Music;

public class Menu implements GraphicObject {

	public int top = 0;
	public int left = 0;
	public int width = 800;
	public int height = 600;
	private Button[] buttons;
	private Button over = null;
	
	//Singleton
	private static Menu instance = null;
	private Menu(){}	
	public static Menu getInstance(){
		if(instance == null){
			instance = new Menu();
			
		}
		return instance;
	}
	
	public void init(Graphics g){
		//Music.play(Graphic.getInstance().main, "10 - War against life.mp3");
		
		Runnable r1 = new Runnable() { 
			public void run() {
				Graphic.getInstance().getInstance().showmenu = false;
				//Music.play(Graphic.getInstance().main, "09 - Atmosphere.mp3");
			}
		};
		Runnable r2 = new Runnable() { 
			public void run() {
				Graphic.getInstance().getInstance().showhighscore = true;
				Graphic.getInstance().getInstance().showmenu = false;
				//Music.play(Graphic.getInstance().main, "07 - game over.mp3");
			}
		};
		Runnable r3 = new Runnable() { 
			public void run() {
				Graphic.getInstance().getInstance().showsettings = true;
				Graphic.getInstance().getInstance().showmenu = false;
			}
		};
		
		Font f = new Font("Helvetica", Font.PLAIN, 30);
		buttons = new Button[3];
		buttons[0] = new CenteredButton(g, "Spiel starten", Color.BLACK, Color.GREEN, width/2, 200, f, r1);
		buttons[1] = new CenteredButton(g, "Highscores", Color.BLACK, Color.GREEN, width/2, 300, f, r2);
		buttons[2] = new CenteredButton(g, "Einstellungen", Color.BLACK, Color.GREEN, width/2, 400, f, r3);
	}
	
	
	//GraphicObject Methods
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		Font f = new Font("Helvetica", Font.PLAIN, 48);
		g.setFont(f);
		Rectangle2D bounds = new TextLayout("Sim'rgy", f, ((Graphics2D)g).getFontRenderContext()).getBounds();
		int strheight = (int) Math.ceil(bounds.getHeight());
		int strwidth = (int) Math.ceil(bounds.getWidth()); 
		int strtop = 20+20+strheight;
		int strleft = width/2-strwidth/2+left;
		g.setColor(Color.BLACK);
		g.drawString("Sim'rgy", strleft, strtop);
		
		for(Button b : buttons) b.draw(g);
	}

	public void click(int x, int y) { for(Button b : buttons) b.click(x,y); }
	public void mouseOver(int x, int y) {
		Button old = over; //alten Button zwischenspeichern
		for(Button b : buttons) if(b.contains(x, y)) over = b; //neuen Button merken
		//neuen mouseOver senden
		if(over!=null && over!=old) over.mouseOver();
		//alten mouseOut senden
		if(old!=null && over!=old) old.mouseOut();		
	}
	public void mouseOut() {
		if(over!=null){
			//alten mouseOut senden
			over.mouseOut();
			//alten löschen
			over=null;
		}
	}




}
