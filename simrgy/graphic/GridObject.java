package simrgy.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import simrgy.game.Building;

public /*abstract*/ class GridObject {
	private int left;
	private int top;
	private int width;
	private int height;
	private Building building;
	private boolean showName;
	private Color highlightColor;
	
	protected GridObject(int left, int top, int width, int height, Building building){
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.building = building;
		this.showName = false;	
		highlightColor = null;
		building.attachGridObject(this);
	}
	
	public void draw(Graphics g){
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
			if (strleft+strwidth+1>Map.getInstance().getWidth()) strleft=Map.getInstance().getWidth()-strwidth-1; //rechts
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
	public void mouseOver(){highlightYellow(); this.showName = true;}
	public void mouseOut(){highlightNone(); this.showName = false;}
	
	public void highlightRed(){highlightColor=Color.RED;}
	public void highlightYellow(){highlightColor=Color.YELLOW;}
	public void highlightNone(){highlightColor=null;}
}
