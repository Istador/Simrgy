package simrgy.graphic;

import simrgy.applet.*;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

public class Menu implements GraphicObject {

	protected Graphic graphic; //parent
	
	public int top = 0;
	public int left = 0;
	public int width = 800;
	public int height = 600;
	private Button[] buttons;
	private Button over = null;
	
	public Menu(Graphic g){
		graphic = g;
		//Music.play(Graphic.getInstance().main, "10 - War against life.mp3");
		
		Runnable r1 = new RunnableMain(getMain()) { 
			public void run() {
				main.getGraphic().showmenu = false;
				//Music.play(Graphic.getInstance().main, "09 - Atmosphere.mp3");
			}
		};
		Runnable r2 = new RunnableMain(getMain()) {
			public void run() {
				main.getGraphic().showhighscore = true;
				main.getGraphic().showmenu = false;
				//Music.play(Graphic.getInstance().main, "07 - game over.mp3");
			}
		};
		Runnable r3 = new RunnableMain(getMain()) { 
			public void run() {
				main.getGraphic().showsettings = true;
				main.getGraphic().showmenu = false;
			}
		};
		
		Font f = new Font("Helvetica", Font.PLAIN, 30);
		buttons = new Button[3];
		buttons[0] = new CenteredButton(this, "Spiel starten", Color.BLACK, Color.GREEN, width/2, 200, f, r1);
		buttons[1] = new CenteredButton(this, "Highscores", Color.BLACK, Color.GREEN, width/2, 300, f, r2);
		buttons[2] = new CenteredButton(this, "Einstellungen", Color.BLACK, Color.GREEN, width/2, 400, f, r3);
	}
	
	
	
	//GraphicObject Methods
	public void draw(){
		Graphics g = graphic.getMain().getBackbuffer();
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
}
