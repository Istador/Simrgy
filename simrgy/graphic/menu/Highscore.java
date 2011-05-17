package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Highscore implements GraphicObject {

	protected Graphic graphic; //parent
	
	public int top;
	public int left;
	public int width;
	public int height;
	private Button[] buttons;
	
	public Highscore(Graphic g){
		graphic = g;
		
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		
		Runnable r1 = new RunnableMain(getMain()) {
			public void run() { 
				main.getGraphic().showMenu();
			}
		};
		
		buttons = new Button[1];
		buttons[0] = new ButtonCenteredText(this, "Zurück zum Menü", c_menu_button_text, c_menu_button_highlight, width/2, height/60*55, f_menu_button, r1);
	}
	
	
	//GraphicObject Methods
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(c_menu_bg);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		g.setFont(f_menu_caption);
		g.setColor(c_menu_caption);
		int[] f = f_size(g, f_menu_caption, "Highscores");
		int strheight = f[0];
		int strwidth = f[1]; 
		int strtop = 40+strheight;
		int strleft = width/2-strwidth/2+left;
		g.drawString("Highscores", strleft, strtop);
		
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
