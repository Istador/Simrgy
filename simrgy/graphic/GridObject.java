package simrgy.graphic;

import simrgy.applet.*;
import simrgy.game.*;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;



public class GridObject implements GraphicObject {
	
	private Grid grid; //parent
	
	private int left;
	private int top;
	private int width;
	private int height;
	private Building building;
	private boolean showName;
	private Color highlightColor;
	
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
		//Highlight
		if(highlightColor != null){
			g.setColor(highlightColor);
			g.fillRect(left+1, top+1, width-1, height-1);
		}
		//Image
		g.drawImage(building.getImage(), left, top, width, height, null);
		//Name
		if(showName){
			//Schriftart
			String name = building.getName();
			Font f = new Font("Helvetica", Font.PLAIN, 16);
			g.setFont(f);
			//Stringausmaße
			Rectangle2D bounds = new TextLayout(name, f, ((Graphics2D)g).getFontRenderContext()).getBounds();
			int strheight = (int) Math.ceil(bounds.getHeight());
			int strwidth = (int) Math.ceil(bounds.getWidth()); 
			//Gebäude in Zeile 0 -> Namensschild unter dem Gebäude
			int ytop; if(top==0) ytop=height+strheight+2; else ytop=-1;
			//String positionen
			int strtop = top+ytop;
			int strleft = 0;
			if(left==0) strleft=1; //links
			else strleft = width/2-strwidth/2+left; //mittig ausrichten
			if (strleft+strwidth+1>getGrid().getMap().getWidth()) strleft=getGrid().getMap().getWidth()-strwidth-1; //rechts
			//White Hintergrund
			g.setColor(Color.WHITE);
			g.fillRect(strleft-1, strtop-strheight-1, strwidth+2, strheight+2);
			//String zeichnen
			g.setColor(Color.BLACK);
			g.drawString(name, strleft, strtop);
		}
	}
	public void click(){
		System.out.println(building.getName()+" clicked");
	}
	public void click(int x, int y){click();}
	public void mouseOver(){highlightYellow(); this.showName = true;}
	public void mouseOver(int x, int y){mouseOver();}
	public void mouseOut(){highlightNone(); this.showName = false;}
	
	public void highlightRed(){highlightColor=new Color(1,0,0,0.3f);}
	public void highlightYellow(){highlightColor=new Color(1,1,0,0.3f);}
	public void highlightNone(){highlightColor=null;}
	
	public Grid getGrid(){return grid;}
	public Main getMain(){return getGrid().getMain();}
	public Graphics getBackbuffer(){return getGrid().getBackbuffer();}
}
