package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Scenes.Hud;
import com.edwardszczepanski.ninecircles.Sprites.Enemy;
import com.edwardszczepanski.ninecircles.Sprites.Hero;
import com.edwardszczepanski.ninecircles.Tools.*;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;


public class PlayScreen implements Screen {
    private NineCircles game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TextureAtlas atlas;

    // Lighting
    private RayHandler rayHandler;
    private ConeLight heroCone;
    private PointLight pointLight;

    // Sprites
    private Hero hero;
    private ArrayList<Enemy> enemyList;

    // Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(NineCircles game){
        atlas = new TextureAtlas("packers.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NineCircles.V_WIDTH / NineCircles.PPM, NineCircles.V_HEIGHT / NineCircles.PPM, gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("desert2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / NineCircles.PPM); // This can take in a second value which is scale

        world = new World(new Vector2(0, 0), true); // Here are the gravity values. I don't want gravity
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        hero = new Hero(world, this);


        enemyList = new ArrayList<Enemy>();
        enemyList.add(new Enemy(world, this, (float) (Math.random()) * 1250 / NineCircles.PPM, (float) (Math.random()) * 1250 / NineCircles.PPM));
        enemyList.add(new Enemy(world, this, (float) (Math.random()) * 1250 / NineCircles.PPM, (float) (Math.random()) * 1250 / NineCircles.PPM));
        enemyList.add(new Enemy(world, this, (float) (Math.random()) * 1250 / NineCircles.PPM, (float) (Math.random()) * 1250 / NineCircles.PPM));
        enemyList.add(new Enemy(world, this, (float) (Math.random()) * 1250 / NineCircles.PPM, (float) (Math.random()) * 1250 / NineCircles.PPM));
        enemyList.add(new Enemy(world, this, (float) (Math.random()) * 1250 / NineCircles.PPM, (float) (Math.random()) * 1250 / NineCircles.PPM));

        world.setContactListener(new WorldContactListener());

        //Lighting methods
        rayHandler = new RayHandler(world);
        RayHandler.useDiffuseLight(true);
        //rayHandler.setAmbientLight(.2f);
        rayHandler.setShadows(true);
        //rayHandler.setLightShader();

        //initializeHeroCone();
        intialiizePointLight();
    }

    private void intialiizePointLight(){
        //PointLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y)
        pointLight = new PointLight(rayHandler, 200, Color.WHITE, 300/ NineCircles.PPM,10/NineCircles.PPM, 10/NineCircles.PPM);
        pointLight.setSoftnessLength(0f);
        pointLight.setActive(true);
        pointLight.attachToBody(hero.b2body);
    }
    private void initializeHeroCone(){
        //ConeLight(RayHandler rayHandler, int rays, Color color,float distance, float x, float y, float directionDegree, float coneDegree) {
        heroCone = new ConeLight(rayHandler,200, Color.WHITE, 50/NineCircles.PPM, 100/NineCircles.PPM,100/NineCircles.PPM, 30, 50);
        heroCone.setSoftnessLength(0f);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Render game map
        renderer.render();

        // Render Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        // Here is the code to display the sprite
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        if(!enemyList.isEmpty()){
            for(int i = 0; i < enemyList.size(); ++i){
                if (!enemyList.get(i).isDestroyed()){
                    enemyList.get(i).draw(game.batch);
                }
            }
        }

        if (hero.bulletList != null){
            for(int i = 0; i < hero.bulletList.size(); ++i){
                hero.bulletList.get(i).draw(game.batch);
            }
        }

        hero.draw(game.batch); // Here is the actual Battle Cruiser being drawn

        game.batch.end();

        // Here is the lighting system. Anything before it will be affected by the lighting
        rayHandler.setCombinedMatrix(gamecam.combined.cpy().scl(1),
                gamecam.position.x, gamecam.position.y,
                gamecam.viewportWidth* gamecam.zoom,
                gamecam.viewportHeight * gamecam.zoom);

        rayHandler.render();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);


        hud.stage.draw();
    }


    public void update(float delta) {
        handleInput(delta);

        // This is how many times you want calculations done
        world.step(1 / 60f, 6, 2);

        // This updates the hero sprite
        hero.update(delta);
        //pointLight.setPosition(hero.b2body.getPosition().x/NineCircles.PPM,hero.b2body.getPosition().y/NineCircles.PPM);


        if (!enemyList.isEmpty()) {
            for (int i = 0; i < enemyList.size(); ++i){
                if(!enemyList.get(i).isDestroyed()){
                    if(enemyList.get(i).getHealth() <= 0){
                        enemyList.get(i).deleteBody();
                        enemyList.get(i).setDestroyed(true);
                        hud.addScore(100);
                        enemyList.add(new Enemy(world, this, (float)(Math.random())*1250/NineCircles.PPM, (float)(Math.random())*1250/NineCircles.PPM));
                    }
                    else{
                        enemyList.get(i).update(delta, hero.b2body.getPosition().x, hero.b2body.getPosition().y);
                    }
                }
            }
        }

        if (hero.bulletList != null){
            for(int i = 0; i < hero.bulletList.size(); ++i){
                hero.bulletList.get(i).update(delta);
                if (System.nanoTime() - hero.bulletList.get(i).creationTime > 2 * 1000000000.0f || hero.bulletList.get(i).destroyed) {
                    hero.bulletList.get(i).deleteBody();
                    hero.bulletList.remove(i);
                }
            }
        }

        hud.update(delta);

        gamecam.position.x = hero.b2body.getPosition().x;
        gamecam.position.y = hero.b2body.getPosition().y;

        gamecam.update(); // Always update our game came every iteration of our render cycle
        renderer.setView(gamecam);

        // Will have to optimize this


        //rayHandler.setCombinedMatrix(gamecam.combined.cpy().scl(NineCircles.PPM));




        rayHandler.update();
        //rayHandler.setCombinedMatrix(gamecam.combined.cpy().scl(NineCircles.PPM));



    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta){
        // You can apply a force or a linearImpulse
        if(Gdx.input.isKeyPressed(Input.Keys.W) && hero.b2body.getLinearVelocity().y <= 5){
            hero.b2body.applyForce(new Vector2(0, 4f), hero.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && hero.b2body.getLinearVelocity().y >= -5){
            hero.b2body.applyForce(new Vector2(0, -4f), hero.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && hero.b2body.getLinearVelocity().x <= 5){
            hero.b2body.applyForce(new Vector2(4f, 0), hero.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && hero.b2body.getLinearVelocity().x >= -5){
            hero.b2body.applyForce(new Vector2(-4f, 0), hero.b2body.getWorldCenter(), true);
        }

        //if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
        if(Gdx.input.justTouched()){
            hero.heroBullet(world, this, hero.b2body.getPosition().x, hero.b2body.getPosition().y, hero.getRotation(), hero.radius/NineCircles.PPM);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new PlayScreen(game));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            pointLight.setPosition(6/NineCircles.PPM, 6/NineCircles.PPM);
        }
    }



    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        hud.dispose();
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
        rayHandler.dispose();
        heroCone.dispose();
        pointLight.dispose();
    }
}
