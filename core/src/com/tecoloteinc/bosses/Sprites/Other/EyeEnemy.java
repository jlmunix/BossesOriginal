package com.tecoloteinc.bosses.Sprites.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.utils.AnimationsFolder.GameAnimations;
import com.tecoloteinc.bosses.Sprites.Characters.Enemies.Enemy;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

/**
 * Class EnemyBunny: This Enemy is the lowest energy enemy
 * in the game, it has the lower health and lower attack,
 * was really used just to create the Super classes
 * when the game was developed :P
 */
public class EyeEnemy extends Enemy {

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
     * unlimited number of states, they depend on the implementation
     * of every enemy.
     */

    enum EnemyStates {
        INITIAL,
        INITIAL2,
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
    static Sound Enemyhurt = Gdx.audio.newSound(Gdx.files.internal("SFX/enemyHurt.wav"));

    // Follow Link.
    boolean enabledFollowing = false;
    int rotation = 0;

    /**
     * Constructor method to instantiate an Enemy.
     *
     * @param game_ref a reference of the main engine.
     * @param x        Position in X to put the enemy.
     * @param y        Position in Y to put the enemy.
     */
    public EyeEnemy(GameEngine game_ref, int x, int y) {
        super(game_ref, 0.8F, 16, 1, x, y, "Bosses/Eye/Eye.png", 2, 9, 0.1f);
        // Provide the list of animations.
        setAnimationList(animation_list);
        // Register new audio.
        EnemyBunnySFX = Gdx.audio.newSound(Gdx.files.internal("Enemies/BunnyEnemy/SFX/jump.ogg"));
        // Set the type of the Sprite.
        setSprite_UID("EyeBoss");
        // Store the spawn zone for future uses.
        spawn_zone = new Vector2(getX(), getY());
        // Add this SpriteObject to the engine.
        //sword = new Weapon(game_ref, "Items/Weapons/Sword.png", 3, true);
        game_ref.addSprite(this);
        game_ref.camFollowActor(this);
        Dialogs dialog = new Dialogs(game_ref, "This is the Monster!!! Be careful!", true);
        //game_ref.addSprite(sword);
        setFrame(1,8);
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

                //if (VIBRATION) Gdx.input.vibrate(50);         //@TODO Change.
                //getEngineRef().getHero().setBlinking(true); //@TODO Change.
            }
        }

        if (getSprite_UID().equals("EyeBoss") &&
                collidingSprite.getSprite_UID() == "sword" && collidingSprite.isVisible())   {
            if (isBlinking() != true) {
                Enemyhurt.setVolume(0, 0.5F);
                setBlinking(true);
                set_health(getHealth() - 1);
                speed = speed + 0.1f;

                if (getHealth() < 0) {
                    Dialogs dialog = new Dialogs(getEngineRef(), "You Have Destroyed the Enemy!", true);
                    dialog = new Dialogs(getEngineRef(), "But that was only a Demo Game!", true);
                    dialog = new Dialogs(getEngineRef(), "You should wait for the next Release!", true);
                    isDeleteRequired();
                    getEngineRef().setGameOver();
                }
                Enemyhurt.play();

                if (getCurrentVerticalAnimation() == RIGHT) {
                    setPosition(getX() - 30, getY());
                }
                if (getCurrentVerticalAnimation() == LEFT) {
                    setPosition(getX() + 30, getY());
                }
                if (getCurrentVerticalAnimation() == UP) {
                    setPosition(getX(), getY() - 30);
                }
                if (getCurrentVerticalAnimation() == DOWN) {
                    setPosition(getX(), getY() + 30);
                }
            }
        }
    }

    /**
     * This function is used to generate the A.I. of the Enemy.
     * Is continuously called.
     */
    @Override
    public void loop() {
        if (getHealth() == 10) {
            this.set_health(9);
        }
        // Enable this variable to put the Enemy to follow Hero.
        if (enabledFollowing) {
            gotoXY(getEngineRef().getHero().getX(), getEngineRef().getHero().getY());
        }
        // This is the State Machine of the Enemy.
        if (current_enemy_state != previous_enemy_state) {
            switch (current_enemy_state) {
                /* The Camera is released from the Enemy after the Presentation.
                 * will trying to catch Hero for 3 seconds. */
                case INITIAL: {
                    getEngineRef().camFollowActor(this);
                    animationsEngineHandler.setNewAnimation(GameAnimations.EyeBossSpawn(), 1, false, 4, true);
                    enabledFollowing = false;
                    waitTime(0.5f);
                    break;
                }
                case INITIAL2: {
                    animationsEngineHandler.setNewAnimation(GameAnimations.EyeBossPresentation(), 1, false, 5, true);
                    enabledFollowing = false;
                    waitTime(4);
                    break;
                }
                case STATE_1: {
                    System.out.print("State1 \n");
                    enabledFollowing = true;
                    waitTime(7);
                    break;
                }
                case STATE_2: {
                    System.out.print("State2 \n");
                    speed = 3;
                    enabledFollowing = true;
                    // Move the Enemy to this Position.
                    //gotoXY(getEngineRef().getHero().getX(), getEngineRef().getHero().getY());
                    waitTime(1);
                    break;
                }
                case STATE_3: {
                    stop();
                    enabledFollowing = false;
                    System.out.print("STATE3 \n");
                    speed = 0.8F;
                    // Wait 5 Seconds.
                    animationsEngineHandler.setNewAnimation(GameAnimations.EyeBossHide(), 1, false, 8, true);
                    waitTime(2);
                    break;
                }
                case STATE_4: {
                    System.out.print("State 4 \n");
                    speed = 0.6F;
                    enabledFollowing=false;
                    animationsEngineHandler.setNewAnimation(GameAnimations.EyeBossSpawn(), 1, false, 4, true);
                    // Wait 5 Seconds.
                    waitTime(0.6F);
                    break;
                }
                case STATE_5: {
                    System.out.print("State 5 \n");
                    speed = 0.6F;
                    enabledFollowing=false;
                    // Wait 5 Seconds.
                    waitTime(1);
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

            case SIGNAL_ELAPSED_TIME: {
                System.out.print("Signal Elapsed \n");
                switch (current_enemy_state) {
                    case INITIAL: {
                        // Return the focus to the Hero.
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.INITIAL2;
                        break;
                    }
                    case INITIAL2: {
                        // Return the focus to the Hero.
                        getEngineRef().camFollowActor(getEngineRef().getHero());
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_1;
                        break;
                    }
                    case STATE_1: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_2;
                        break;
                    }
                    case STATE_2: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_3;
                        break;
                    }
                    case STATE_3: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_4;
                        break;
                    }
                    case STATE_4: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_5;
                        break;
                    }
                    case STATE_5: {
                        previous_enemy_state = current_enemy_state;
                        current_enemy_state = EnemyStates.STATE_1;
                        break;
                    }
                    default: {
                        break;
                    }
                }
                return;
            }
        }
    }
}
