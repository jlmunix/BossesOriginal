package com.tecoloteinc.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.tecoloteinc.bosses.Sprites.Other.Dialogs;

/**
 * This class implements the main menu, the first thing that appears on the screen
 * when the game is open, options like start game, options menu are to be here.
 */
public class BossesMenu extends GameEngine {

    float time = 0;

    //protected GamePlayer Link = new GamePlayer(this, 242, 333);

    Sound LinkHurt = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/LTTP_Link_Hurt.wav"));

//    Dialogs dialog2 = new Dialogs(this, "It's Dangerous to go outside Be careful!");
//    Dialogs dialog3 = new Dialogs(this, "I'm Ed Schultz talking with  You over Basa-telepathy");
//    Dialogs dialog4 = new Dialogs(this, "Did you fill IPL?  I'll be watching you (SKI)");
//    Dialogs dialog5 = new Dialogs(this, "Look a link face: @");
//    Dialogs dialog6 = new Dialogs(this, "It's to late, go to sleep");

    /**
     * Constructor, receives BossesMain object to keep visibility with
     * the main game.
     *
     * @param scr_width  @TODO
     * @param scr_height @TODO
     */
    public BossesMenu(int scr_width, int scr_height) {
        super(scr_width, scr_height);
    }

    @Override
    public void OnCreate() {
        /*************************************************************
         * This section is used to manage the background image
         * @TODO Remove this and use MAPS.
         *************************************************************/
        //setDebugMode();
        loadMap("HeroHouse.tmx");

    }

    /**
     * This method is the main method for the Screen, do here what this Screen should be doing
     *
     * @param delta time between each call to this function.
     */
    @Override
    public void game_loop(float delta) {


    }
}
