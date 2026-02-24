package com.tecoloteinc.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.tecoloteinc.bosses.Sprites.Characters.Player.GamePlayer;
import com.tecoloteinc.bosses.Sprites.Characters.Enemies.EnemyBunny;
import com.tecoloteinc.bosses.utils.InputOutput.GameInputs;
import com.tecoloteinc.bosses.Sprites.Other.Dialogs;
import com.tecoloteinc.bosses.Sprites.Other.EyeEnemy;
import com.tecoloteinc.bosses.Sprites.Characters.Enemies.SoldierEnemy;
import com.tecoloteinc.bosses.Sprites.SpriteObject;

import static com.tecoloteinc.bosses.utils.Others.GameConstants.*;

import java.util.ArrayList;

/**
 * This class holds all the methods for controlling the camera,
 * this class must be inherited by screens wanting to draw something.
 */
public abstract class GameEngine extends OrthogonalTiledMapRenderer implements Screen{

    /**
     * enable this variable to see debug output in console (ONLY FOR DEVELOPMENT)
     */
    private boolean DEBUG_MODE_ON = false;

    /*******************************************
     * Queues for Tiles and Sprites
     *  This queues stores all sprites and
     *  all Tiles, this class does not check
     *  for collision problems.
     ******************************************/
    /**
     * Only used to keep track of the movable characters
     */
    private ArrayList sprites_buffer = new ArrayList<SpriteObject>();
    private ArrayList dialogs_buffer = new ArrayList<Dialogs>();
    private ArrayList simple_sprites_buffer = new ArrayList<Sprite>();

    // Keep the count of the Sprites
    private int sprites_size;
    // Keep the count of the Sprites
    private int simple_sprites_size;
    //
    private int dialogs_size;

    /***********************************************************************************************
     * Camera and viewports
     **********************************************************************************************/
    // Objects for viewport and camera.
    public OrthographicCamera camera;
    public Viewport viewport;
    // Keep the screen size in all inherited classes.
    private float ViewPort_Width;
    private float ViewPort_Height;

    // View area ratio, this is the black area of the screen.
    float darkAreaWidthHalf;
    float darkAreaHeightHalf;

    /***********************************************************************************************
     *                           Map animation variables
     **********************************************************************************************/

    /**
     * Variables to request load a new Map, a Map can't be loaded
     * as is or will cause the container to be invalidates when
     * scanning the Tiles.
     */
    private class gameMap {
        gameMap(String Map, int hero_position_x, int hero_position_y) {
            newMap = Map;
            heroX = hero_position_x;
            heroY = hero_position_y;
        }
        String newMap = "";
        int heroX = 0;
        int heroY = 0;
    }

    /**
     * map variable to control loading new maps.
     */
    private gameMap map;

    /***********************************************************************************************
     *                           Screen animation variables
     **********************************************************************************************/
    /**
     * controls when the render should process an screen animation
     */
    private boolean underAnimation = false;
    /**
     * Measures the time of the animation
     */
    private float animationTimer = 0;
    /**
     * Controls the amount of time that the animation should be played.
     */
    private int animationDuration;

    /**
     * Enum with all the Animation Types
     */
    public enum ScreenAnimations {
        UNLOAD,
        LOAD,
        ANOTHER,
    }

    /**
     * Type of animation requested
     */
    ScreenAnimations animation;

    /***********************************************************************************************
     *                               Sprites control
     **********************************************************************************************/

    /**
     * If this variable is true, the game wont process any game mobility
     */
    private boolean GameStopped = false;

    public void setEngineStop() {
        GameStopped = true;
    }

    public void cancelEngineStop() {
        GameStopped = false;
    }

    public boolean isGameStopped() {
        return GameStopped;
    }

    /**
     * What Sprite the camera will be following
     */
    private SpriteObject Sprite_focus;

    //==========================================================
    FPSLogger logger;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    protected GamePlayer Hero;
    protected GameInputs controller;

    /*******************************************************************
     * Maps Variables
     *******************************************************************/
    protected static TiledMap tiledMap = new TmxMapLoader().load("Map/FinalMaps/HeroHouse.tmx");

    // Get the width and height of our maps
    int mapWidth;
    int mapHeight;

