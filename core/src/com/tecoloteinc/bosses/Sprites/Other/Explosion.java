package com.tecoloteinc.bosses.Sprites.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.utils.AnimationsFolder.AnimationsEngine;
import com.tecoloteinc.bosses.utils.AnimationsFolder.GameAnimations;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

/**
 * Explosion class, this is the animation of the explosion. It does not collides with nothing.
 */
public class Explosion extends SpriteObject {

    // Necessary to process animations.
    AnimationsEngine animationsEngineHandler = new AnimationsEngine();
    
    boolean enabled = false;

    static Sound Enemyhurt = Gdx.audio.newSound(Gdx.files.internal("SFX/enemyHurt.wav"));

    public Explosion(GameEngine game_engine, float x, float y ) {
        super(game_engine);
        /** Set the region and the Size of the frame.
         *  and establishes the first frame as the
         *  current texture.  */
        loadTexture("SFX/Explosion.png", 6, 1);
        setSpriteType(SpriteObject.SpriteTypes.ANIM);
        setSprite_UID("ExplodeAnim");
        isVisible(true);
        setPosition(x, y);
        game_engine.addSprite(this);
        animationsEngineHandler.setNewAnimation(GameAnimations.Explosion(), 1, true, 0, 0, true);

    }

    public void setExplosion(){

    }

    @Override
    public void colliding(SpriteObject collidingSprite) {

    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public void executive(float delta) {
        AnimationsEngine.AnimationSequenceType frames;
        if ((null != animationsEngineHandler) && (animationsEngineHandler.isAnimationValid())) {
            frames = animationsEngineHandler.getFrame(delta);
            /** If @code{isAnimationValid()} is true, it means that the invalidation occurred while
             * grabbing the frame but it was not valid anymore, is time to process the ending of
             * the animations*/
            if (!animationsEngineHandler.isAnimationValid()) {
                System.out.print("aqui mero!\n");
                enabled = false;
                isVisible(false);
                deleteObj();
            }
            updateAnimation(frames.horizontal_frame, frames.vertical_frame);
        }
    }
}
