package com.tecoloteinc.bosses.utils.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tecoloteinc.bosses.GameEngine;
import com.tecoloteinc.bosses.Sprites.Characters.Player.GamePlayer;

import static com.tecoloteinc.bosses.utils.Others.GameConstants.*;

/**
 * This class is in charge of the movements of our
 * hero. It inherits from InputAdapter an object
 * that must be passed to the Gdx.Input system.
 */
public class GameInputs extends InputAdapter {

    // Our reference to the Sprite we will process
    GamePlayer player;

    // Work area for the Touch input.
    Vector2 controller_position;

    /**
     * Reference for the engine.
     */
    private GameEngine engine_ref;

    /**
     * Enables the dragging measurement.
     */
    private boolean enable_dragging = false;

    /**
     * Circles to be drawn in the render
     * circle[0] -> Base of the Pad.
     * circle[1] -> Movable pad.
     * circle[2] -> Button A.
     * circle[3] -> Button B.
     */
    protected Circle[] circle_area = new Circle[4];

    /**
     * Stores the pad_pointer number
     */
    private int pad_pointer = 0;

    /**
     * This constructor allows to pass events from the touch
     * to a Character.
     *
     * @param eng_ref      Reference of the engine.
     * @param sprite_obj   the sprite that receives the events.
     * @param controller_x Where to draw the Controller in X.
     * @param controller_y where to draw the Controller in Y.
     */
    public GameInputs(GameEngine eng_ref, GamePlayer sprite_obj, float controller_x, float controller_y, float radius) {
        engine_ref = eng_ref;
        player = sprite_obj;

        // Set the controller base in the requested position.
        controller_position = new Vector2(controller_x + radius, controller_y + radius);

        // Re-locate the Controller to have a centered origin.
        circle_area[0] = new Circle(controller_position.x, controller_position.y, radius);
        circle_area[1] = new Circle(circle_area[0].x, circle_area[0].y, radius / 2);
        circle_area[2] = new Circle(circle_area[1].radius * 4, controller_position.y - 15, circle_area[1].radius);
        circle_area[3] = new Circle(circle_area[1].radius * 2, controller_position.y + circle_area[1].radius, circle_area[1].radius);
    }

    /**
     * This function is executed when the finger touches the screen
     * but wont be called again if the finger keeps touching the screen.
     *
     * @param x       Position in X of the finger.
     * @param y       Position in Y of the finger.
     * @param pointer Finger index number.
     * @param button  Don't know @TODO what ii is this?
     * @return true if the data was processed false otherwise.
     */
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        /** Inform Hero that screen has been touched */
        Vector2 touch_pos = engine_ref.unproject_screen(x, y);

        // The new circle of 1 pixel of radius (pointer x and y) is inside of the circle?
        if (Intersector.overlaps(circle_area[0], new Circle(touch_pos.x, touch_pos.y, 1))) {
            pad_pointer = pointer;
            enable_dragging = true;
            // Center the inner circle.
            circle_area[1].x = touch_pos.x;
            circle_area[1].y = touch_pos.y;

            if (VIBRATION)
                Gdx.input.vibrate(50);
        }

        // The new circle of 1 pixel of radius (pointer x and y) is inside of the circle?
        if (Intersector.overlaps(circle_area[2],
                new Circle(engine_ref.viewport.getWorldWidth() - touch_pos.x, touch_pos.y, 1))) {
            // Reduce the circle to simulate a button press.
            circle_area[2].radius = circle_area[2].radius * .75f;
            if (!engine_ref.isGameOver()) {
                player.Button(0);
            }
            if (VIBRATION)
                Gdx.input.vibrate(50);
        }
        if (Intersector.overlaps(circle_area[3],
                new Circle(engine_ref.viewport.getWorldWidth() - touch_pos.x, touch_pos.y, 1))) {
            // Reduce the circle to simulate a button press.
            circle_area[3].radius = circle_area[3].radius * .75f;
            if (!engine_ref.isGameOver()) {
                player.Button(1);
                if (VIBRATION)
                    Gdx.input.vibrate(50);
            }
    }

