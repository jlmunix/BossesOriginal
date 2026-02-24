package com.tecoloteinc.bosses.utils.AnimationsFolder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.concurrent.Callable;

/**
 * Cgf
 */
public class AnimationsEngine {

    public boolean can_be_moved = true;

    public static class AnimationSequenceType {
        public int horizontal_frame;
        public int vertical_frame;
        public float duration_time;
        public float pos_x;
        public float pos_y;
    }

    // The animation to be played.
    private AnimationSequenceType anim_sequence[];
    // Keeps the current frame of the anim_sequence.
    private int currentFrameCounter = 0;
    // Duration of each the full animation time.
    public float animationTime = 0;
    // Float current time.
    private float elapsedTime = 0;

    /**
     * This boolean variable controls when the animation is valid
     * this is invalidated once the cycle is completed.
     */
    private boolean isAnimationValid = false;
    /**
     * Set this variable to true, to hide the object at the end.
     */
    private boolean isInvisibleAfter = false;

    /**
     * Previous looking side.
     */
    private int currentSide = 0;

    /**
     * Vertical frame to work with.
     */
    private int sideToWork = -1;

    /**
     * Return the status of the validity of the animation
     */
    public boolean isAnimationValid() {
        return isAnimationValid;
    }


    /**
     * Call this function to know if the the SpriteObject must be hided.
     *
     * @return true if the SpriteObject should be hided.
     */
    public boolean isInvisibleAfter() {
        return isInvisibleAfter;
    }

    /**
     * Constructor that allows creation of a new animation. Use this Constructor to specify in which
     * vertical frame of the texture the animation should work.
     *
     * @param frame_sequence_hvt The frame to be taken from the Texture of the SpriteObject.
     * @param time               The complete duration of the animation.
     * @param is_movable         If the Sprite can be moved by it's IA or Controller during the animation.
     * @param current_side       Is the current position in v_frames of the sprite, before making any
     *                           edition with this animation.
     * @param side_to_work       Is the vertical frame to work with.
     */
    public void setNewAnimation(AnimationSequenceType[] frame_sequence_hvt, float time, boolean is_movable,
                                int current_side, int side_to_work, boolean hide_after) {
        if (!isAnimationValid) {
            anim_sequence = frame_sequence_hvt;
            can_be_moved = is_movable;
            animationTime = time;
            isAnimationValid = true;
            elapsedTime = 0;
            currentSide = current_side;
            sideToWork = side_to_work;
            currentFrameCounter = 0;
            isInvisibleAfter = hide_after;
        }
    }

    /**
     * Constructor that allows creation of a new animation.
     *
     * @param frame_sequence_hvt The frame to be taken from the Texture of the SpriteObject.
     * @param time               The complete duration of the animation.
     * @param is_movable         If the Sprite can be moved by it's IA or Controller during the animation.
     * @param current_side       Is the current position in v_frames of the sprite, before making any
     *                           edition with this animation.
     */
    public void setNewAnimation(AnimationSequenceType[] frame_sequence_hvt, float time, boolean is_movable,
                                int current_side, boolean hide_after) {
        if (!isAnimationValid) {
            anim_sequence = frame_sequence_hvt;
            can_be_moved = is_movable;
            animationTime = time;
            isAnimationValid = true;
            elapsedTime = 0;
            currentSide = current_side;
            currentFrameCounter = 0;
            sideToWork = -1;
            isInvisibleAfter = hide_after;
        }
    }

    public AnimationSequenceType getFrame(float time) {
        AnimationSequenceType return_data = anim_sequence[currentFrameCounter];
        elapsedTime += time;

        if (elapsedTime >= anim_sequence[currentFrameCounter].duration_time) {
            currentFrameCounter++;
            elapsedTime = 0;
            if (currentFrameCounter >= anim_sequence.length) {
                currentFrameCounter--;
                isAnimationValid = false;
                anim_sequence[currentFrameCounter].vertical_frame = currentSide;
            }
            return_data = anim_sequence[currentFrameCounter];
        }

        /* The Frame contains VerticalFrame==1 so, that's indicate that the vertical
         * frame should be the last one in the SpriteObject
         */
        if (anim_sequence[currentFrameCounter].vertical_frame == -1) {
            anim_sequence[currentFrameCounter].vertical_frame = currentSide;
        }


        // Special case, the user specified to work with the VerticalFrame 'sideToWork'.
        if ((sideToWork > -1) && (isAnimationValid != false)) {
            anim_sequence[currentFrameCounter].vertical_frame = sideToWork;
        }
        return return_data;
    }

}



