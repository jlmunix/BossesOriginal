package com.tecoloteinc.bosses.Sprites.Characters.Enemies;

import com.badlogic.gdx.Gdx;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

import static com.tecoloteinc.bosses.utils.Others.GameConstants.VIBRATION;

public abstract class Enemy extends com.tecoloteinc.bosses.Sprites.Characters.Character {

    /** Signals from Enemies, these signals are
     * processed based on the
     */
    /**
     * Keeps the life value of the character
     */
    private int m_health;
    /**
     * Keeps the damage it causes to enemies
     */
    private int m_attack_power;

    /******************************************
     * Abilities and resistances.
     ******************************************/
    private int fire_resistance, fire_attack;
    private int wind_resistance, wind_attack;
    private int water_resistance, water_attack;
    private int earth_resistance, earth_attack;

    /**
     * Constructor of any Enemy Sprite Object.
     *
     * @param speed        Speed of the Enemy.
     * @param health       Life.
     * @param attack_power The damage the Enemy causes to GamePlayer.
     * @param TexturePath  Path to the Texture image.
     * @param h_frames     Horizontal Frames.
     * @param v_frames     Vertical Frames.
     */
    public Enemy(GameEngine game_ref, float speed, int health, int attack_power, int x, int y, String TexturePath, int h_frames, int v_frames, float anim_speed) {
        super(game_ref, speed, true, TexturePath, h_frames, v_frames, anim_speed);

        setPosition(x, y);
        m_health = health;
        m_attack_power = attack_power;
        setSpriteType(SpriteTypes.ENEMY);
    }

    /**
     * This abstract method is used to execute the colliding() function
     * in the desired Enemy SpriteObject, this is an override-able method
     * but is common for simple enemies.
     *
     * @param collidingSprite The spriteObject that is touching this spriteObject.
     */
    @Override
    public void colliding(SpriteObject collidingSprite) {
        if (SpriteTypes.PLAYER == collidingSprite.getSpriteType()) {
            if (!collidingSprite.isBlinking()) { /** Do not process while Hero is blinking */
                if (VIBRATION) Gdx.input.vibrate(50);         //@TODO Change.
                getEngineRef().getHero().setBlinking(true); //@TODO Change.
                attack();
            }

        }
    }

    /**
     * @param attack_power
     * @TODO Change function coding standard.
     */
    public void set_attack_power(int attack_power) {
        this.m_attack_power = attack_power;
    }

    /**
     * * @TODO Change function coding standard.
     *
     * @param health
     */
    public void set_health(int health) {
        this.m_health = health;
    }

    /**
     * * @TODO Change function coding standard.
     *
     */
    public int getHealth() {
        return this.m_health;
    }

}
