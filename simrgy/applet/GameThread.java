package simrgy.applet;


public class GameThread extends Thread{
	protected Main main;
	
	protected boolean running = false;
	
	public GameThread(Main m){
		this.main=m;
	}
	
	public void run() {		
		running=true;
		long lastTime = System.currentTimeMillis();
		while(running){
			long currTime = System.currentTimeMillis();
			long timeDiff = currTime - lastTime;
			lastTime = currTime;
			if(timeDiff >= 0)
				{
				//60s = 1 Jahr (Bauzeit, Geld, Strom)
				//240s = 1 Tag (Sonne) stimmt nicht mehr
				if(main.getGraphic().getSettings().speed) timeDiff = timeDiff * 2;
				main.getGame().tick(timeDiff);
				main.repaint();
				}
			try {
				//Bei nem "Browsergame" mann muss ja auch net unbedingt 100% CPU verbrauchen... (Laptop Akku anyone?!)
				//Optimal: 40 - 50 ms (-> 25-20 FPS) beim Spielen
				//albern zu hoch wenn pausiert. -> 5 FPS bei Pause
				
				//ausgehend vom letzten Zeitbedarf, die sleep-time dynamisch anpassen
				//(ab einer Berechnungszeit von > 50ms könnte das bild merklich anfangen zu laggen).
				
				long tmp1 = (timeDiff-( main.getGame().running ? 50 : 200 ) ); //defizite beim sleep/lag kompensieren
				tmp1 = tmp1<0 ? 0 : tmp1;
				long tmp2 = (System.currentTimeMillis() - currTime); //Berechnungszeit für main.getGraphic().tick() und main.repaint() angenommen fürs nächste mal
				long sleeping = ( main.getGame().running ? 50 : 200 ) - tmp1 - tmp2; 
				if(sleeping>0)
					sleep(sleeping);
			} catch (Exception e) {}
		}
	}


    public void done() {
    	running = false;
    }
    
    public boolean isRunning(){
    	return running;
    }
}
