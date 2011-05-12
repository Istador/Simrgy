package simrgy.graphic.menu;

import simrgy.applet.*;
import simrgy.graphic.Button;
import simrgy.graphic.ButtonCenteredText;
import simrgy.graphic.Graphic;
import simrgy.graphic.GraphicObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.*;
import java.awt.geom.*;


public class Intro implements GraphicObject {

	protected Graphic graphic; //parent
	
	public int top;
	public int left;
	public int width;
	public int height;
	private Button[] buttons;
	private Button over = null;
	
	public Intro(Graphic g){
		graphic = g;
		
		top=getMain().top;
		left=getMain().left;
		width=getMain().width;
		height=getMain().height;
		
		Runnable r1 = new RunnableMain(getMain()) {
			public void run() { 
				main.getGraphic().showGame();
				main.getGame().start();
			}
		};
		Runnable r2 = new RunnableMain(getMain()) {
			public void run() { 
				main.getGraphic().showMenu();
			}
		};
		
		Font f = new Font("Helvetica", Font.PLAIN, 30);
		buttons = new Button[2];
		buttons[0] = new ButtonCenteredText(this, "Starte Spiel", Color.BLACK, Color.GREEN, width/2, height/60*50, f, r1);
		buttons[1] = new ButtonCenteredText(this, "Zurück zum Menü", Color.BLACK, Color.GREEN, width/2, height/60*55, f, r2);
		
	}
	
	
	//GraphicObject Methods
	public void draw(){
		Graphics g = getBackbuffer();
		g.setColor(Color.WHITE);
		g.fillRect(left+20, top+20, width-40, height-40);
	
		Font f = new Font("Helvetica", Font.PLAIN, 48);
		g.setFont(f);
		Rectangle2D bounds = new TextLayout("Sim'rgy", f, ((Graphics2D)g).getFontRenderContext()).getBounds();
		int strheight = (int) Math.ceil(bounds.getHeight());
		int strwidth = (int) Math.ceil(bounds.getWidth()); 
		int strtop = 40+strheight;
		int strleft = width/2-strwidth/2+left;
		g.setColor(Color.BLACK);
		g.drawString("Sim'rgy", strleft, strtop);
		
		//TODO Text und Sound
		
		for(Button b : buttons) if(b!=null) b.draw();
	}

	public void click(int x, int y) { for(Button b : buttons) if(b!=null) b.click(x,y); }
	public void mouseOver(int x, int y) {
		Button old = over; //alten Button zwischenspeichern
		for(Button b : buttons) if( b!=null && b.contains(x, y)) over = b; //neuen Button merken
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

	public Graphic getGraphic(){return graphic;}
	public Main getMain(){return getGraphic().getMain();}
	public Graphics getBackbuffer(){return getGraphic().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
	
}
