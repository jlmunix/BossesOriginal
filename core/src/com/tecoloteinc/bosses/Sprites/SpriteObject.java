package com.tecoloteinc.bosses.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.Sprites.Other.Dialogs;

import static com.tecoloteinc.bosses.utils.Others.GameConstants.*;

/**
 * This class is used the base class of any Animated Sprite in this Engine.
 * This class allows GameEngine to store a buffer of this type to later
 * process it polling all the Sprites. It keeps all the basic configuration
 * of a Sprite, IT SHOULD NOT CONTAIN SETTINGS OF CHARACTERS OR ENEMY.
 */
public abstract class SpriteObject extends Sprite {

    /*----------------------------------------------------------------*/
    /**
     * Set this property to remove the SpriteObject from the Game
     */
    private boolean requestDeletion = false;

    public void deleteObj() {
        requestDeletion = true;
    }

    public boolean isDeleteRequired() {
        return requestDeletion;
    }

    // Current layer in which the Sprite is going to be drawn.
    private int layerLevel = 0;

    // get the current Sprite Layer.
    public int getLayerLevel() {
        return layerLevel;
    }

    public void setLayerLevel(int layer) {
        layerLevel = layer;
    }


    /*------------(BLINKING ANIMATION)--------------------------------*/
    public boolean isBlinking() {
        return isBlinking;
    }

    /**
     * Keep the condition if the Sprite was blocked in the past by a Block type Tile
     * This help the system to generate the Signal "SIGNAL_BLOCKED_DESTINATION"
     */
    private boolean blockedByBlock = false;

    /**
     * Used to put the sprite in BLINKING_ANIMATION mode
     * Do not use this variables in another method.
     */
    private boolean isBlinking = false;
    private boolean blinkingToggle = false;
    private float blink_time;
    private float blink_duration = 0;
    /*------------(BLINKING ANIMATION)--------------------------------*/

    /** Track if the SpriteObject can be moved or not */
    //public boolean isMovable = true;
    /**
     * keeps the current Sprite Type. This defines the behaviour of the collision with other
     * SpriteObjects.
     */
    SpriteTypes current_sprite_type = SpriteTypes.CHARACTER;

    /**
     * Sprite Unique ID, used to identify the SpriteObject, this must be set
     * in the final class and should be different than others.
     */
    private String sprite_UID = "UNKNOWN";

    /**
     * This enum controls the Basic SpriteObject types, this is different
     * of the sprite_UID. Is used to perform a basic collision detection
     * with different Sprites.
     */
    public enum SpriteTypes {
        PLAYER,
        ENEMY,
        SWORD,
        CHARACTER,
        BOSS,
        DEATH,
        DIALOG,
        ANIM
    }

    /**
     * Number of frames per sprite Horizontal and Vertical
     */
    protected int m_HFrames, m_VFrames;
    /**
     * Keeps control of the number of rows the texture contains.
     * This name was selected because sprites will have different
     * sides. I.E:
     *                    m_HFrames+1   m_HFrames+1   m_HFrames+1   m_HFrames+1
     *                    _______________________________________________________
     * current_h_frame=0 | Right       |  Right+1    | Right+2     |  Right+3   |
     *                   |-------------+-------------+-------------+------------+
     * current_h_frame=1 | Left        |  Left+1     | Left+2      |  Left+3    |
     *                   |-------------+-------------+-------------+------------+
     * current_h_frame=2 |  Up         |  Up+1       |  Up+2       |  Up+3      |
     *                   |-------------+-------------+-------------+------------+
     * current_h_frame=3 |  Down       |  Down+1     |  Down+2     |  Down+3    |
     *                    -------------+-------------+-------------+------------+
     */
    /**
     * Keep track of the current horizontal frame
     */
    protected int current_h_frame;
    /**
     * The current vertical animation
     */
    private int current_v_frame;
    /**
     * Keeps the previous position in the X of the spriteObject
     */
    protected float m_previous_x;
    /**
     * Keeps the previous position in the Y of the spriteObject
     */
    protected float m_previous_y;

    /**
     * Maximum number of vertical frames we can have, these
     * are the animations that our Sprites can support.
     */
    private int[] animation_list = new int[10];

    /**
     * Sprite visibility, is false, the Sprite will exist and will be
     * processed by the Engine but wont be visible.
     */
    private boolean sprite_visible = true;

    /**
     * A reference of the GameEngine.
     */
    private GameEngine gameReference;

    /**
     * geter for previous positions.
     *
     * @return vector with previous positions.
     */
    public Vector2 getPreviousPositions() {
        return new Vector2(m_previous_x, m_previous_y);
    }

