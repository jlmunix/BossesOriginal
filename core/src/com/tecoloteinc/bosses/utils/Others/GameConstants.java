package com.tecoloteinc.bosses.utils.Others;

/**
 * This class is used to manage all static const variables,
 * is basically a class for configuring the game.
 */

public final class GameConstants {
    /** Avoid instantiation of this class.*/
    private GameConstants(){
    }
    /**
     * 'Character Width Area', this variable is used to manage the working area
     * of the SpriteObject, This Working Area is the area used to detect collisions.
     * The final width of the SpriteObject will be in X: getX()+CWA, getX()+getWidth() -CWA
     * default=2
     */
    public static float CWA = 2;
    /**
     * 'Character Height Area', this variable is used to manage the working area
     * of the SpriteObject, This Working Area is the area used to detect collisions.
     * The final height of the SpriteObject will be in Y: getY()+CHA, getY()+getHeight() -CHA
     * default=0.2
     */
    public static float CHA = 0.5F;

    /**************************************
     * ANIMATION SPECIFIC CONSTANTS       *
     **************************************/
    /** 'Animation Blinking Duration',
     *  This is the Time our SpriteObject will
     *  blinking is the method is called.
     *  default=5
     */
    public static int ABD = 2;
    /** 'Animation Blinking Toggling' time duration
     *  is the time delay in each display frame.
     *  ABT seconds Alpha 1, and ABT seconds Alpha 0.
     *  default=0.05.
     */
    public static float ABT = 0.05F;


    /******************************************
     * SIGNALING HANDLING
     ******************************************/
    /** 'Signal Processing Timeout' is the time
     *  in which signals are to be processed and
     *  sent in case any of these meet the
     *  requirements.
     */
    public static float SPT = 0.3F;


    /***************************************
     * GAME SETTINGS
     ***************************************/
    /**
     * Vibration lets to generate vibrations in the game.
     */
    public static boolean VIBRATION = true;
}
