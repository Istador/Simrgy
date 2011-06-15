package simrgy.graphic.gui;

import simrgy.applet.*;
import simrgy.graphic.GraphicObject;
import static simrgy.res.RessourceManager.*;

import java.awt.*;
import java.awt.event.KeyEvent;


public class StatusTab implements GraphicObject {
	public int top;
	public int left;
	public int width;
	public int height;
	
	private GUI gui;
	
	public StatusTab(GUI g){
		gui = g;
		top   = 61;
		left  = getGUI().left+26;
		width = getMain().getWidth()-left-1;
		height = getMain().getHeight()-top-1;
	}	

	public void draw() {
		Graphics g = getBackbuffer();
		g.setFont(f_status);
		g.setColor(cBlack);
		
		int ttop = top+15;
		int tleft = left+5;
		int tright = left+130;
		
		//Anzeige als Xh Ym Zs
		long time = getMain().getGame().time/1000;
		int s = (int)(time%60);
		int m = (int)(((time-s)/60)%60);		
		int h = (int)(((time-s)/60-m)/60);
		g.drawString("Zeit:", tleft, ttop);
		g.drawString(String.valueOf(h)+"h "+String.valueOf(m)+"m "+String.valueOf(s)+"s", tright, ttop);
		
		ttop+=35;
		
		g.drawString("Windstärke:", tleft, ttop); //max 74 km/h = stürmischer Wind
		g.drawString(df_float1.format(getMain().getGame().windpower*74.0)+" km/h", tright, ttop);
		ttop+=20;
        g.drawString("Sonnenintensität:", tleft, ttop);
        g.drawString(df_int.format(getMain().getGame().sonnenintensitaet*100.0)+" %", tright, ttop);
		
		ttop+=35;
		
		g.drawString("Strombedarf:", tleft, ttop);
		g.drawString(df_int.format(getMain().getGame().strombedarf)+" MW", tright, ttop);
		ttop+=20;
		g.drawString("Stromerzeugung:", tleft, ttop);
		g.drawString(df_int.format(getMain().getGame().mw)+" MW", tright, ttop);
		ttop+=20;
		g.drawString("Stromeinkauf:", tleft, ttop);
		double einkauf = getMain().getGame().strombedarf - getMain().getGame().mw;
		einkauf = ( einkauf >= 0.0 ? einkauf : 0.0);
		g.drawString(df_int.format(einkauf)+" MW", tright, ttop);
		ttop+=20;
		g.drawString("Atomkraft:", tleft, ttop);
		if(getMain().getGame().uran==0) g.setColor(cRed);
		g.drawString(df_int.format(getMain().getGame().mw_atom)+" MW", tright, ttop);
		g.setColor(cBlack);
		ttop+=20;
		g.drawString("Windkraft:", tleft, ttop);
		g.drawString(df_int.format(getMain().getGame().mw_wind)+" MW", tright, ttop);
		ttop+=20;
        g.drawString("Kohlekraft:", tleft, ttop);
        if(getMain().getGame().kohle==0) g.setColor(cRed);
        g.drawString(df_int.format(getMain().getGame().mw_kohle)+" MW", tright, ttop);
        g.setColor(cBlack);
        ttop+=20;
        g.drawString("Sonnenkraft:", tleft, ttop);
        g.drawString(df_int.format(getMain().getGame().mw_sonne)+" MW", tright, ttop);
        ttop+=20;
        g.drawString("Wasserkraft:", tleft, ttop);
        g.drawString(df_int.format(getMain().getGame().mw_wasser)+" MW", tright, ttop);
       
        
        ttop+=35;
        g.drawString("Uran:", tleft, ttop);
        if(getMain().getGame().uran==0) g.setColor(cRed);
        g.drawString(df_int.format((int)getMain().getGame().uran)+"/"+df_int.format((int)getMain().getGame().uran_max)+" kg", tright-14, ttop);
        g.setColor(cBlack);
        ttop+=20;
        g.drawString("Kohle:", tleft, ttop);
        if(getMain().getGame().kohle==0) g.setColor(cRed);
        g.drawString(df_int.format(getMain().getGame().kohle)+"/"+df_int.format(getMain().getGame().kohle_max)+" kg", tright-14, ttop);
        g.setColor(cBlack);
        ttop+=20;
        g.drawString("Atommüll:", tleft, ttop);
        g.drawString(df_int.format((int)getMain().getGame().uran_max-(int)getMain().getGame().uran)+" kg", tright-14, ttop);
        
        ttop+=35;
        g.drawString("Gewinn:", tleft, ttop);
        g.drawString(df_money.format((long)getMain().getGame().gewinn)+" €/s", tright, ttop);
        ttop+=20;
        g.drawString("Inland Verkauf:", tleft, ttop);
        g.drawString(df_money.format((long)getMain().getGame().gewinn_inland)+" €/s", tright, ttop);
        ttop+=20;
        g.drawString("Ausland Verkauf:", tleft, ttop);
        g.drawString(df_money.format((long)getMain().getGame().gewinn_ausland)+" €/s", tright, ttop);
        ttop+=20;
        g.drawString("Ausland Einkauf:", tleft, ttop);
        g.drawString(df_money.format((long)(getMain().getGame().verlust_ausland*-1.0))+" €/s", tright, ttop);
        ttop+=20;
        g.drawString("Gebäudekosten:", tleft, ttop);
        g.drawString(df_money.format((long)(getMain().getGame().verlust_gebauedekosten*-1.0))+" €/s", tright, ttop);
        
        ttop+=35;
        g.drawString("CO2 Ausstoß:", tleft, ttop);
        g.drawString(df_int.format(getMain().getGame().CO2)+" kg", tright, ttop);
        
        ttop+=35;
        g.drawString("Zufriedenheit:", tleft, ttop);
        g.drawString(df_int.format(getMain().getGame().getZufriedenheit()*100.0)+" %", tright, ttop);
        
	}

	public void click(int x, int y) {}
	public void mouseOver(int x, int y) {}
	public void mouseOut(){}
	
	public GUI getGUI(){return gui;}
	public Main getMain() { return getGUI().getMain(); }
	public Graphics getBackbuffer() { return getGUI().getBackbuffer(); }
	public void keyPress(KeyEvent ke){}
}