    /**
     * Default constructor to allocate the properties for
     * all Sprites.
     */
    public SpriteObject(GameEngine game_ref) {
        gameReference = game_ref;
    }

    /************************************************************************
     * METHODS
     ************************************************************************/

    /**
     * Getter for the Sprite UID.
     *
     * @return the sprite Unique Identifier.
     * @see {@code sprite_UID}.
     */
    public String getSprite_UID() {
        return sprite_UID;
    }

    /**
     * Setter for the Sprite UID. This UID (Unique Identifier) allows the engine
     * or other SpriteObject to know, how to treat it, I.e: EnemyType...
     *
     * @param uid the unique identifier for the
     *            sprite.
     */
    public void setSprite_UID(String uid) {
        sprite_UID = uid;
    }

    /**
     * Getter for the SpriteType of the SpriteObject.
     *
     * @return SpriteTypes of the SpriteObject.
     */
    public SpriteTypes getSpriteType() {
        return current_sprite_type;
    }

    /**
     * This method allow to change the type of the Sprite. The SpritesTypes
     * defines the functionality of the Sprite.
     *
     * @see {@code SpriteTypes} for more information.
     */
    public void setSpriteType(SpriteTypes type) {
        current_sprite_type = type;
    }

    /**
     * This method get the direction of the Sprite based
     * in the previous position.
     *
     * @return return the 'x' direction, 1 for Right, -1 for Left, 0 no walking.
     */
    public int getDirectionX() {
        int return_val;
        // Walking to the Right
        if ((getX() - m_previous_x) > 0) {
            return_val = +1;
        }
        // Walking to the Left
        else if ((getX() - m_previous_x) < 0) {
            return_val = -1;
        }
        // No walking
        else {
            return_val = 0;
        }
        return return_val;
    }

    /**
     * This method get the direction of the Sprite based
     * in the previous position.
     *
     * @return return the 'y' direction, 1 for Up, -1 for Down, 0 no walking.
     */
    public int getDirectionY() {
        int return_val;
        // Walking Up.
        if ((getY() - m_previous_y) > 0) {
            return_val = +1;
        }
        // Walking down.
        else if ((getY() - m_previous_y) < 0) {
            return_val = -1;
        }
        // No walking
        else {
            return_val = 0;
        }
        return return_val;
    }

    /**
     * Get function to retrieve the proper animation number.
     *
     * @return the corresponding animation.
     */
    public int getCurrentVerticalAnimation() {
        return current_v_frame;
    }

    /**
     * Get function to retrieve the proper animation number.
     *
     * @return the corresponding animation.
     */
    public int getCurrentHorizontalAnimation() {
        return current_h_frame;
    }

    /**
     * This method allow to put a SpriteObject into a position (according to our Image)
     * As {@code current_v_frame} is controlling the side of the Sprite, this method can
     * change which v_frame is right now.
     *
     * @param animation_number
     */
    public void setCurrentAnimation(int animation_number) {
        current_v_frame = animation_list[animation_number];
    }

    /**
     * Set the proper animation number. This is used to detect how the Positions are in the
     * image of the Sprite. This method is used to set a different animation list.
     * For Instance, if the image has only Left and Right position, then the animation
     * list must be: RIGHT,LEFT,RIGHT, RIGHT.
     * This means that if the Sprite is going to the Right, will use RIGHT,
     * if the Sprite goes left, will use LEFT, if goes UP will use RIGHT and if is going to
     * down will use RIGHT.
     *
     * @param anim_list list of the new behaviour.
     */
    public void setAnimationList(int[] anim_list) {
        animation_list = anim_list;
    }

    /**
     * This function gets the specified image and split into a
     * separate set of images, specified by the user. The Animation
     * will be used later to animate the Sprite when required.
     *
     * @param batch      the batch of the game (there is only one batch).
     * @param delta_time Delta time happened during each frame.
     */
    public void draw_sprite(SpriteBatch batch, float delta_time) {

        if (isBlinking) {
            blinkingAnimation(delta_time);
        }
        // Only draw the Sprite if is visible.
        if (isVisible()) {
            draw(batch);
        }
    }

    /**
     * Set a particular frame of of the texture Region
     *
     * @param frameX Position in X of the TextureRegion.
     * @param frameY Position in Y of the TextureRegion.
     */
    public void setFrame(int frameX, int frameY) {
        current_h_frame = frameX;
        current_v_frame = frameY;
    }

