package simrgy.applet;

//import java.io.*;
import java.applet.*;

import javax.media.*;

public class Music {
		
	public static void play(Applet app, String filename){
		try{
			Player mp = Manager.createPlayer(app.getClass().getResource("../res/music/"+filename));
			mp.start();
			
			//nur wav
			//app.play(app.getClass().getResource("../res/music/"+filename));
		}
		catch(Exception e){System.out.println("Error with music");}
	}
}
