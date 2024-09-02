package simrgy.applet;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import simrgy.graphic.*;
import simrgy.game.Game;

public class Main extends JApplet implements MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = -7673817554230011101L;

	Graphic graphic;
	private Image backbuffer;
	private Graphics backg;
	private Game game;
	private GameThread gt;
		
	public int top = 0;
	public int left = 0;
	public int min_width = 735;
	public int min_height = 555;
	public int width = 800;
	public int height = 600;
	private Dimension dim = null;
	
	/*
	public static void main(String args[]){
		Main app = new Main();

		//app.setSize(800, 600);
		app.init();
		
		JFrame f = new JFrame("Sim'rgy");
		f.setContentPane(app);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		app.start();
		f.pack();
	}
	*/
	
	public void init(){		
		width = getSize().width;
		height = getSize().height;
		if(width<=min_width)
			width=min_width;
		if(height<=min_height)
			height=min_height;
				
		dim = new Dimension(width, height);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		setSize(width, height);
		
		addMouseListener(this); 
		addMouseMotionListener(this);
		addKeyListener(this);
		//addComponentListener(new ComponentAdapter(){ public void componentResized(ComponentEvent e){ setSize(); } });
		
	}
	
	public void start(){
		//init Graphic, with backbuffer (no flickering)
		//Source: http://profs.etsmtl.ca/mmcguffin/learn/java/07-backbuffer/
		backbuffer = createImage(width, height);
		backg = backbuffer.getGraphics();
		
		graphic = new Graphic(this);
		game = new Game(this);
		
		setSize(width, height);
		
		gt = new GameThread(this);
		gt.start();	
	}
	
	public void stop(){
		gt.done();
	}
	
	public void update( Graphics g ) {
		graphic.draw();
		g.drawImage( backbuffer, 0, 0, null );
	}
	
	public void paint(Graphics g){
		update(g);
	}
	
	public void mouseClicked (MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		getGraphic().click(x, y);
		me.consume();
	}
	public void mouseMoved(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		getGraphic().mouseOver(x, y);
		me.consume();
	} 
	public void mouseExited(MouseEvent me) {
		getGraphic().mouseOut();
		me.consume();
	}
	public void mouseEntered(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
	public void mouseDragged(MouseEvent arg0) {}
	
	public Game getGame(){return game;}
	public Graphic getGraphic(){return graphic;}
	public Graphics getBackbuffer(){return backg;}

	public void keyPressed(KeyEvent ke) {
		getGraphic().keyPress(ke);
		ke.consume();
	}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	
	@Override
	public int getWidth(){
		return width;
	}
	
	@Override
	public int getHeight(){
		return height;
	}
	
	@Override
	public Dimension getSize(){
		if(dim==null)
			return super.getSize();
		else
			return dim;
	}
	
	@Override
	public String getAppletInfo(){
		return "Sim'rgy ist ein Browserspiel, in der du die Kontrolle über die deutsche Energieversorgung übernimmst.\n\nUrheber:\nRobin C. Ladiges\nSebastian Möllmann";
	}
	
}
