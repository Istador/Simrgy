package simrgy.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import simrgy.game.Action;
import simrgy.game.Building;

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
			
			protected Font f_caption;
			protected Font f_button;
			
			protected String caption;
			int caption_height;
			int caption_width;
			
			protected Action over = null;
			
			protected java.util.Map<Action,Rectangle2D> actions;
			
			public void init(){
				caption =  building.getName();
				f_caption = new Font("Helvetica", Font.PLAIN, 18);
				f_button = new Font("Helvetica", Font.PLAIN, 14);
				
				Rectangle2D bounds = new TextLayout(caption, f_caption, ((Graphics2D)getBackbuffer()).getFontRenderContext()).getBounds();
				caption_height = (int) Math.ceil(bounds.getHeight());
				caption_width = (int) Math.ceil(bounds.getWidth());
				
				actions = new java.util.HashMap<Action,Rectangle2D>();
				calc_constrains();
			}
			
			protected void calc_constrains(){
				int tmp_width= caption_width;
				for(Action a : building.getActions()){
					Rectangle2D bounds = new TextLayout(a.getName(), f_button, ((Graphics2D)getBackbuffer()).getFontRenderContext()).getBounds();
					int name_width = (int) Math.ceil(bounds.getWidth())+10;
					if(tmp_width<name_width) tmp_width = name_width;
				}
				
				width = tmp_width + 30;
				height = 25+caption_height+5+25*building.getActions().size();
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
				
				int btop = top + 10 + caption_height + 10;
				int bheight = 20;
				for(Action a : building.getActions()){
					Rectangle b = new Rectangle();
					b.setSize(tmp_width, bheight);
					b.setLocation(left+(width-tmp_width)/2, btop);					
					actions.put(a, b);
					btop+=25;
				}
			}
			
			public void draw() {
				init();
				Graphics g = getBackbuffer(); 
				g.setColor(Color.BLACK);
				g.fillRect(left, top, width, height);
				g.setColor(Color.WHITE);
				g.fillRect(left+10, top+10, width-20, height-20);
				g.setColor(Color.BLACK);
				g.setFont(f_caption);
				g.drawString(caption, left+(width-caption_width)/2, top+10+5+caption_height);

				//Draw Actions
				for(Action a : actions.keySet()){
					g.setFont(f_button);
					Rectangle2D b = actions.get(a);
					if(a==over){
						g.setColor(Color.GREEN);
						g.fillRect((int)b.getX(), (int)b.getY(), (int)b.getWidth(), (int)b.getHeight());
					}
					g.setColor(Color.BLACK);
					g.drawRect((int)b.getX(), (int)b.getY(), (int)b.getWidth(), (int)b.getHeight());
					String name = a.getName();
					Rectangle2D bounds = new TextLayout(name, f_button, ((Graphics2D)g).getFontRenderContext()).getBounds();
					int name_height = (int) Math.ceil(bounds.getHeight());
					int name_width = (int) Math.ceil(bounds.getWidth());
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
	
	
	public String getName(){
		return "Aktionsauswahl";
	}
}