    // Layer with the foot level 'things'
    TiledMapTileLayer lowerLayer;
    // Layer with the foot level 'things'
    TiledMapTileLayer enemiesLayer;
    // Context for writing into GFX card RAM.
    private static SpriteBatch main_batch = new SpriteBatch();

    /***********************************************************************************************
     * Fonts Area
     **********************************************************************************************/
    BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/Zelda.fnt"), false);

    /***********************************************************************************************
     * Music Area
     **********************************************************************************************/
    Music WorldMusic;

    /***********************************************************************************************
     * General Game Area
     **********************************************************************************************/
    /**
     * Set to true to finish the game
     */
    private boolean GameIsOver = false;
    /**
     * Enable this variable to delete all SpriteObjects
     */
    private boolean requestClean = false;

    /**
     * Call this method to finish the game.
     */
    public void setGameOver() {
        GameIsOver = true;
        requestClean = true;

        //tiledMapRenderer.render();
    }

    /**
     * Method to request the current state of the game.
     *
     * @return return true if the game is already end.
     */
    public boolean isGameOver() {
        return GameIsOver;
    }

    /**
     * Convert normal coordinates into World coordinates.
     *
     * @param x Coordinate in X
     * @param y Coordinate in Y
     * @return the new vector with the world coordinates.
     */
    public Vector2 unproject(float x, float y) {
        Vector2 positions = new Vector2(x, y);
        positions = viewport.unproject(positions);
        return positions;
    }

    /**
     * Convert normal coordinates into Screen coordinates. the
     * Y axis is starting from below to the upper side of the screen.
     *
     * @param x Coordinate in X
     * @param y Coordinate in Y
     * @return the new vector with the Screen coordinates.
     */
    public Vector2 unproject_screen(float x, float y) {
        Vector2 positions = new Vector2((x - darkAreaWidthHalf) * getScreenRatioX(),
                ((-y + darkAreaHeightHalf) * getScreenRatioY()) + ViewPort_Height);
        return positions;
    }

    /**
     * This method will return the 0 position of the visible area of the Screen, regardless
     * if the Map is longer or smaller.
     *
     * @return 0 position of the visible screen.
     */
    public Vector2 getScreenOrigin() {
        return new Vector2((camera.position.x - (camera.viewportWidth / 2)),
                (camera.position.y - (camera.viewportHeight / 2)));
    }

    /**
     * This method will return the 0 position of the visible area of the Screen starting
     * from the right, regardless if the Map is longer or smaller.
     *
     * @return 0 position of the visible screen.
     */
    public Vector2 getRightScreenOrigin() {
        return new Vector2((camera.position.x + (camera.viewportWidth / 2)),
                (camera.position.y + (camera.viewportHeight / 2)));
    }

    /**
     * This method puts the camera to follow an actor in the world.
     *
     * @param actor the Sprite to follow with the camera.
     */
    public void camFollowActor(SpriteObject actor) {
        Sprite_focus = actor;
    }

    /**
     * With this method the size of the screen is controlled,
     * an object inheriting this class, will have to pass a
     * drawable area that could be different from the screen
     * size.
     *
     * @param width,  width of the drawable area.
     * @param height, height of the drawable area.
     */
    public GameEngine(int width, int height) {
        super(tiledMap, main_batch);
        ViewPort_Width = width;
        ViewPort_Height = height;

        logger = new FPSLogger();
        // Start the system logger.
        if (DEBUG_MODE_ON) {
            logger = new FPSLogger();
        }
        /*************************************************************
         * This section is used to manage camera position and the
         * view port, 256x224 is used as is the size of the image, so
         * the view port will take 1 pixel per unit.
         ************************************************************/
        camera = new OrthographicCamera();
        /* Fit the screen to the size of defined pixel size */
        viewport = new FitViewport(ViewPort_Width, ViewPort_Height, camera);
        /* Apply Changes to view port*/
        viewport.apply();
        /* Set the camera to the desired position */
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);

