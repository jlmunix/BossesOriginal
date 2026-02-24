package com.tecoloteinc.bosses.Sprites.Characters.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.Sprites.Items.Weapon;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

/**
 * Class EnemyBunny: This Enemy is the lowest energy enemy
 * in the game, it has the lower health and lower attack,
 * was really used just to create the Super classes
 * when the game was developed :P
 */
public class SoldierEnemy extends com.tecoloteinc.bosses.Sprites.Characters.Enemies.Enemy {

    /******************************************************
     * Required Variables
     ******************************************************/

    /**
     * AnimationsEngine of The bunny enemy. It only has two sides, that's
     * why
     */
    private int[] animation_list = {RIGHT, LEFT, UP, DOWN};

    /**
     * These are the States that the Enemy will process, there are
     *  unlimited number of states, they depend on the implementation
     *  of every enemy.
     */

    enum EnemyStates {
        INITIAL,
        STATE_1, // Going for the click
        STATE_2, // Going to Zero
        STATE_3,  // Die.
        STATE_4,
        STATE_5,
        STATE_6,
        STATE_7,
        STATE_8

    }

    /**
     * State machine variables
     */
    EnemyStates current_enemy_state = EnemyStates.INITIAL;
    EnemyStates previous_enemy_state = EnemyStates.STATE_1;

    /******************************************************
     * Sounds Section
     ******************************************************/
    Sound EnemyBunnySFX;


    /******************************************************
     * Enemy Defined Variables
     ******************************************************/
    /**
     * Spawn Zone, this vector controls the spawning zone
     * of the Enemy.
     */
    Vector2 spawn_zone;
    Weapon sword;

    /**
     * Constructor method to instantiate an Enemy.
     *
     * @param game_ref a reference of the main engine.
     * @param x        Position in X to put the enemy.
     * @param y        Position in Y to put the enemy.
     */
    public SoldierEnemy(GameEngine game_ref, int x, int y) {
        super(game_ref, 1.2F, 3, 1, x, y, "Enemies/GreenSoldier/Enemy64.png", 3, 8, 0.1f);
        // Provide the list of animations.
        setAnimationList(animation_list);
        // Register new audio.
        EnemyBunnySFX = Gdx.audio.newSound(Gdx.files.internal("Enemies/BunnyEnemy/SFX/jump.ogg"));
        // Set the type of the Sprite.
        setSprite_UID("GreenEnemy");
        // Store the spawn zone for future uses.
        spawn_zone = new Vector2(getX(), getY());
        // Add this SpriteObject to the engine.
        sword = new Weapon(game_ref, "Items/Weapons/Sword.png", 3, true);
        game_ref.addSprite(this);
        game_ref.addSprite(sword);
    }

    /**
     * If this function is called, is because this Character is colliding
     * with another SpriteObject. collidingSprite is a reference of the
     * collidingSprite, if not defined, the default method will be provided
     * by just attacking Hero and make it blink for some seconds.
     *
     * @param collidingSprite The spriteObject that is touching this spriteObject.
     */
    @Override
    public void colliding(SpriteObject collidingSprite) {
        if (SpriteTypes.PLAYER == collidingSprite.getSpriteType()) {
            if (!collidingSprite.isBlinking()) { /** Do not process while Hero is blinking */
                //LinkHurt.play();
                //if (VIBRATION) Gdx.input.vibrate(50);         //@TODO Change.
                //getEngineRef().getHero().setBlinking(true); //@TODO Change.
            }
        }
    }

