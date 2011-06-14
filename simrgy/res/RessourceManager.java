package simrgy.res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.ImageIcon;

public class RessourceManager {

	/**
	 * 
	 * Music
	 * 
	 */
		
	//zum einmaligem laden beim systemstart, und nicht immer wieder neu w‰rend des progs
	/*
	private static byte[] getMusic(String filename){
		byte[] result = null;
		try {
			InputStream is = RessourceManager.class.getResourceAsStream(filename);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int next;
			next = is.read();
			while (next > -1) {
				bos.write(next);
				next = is.read();
			}
			bos.flush();
			result = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Background Music
	private static final byte[][] bg_music = {
		getMusic("music/10 - The heaven is not so far.mp3"),	// Dj Fab - The heaven is not so far - cc by-nc-sa 2.0 fr
		getMusic("music/01 - rebirth.mp3"),						// stefano mocini - rebirth - cc by-sa 3.0
		getMusic("music/02 - Swashing the buck.mp3"),			// Celestial Aeon Project - Swashing the buck - cc by-nc-sa 3.0
		getMusic("music/07 - Nubes.mp3"),						// Xera - —ubes - cc by-nc-nd 3.0
		getMusic("music/01 - Shoreless.mp3")					// Adult Only - Shoreless - cc by-nc-sa 3.0
	};
	
	*/
	
	private static final String[] bg_music = {
		"music/10 - The heaven is not so far.mp3",	// Dj Fab - The heaven is not so far - cc by-nc-sa 2.0 fr
		"music/01 - rebirth.mp3",					// stefano mocini - rebirth - cc by-sa 3.0
		"music/02 - Swashing the buck.mp3",			// Celestial Aeon Project - Swashing the buck - cc by-nc-sa 3.0
		"music/07 - Nubes.mp3",						// Xera - —ubes - cc by-nc-nd 3.0
		"music/01 - Shoreless.mp3"					// Adult Only - Shoreless - cc by-nc-sa 3.0
	};
	
	public static URL sAKWUnfall = RessourceManager.class.getResource("sounds/akw_warnung.wav");
	public static URL sClick = RessourceManager.class.getResource("sounds/click.wav");
	public static String sIntro = "sounds/intro.mp3";
	
	//public static URL vAtom = RessourceManager.class.getResource("explosion.mpg");
	
	public static String getRandomBackgroundMusic(){
		return bg_music[new Random().nextInt(bg_music.length)];
	}
	
	
	/**
	 * 
	 * Images
	 * 
	 */
	
	public static final Image map = new ImageIcon(RessourceManager.class.getResource("img/map.png")).getImage();
	
	public static final Image rcl = new ImageIcon(RessourceManager.class.getResource("img/rcl.jpg")).getImage();
	
	public static final Image none = new ImageIcon(RessourceManager.class.getResource("img/buildings/none.png")).getImage();
	
	public static final Image hq = new ImageIcon(RessourceManager.class.getResource("img/buildings/hq.png")).getImage();
	
	public static final Image akw_0 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_0.png")).getImage();
	public static final Image akw_1 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_1.png")).getImage();
	public static final Image akw_2 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_2.png")).getImage();
	public static final Image akw_3 = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_3.png")).getImage();
	
	public static final Image akw_unfall = new ImageIcon(RessourceManager.class.getResource("img/buildings/akw_unfall.png")).getImage();
	
	public static final Image windrad = new ImageIcon(RessourceManager.class.getResource("img/buildings/windrad.png")).getImage();
	
	public static final Image kohle = new ImageIcon(RessourceManager.class.getResource("img/buildings/kohle.png")).getImage();
	
	public static final Image solar = new ImageIcon(RessourceManager.class.getResource("img/buildings/solar.png")).getImage();
	
	public static final Image staudamm = new ImageIcon(RessourceManager.class.getResource("img/buildings/staudamm.png")).getImage();
	public static final Image laufwasser = new ImageIcon(RessourceManager.class.getResource("img/buildings/laufwasserkraftwerk.png")).getImage();
	
