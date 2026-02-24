package com.tecoloteinc.bosses.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.utils.AnimationsFolder.AnimationsEngine;
import com.tecoloteinc.bosses.utils.AnimationsFolder.GameAnimations;
import com.tecoloteinc.bosses.Sprites.Other.Dialogs;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

import static com.tecoloteinc.bosses.utils.Others.GameConstants.VIBRATION;

/**
 * This class, holds the declaration of any sword in game. Sword class define different
 * types of swords.
 */
public class Weapon extends SpriteObject {

    AnimationsEngine animationsEngineHandler = new AnimationsEngine();
    float initial_x = 0, initial_y = 0;
    boolean enabled = false;

    static Sound LinkHurt = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/LTTP_Link_Hurt.wav"));
    static Sound Enemyhurt = Gdx.audio.newSound(Gdx.files.internal("SFX/enemyHurt.wav"));

    public Weapon(GameEngine game_engine, String TexturePath, int power, boolean enemy_sword) {
        super(game_engine);
        /** Set the region and the Size of the frame.
         *  and establishes the first frame as the
         *  current texture.  */
        loadTexture(TexturePath, 9, 4);
        setSpriteType(SpriteTypes.SWORD);
        if (enemy_sword) {
            setSprite_UID("EnemySword");
        } else
            setSprite_UID("sword");
        isVisible(false);
    }

    /**
     * This function will be called when the SpriteCollisionDetector
     * detects a collision related with This SpriteObject.
     *
     * @param collidingSprite The spriteObject that is colliding with this.
     */
    @Override
    public void colliding(SpriteObject collidingSprite) {
        if (enabled) {
            if (SpriteTypes.ENEMY == collidingSprite.getSpriteType() && getSprite_UID().equals("sword") &&
            collidingSprite.getSprite_UID() == "GreenSoldier") {
                if (collidingSprite.isBlinking() != true) {
                    Enemyhurt.play();
                    collidingSprite.setBlinking(true);
                    Dialogs dialog = new Dialogs(getEngineRef(), "Hey! I'm on your Side! Stop Hitting me!", false);
                    enabled=false;
                }
            }
//            if (SpriteTypes.ENEMY == collidingSprite.getSpriteType() && getSprite_UID().equals("sword") &&
//                    collidingSprite.getSprite_UID() == "EyeBoss") {
//                if (collidingSprite.isBlinking() != true) {
//                    Enemyhurt.setVolume(0, 0.5F);
//                    collidingSprite.setBlinking(true);
//                    Enemyhurt.play();
//                    enabled=false;
//                }
//            }
            if (SpriteTypes.PLAYER == collidingSprite.getSpriteType() && getSprite_UID().equals("enemy_sword")) {
                LinkHurt.play();
                if (VIBRATION) Gdx.input.vibrate(50);         //@TODO Change.
                getEngineRef().getHero().setBlinking(true); //@TODO Change.
            }
        }
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    /**
     * Method attack, will set a new attack animation in the weapon.
     *
     * @param x    Start X point of the animation.
     * @param y    Start Y point of the animation.
     * @param side Looking side of the animation (vertical frame).
     */
    public void attack(float x, float y, int side) {
        //enabledAttacking = true;
        isVisible(true);
        animationsEngineHandler.setNewAnimation(GameAnimations.swordAttack(side), 1, true, side, side, true);
        initial_x = x;
        initial_y = y;
        enabled = true;
    }

    /**
     * This function is executed every cycle and let this task (Sprite) to process what it
     * needs to do. In This case, the Sword only process animations.
     * @param delta The time between every call to this function.
     */
    @Override
    public void executive(float delta) {
        AnimationsEngine.AnimationSequenceType frames;
        if ((null != animationsEngineHandler) && (animationsEngineHandler.isAnimationValid())) {
            frames = animationsEngineHandler.getFrame(delta);
            /** If @code{isAnimationValid()} is true, it means that the invalidation occurred while
             * grabbing the frame but it was not valid anymore, is time to process the ending of
             * the animations*/
            if (!animationsEngineHandler.isAnimationValid()) {
                enabled = false;
                isVisible(false);
            }
            setPosition(initial_x + frames.pos_x, initial_y + frames.pos_y);
            updateAnimation(frames.horizontal_frame, frames.vertical_frame);
        }
    }
}