    /**
     * This function is used to generate the A.I. of the Enemy.
     */
    @Override
    public void loop() {

//        // Follow link if is near to 100 pixels.
//        if (getX() > getEngineRef().getHero().getX() - 100 &&
//                getX() < getEngineRef().getHero().getX() + 100 &&
//                getY() > getEngineRef().getHero().getY() - 100 &&
//                getY() < getEngineRef().getHero().getY() + 100) {
//            gotoXY(getEngineRef().getHero().getX(), getEngineRef().getHero().getY());
//        }
//        // Follow link if is near to 100 pixels.
//        if (getX() > getEngineRef().getHero().getX() - 20 &&
//                getX() < getEngineRef().getHero().getX() + 20 &&
//                getY() > getEngineRef().getHero().getY() - 20 &&
//                getY() < getEngineRef().getHero().getY() + 20) {
//            if (sword != null && !getEngineRef().getHero().isBlinking()) {
//                stop();
//                attack();
//                sword.attack(getX(), getY(), getCurrentVerticalAnimation());
//            }
//        } else {
            if (current_enemy_state != previous_enemy_state) {
                switch (current_enemy_state) {
                    case INITIAL: {
                        // Move the Enemy to this position
                        gotoXY(spawn_zone.x + 50, spawn_zone.y);
                        break;
                    }
                    case STATE_1: {
                        // Wait 5 Seconds.
                        stop();
                        //EnemyBunnySFX.stop();
                        waitTime(5);
                        break;
                    }
                    case STATE_2: {
                        // Move the Enemy to this Position.
                        gotoXY(spawn_zone.x - 50, spawn_zone.y);
                        break;
                    }
                    case STATE_3: {
                        // Wait 5 Seconds.
                        stop();
                        waitTime(5);
                        break;
                    }
                    case STATE_4: {
                        // Move the Enemy to this Position.
                        gotoXY(spawn_zone.x, spawn_zone.y + 50);
                        break;
                    }
                    case STATE_5: {
                        // Wait 5 Seconds.
                        stop();
                        waitTime(5);
                        break;
                    }
                    case STATE_6: {
                        // Move the Enemy to this Position.
                        gotoXY(spawn_zone.x, spawn_zone.y - 50);
                        break;
                    }
                    case STATE_7: {
                        // Wait 5 Seconds.
                        stop();
                        waitTime(5);
                        break;
                    }
                    case STATE_8: {
                        // Move the Enemy to this Position.
                        gotoXY(spawn_zone.x, spawn_zone.y);
                        break;
                    }
                }
            }
            previous_enemy_state = current_enemy_state;
        //}
    }
    /**
     * This function is used to receive the Signal
     * from the Game and process it according
     * to the Enemy I.A.
     *
     * @param signal Signal received from the Game
     */
    @Override
    public void receivedSignal(GameSignals signal) {
        switch (signal) {
            case SIGNAL_REACHED_DESTINATION: {
                switch (current_enemy_state) {
                    case INITIAL:
                    case STATE_2:
                    case STATE_4:
                    case STATE_6:
                    case STATE_8: {
                        previous_enemy_state = current_enemy_state;
                        if (current_enemy_state == EnemyStates.INITIAL) {
                            current_enemy_state = EnemyStates.STATE_1;
                        }
                        if (current_enemy_state == EnemyStates.STATE_2) {
                            current_enemy_state = EnemyStates.STATE_3;
                        }
                        if (current_enemy_state == EnemyStates.STATE_4) {
                            current_enemy_state = EnemyStates.STATE_5;
                        }
                        if (current_enemy_state == EnemyStates.STATE_6) {
                            current_enemy_state = EnemyStates.STATE_7;
                        }
                        if (current_enemy_state == EnemyStates.STATE_8) {
                            current_enemy_state = EnemyStates.INITIAL;
                        }
                        EnemyBunnySFX.stop();
                        break;
                    }
                    default: {
                        // Something bad has happened,
                        // Ivan is crying #:'{) call 9-11.
                    }
                }
                break;
            }
            case SIGNAL_BLOCKED_DESTINATION: {
                switch (current_enemy_state) {
                    case INITIAL: {
                        previous_enemy_state = current_enemy_state;
                        stop();
                        //current_enemy_state = EnemyStates.STATE_1;
                        EnemyBunnySFX.stop();
                        break;
                    }
                    default: {
                        // Something bad has happened,
                        // Ivan is crying #:'{) call 9-11.
                    }
                }
                break;
            }

            case SIGNAL_ELAPSED_TIME: {
                switch (current_enemy_state) {
                    case STATE_1:
                    case STATE_3:
                    case STATE_5:
                    case STATE_7: {

                        previous_enemy_state = current_enemy_state;
                        if (current_enemy_state == EnemyStates.STATE_1) {
                            current_enemy_state = EnemyStates.STATE_2;
                        }
                        if (current_enemy_state == EnemyStates.STATE_3) {
                            current_enemy_state = EnemyStates.STATE_4;
                        }
                        if (current_enemy_state == EnemyStates.STATE_5) {
                            current_enemy_state = EnemyStates.STATE_6;
                        }
                        if (current_enemy_state == EnemyStates.STATE_7) {
                            current_enemy_state = EnemyStates.STATE_8;
                        }
                        break;
                    }
                }
            }
        }
    }
}
