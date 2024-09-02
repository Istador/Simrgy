package simrgy.graphic;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.game.actions.*;
import simrgy.game.buildings.AKW;
import simrgy.game.buildings.HQ;
import static simrgy.res.RessourceManager.*;

public class RechtsklickTab implements Action{
	//Singleton
	private RechtsklickTab(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new RechtsklickTab();
		return instance;
	}
	
	public void run(Building b) {
		GraphicObject go = new OverlayBuilding(b) {
			int top;
			int left;
			int width;
			int height;
			
			protected String caption;
			int caption_height;
			int caption_width;
			
			protected Action over = null;
			
			protected java.util.Map<Action,Rectangle> actions;
			
			public void init(){
				caption =  building.getName();
				
				int[] b = f_size(getBackbuffer(), f_rclick_caption, caption);
				caption_height = b[0];
				caption_width = b[1];
				
				actions = new java.util.HashMap<Action,Rectangle>();
				calc_constrains();
			}
			
			protected void calc_constrains(){
				int tmp_width= caption_width;
				int size = 0;
				for(Action a : building.getActions()){
					if(a.isPossible(building)){
						size++;
						int name_width = f_size(getBackbuffer(), f_rclick_button, a.getName(building))[1] + 10;
						if(tmp_width<name_width) tmp_width = name_width;
					}
				}
				int min_width = ( building instanceof AKW ? 235 : 220 );
				tmp_width = ( tmp_width<min_width ? min_width : tmp_width );

				int akw_height = ( building instanceof AKW ? 15 : 0 );
				
				width = tmp_width + 30;
				height = 25+caption_height+5+25*size+65 + akw_height ;
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
				
				int btop = top + 10 + caption_height + 10 + 65 + akw_height;
				int bheight = 20;
				for(Action a : building.getActions()){
					if(a.isPossible(building)){
						Rectangle b = new Rectangle();
						b.setSize(tmp_width, bheight);
						b.setLocation(left+(width-tmp_width)/2, btop);					
						actions.put(a, b);
						btop+=25;
					}
				}	
				
				if(building instanceof AKW){
					//Personalveränderungen
					Rectangle b = new Rectangle();
					b.setSize(15, 15);
					b.setLocation(left+15, top+10+5+caption_height+5+40+20);					
					actions.put(DecPersonal.getInstance(), b);
					b = new Rectangle();
					b.setSize(15, 15);
					b.setLocation(left+15+20, top+10+5+caption_height+5+40+20);					
					actions.put(IncPersonal.getInstance(), b);
					
					//Leistungsveränderungen
					b = new Rectangle();
					b.setSize(15, 15);
					b.setLocation(left+155, top+10+5+caption_height+5+40+20);					
					actions.put(DecLeistung.getInstance(), b);
					b = new Rectangle();
					b.setSize(15, 15);
					b.setLocation(left+155+20, top+10+5+caption_height+5+40+20);					
					actions.put(IncLeistung.getInstance(), b);
				}
				else if(building instanceof HQ){
					//Strompreisveränderungen
					Rectangle b = new Rectangle();
					b.setSize(15, 15);
					b.setLocation(left+155, top+10+5+caption_height+5+30);					
					actions.put(DecStrompreis.getInstance(), b);
					b = new Rectangle();
					b.setSize(15, 15);
					b.setLocation(left+155+20, top+10+5+caption_height+5+30);					
					actions.put(IncStrompreis.getInstance(), b);
				}
			}
			
			public void draw() {
				init();
				Graphics g = getBackbuffer(); 
				g.setColor(c_rclick_rand);
				g.fillRect(left, top, width, height);
				g.setColor(c_rclick_bg);
				g.fillRect(left+10, top+10, width-20, height-20);
				g.setColor(c_rclick_caption);
				g.setFont(f_rclick_caption);
				g.drawString(caption, left+(width-caption_width)/2, top+10+5+caption_height);

				//Draw Building Infos
				g.setFont(f_rclick_text);
				g.setColor(c_rclick_text);
				//Geld
				g.drawString(df_money.format((int)(building.getMW()*building.getGame().getStrompreis() - building.getMoneyCostH()))+" €/s", left+15, top+10+5+caption_height+5+10);
				//MW
				g.drawString((int)building.getMW()+" MW", left+15, top+10+5+caption_height+5+25);
				//Module
				g.drawString(building.getModulesText()+" Module", left+15, top+10+5+caption_height+5+40);
				//Personal
				g.drawString(building.getPersonalText()+" Personal", left+15, top+10+5+caption_height+5+55);
				
				if(!(building instanceof HQ)){
					//Sonne
					g.drawString(String.valueOf((int)(getMain().getGame().getSolarPower(building)*100.0))+"% Sonne", left+155, top+10+5+caption_height+5+10);
					//Wind
					g.drawString(String.valueOf((int)(getMain().getGame().getWindpower(building)*100.0))+"% Wind", left+155, top+10+5+caption_height+5+25);
					//CO2
					g.drawString(df_float1.format(building.getCO2())+"kg CO2", left+155, top+10+5+caption_height+5+40 );
					//Leistung
					if(building instanceof AKW){
						g.drawString(String.valueOf((int)(((AKW)building).getLeistung()*100.0)+"% Leistung"), left+155, top+10+5+caption_height+5+55 );
					}
				}
				else{
					g.drawString("Strompreis:", left+155, top+10+5+caption_height+5+10);
					g.drawString(df_float3.format(getMain().getGame().strompreis)+" €/kWs", left+155, top+10+5+caption_height+5+25 );
				}
				
				//Draw Actions
				for(Action a : actions.keySet()){
					g.setFont(f_rclick_button);
					Rectangle2D b = actions.get(a);
					if(a==over){
						g.setColor(c_rclick_button_highlight);
						g.fillRect((int)b.getX(), (int)b.getY(), (int)b.getWidth(), (int)b.getHeight());
					}
					g.setColor(cBlack);
					g.drawRect((int)b.getX(), (int)b.getY(), (int)b.getWidth(), (int)b.getHeight());
					String name = a.getName(building);
					int[] bound = f_size(g, f_rclick_button, name);
					int name_height = bound[0];
					int name_width = bound[1];
					g.drawString(name, (int)(b.getX()+(b.getWidth()-name_width)/2), (int)(b.getY()+b.getHeight()-(b.getHeight()-name_height)/2) );
				}
			}

			public void click(int x, int y) {
				//im Fenster
				if(x>=left && x<=left+width && y>=top && y<=top+height){
					for(Action a : actions.keySet()){
						Rectangle2D b = actions.get(a);
						if(b.contains(x, y)){
							getMain().play(sClick);
							a.run(building);
						}
					}
				}
				else{
					getMain().getGraphic().removeOverlay();
					getMain().getGraphic().click(x, y);
				}
			}

			public void mouseOver(int x, int y) {
				over = null;
				for(Action a : actions.keySet()){
					Rectangle2D b = actions.get(a);
					if(b.contains(x, y)) over=a;
				}
			}

			public void mouseOut(){
				over = null;
			}
			
			public void keyPress(KeyEvent ke){
				if(ke.getKeyCode()==KeyEvent.VK_ESCAPE)
					getMain().getGraphic().removeOverlay();
			}

		};
		b.getGame().getMain().getGraphic().setOverlay(go);
	}
	
	
	public String getName(Building b){
		return "Aktionsauswahl";
	}
	
	public boolean isPossible(Building b) {
		return true;
	}
}