        // Keep the ratio of the visible drawable area
        darkAreaWidthHalf = (Gdx.graphics.getWidth() - viewport.getScreenWidth()) / 2;
        darkAreaHeightHalf = (Gdx.graphics.getHeight() - viewport.getScreenHeight()) / 2;
    }

    /**
     * This method is called by the Screen class and is used to call
     * initialization method in child classes (Game Screens).
     */
    @Override
    public final void show() {
        /************************************************************
         * Call initialize method of the child class
         ***********************************************************/
        OnCreate();
    }

    /**
     * This function allow the GameEngine to load a map. The map passed to the Engine.
     * The tiledMap is parsed tile by tile, if an enemy is found it will be created
     * at this point. All SpriteObjects from the previous map are going to be destroyed.
     * The tiledMap should be located at "/Map/"
     *
     * @param map The String of the name of the MAP.
     */
    public void loadMap(String map) {
        /** @TODO Destroy all Sprite Objects.*/
        setAnimation(ScreenAnimations.UNLOAD, 1);
        for (Object o : sprites_buffer) {
            SpriteObject sprite = (SpriteObject) o;
            sprite.getTexture().dispose();
            sprites_size--;
        }
        sprites_buffer.clear();

        // Load the Map.
        setMap(new TmxMapLoader().load("Map/FinalMaps/" + map));
        if (WorldMusic != null) {
            WorldMusic.stop();
            WorldMusic.dispose();
        }

        WorldMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/"+getMap().getProperties().get("bgm", String.class)));
        System.out.print("----------------Music: " + getMap().getProperties().get("bgm", String.class));
        WorldMusic.setVolume(0.5F);
        WorldMusic.play();
        WorldMusic.setLooping(true);


        // Get the width and height of our maps
        mapWidth = getMap().getProperties().get("width", Integer.class);
        mapHeight = getMap().getProperties().get("height", Integer.class);

        System.out.print("MapHeight: " + mapHeight);

        setView(camera);

        // Layer with the foot level 'things'
        lowerLayer = (TiledMapTileLayer) getMap().getLayers().get("LowerLayer");
        // Layer with the foot level 'things'
        enemiesLayer = (TiledMapTileLayer) getMap().getLayers().get("Enemies");

        //MapObjects objects = map.getLayers().get("Obstacles").getObjects();
        for (int i = 0; i < mapWidth; i += 1) {
            for (int j = 0; j < mapHeight; j += 1) {

                if (getCellProperty(enemiesLayer, "Type", i * 16, j * 16).equals("Hero")) {
                    Hero = new GamePlayer(this, i * 16, j * 16);
                    controller = new GameInputs(this, Hero, 0, 0, 35);
                    Gdx.input.setInputProcessor(controller);
                }

                if (getCellProperty(enemiesLayer, "Type", i * 16, j * 16).equals("GreenSoldier")) {
                    SoldierEnemy m_soldier = new SoldierEnemy(this, i * 16, j * 16);
                }
                if (getCellProperty(enemiesLayer, "Type", i * 16, j * 16).equals("EyeBoss")) {
                    EyeEnemy boss = new EyeEnemy(this, i * 16, j * 16);
                }
                if (getCellProperty(enemiesLayer, "Type", i * 16, j * 16).equals("BunnyEnemy")) {
                    EnemyBunny m_bunny = new EnemyBunny(this, i * 16, j * 16);
                }
            }
        }
    }

    /**
     * Overload function of loadMap. This one accept the x and y of the hero to re-locate
     * it on the Map. This function will request the render engine, to load a new Map
     * whenever it is free to call it.
     *
     * @param map_path The String of the name of the MAP.
     * @param heroX    Cell X.
     * @param heroY    Cell Y.
     */
    public void loadMap(String map_path, int heroX, int heroY) {
        map = new gameMap(map_path, heroX, heroY);
    }

    /**
     * Method to add an SpriteObject, if the SpriteObject is not stored with this method, will
     * not be processed by the Engine.
     *
     * @param dialog The Sprite to be added into the array to be processed by the Engine.
     */
    public final void addDialog(Dialogs dialog) {
        if (dialog != null) { //Do not add null Sprites
            dialogs_buffer.add(dialogs_size, dialog);
            dialogs_size++;
        }
    }

    /**
     * Remove an SpriteObject from the sprites_buffer.
     *
     * @param sprite The Object to be removed from the lsit
     */
    public boolean delDialog(Dialogs sprite) {
        if (dialogs_buffer.remove(sprite)) {
            dialogs_size--;
            return true;
        }
        return false;
    }

    /**
     * Method to add an SpriteObject, if the SpriteObject is not stored with this method, will
     * not be processed by the Engine.
     *
     * @param sprite The Sprite to be added into the array to be processed by the Engine.
     */
    public final void addSprite(SpriteObject sprite) {
        if (sprite != null) { //Do not add null Sprites
            System.out.print("-----------------Added \n");
            sprites_buffer.add(sprites_size, sprite);
            sprites_size++;
        }
    }

    /**
     * Remove an SpriteObject from the sprites_buffer.
     *
     * @param sprite The Object to be removed from the lsit
     */
    public boolean delSprite(SpriteObject sprite) {
        if (sprites_buffer.remove(sprite)) {
            sprites_size--;
            return true;
        }
        return false;
    }

    /**
     * Method to add a simple sprite, this type of Sprite is not the same as SpriteObject
     * and does not contains the whole properties. It is basically a Texture that is going
     * to be rendered but will have no action with other Sprites.
     * This was created with the Controller in Mind.
     *
     * @param sprite @TODO Add description.
     */
    public final void addSprite(Sprite sprite) {
        simple_sprites_buffer.add(simple_sprites_size, sprite);
        simple_sprites_size++;
    }

    /**
     * This function will execute the desired animation when required. The
     * function is always called by render function but only process the
     * animation if was previously enabled.
     *
     * @param time is the amount of time displaying the animation.
     */
    float direc = -0.05F;

    private void screenAnimation(float time) {
        if (underAnimation) {
            switch (animation) {
                case UNLOAD: {
                    camera.zoom += direc;
                    if (camera.zoom <= 0) {
                        direc = 0.05F;
                    }
                    if (camera.zoom >= 1) camera.zoom = 1;
                    break;
                }
            }
            animationTimer += time;

            // Stop the animation.
            if (animationTimer > animationDuration) {
                underAnimation = false;
                animationDuration = 0;
                animationTimer = 0;
                camera.rotate(0);
                direc = -0.1F;
                if (camera.zoom != 1)
                    camera.zoom = 1F;
            }
        }
    }

    /**
     * Enables the animation with the specified characteristics.
     *
     * @param anim               type of animation.
     * @param animation_duration amount of time to display 'anim' animation.
     */
    public void setAnimation(ScreenAnimations anim, int animation_duration) {
        underAnimation = true;
        animation = anim;
        animationDuration = animation_duration;
    }

    /***********************************************************************************************
     * Main Engine, this method is called along the life of the game. It is for
     * processing sprites or call other processing functions.
     *
     * @param delta Difference of time in seconds each time this method is called
     *              should be around 1/60 times per second.
     **********************************************************************************************/
    @Override
    public final void render(float delta) {

        /** Logs and print the Frame Rate in console */
        logger.log();
        if (DEBUG_MODE_ON) {
            logger.log();
        }
        /** Call the child process, to let other classes to do their
         * stuff like processing inputs or other stuff.
         * Is the loop in GameScreens not in SpriteObjects.*/
        if (!GameStopped) {
            game_loop(delta);

            /** Calls the executive function of each sprite stored */
            character_movements(delta);
            SpriteCollision();

            /** Process if a new Map was requested */
            if (map != null) {
                loadMap(map.newMap);
                Hero.setPosition(map.heroX * 16, (-map.heroY - 1 + mapHeight) * 16);
                map = null;
            }
        }
        /** Update the camera with the previous parameters. */
        camera.update();

        /** This section manages the actor being followed by the camera */
        if (Sprite_focus != null) {
            camera.position.set(Sprite_focus.getX() + Sprite_focus.getWidth() / 2, Sprite_focus.getY() + Sprite_focus.getHeight() / 2, 0);
            setView(camera);
        }
        /** Begin the config stuff */
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        /** Set the projection matrix */
        main_batch.setProjectionMatrix(camera.combined);

        /*******************************************************/
        if (requestClean) {
            System.out.print("Erased everything\n");
            sprites_buffer.clear();
            sprites_size = 0;
            requestClean = false;
            addSprite(Hero);
            Hero.setBlinking(false);
            WorldMusic.stop();
            WorldMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/FairyFountain.ogg"));
            WorldMusic.play();
        }
        /*******************************************************************************************
         *                    Start the drawing stuff here
         ******************************************************************************************/
        main_batch.begin();

        /** Render the Base layer*/
        renderLayer("BaseLayer");

        /** Render the Lower layer*/
        renderLayer("LowerLayer");

        /*****************************************
         * Draw all Sprites stored in
         * sprites_buffer ArrayList only if they are
         * assigned to the first layer.
         ****************************************/
        for (int i = 0; i < sprites_buffer.size(); i++) {
            SpriteObject sprite = (SpriteObject) sprites_buffer.get(i);
            // Only draw the SpriteObject if is in the first layer
            if (0 == sprite.getLayerLevel()) sprite.draw_sprite(main_batch, delta);
            if (sprite.isDeleteRequired()) {
                delSprite(sprite);
            }
        }

        /*******************************************************/
        /** Draw simple sprites as the main input control
         *******************************************************/
        for (Object o : simple_sprites_buffer) {
            Sprite sprite = (Sprite) o;
            sprite.draw(main_batch);
        }
        screenAnimation(delta);


        /*======================================================================================
         * Upper SpriteObjects
         *====================================================================================*/
        for (int k = 0; k < sprites_buffer.size(); k++) {
            SpriteObject sprite_obj = (SpriteObject) sprites_buffer.get(k);
            sprite_obj.draw_sprite(main_batch, delta);
            if (sprite_obj.isDeleteRequired()) {
                delSprite(sprite_obj);
            }
        }

        /** Render the upper layer*/
        renderLayer("UpperLayer");

        //------------------------------------------------------------------------------------------
        // Dialogs
        //------------------------------------------------------------------------------------------
        if (dialogs_buffer.size() > 0) {
            Dialogs dialog = (Dialogs) dialogs_buffer.get(0);
            if (dialog != null) {
                //GameStopped = true;
                dialog.draw_sprite(main_batch, delta);
                // Only draw the SpriteObject if is in the first layer
                dialog.executive(delta);
                if(dialog.isDeleteRequired()){
                    dialogs_buffer.remove(dialog);
                    dialogs_size--;
                }
            }
        }

        //------------------------------------------------------------------------------------------
        // Fonts
        //------------------------------------------------------------------------------------------
        if (GameIsOver) {
            font.setColor(1, 1, 1, 1);
            font.draw(main_batch, "Game Over!!!", Hero.getX(), Hero.getY());
        }
        main_batch.end();
        /* ------- End the Drawings here ------------- */

        // Request controller to draw.
        shapeRenderer.setProjectionMatrix(camera.combined);
        Vector2 origin_coordinates = getScreenOrigin();
        controller.renderController(shapeRenderer, origin_coordinates);

        /*******************************************************
         *  Enable the workingArea rectangles/points of the SpriteObjects
         *******************************************************/
        if (DEBUG_MODE_ON) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            for (Object o : sprites_buffer) {
                SpriteObject sprite_dbg = (SpriteObject) o;
                shapeRenderer.setColor(1, 0, 0, 0.3f);
                /** dr stands Debug Rectangle */
                Rectangle dr = new Rectangle(sprite_dbg.getActiveRectangle());
                shapeRenderer.rect(dr.getX(), dr.getY(), dr.getWidth(), dr.getHeight());
                shapeRenderer.setColor(0, 0, 0, 0.3f);
                shapeRenderer.circle(
                        (dr.getX() + (dr.getWidth() / 2)),
                        (dr.getY() + (dr.getHeight() * CHA)),
                        1);
            }
        }
        shapeRenderer.end();
    }

    /**
     * Calls the executive function in all SpriteObjects so they
     * can process any movement or Animation.
     *
     * @param delta time between each call to this function.
     */
    protected void character_movements(float delta) {
        for (Object o : sprites_buffer) {
            SpriteObject sprite = (SpriteObject) o;
            sprite.executive(delta);
            sprite.processCurrentTile();
        }
    }

    /**
     * This method check if the s rectangle is colliding with other sprite rectangle.
     * The rectangle is not the Texture size, is a preprocessed rectangle based on the
     * Texture Size minus CWA in X, and Y * CHA. Usually CHA is a fraction of 1.
     * <p/>
     * |-- CHA
     * .-------_------.
     * |       |      |
     * |       |      |
     * |  ,--------,  |
     * |  |        |<-|----- Collision-able Area
     * |  |        |  |
     * |  |        |  |<---- Texture
     * |__|________|__|
     * /\          /\
     * CWA         CWA
     *
     * @return return true if the sprite is colliding, false otherwise.
     * @TODO modify this function to be called only once.
     */
    public void SpriteCollision() {
        // Counter to perform the tree sprite collision detection.
        int counter = 0;

        for (Object o1 : sprites_buffer) {
            SpriteObject sprite_o1 = (SpriteObject) o1;

            for (int i = counter; i < sprites_buffer.size(); i++) {
                SpriteObject sprite_o2 = (SpriteObject) sprites_buffer.get(i);
                /** Build two rectangles with the processed info, since we are not using the
                 *  the texture properties for collisions. */
                Rectangle o2_rectangle = new Rectangle(sprite_o2.getX() + CWA, sprite_o2.getY(), sprite_o2.getWidth() - CWA, sprite_o2.getHeight() * CHA);

                if (sprite_o1 != sprite_o2) {
                    if (Intersector.overlaps(o2_rectangle, new Rectangle(sprite_o1.getX() + CWA, sprite_o1.getY(),
                            sprite_o1.getWidth() - CWA, sprite_o1.getHeight() * CHA))) {
                        sprite_o1.colliding(sprite_o2);
                        sprite_o2.colliding(sprite_o1);
                        //System.out.print(sprite_o2.getSprite_UID() + " <-> Colliding <-> " + sprite_o1.getSprite_UID() + "\n");
                    }
                }
            }
            counter++;
        }
    }

    /***********************************************************************************************
     *                               Tiles Manipulation
     **********************************************************************************************/

    /**
     * This override-able method is used to process the collision with tiles
     * by reading the property "Type", this default method detect only Block
     * Tiles and does not allow the SpriteObject to move to the new position
     * making the effect of a 'block' in front of it.
     * The SpriteObject has 4 points that this method will check which Tile Type
     * are touching.
     *
     * @param o, the SpriteObject under analysis.
     * @see {@getCellsProperties The collection}
     */
    public void processTileCollision(SpriteObject o) {
        // Get the 'type' property from the Tile.
        String[] cell_value = getCellsProperties(o);
        int directionX = o.getDirectionX();
        int directionY = o.getDirectionY();
        if (directionX == 1) {
            // Cell X2Y1 and X2Y2
            if ((cell_value[1].equals("Block")) || (cell_value[3].equals("Block"))) {
                o.setPosition(o.getPreviousPositions().x, o.getY());
                /** This function let us to inform the sprite that it was returned by a block */
                o.setBlocked(true);
            }
        }
        if (directionX == -1) {
            // Cell X1Y1 and X1Y2
            if ((cell_value[0].equals("Block")) || (cell_value[2].equals("Block"))) {
                o.setPosition(o.getPreviousPositions().x, o.getY());
                o.setBlocked(true);
            }
        }

        if (directionY == 1) {
            // Cell X1Y1 and X2Y1
            if ((cell_value[0].equals("Block")) || (cell_value[1].equals("Block"))) {
                o.setPosition(o.getX(), o.getPreviousPositions().y);
                o.setBlocked(true);
            }
        }
        if (directionY == -1) {
            // Cell X1Y2 and X2Y2
            if ((cell_value[2].equals("Block")) || (cell_value[3].equals("Block"))) {
                o.setPosition(o.getX(), o.getPreviousPositions().y);
                o.setBlocked(true);
            }
        }
    }

    /**
     * This method gets the four tiles properties that the SpriteObject is
     * touching.
     * Tile1       Tile2
     * .--------.---------.
     * |         |         |
     * |  (x1y1)----(x2y1) |
     * |      |  |  |      |
     * '------|--'--|------' SpriteObject Texture.
     * |      |  |  |      |
     * |  (x1y2)----(x2y2) |
     * |         |         |
     * `---------`---------`
     * Tile3      Tile4
     *
     * @param obj The SpriteObject to be evaluated.
     * @return The value of the Tile's 'Type' property.
     */
    public String[] getCellsProperties(SpriteObject obj) {
        String[] xy_tile = new String[4];
        // X1Y1 Point of the Sprite.
        xy_tile[0] = getCellProperty(lowerLayer, "Type", obj.getX() + CWA, obj.getY() + obj.getHeight() * CHA);
        // X2Y1 Point of the Sprite.
        xy_tile[1] = getCellProperty(lowerLayer, "Type", obj.getX() + obj.getWidth() - CWA, obj.getY() + obj.getHeight() * CHA);
        // X1Y2 Point of the Sprite.
        xy_tile[2] = getCellProperty(lowerLayer, "Type", obj.getX() + CWA, obj.getY());
        // X2Y2 Point of the Sprite.
        xy_tile[3] = getCellProperty(lowerLayer, "Type", obj.getX() + obj.getWidth() - CWA, obj.getY());
        return xy_tile;
    }

    /**
     * Gets the cell property @TODO arreglar
     */
    public String getCellProperties(int x, int y) {
        String xy_tile;
        // X1Y1 Point of the Sprite.
        xy_tile = getCellProperty(enemiesLayer, "Type", x * 16, y * 16);
        return xy_tile;
    }

    /**
     * Override method used to get a cell property from the "lowerLayer"
     *
     * @param x Coordinate x.
     * @param y Coordinate y.
     * @return the value of the property of the cell.
     */
    public String getCellProperty(String type, float x, float y) {
        return getCellProperty(lowerLayer, type, x, y);
    }

    /**
     * Transform the indicated cell by coordinates (x,y) into
     * tile specified by tile_id in the 'lowerLayer'.
     *
     * @param x Coordinate x in the map.
     * @param y Coordinate y in the map.
     */
    public void transformCell(float x, float y, int tile_id) {
        TiledMapTileLayer.Cell cell = lowerLayer.getCell(
                (int) (x / lowerLayer.getTileWidth()),
                (int) (y / lowerLayer.getTileHeight()));

        // Check whether the Cell contains valid data.
        if (cell != null && cell.getTile() != null) {
            cell.setTile(getMap().getTileSets().getTile(tile_id + 1));
        }
    }

    /**
     * Transform the indicated cell by coordinates (x,y) into
     * tile specified by tile_id in the 'lowerLayer'.
     *
     * @param x Coordinate x in the map.
     * @param y Coordinate y in the map.
     */
    public void changeCell(float x, float y, String key, String value) {
        TiledMapTileLayer.Cell cell = lowerLayer.getCell(
                (int) (x / lowerLayer.getTileWidth()),
                (int) (y / lowerLayer.getTileHeight()));

        // Check whether the Cell contains valid data.
        if (cell != null && cell.getTile() != null) {
            cell.getTile().getProperties().put(key, value);
        }
    }

    /**
     * This method returns the value of the x,y cell.
     *
     * @param x Coordinate x.
     * @param y Coordinate y.
     * @return the value of the property of the cell.
     */
    public String getCellProperty(TiledMapTileLayer layer, String property, float x, float y) {
        String return_value = "invalid";

        TiledMapTileLayer.Cell cell = layer.getCell(
                (int) (x / layer.getTileWidth()),
                (int) (y / layer.getTileHeight()));

        // Check whether the Cell contains valid data.
        if (cell != null && cell.getTile() != null) {
            if (cell.getTile().getProperties().containsKey(property)) {
                return_value = cell.getTile().getProperties().get(property).toString();
            }
        }
        if (DEBUG_MODE_ON) {
            System.out.print(" Tile: " + (int) (x / lowerLayer.getTileWidth()) + " " + (int) ((-y + ViewPort_Height) / lowerLayer.getTileHeight()) + " Type: " + return_value);
        }
        return return_value;
    }


    /**
     * This function is executed when the window is resized
     * like resizing Desktop windows for instance.
     *
     * @param height New screen height size.
     * @param width  New screen width size.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        setView(camera);
        // Keep the ratio of the visible drawable area
        darkAreaWidthHalf = (Gdx.graphics.getWidth() - viewport.getScreenWidth()) / 2;
        darkAreaHeightHalf = (Gdx.graphics.getHeight() - viewport.getScreenHeight()) / 2;
    }


    /***********************************************************************************************
     *                                    Getters
     **********************************************************************************************/

    /**
     * Return the Player object.
     *
     * @return The last GamePlayer object registered.
     */
    public GamePlayer getHero() {
        return Hero;
    }

    /**
     * Get the Screen ratio of the X axis.
     *
     * @return return the value of the ScreenRatio in X.
     */
    public float getScreenRatioX() {
        return ViewPort_Width / viewport.getScreenWidth();
    }

    /**
     * Get the Screen ratio of the Y axis.
     *
     * @return return the value of the ScreenRatio in Y.
     */
    public float getScreenRatioY() {
        return ViewPort_Height / viewport.getScreenHeight();
    }


    /**
     * @TODO Add Header
     */
    @Override
    public void pause() {

    }

    /**
     * @TODO Add Header
     */
    @Override
    public void resume() {

    }

    /**
     * @TODO Add Header
     */
    @Override
    public void hide() {

    }

    /**
     * Used to clear all the stored objects in the
     * video card memory. The garbage collector does not
     * clean the textures.
     */
    @Override
    public void dispose() {
        System.out.print("Exiting... Disposing sprites textures.");
        for (Object o : sprites_buffer) {
            Sprite sprite = (Sprite) o;
            sprite.getTexture().dispose();
        }
    }


