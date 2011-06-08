package simrgy.graphic;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import simrgy.game.Action;
import simrgy.game.Building;
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
				
				
				width = tmp_width + 30;
				height = 25+caption_height+5+25*size;
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
				
				int btop = top + 10 + caption_height + 10;
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
						if(b.contains(x, y)) a.run(building);
					}
				}
				else{
					getMain().getGraphic().removeOverlay();
					getMain().getGame().pause();
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
			}

		};
		b.getGame().pause();
		b.getGame().getMain().getGraphic().setOverlay(go);
	}
	
	
	public String getName(Building b){
		return "Aktionsauswahl";
	}
	
	public boolean isPossible(Building b) {
		return true;
	}
}