	public static final Image rUran = new ImageIcon(RessourceManager.class.getResource("img/research/uran.png")).getImage();
	public static final Image rSarkophag = new ImageIcon(RessourceManager.class.getResource("img/research/sarkophag.png")).getImage();
	public static final Image rEndlager = new ImageIcon(RessourceManager.class.getResource("img/research/endlager.png")).getImage();
	public static final Image rGlueh = new ImageIcon(RessourceManager.class.getResource("img/research/glueh.png")).getImage();
	public static final Image rWind = new ImageIcon(RessourceManager.class.getResource("img/research/2xwindrad.png")).getImage();
	public static final Image rKohle = new ImageIcon(RessourceManager.class.getResource("img/research/kohle.png")).getImage();
	public static final Image rKohleCO2 = new ImageIcon(RessourceManager.class.getResource("img/research/co2filter.png")).getImage();
	public static final Image rStaudamm = new ImageIcon(RessourceManager.class.getResource("img/research/extstaudamm.png")).getImage();
	public static final Image rSolar = new ImageIcon(RessourceManager.class.getResource("img/research/solarpanel.png")).getImage();
	public static final Image rSonne = new ImageIcon(RessourceManager.class.getResource("img/research/sonne.png")).getImage();
	
	public static final Image rNetz = new ImageIcon(RessourceManager.class.getResource("img/research/effizientes_netz.png")).getImage();
	public static final Image rPlusSolar = new ImageIcon(RessourceManager.class.getResource("img/research/plus_solarpanel.png")).getImage();
	public static final Image rPlusWind = new ImageIcon(RessourceManager.class.getResource("img/research/plus_windrad.png")).getImage();
	
	/**
	 * 
	 * Fonts
	 * 
	 */
	
	//Standard Fonts
	public static final Font f_h14 = new Font("Helvetica", Font.PLAIN, 14);
	public static final Font f_h15 = new Font("Helvetica", Font.PLAIN, 15);
	public static final Font f_h16 = new Font("Helvetica", Font.PLAIN, 16);
	public static final Font f_h17 = new Font("Helvetica", Font.PLAIN, 17);
	public static final Font f_h18 = new Font("Helvetica", Font.PLAIN, 18);
	public static final Font f_h19 = new Font("Helvetica", Font.PLAIN, 19);
	public static final Font f_h20 = new Font("Helvetica", Font.PLAIN, 20);
	public static final Font f_h28 = new Font("Helvetica", Font.PLAIN, 28);
	public static final Font f_h30 = new Font("Helvetica", Font.PLAIN, 30);
	public static final Font f_h48 = new Font("Helvetica", Font.PLAIN, 48);
	
	//Forms Fonts
	public static final Font f_money = f_h30;
	
	public static final Font f_menu_button = f_h28;
	public static final Font f_menu_caption = f_h48;
	public static final Font f_menu_smaltext = f_h16;
	public static final Font f_menu_medtext = f_h18;
	public static final Font f_menu_bigtext = f_h20;
	
	public static final Font f_build_caption = f_h17;
	public static final Font f_build_text = f_h14;
	
	public static final Font f_research_caption = f_h17;
	public static final Font f_research_text = f_h14;
	
	public static final Font f_gridobj_nameplate = f_h16;
	
	public static final Font f_grid_prozent = f_h18;
	
	public static final Font f_rclick_text = f_h14;
	public static final Font f_rclick_caption = f_h18;
	public static final Font f_rclick_button = f_h14;
	
	public static final Font f_tabselect_tabfont = f_h18;
	
	public static final Font f_status = f_h14;
	
	
	//Float -> String Formate
	public static final DecimalFormat df_money = (DecimalFormat) DecimalFormat.getInstance(Locale.GERMAN);
	public static final DecimalFormat df_int = new DecimalFormat("0");
	public static final DecimalFormat df_float1 = new DecimalFormat("0.0");
	public static final DecimalFormat df_float2 = new DecimalFormat("0.00");
	
	
	
	
	