    /**
     * Function to get the size of the image, the texture and it
     * creates the Sprite object. It set the size of the sprite
     * based on the frames the image contains. The image must be
     * a series of frames.
     * ________
     * |__|__|__| = 3 Frames.
     *
     * @param texturePath Local Path of the texture.
     * @param h_frames    Number of horizontal frames in the texture.
     * @param v_frames    Number of vertical frames.
     */
    public void loadTexture(String texturePath, int h_frames, int v_frames) {
        m_HFrames = h_frames;
        m_VFrames = v_frames;
//        /** Set by default to Right */
//        current_h_frame = 0;
        Texture texture = new Texture(texturePath);

        // Protect for division by 0.
        if (0 == m_HFrames) {
            System.out.print("0 Frames uh?, erase this SDK...");
            System.exit(0);
            m_HFrames = 1;
        }
        if (0 == m_VFrames) {
            m_VFrames = 1;
        }
        // Get the region of the image per frames.
        int region_width = texture.getWidth() / m_HFrames;
        // Get the region height by diving image the number of sides
        int region_height = texture.getHeight() / m_VFrames;

        set(new Sprite(texture));
        setRegion(0, 0, region_width, region_height);
        setSize(region_width, region_height);
    }

    /**
     * This function loads a texture and applies it to the (Sprite)(this)
     *
     * @param texturePath Path of the location of the image.
     * @TODO Review if this is possible, can affect the frame number. Red enemies?
     * @TODO I don't see why an enemy could load a new texture.
     */
    public void setTexture(String texturePath) {
        set(new Sprite(new Texture(texturePath)));
    }

    /**
     * This function update the frame depending on the
     * h_frame and v_frame. The number of horizontal frames
     * is reduced by one, since the images are from
     * 0 to n = n-1, Max number is n-1.
     * This depends on the number of frames of your Texture.
     */
    public void updateAnimation(int h_frame, int v_frame) {
        /** Did we reached the max number of frames in X return to the first one */
        if ((h_frame > (m_HFrames - 1))) {
            h_frame = 0;
        }
        /** Selected side does not exist? */
        if (v_frame > (m_VFrames - 1)) {
            v_frame = m_VFrames;
        }
        if(getSprite_UID()=="EyeBoss") {
        }
        setRegion(getRegionWidth() * h_frame, getRegionHeight() * v_frame, getRegionWidth(), getRegionHeight());
    }

    /**
     * Move the SpriteObject one step in 'x'.
     *
     * @param x_step Steps to move in X coordinate.
     */
    public final void move_x(float x_step) {
        // Get the current coordinates.
        m_previous_x = getX();
        m_previous_y = getY();
        // Move the SpriteObject to the desired position.
        setPosition(getX() + x_step, getY());
        // Check if we are colliding, if so, go back.
        gameReference.processTileCollision(this);
    }

    /**
     * Move the SpriteObject one step in 'y'.
     *
     * @param y_step Steps to move in Y coordinate.
     */
    public final void move_y(float y_step) {
        // Get the current coordinates.
        m_previous_y = getY();
        m_previous_x = getX();
        // Move the SpriteObject to the desired position.
        setPosition(getX(), getY() + y_step);
        // Check if we are colliding, if so, go back.
        gameReference.processTileCollision(this);
        //processSpriteCollision();
        processCurrentTile();
    }

    /**
     * This method is used to interact with the special Tiles that are not simple blocks
     * they are used to do special things like Teleport or kill hero.
     * The checking point is the center of the WorkingArea of the Sprite
     */
    public final void processCurrentTile() {
        float posX = (getX() + (getWidth() / 2));
        float posY = (getY() + (getHeight() * CHA));

        // Get the type property.
        String cellType = gameReference.getCellProperty("Type", posX, posY);

        /* Process all sprites touched only by player */
        if (current_sprite_type == SpriteTypes.PLAYER || SpriteTypes.ENEMY == current_sprite_type) {
            // Is a teleport tile?
            if (cellType.equals("Teleport")) {
                int X = Integer.parseInt(gameReference.getCellProperty("TeleportX", posX, posY));
                int Y = Integer.parseInt(gameReference.getCellProperty("TeleportY", posX, posY));
                gameReference.loadMap(gameReference.getCellProperty("TeleportTo", posX, posY), X, Y);
            }
            // Is a Transform Tile?
            if (cellType.equals("Transform")) {
                int tileID = Integer.parseInt(gameReference.getCellProperty("TransformToID", posX, posY));
                String soundToPlay = gameReference.getCellProperty("Play", posX, posY);
                if (soundToPlay != null && soundToPlay != "invalid") {
                    Sound sound = Gdx.audio.newSound(Gdx.files.internal("Player/SFX/" + soundToPlay));
                    sound.play();
                }
                gameReference.transformCell(posX, posY, tileID);
            }
            if (cellType.equals("ChangeFace")) {
                int faceX = Integer.parseInt(gameReference.getCellProperty("FrameX", posX, posY));
                int faceY = Integer.parseInt(gameReference.getCellProperty("FrameY", posX, posY));
                gameReference.getHero().setFrame(faceX, faceY);
            }
            if (cellType.equals("Dialog")) {

                String text_to_say;
                Dialogs dialog_requested_by_tile;
                for (int dialog_counter = 1; ; dialog_counter++) {
                    text_to_say = gameReference.getCellProperty("TextToSay" + dialog_counter, posX, posY);
                    if (text_to_say.equals("invalid")) {
                        break;
                    }
                    dialog_requested_by_tile = new Dialogs(gameReference, text_to_say, true);
                }

                String afterDialog = gameReference.getCellProperty("AfterDialog", posX, posY);

                gameReference.changeCell(posX, posY, "Type", afterDialog);
            }
            /********************************
             * Add here more Special Tiles
             ********************************/
            //if (cellType.equals("SpecialTile")) {
            //int tileID = Integer.parseInt(gameReference.getCellProperty("TransformToID", (int) (getX() + (getWidth() / 2)), (int) (getY() + (getHeight() / 2))));
            //gameReference.transformCell((int) (getX() + (getWidth() / 2)), (int) (getY() + (getHeight() / 2)), tileID);
            //}
        }
    }

