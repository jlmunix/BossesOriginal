package com.tecoloteinc.bosses.Sprites.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.utils.AnimationsFolder.AnimationsEngine;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

/**
 * Created by asd
 */
public class Dialogs extends SpriteObject {
    public String TextToSay;
    float width, height;
    GameEngine Game_Engine;
    boolean alreadyHappened = false;
    float alpha = 0;
    boolean blockedDialog = true;
    BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/Zelda.fnt"), false);

    AnimationsEngine animationsEngineHandler = new AnimationsEngine();
    float initial_x = 0, initial_y = 0;

    static Sound PopUp = Gdx.audio.newSound(Gdx.files.internal("SFX/menu_sound.wav"));

    public Dialogs(GameEngine game_engine, String text_to_say, boolean blocked_dialog) {
        super(game_engine);
        /** Set the region and the Size of the frame.
         *  and establishes the first frame as the
         *  current texture.  */
        loadTexture("RegularFigures/Dialog.png", 1, 1);
        width = getWidth();
        height = getHeight();
        blockedDialog = blocked_dialog;
        //setSize(1F, 1F);
        setPosition(game_engine.getScreenOrigin().x, game_engine.getScreenOrigin().y);
        setAlpha(0.0f);
        setSpriteType(SpriteTypes.DIALOG);
        isVisible(true);
        if (text_to_say.length() >= 31) {
            TextToSay = text_to_say.substring(0, text_to_say.substring(0, 30).lastIndexOf(" ")) + '\n' +
                    text_to_say.substring(text_to_say.substring(0, 30).lastIndexOf(" ") + 1);
        } else
            TextToSay = text_to_say;
        Game_Engine = game_engine;
        setLayerLevel(1);
        Game_Engine.addDialog(this);
    }

    /**
     * This function will be called when the SpriteCollisionDetector
     * detects a collision related with This SpriteObject.
     *
     * @param collidingSprite The spriteObject that is colliding with this.
     */
    @Override
    public void colliding(SpriteObject collidingSprite) {
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public void executive(float delta) {
//        if (!alreadyHappened) {
//            if (getWidth() < width) setSize(getWidth() + 8, getHeight());
//            if (getHeight() < height) setSize(getWidth(), getHeight() + 8);
//
//            if (getWidth() >= width && getHeight() >= height) {
//                alreadyHappened = true;
//                PopUp.play();
//            }
//
//        } else {

        if (blockedDialog) {
            if (!alreadyHappened) {
                if (alpha == 0)
                    PopUp.play();
                setAlpha(alpha += 0.04F);

                if (alpha >= 0.7f) {
                    alreadyHappened = true;
                }
            } else {
                font.setColor(.2f, .2f, .2f, 0.7f);
                font.getData().setScale(0.5f);
                font.setColor(0.5f, 0.1f, 0.9f, 0.7f);
                font.draw(Game_Engine.getBatch(), TextToSay,
                        Game_Engine.getScreenOrigin().x + 19 + 80,
                        Game_Engine.getScreenOrigin().y + 30 + 20);
            }
            setPosition(Game_Engine.getScreenOrigin().x + 80, Game_Engine.getScreenOrigin().y);
            Game_Engine.setEngineStop();

        } else {
            if (!alreadyHappened) {
                if (alpha == 0)
                    PopUp.play();
                setAlpha(alpha += 0.04F);

                if (alpha >= 0.7f) {
                    alreadyHappened = true;
                }
            } else {
                font.setColor(.2f, .2f, .2f, 0.7f);
                font.getData().setScale(0.5f);
                font.setColor(0.5f, 0.1f, 0.9f, 0.7f);
                font.draw(Game_Engine.getBatch(), TextToSay,
                        Game_Engine.getScreenOrigin().x + 19 + 80,
                        Game_Engine.getScreenOrigin().y + 30 + 20);
                setAlpha(alpha -= 0.02F);
                if(alpha <= 0) {
                    deleteObj();
                }
            }
            setPosition(Game_Engine.getScreenOrigin().x + 80, Game_Engine.getScreenOrigin().y);
        }
    }
}