//    /**
//     * This method is to set the SoundFX controller, they will
//     * be called by the Character class, this method is just to
//     * define the behaviour of each case.
//     *
//     * @param soundFX The index of the SFX that the character class wants to play.
//     */
//    public void playSound(int soundFX) {
//        switch (soundFX) {
//            case HERO_HURT:
////                Player.
//            case SFX_WALKING:
//                //EnemyBunnySFX.stop();
//                //EnemyBunnySFX.loop();
//                break;
//            case SFX_STOP:
//                //EnemyBunnySFX.stop();
//                break;
//            default:
//        }
//    }

    public void ButtonPressed(int button) {
        System.out.print("Button Pressed " + button + "\n");
        if ((button == 1) && (dialogs_buffer.size() > 0)) {
            Dialogs dialog = (Dialogs) dialogs_buffer.get(0);
            if (dialog != null) {
                dialogs_buffer.remove(dialog);
                dialogs_size--;
                GameStopped=false;
            }
        }
    }

    /**
     * When this method is called, the debug mode will start, this mean that
     * rectangles around the Sprites are going to be displayed.
     */
    public void setDebugMode() {
        DEBUG_MODE_ON = true;
    }


    /*************************************************************
     *   Abstract methods
     ************************************************************/
    /**
     * This functions must be defined in the child class
     * this function will be executed during the game execution.
     *
     * @param dt Time between each call to this function.
     */
    public abstract void game_loop(float dt);

    /**
     * This function must be defined in the child class
     * this function will be executed once the drawing system I.e:
     * camera and view ports, buffers are initialized.
     */
    protected abstract void OnCreate();

    /**
     * Render all layers, this method is copied from the BatchTiledMapRenderer to avoid
     * beging and end with as the batch is the same.
     * @param layers array of layer to render.
     */
    @Override
    public void render(int[] layers) {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        for (int layerIdx : layers) {
            MapLayer layer = getMap().getLayers().get(layerIdx);
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    System.out.print("here\n");
                    renderTileLayer((TiledMapTileLayer) layer);
                }
            }
        }
    }

    /**
     * Call this method with the name of a layer in order to render
     * a layer of the loaded map.
     *
     * @param layer_name String name of the layer in tmx.
     */
    public void renderLayer(String layer_name) {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        MapLayer layer = getMap().getLayers().get(layer_name);
        if (layer.isVisible()) {
            if (layer instanceof TiledMapTileLayer) {
                renderTileLayer((TiledMapTileLayer) layer);
            }
        }
    }
}