    /**
     * This method process the Sprite Collision, is separated from the
     * {@code checkForCollision} method to allow to override this or
     * {@code processTileCollision} method separately.
     */
//    public void processSpriteCollision() {
//        SpriteObject collidingSprite = gameReference.SpriteCollision(this);
//        /** Get the collision information (when colliding with Another Sprite) */
//        if (collidingSprite != null) {
//            colliding(collidingSprite);
//        }
//    }


    /***********************************************************************
     * ANIMATION OF THE SPRITES                                            *
     * This methods are used to perform certain animations in the Sprites  *
     ***********************************************************************/
    /**
     * Animation that puts the Sprite toggling from alpha 0 to alpha 1.
     *
     * @param dt Delta Time between each call to this function.
     */
    protected void blinkingAnimation(float dt) {

        blink_time += dt;
        blink_duration += dt;
        /** Blink time of ABT seconds */
        if (blink_time > ABT) {
            blinkingToggle = !blinkingToggle;
            if (blinkingToggle) {
                setAlpha(1F);
            } else {
                setAlpha(0F);
            }
            blink_time = 0;
        }

        if (blink_duration > ABD) {
            blink_duration = 0;
            isBlinking = false;
            setAlpha(1F);
        }
    }

    /**
     * Method to put the SpriteObject into a blinking state.
     */
    public void setBlinking(boolean blinking) {
        isBlinking = blinking;
    }

    /**
     * Getter for the visibility property.
     *
     * @return if the SpriteObject visible's property is true or not.
     */
    public boolean isVisible() {
        return sprite_visible;
    }

    /**
     * setter for the visibility property.
     */
    public void isVisible(boolean is_visible) {
        sprite_visible = is_visible;
    }

    /**
     * Inform the Sprite that it has a collision with a Block once
     * to clear this condition, it needs to call getBlock function
     *
     * @param blocked set true if the Sprite collided with a Block.
     */
    public void setBlocked(boolean blocked) {
        blockedByBlock = blocked;
    }

    /**
     * Return if the Sprite was blocked by a Tile and clear the condition.
     */
    public boolean wasPreviouslyBlocked() {
        boolean ret_val = blockedByBlock;
        blockedByBlock = false;
        return ret_val;
    }

    /**
     * This method return the processed rectangle, since we are not using
     * the texture as the size we had to use a different one, this ActiveArea
     * can be modified in the GameConstants.
     *
     * @return The rectangle that is going to be used for collision detection.
     * @see {@code GameConstans}
     */
    public Rectangle getActiveRectangle() {
        return new Rectangle(getX() + CWA, getY(), getWidth() - CWA, getHeight() * CHA);
    }

    /**
     * Getter for the engine reference.
     *
     * @return the reference of the Engine.
     */
    public GameEngine getEngineRef() {
        return gameReference;
    }

    public void deleteSprite() {

    }

    /***********************************************
     * Abstract classes
     **********************************************/
    /**
     * This function gets called when another sprite
     * touched this sprite.
     *
     * @param collidingSprite The spriteObject that is
     *                        touching this sprite.
     */
    public abstract void colliding(SpriteObject collidingSprite);

    /**
     * Return the speed property of the child class.
     *
     * @return the speed of the SpriteObject.
     */
    public abstract float getSpeed();

    /**
     * This method is used to execute animations or other
     * stuff in the final class, like Characters or
     * Items.
     */
    public abstract void executive(float delta);

}