    return true; // return true to indicate the event was handled
}

    /**
     * This method is executed when the finger is moved touching the screen.
     *
     * @param screenX Final position of the finger in X.
     * @param screenY Final position of the finger in Y.
     * @param pointer The number of the finger.
     * @return true to indicate it was processed, false otherwise.
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (enable_dragging && pointer == pad_pointer) {
            Vector2 touch_pos = engine_ref.unproject_screen(screenX, screenY);
            float pad_distance;
            float move_in_x, move_in_y;

            // Position the movable circle.
            circle_area[1].x = touch_pos.x;
            circle_area[1].y = touch_pos.y;

            // Get the angle of the two points.
            float angle = MathUtils.atan2(circle_area[1].y - circle_area[0].y, circle_area[1].x - circle_area[0].x);

            // Calculate the magnitude of the vector.
            pad_distance = (float) Math.sqrt((circle_area[1].x - circle_area[0].x) * (circle_area[1].x - circle_area[0].x) + (circle_area[1].y - circle_area[0].y) * (circle_area[1].y - circle_area[0].y));

            // if magnitude is bigger than circle base minus circle movable?
            if (pad_distance > circle_area[0].radius - circle_area[1].radius) {
                // Set the maximum possible position for the movable circle.
                circle_area[1].x = (circle_area[0].radius - circle_area[1].radius) * MathUtils.cos(angle) + circle_area[0].x;
                circle_area[1].y = (circle_area[0].radius - circle_area[1].radius) * MathUtils.sin(angle) + circle_area[0].y;
                // Recalculate the magnitude of the vector.
                pad_distance = (float) Math.sqrt((circle_area[1].x - circle_area[0].x) * (circle_area[1].x - circle_area[0].x) + (circle_area[1].y - circle_area[0].y) * (circle_area[1].y - circle_area[0].y));
            }
            // Get the components of the resulting vector.
            move_in_x = pad_distance * MathUtils.cos(angle);
            move_in_y = pad_distance * MathUtils.sin(angle);

            /* Only inform Player about the new positions, when the magnitude of the vector
               is greater than 1/4 of the radius of the base circle. */
            if (pad_distance > circle_area[0].radius / 4) {
                /* Inform GamePlayer about the new positions. It is multiplied by 1000
                 * in order to generate very long distances to make the GamePlayer keep
                 * moving trying to find those positions.
                 */
                if (!engine_ref.isGameOver()) {
                    player.touchedDown(player.getX() + (move_in_x * 1000),
                            player.getY() + move_in_y * 1000);
                }
            } else {
                if (!engine_ref.isGameOver()) {
                    player.touchedUp();
                }
            }
        }
        return true; // return true to indicate the event was handled
    }

    /**
     * This function is executed when the finger left the touchscreen.
     *
     * @param screenX Last X point the finger touched.
     * @param screenY Last Y point the finger touched.
     * @param pointer The number of the finger that releases.
     * @param button  @TODO don't know what it is.
     * @return return true to indicate the data was processed, false otherwise.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == pad_pointer) {
            circle_area[1].setPosition(circle_area[0].x, circle_area[0].y);
            enable_dragging = false;
            if (!engine_ref.isGameOver()) {
                player.touchedUp();
            }
        }
        // Resize the circle to its original size to simulate a button release.
        circle_area[2].radius = circle_area[1].radius;
        circle_area[3].radius = circle_area[1].radius;
        return true;
    }

    /**
     * This method draws the controller with the renderer provided by the engine.
     *
     * @param renderer ShapeRenderer provided by the engine.
     * @param origin   Starting coordinates of the visible screen.
     */
    public void renderController(ShapeRenderer renderer, Vector2 origin) {
        /*****************************************************
         * Controllers
         *****************************************************/
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0.22f, 0.33f, 0.44f, 0.25f);
        // Draw the base circle for our controller.
        renderer.circle(
                origin.x + circle_area[0].x,
                origin.y + circle_area[0].y,
                circle_area[0].radius);
        // Draw the movable pad.
        renderer.setColor(0.33f, 0.44f, 0.55f, 0.45f);
        renderer.circle(
                origin.x + circle_area[1].x,
                origin.y + circle_area[1].y,
                circle_area[1].radius);
        /********************************************************
         * Right Side of the Controller (Buttons)
         *******************************************************/
        renderer.setColor(0.10f, 0.10f, 0.65f, 0.45f);
        Vector2 rightCorner = engine_ref.getRightScreenOrigin();
        // Draw the button A.
        renderer.circle(
                rightCorner.x - circle_area[2].x,
                origin.y + circle_area[2].y,
                circle_area[2].radius);
        // Draw the button B.
        renderer.setColor(0.70f, 0.00f, 0.00f, 0.45f);
        renderer.circle(
                rightCorner.x - circle_area[3].x,
                origin.y + circle_area[3].y,
                circle_area[3].radius);
        /****************************************************
         *  End Drawing of controllers.
         ***********************/
        renderer.end();
    }
}