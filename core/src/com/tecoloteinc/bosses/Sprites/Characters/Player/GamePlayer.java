package com.tecoloteinc.bosses.Sprites.Characters.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.Sprites.Characters.Character;
import com.tecoloteinc.bosses.Sprites.Items.Weapon;
import com.tecoloteinc.bosses.Sprites.Other.Explosion;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

/**
 * This class is used to handle all stuff related with the GamePlayer.
 */
public class GamePlayer extends Character {
    /**
     * Array of animations supported by our Hero.
     */
    private int[] animation_list = {RIGHT, LEFT, UP, DOWN};

    /**
     * This flag is used to catch and keep the state of the
     * touch. If the touch is pressed, libGDX will not continuously
     * inform us that the finger is touching the screen. This variable
     * controls this in this class.
     */
    private boolean touchedDownFlag;
    /**
     * This is the step link will move, it is
     * separated from the speed variable because
     * can be a partial speed.
     */
    float moving_x = 0, moving_y = 0;

//    /**
//     * States for our player,
//     * these are actually the animations it can
//     * handle.
//     */
//    enum PlayerStates {
//        STATE_STANDBY,
//        STATE_WALKING, // Going for the click
//        STATE_2, // Going to Zero
//        STATE_3  // Die.
//    }

    /**
     * Keeps the life value of the character
     */
    private int player_health;
    /**
     * Keeps the damage it causes to enemies
     */
    private int attack_power;

    static Sound Enemyhurt = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/LTTP_Link_Hurt.wav"));

    /***********************************************************************************************
     * Holding Items
     **********************************************************************************************/
    Weapon sword;

    /*******************************************************
     *                Methods Section
     *******************************************************/

    /**
     * Constructor, it has defined the Character(Super) defaults,
     * that can be changed later in the game.
     *
     * @param game_ref Keeps a reference of the engine.
     * @param x        position X where the player will be located.
     * @param y        position Y where the player will be located.
     *                 It has speed 0.9, it is visible by default and
     *                 blocking to collide with other objects.
     *                 It has 9x4 Frames and has an animation speed
     *                 of 0.03 SPF. The health is 3 and attack power
     *                 is 1, this could be loaded with the save/load
     *                 game function.
     */
    public GamePlayer(GameEngine game_ref, int x, int y) {
        super(game_ref, 1.3F, true, "Player/Player.png", 9, 9, 0.05F);
        setPosition(x, y);
        player_health = 3;
        attack_power = 1;
        setAnimationList(animation_list);
        setSprite_UID("Player");
        game_ref.addSprite(this);
        setSpriteType(SpriteTypes.PLAYER);
        game_ref.camFollowActor(this);
        sword = new Weapon(game_ref, "Items/Weapons/Sword.png", 3, false);
        game_ref.addSprite(sword);
    }

    /*---------------------------------
     *  Mobility
     *--------------------------------*/

    /**
     * This function enables the touchedDownFlag
     * so in the main loop, the hero can know that
     * the finger is still pressing the screen. This allow
     * the hero to keep moving.
     *
     * @param x the position where the hero should go in X
     * @param y the position where the hero should go in Y
     */
    public void touchedDown(float x, float y) {
        moving_x = x;
        moving_y = y;
        touchedDownFlag = true;
    }

    /**
     * This function is executed when the finger stop
     * touching the screen
     */
    public void touchedUp() {
        stop();
        current_h_frame = 0;
        animating();
        touchedDownFlag = false;
        moving_x = 0;
        moving_y = 0;
    }

    /**
     * Represent one of the two on screen buttons.
     *
     * @param button Button pressed.
     */
    public void Button(int button) {
        if (getEngineRef().isGameStopped()) {
            getEngineRef().ButtonPressed(1);
        } else {

            if ((1 == button) && (sword != null)) {
                attack();
                sword.attack(getX(), getY(), getCurrentVerticalAnimation());
            }
            if(0 == button) {
                System.out.print("Aqui mero\n");
                Explosion explosion = new Explosion(getEngineRef(), getX(), getY());
            }
        }

    }


    /*********************************************************************************
     * Main() function of the hero. Currently the movements are processed with the
     * pad, the pad gives moving(x,y), variables but the final vector is a very long
     * distance, so the hero keeps moving
     *********************************************************************************/
    @Override
    public void loop() {
        if (touchedDownFlag) {
            /** @TODO Oscar's Change Has a big issue, causes the player in certain coordinates to move to infinite x,y*/
            //gotoXY(motion.getCurrPosition().pX + moving_x, motion.getCurrPosition().pY + moving_y);
            if (!getIsBlocked()) {
                gotoXY(moving_x, moving_y);
                if (null != sword) {
                    sword.setPosition(this.getX(), this.getY());
                }
            } else {
                stop();
            }
        }
    }


    /**
     * This function is used to receive the Signal from the Game and process it according
     * to the Enemy I.A. Put here all the signals that the Player can handle.
     *
     * @param signal Signal received from the Game engine.
     */
    @Override
    public void receivedSignal(GameSignals signal) {
        switch (signal) {

            /** Hero has reached the destination, release touch
             * and request stop moving
             */
            case SIGNAL_REACHED_DESTINATION: {
                // Release Mouse
                touchedUp();
                break;
            }
            case SIGNAL_ELAPSED_TIME: {

                break;
            }
            default: {
            }
        }
    }

    /**
     * If this function is called, is because this Character is colliding
     * with another SpriteObject. collidingSprite is a reference of the
     * collidingSprite, This must be defined.
     *
     * @param collidingSprite The spriteObject that is touching this spriteObject.
     */
    @Override
    public void colliding(SpriteObject collidingSprite) {
        if (!isBlinking()) { /** Do not process while Hero is blinking */
            if (SpriteTypes.SWORD == collidingSprite.getSpriteType() && collidingSprite.getSprite_UID().equals("EnemySword")) {
                player_health--;
                setBlinking(true);
                if (player_health < 0) {
                    getEngineRef().setGameOver();
                    die();
                }
            }
            if (SpriteTypes.ENEMY == collidingSprite.getSpriteType() && collidingSprite.getSprite_UID().equals("EyeBoss")) {
                if (!isBlinking()) {
                    player_health--;
                    Enemyhurt.play();
                    setBlinking(true);
                    if (player_health < 0) {
                        getEngineRef().setGameOver();
                        die();
                    }
                }
            }
        }
    }

    /**
     * @param attack_power the number of the damage our hero can do.
     */
    public void setAttackPower(int attack_power) {
        this.attack_power = attack_power;
    }

    /**
     * @param health Set the Life of our hero.
     */
    public void setHealth(int health) {
        this.player_health = health;
    }

}