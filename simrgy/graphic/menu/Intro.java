package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.ButtonHTTPImage;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Intro implements GraphicObject {

	protected Graphic graphic; //parent
	
	public int top;
	public int left;
	public int width;
	public int height;
	private Button[] buttons;
		
	public Intro(Graphic g){
		graphic = g;
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		
		Runnable r1 = new RunnableMain(getMain()) {
			public void run() { 
				IntroThread.stop_intro();
				main.getGraphic().showGame();
				main.getGame().start();
			}
		};
		Runnable r2 = new RunnableMain(getMain()) {
			public void run() {
				IntroThread.stop_intro();
				main.getGraphic().showMenu();
			}
		};
		
		buttons = new Button[3];
		buttons[0] = new ButtonCenteredText(this, "Starte Spiel", c_menu_button_text, c_menu_button_highlight, width/2, height-93, f_menu_button, r1);
		buttons[1] = new ButtonCenteredText(this, "Zurück", c_menu_button_text, c_menu_button_highlight, width/2, height-45, f_menu_button, r2);
		buttons[2] = new ButtonHTTPImage(this, mims, "http://mims.projects.emercs.com/", width-30-194, height-30-96, 194, 96);
	}
		
	//GraphicObject Methods
	public void draw(){
		//Rand
		Graphics g = getBackbuffer();
		g.setColor(c_menu_bg);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		//Überschrift
		g.setFont(f_menu_caption);
		g.setColor(c_menu_caption);
		int[] f = f_size(g, f_menu_caption, "Sim'rgy");
		int strheight = f[0];
		int strwidth = f[1]; 
		int strtop = 20+strheight;
		int strleft = width/2-strwidth/2+left;
		g.drawString("Sim'rgy", strleft, strtop);
		
		strtop+=50;
		
		//Text
		g.setFont(f_menu_smaltext);
		g.setColor(c_menu_text);
		g.drawString("Die Welt wie wir sie kennen steht vor einem Wandel. Die zunehmende Überbevölkerung",left+40,strtop);		
		strtop+=25;
		g.drawString("sorgt dafür, das unser Ressourcen- und Energiebedarf steigt. Die fossilen Brennstoffe, die",left+40,strtop);
		strtop+=25;
		g.drawString("uns bisher scheinbar günstig und zuverlässig mit Strom versorgten, nähern sich dem Ende.",left+40,strtop);
		
		strtop+=50;
		
		g.drawString("In einer Ära, in der technische Geräte zunehmend Verbreitung finden, und selbst Kühl-",left+40,strtop);
		strtop+=25;
		g.drawString("schränke und Toaster einen eigenen Internetanschluss erhalten, übernehmen Sie die",left+40,strtop);
		strtop+=25;
		g.drawString("Führung eines renommierten Energieerzeugers, um die Energieversorgung",left+40,strtop);
		strtop+=25;
		g.drawString("Deutschlands, in dieser schweren Zeit, sicherzustellen.",left+40,strtop);
		
		strtop+=50;
		
		g.drawString("Schaffen Sie den Ausstieg aus der Abhängigkeit von fossilen Brennstoffen hin zu",left+40,strtop);
		strtop+=25;
		g.drawString("eneuerbaren Energien?",left+40,strtop);
		
		//Buttons
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
