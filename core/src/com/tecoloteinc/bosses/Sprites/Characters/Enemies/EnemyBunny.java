package com.tecoloteinc.bosses.Sprites.Characters.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

import static com.tecoloteinc.bosses.utils.Others.GameConstants.VIBRATION;

/**
 * Class EnemyBunny: This Enemy is the lowest energy enemy
 * in the game, it has the lower health and lower attack,
 * was really used just to create the Super classes
 * when the game was developed :P
 */
public class EnemyBunny extends com.tecoloteinc.bosses.Sprites.Characters.Enemies.Enemy {

    /******************************************************
     * Required Variables
     ******************************************************/
    /**
     * AnimationsEngine of The bunny enemy. It only 4 sides.
     */
    private int[] animation_list = {RIGHT, LEFT, DOWN, UP};

    /**
     * These are the States that the Enemy will process, there are
     *  unlimited number of states, they depend on the implementation
     *  of every enemy.
     */
    enum EnemyStates {
        INITIAL,
        STATE_1,  // Go up right.
        STATE_2,  // Wait 5 seconds.
        STATE_3,  // Go Spawn zone.
        STATE_4   // Wait 5 seconds.
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
    Sound LinkHurt = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/LTTP_Link_Hurt.wav"));

    /******************************************************
     * Enemy Defined Variables
     ******************************************************/
    /**
     * Spawn Zone, this vector controls the spawning zone
     * of the Enemy.
     */
    Vector2 spawn_zone;

    /**
     * Constructor method to instantiate an EnemyBunny.
     *
     * @param game_ref a reference of the main engine.
     * @param x        Position in X to put the enemy.
     * @param y        Position in Y to put the enemy.
     */
    public EnemyBunny(GameEngine game_ref, int x, int y) {
        super(game_ref, 0.9F, 3, 1, x, y, "Enemies/BunnyEnemy/BunnyArray.png", 6, 4, 0.04F);
        // Provide the list of animations.
        setAnimationList(animation_list);
        // Register new audio.
        EnemyBunnySFX = Gdx.audio.newSound(Gdx.files.internal("Enemies/BunnyEnemy/SFX/jump.ogg"));
        // Set the type of the Sprite.
        setSprite_UID("BadBunny");
        // Store the spawn zone for future uses.
        spawn_zone = new Vector2(getX(), getY());
        // Add this SpriteObject to the engine.
        game_ref.addSprite(this);
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
                LinkHurt.play();
                if (VIBRATION) Gdx.input.vibrate(50);         //@TODO Change.
                getEngineRef().getHero().setBlinking(true);   //@TODO Change.
            }
        }
    }

    /**
     * Main method to control the A.I. There is not an official method to generate the Artifical
     * Intelligence of the Sprite, but a State Machine provides the basics for doing it.
     */
    @Override
    public void loop() {
        if (current_enemy_state != previous_enemy_state) {
            switch (current_enemy_state) {
                case INITIAL: {
                    // Move the Enemy to this position
                    gotoXY(spawn_zone.x - 48, spawn_zone.y + 48);
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
                    gotoXY(spawn_zone.x - 50, spawn_zone.y + 180);

                    //EnemyBunnySFX.stop();
                    //EnemyBunnySFX.loop();
                    break;
                }
                case STATE_3: {
                    // Move the Enemy to this Position.
                    gotoXY(spawn_zone.x, spawn_zone.y);

                    //EnemyBunnySFX.stop();
                    //EnemyBunnySFX.loop();
                    break;
                }
                case STATE_4: {
                    // Move the Enemy to this Position.
                    gotoXY(spawn_zone.x, spawn_zone.y);

                    //EnemyBunnySFX.stop();
                    //EnemyBunnySFX.loop();
                    break;
                }
            }
        }
        previous_enemy_state = current_enemy_state;
    }

    /**
     * This function is used to receive the Signal from the Game and process it according
     * to the Enemy I.A. Developer of the Enemy is in charge of processing all required
     * signals.
     *
     * @param signal Signal received from the Game
     */
    @Override
    public void receivedSignal(GameSignals signal) {
        switch (signal) {
            case SIGNAL_REACHED_DESTINATION: {
                switch (current_enemy_state) {
                    case INITIAL: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_1;
                        EnemyBunnySFX.stop();
                        break;
                    }
                    case STATE_2: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_3;
                        break;
                    }
                    case STATE_3: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.INITIAL;
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
                    case STATE_1: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_2;
                        break;
                    }
                }
            }
        }
    }
}
