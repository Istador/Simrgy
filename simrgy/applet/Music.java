package simrgy.applet;

//import java.io.*;
import java.applet.*;

import javax.media.*;

public class Music {
	
	private static Player mp;
	
	public static void play(Applet app, String filename){
		try{
			//if(mp!=null) mp.stop();
			//mp = Manager.createPlayer(app.getClass().getResource("../res/music/"+filename));
			//mp.start();
			
			//nur wav
			//app.play(app.getClass().getResource("../res/music/"+filename));
		}
		catch(Exception e){System.out.println("Error with music");}
	}
}
