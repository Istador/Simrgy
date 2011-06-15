package simrgy.graphic.map;

import simrgy.applet.*;
import simrgy.game.*;
import simrgy.game.buildings.AKW;
import simrgy.graphic.GraphicObject;
import simrgy.graphic.RechtsklickTab;
import simrgy.res.RessourceManager;
import static simrgy.res.RessourceManager.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;



public class GridObject implements GraphicObject {
	
	private Grid grid; //parent
	
	private int left;
	private int top;
	private int width;
	private int height;
	private Building building;
	private boolean showName;
	private java.awt.Color highlightColor;
	
	public GridObject(Grid g, int left, int top, int width, int height, Building building){
		grid = g;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.building = building;
		this.showName = false;	
		highlightColor = null;
		building.attachGridObject(this);
	}
	
	public void draw(){
		Graphics g = getBackbuffer();
		boolean unfall = building instanceof AKW && ((AKW)building).unfall;
		int dgrid = ( getMain().getGraphic().getSettings().drawgrid ? 1 : 0 );
		
		//Highlight
		if(unfall){
			g.setColor(cRed);
			g.fillRect(left+dgrid, top+dgrid, width-dgrid, height-dgrid);
		}
		else if(highlightColor != null){
			g.setColor(highlightColor);
			g.fillRect(left+dgrid, top+dgrid, width-dgrid, height-dgrid);
		}
		//Image
		g.drawImage(building.getImage(), left+dgrid, top+dgrid, width-dgrid, height-dgrid, null);
		//AKW Unfall
		if(unfall){
			g.drawImage(RessourceManager.akw_unfall, left+dgrid, top+dgrid, width-dgrid, height-dgrid, null);
		}
		//Baustatus
		if(building.getBaustatus() < 1.0){
			g.setColor(c_gridobj_build_todo); 
			g.fillRect(left+1, top+height-5, width-1, 5);
			if(building.isDeploying()) g.setColor(c_gridobj_build_deploy);
			else g.setColor(c_gridobj_build_done);
			g.fillRect(left+1, top+height-5, (int)((width-1)*building.getBaustatus()), 5);
			g.setColor(cBlack);
			g.drawRect(left, top+height-6, width-1+dgrid, 6);
		}
		//Modulanzahl
		if(building.drawModules()){
			g.setFont(f_gridobj_nameplate);
			//Stringausmaße
			String str = String.valueOf(building.activeModules());
			int[] f = f_size(g, f_gridobj_nameplate, str);
			int strheight = f[0];
			int strwidth = f[1]; 
			//String positionen
			int strtop = top+strheight+1+dgrid;
			int strleft = left+width-strwidth-1-dgrid;
			//White Hintergrund
			g.setColor(c_gridobj_namebg);
			g.fillRect(strleft-1, strtop-strheight-1, strwidth+2, strheight+2);
			//String zeichnen
			g.setColor(c_gridobj_name);
			g.drawString(str, strleft, strtop);
		}
		
		//Name
		if(showName){
			//Schriftart
			String name = building.getName();
			g.setFont(f_gridobj_nameplate);
			//Stringausmaße
			int[] f = f_size(g, f_gridobj_nameplate, name);
			int strheight = f[0];
			int strwidth = f[1]; 
			//Gebäude in Zeile 0 -> Namensschild unter dem Gebäude
			int ytop; if(top==0) ytop=height+strheight+2; else ytop=-1;
			//String positionen
			int strtop = top+ytop;
			int strleft = 0;
			if(left==0) strleft=1; //links
			else strleft = width/2-strwidth/2+left; //mittig ausrichten
			if (strleft+strwidth+1>getGrid().getMap().getWidth()) strleft=getGrid().getMap().getWidth()-strwidth-1; //rechts
			//White Hintergrund
			g.setColor(c_gridobj_namebg);
			g.fillRect(strleft-1, strtop-strheight-1, strwidth+2, strheight+2);
			//String zeichnen
			g.setColor(c_gridobj_name);
			g.drawString(name, strleft, strtop);
		}
	}
	public void click(){ 
		getMain().play(sClick);
		RechtsklickTab.getInstance().run(building);
	}
	public void click(int x, int y){ if(contains(x,y)) click(); }
	public void mouseOver(){ highlightYellow(); this.showName = true; }
	public void mouseOver(int x, int y){ if(contains(x,y)) mouseOver(); }
	public void mouseOut(){ highlightNone(); this.showName = false; }
	
	public boolean contains(int x, int y){
		return x>=left && x<=left+width && y>=top && y<=top+height;
	}
	
	public void highlightRed(){highlightColor=cRed60;}
	public void highlightYellow(){highlightColor=cYellow30;}
	public void highlightNone(){highlightColor=null;}
	
	public Grid getGrid(){return grid;}
	public Main getMain(){return getGrid().getMain();}
	public Graphics getBackbuffer(){return getGrid().getBackbuffer();}
	public void keyPress(KeyEvent ke){}
}
