package simrgy.game.actions;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import static simrgy.res.RessourceManager.*;

import simrgy.game.Action;
import simrgy.game.Building;
import simrgy.graphic.OverlayBuilding;
import simrgy.graphic.GraphicObject;

public class Rename implements Action {

	//Singleton
	private Rename(){}
	protected static Action instance = null;
	public static Action getInstance(){
		if(instance==null) instance = new Rename();
		return instance;
	}
	
	public void run(Building b) {
		if( !isPossible(b) ) return;

		/**
		 * Klassen Start
		 */
		GraphicObject go = new OverlayBuilding(b) {
			int top;
			int left;
			int width;
			int height;
						
			protected String name;
			int name_width;
			protected String caption;
			int caption_height;
			int caption_width;
			
			public void init(){
				name =  building.getName();
				caption = "\""+name+"\" Umbenennen";
				
				int[] f = f_size(getBackbuffer(), f_menu_medtext, caption);
				caption_height = f[0];
				caption_width = f[1]; 
				
				calc_constrains();
			}
			
			protected void calc_constrains(){
				name_width = f_size(getBackbuffer(), f_menu_smaltext, name)[1]; 
				
				int tmp_width = ( name_width>caption_width ? name_width : caption_width );
				width = tmp_width + 30;
				height = 100;
				top = getMain().height/2-height/2;
				left = getMain().width/2-width/2;
			}
			
			public void draw() {
				Graphics g = getBackbuffer(); 
				g.setColor(cBlack);
				g.fillRect(left, top, width, height);
				g.setColor(cWhite);
				g.fillRect(left+10, top+10, width-20, height-20);
				g.setColor(cBlack);
				g.setFont(f_menu_medtext);
				g.drawString(caption, left+(width-caption_width)/2, top+10+5+caption_height);
				g.setFont(f_menu_smaltext); 
				g.drawString(name, left+(width-name_width)/2, top+60);
			}

			public void click(int x, int y) {
				//im Fenster
				if(x>=left && x<=left+width && y>=top && y<=top+height){
					//System.out.println("Overlay click");
				}
				else{
					getMain().getGraphic().removeOverlay();
					getMain().getGame().pause();
				}
			}

			public void mouseOver(int x, int y) {
			}

			public void keyPress(KeyEvent ke){
				int kc = ke.getKeyCode();
				if(kc==KeyEvent.VK_BACK_SPACE && name.length()!=0 ){
					name = name.substring(0, name.length()-1);
					calc_constrains();
				}
				else if (kc==KeyEvent.VK_ENTER && name.length()!=0) {
					building.setName(name);
					getMain().getGraphic().removeOverlay();
					getMain().getGame().pause();
				}
				else if (kc==KeyEvent.VK_ESCAPE) {
					getMain().getGraphic().removeOverlay();
					getMain().getGame().pause();
				}
				else if(kc>=0x20 && kc<=0x7e && name.length()<=30){
					name+=ke.getKeyChar();
					calc_constrains();
				}
			}

		};
		/**
		 * Klassen Ende
		 */
		b.getGame().getMain().getGraphic().setOverlay(go);
	}

	
	public String getName(){
		return "Umbenennen";
	}
	
	public boolean isPossible(Building b){
		return b.getActions().contains(instance);
	}
	
}
