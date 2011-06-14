package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Menu implements GraphicObject {

	protected Graphic graphic; //parent
	
	public int top;
	public int left;
	public int width;
	public int height;
	
	private Button[] buttons;
	
	public Menu(Graphic g){
		graphic = g;
		
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		
		Runnable r1 = new RunnableMain(getMain()) { 
			public void run() {
				main.getGraphic().showIntro();
				IntroThread.play_intro();
			}
		};
		/*
		Runnable r2 = new RunnableMain(getMain()) {
			public void run() {
				main.getGraphic().showHighscore();
			}
		};
		*/
		Runnable r3 = new RunnableMain(getMain()) { 
			public void run() {
				main.getGraphic().setOverlay(main.getGraphic().getSettings());
				//main.getGraphic().showSettings();
			}
		};
		Runnable r4 = new RunnableMain(getMain()) { 
			public void run() {
				main.getGraphic().showAbout();
			}
		};
		
		buttons = new Button[3];
		buttons[0] = new ButtonCenteredText(this, "Spiel starten", c_menu_button_text, c_menu_button_highlight, width/2, height/6*2, f_menu_button, r1);
		//buttons[1] = new ButtonCenteredText(this, "Highscores", c_menu_button_text,c_menu_button_highlight, width/2, height/6*3, f_menu_button, r2);
		buttons[1] = new ButtonCenteredText(this, "Einstellungen", c_menu_button_text, c_menu_button_highlight, width/2, height/6*4, f_menu_button, r3);
		buttons[2] = new ButtonCenteredText(this, "Credits", c_menu_button_text, c_menu_button_highlight, width/2, height/6*5, f_menu_button, r4);
	}
		
	//GraphicObject Methods
	public void draw(){
		Graphics g = graphic.getMain().getBackbuffer();
		g.setColor(c_menu_bg);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		g.setFont(f_menu_caption);
		g.setColor(c_menu_caption);
		int[] f = f_size(g, f_menu_caption, "Sim'rgy");
		int strheight = f[0];
		int strwidth = f[1]; 
		int strtop = 20+strheight;
		int strleft = width/2-strwidth/2+left;
		g.drawString("Sim'rgy", strleft, strtop);
		g.setFont(f_menu_smaltext);
		g.drawString("Version 019", left+width-110, top+height-20-10);
		for(Button b : buttons) if(b!=null) b.draw();
	}

	public void click(int x, int y) { for(Button b : buttons) if(b!=null) b.click(x,y); }

	public void mouseOver(int x, int y) {
		for(Button b : buttons) if(b!=null) b.mouseOver(x, y);
	}
	public void mouseOut() {
		for(Button b : buttons) if(b!=null) b.mouseOut();
	}

	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
}
