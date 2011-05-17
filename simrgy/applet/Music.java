package simrgy.applet;

import java.io.ByteArrayInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class Music extends Thread {
	
	private Player mp;
	@SuppressWarnings("unused")	private Music(){}
	public Music(Player mp){
		this.mp = mp;
	}
	public void run(){
		try {
			mp.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close(){
		mp.close();
	}
	
	private static Music thread;
	public static void play_music(byte[] file){
		stop_music();
		try {
			thread = new Music(new Player(new ByteArrayInputStream(file)));
			thread.start();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public static void stop_music(){
		if(thread!=null){
			thread.close();
			thread = null;
		}
	}
}
