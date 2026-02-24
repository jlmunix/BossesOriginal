package com.tecoloteinc.bosses;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BossesMain extends Game {

	// Menu for selecting game options I.e: Start Game..
    BossesMenu main_menu;

    //---------------------------------
    // Keep Screen size in this class
    //---------------------------------
    public static int ScreenHeight;
    public static int ScreenWidth;


    /**
     * This class is executed when this class is called,
     * this class is the first thing to be called at
     * initialization of the game.
     */
	@Override
	public void create () {
        // Fill the screen size variables.
        ScreenWidth  = Gdx.graphics.getWidth();
        ScreenHeight = Gdx.graphics.getHeight();
        // Set the main menu as the first Screen.
		/**
         *  Create the Main Menu (Screen that fits our Background Image)
         *  This means maps of (x=23 * y=14) Tiles.
         */
		main_menu = new BossesMenu(368, 224);
		setScreen(main_menu);
	}
}
