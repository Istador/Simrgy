package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.buildings.*;
import simrgy.res.RessourceManager;

import java.awt.*;

public class BuildTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	private Color backgroundColor = new Color(0xBEE554); //CatEye
	
	private int box;
	
	private GUI gui;

	public BuildTab(GUI g) {
		gui = g;
		top   = 61;
		left  = getGUI().left+26;
		width = getMain().getWidth()-left-1;
		height = getMain().getHeight()-top-1;
		int a = (width-4*5)/3;
		int b = (height-6*5)/5;
		box = (a<=b ? a : b );
	}

	public void draw() {
		//Hintergrund
		Graphics g = getBackbuffer();
		g.setColor(backgroundColor);
		g.fillRect(left, top, width, height);
		
		//AKWs
		drawImageRand(g, RessourceManager.akw_1, left+5, top+5, box, box);
		drawImageRand(g, RessourceManager.akw_2, left+10+box, top+5, box, box);
		drawImageRand(g, RessourceManager.akw_3, left+15+2*box, top+5, box, box);
		
		//Kohle
		drawImageRand(g, RessourceManager.kohle, left+5, top+10+box, box, box);
		
		//Solar
		drawImageRand(g, RessourceManager.solar, left+5, top+15+2*box, box, box);
		
		//Wind
		drawImageRand(g, RessourceManager.windrad, left+5, top+20+3*box, box, box);
		
		//Staudamm
		drawImageRand(g, RessourceManager.staudamm, left+5, top+25+4*box, box, box);
	}

	private void drawImageRand(Graphics g, Image img,int left, int top, int width, int height){
		g.drawImage(img, left, top, width, height, null);
		g.setColor(Color.BLACK);
		g.drawRect(left, top, width, height);
	}
	
	public void click(int x, int y) {
		getMain().getGraphic().getMap().getGrid().highlightUnderground(AKW.underground);
	}
	public void mouseOver(int x, int y) {
		//1. Spalte
		if(x>=left+5 && x<=left+5+box){
			//AKW 1
			if(y>=top+5 && y<=top+5+box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(AKW.underground);
				return;
			}
			//Kohle
			else if(y>=top+10+box && y<=top+10+2*box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(Kohle.underground);
				return;
			}
			//Solar
			else if(y>=top+15+2*box && y<=top+15+3*box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(Solar.underground);
				return;
			}
			//Wind
			else if(y>=top+20+3*box && y<=top+20+4*box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(Windrad.underground);
				return;
			}
			//Staudamm
			else if(y>=top+25+4*box && y<=top+25+5*box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(Staudamm.underground);
				return;
			}
		}
		//2. Spalte
		else if(x>=left+10+box && x<=left+10+2*box){
			//AKW 2
			if(y>=top+5 && y<=top+5+box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(AKW.underground);
				return;
			}
		}
		//3. Spalte
		else if(x>=left+15+2*box && x<=left+15+3*box){
			//AKW 3
			if(y>=top+5 && y<=top+5+box){
				getMain().getGraphic().getMap().getGrid().highlightUnderground(AKW.underground);
				return;
			}
		}
		getMain().getGraphic().getMap().getGrid().highlightUnderground(0);
	}
	public void mouseOut() {
		getMain().getGraphic().getMap().getGrid().highlightUnderground(0);
	}

	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
}