	/**
	 * Ausmaﬂe eines gezeichneten Strings
	 * @param g Backbuffer
	 * @param f Font
	 * @param s String
	 * @return [0]=height, [1]=width
	 */
	public static int[] f_size(Graphics g, Font f, String s){
		Rectangle2D bounds = new TextLayout(s, f, ((Graphics2D)g).getFontRenderContext()).getBounds();
		return new int[] { (int) Math.ceil(bounds.getHeight()) , (int) Math.ceil(bounds.getWidth()) };
	}
	
	
	
	
	
	/**
	 * 
	 * Colors
	 * 
	 */
	
	//Standardfarben
	public static final Color cBlack = Color.BLACK;
	public static final Color cWhite = Color.WHITE;
	public static final Color cRed = Color.RED;
	public static final Color cGreen = Color.GREEN;
	public static final Color cBlue = Color.BLUE;
	public static final Color cYellow = Color.YELLOW;
	public static final Color cOrange = Color.ORANGE;
	public static final Color cLightGray = Color.LIGHT_GRAY;
	
	//Spezielle Farben
	public static final Color cMelon = new Color(0xE3A869);
	public static final Color cBabyBlue = new Color(0x89CFF0);
	public static final Color cDarkOrange = new Color(0xFF8C00);
	public static final Color cCatEye = new Color(0xBEE554);
	
	//Transparente Farben
	public static final Color cGreen30 = new Color(0, 1, 0, 0.3f);
	public static final Color cGreen60 = new Color(0, 1, 0, 0.6f);
	public static final Color cRed30 = new Color(1, 0, 0, 0.3f);
	public static final Color cRed60 = new Color(1, 0, 0, 0.6f);
	public static final Color cYellow30 = new Color(1, 1, 0, 0.3f);
	public static final Color cYellow60 = new Color(1, 1, 0, 0.6f);
	public static final Color cBlack30 = new Color(0, 0, 0, 0.3f);
	public static final Color cBlack60 = new Color(0, 0, 0, 0.6f);
	
	
	//Forms Farben
	public static final Color c_gui_bg = cMelon;
	
	public static final Color c_grid_nichtbaubar = cBlack60;
	
	public static final Color c_tabselect_bg = cMelon;
	public static final Color c_tabselect_highlight = cDarkOrange;
	
	public static final Color c_menu_bg = cWhite;
	public static final Color c_menu_rand = cBlack;
	public static final Color c_menu_caption = cBlack;
	public static final Color c_menu_text = cBlack;
	public static final Color c_menu_button_text = cBlack;
	public static final Color c_menu_button_highlight = cGreen;
	public static final Color c_menu_label_highlight = cBlue;
	
	public static final Color c_rclick_rand = cBlack;
	public static final Color c_rclick_bg = cWhite;
	public static final Color c_rclick_text = cBlack;
	public static final Color c_rclick_caption = cBlack;
	public static final Color c_rclick_button_highlight = cGreen;
	
	public static final Color c_build_bg = cCatEye;
	public static final Color c_build_highlight = cGreen30; 
	public static final Color c_build_mouseOver_bg = cYellow;
	public static final Color c_build_mouseOver_rand = cBlack;
	public static final Color c_build_mouseOver_caption = cBlack;

	public static final Color c_research_bg = cBabyBlue;
	public static final Color c_research_highlight = cGreen30; 
	public static final Color c_research_mouseOver_bg = cYellow;
	public static final Color c_research_mouseOver_rand = cBlack;
	public static final Color c_research_mouseOver_caption = cBlack;
	public static final Color c_research_mouseOver_text = cBlack;
	public static final Color c_research_todo = cBlack;
	public static final Color c_research_done = cBabyBlue.darker();
	
	public static final Color c_gui_strom_inland = cYellow;
	public static final Color c_gui_strom_ausland_einkauf = cRed;
	public static final Color c_gui_strom_ausland_verkauf = cGreen;
	
	public static final Color c_gridobj_build_todo = cLightGray;
	public static final Color c_gridobj_build_done = cGreen;
	public static final Color c_gridobj_build_deploy = cRed;
	public static final Color c_gridobj_name = cBlack;
	public static final Color c_gridobj_namebg = cWhite;
	
}
