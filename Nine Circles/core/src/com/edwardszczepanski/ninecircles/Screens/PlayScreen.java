package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Scenes.Hud;
import com.edwardszczepanski.ninecircles.Sprites.Enemy;
import com.edwardszczepanski.ninecircles.Sprites.Hero;
import com.edwardszczepanski.ninecircles.Tools.*;

import java.util.ArrayList;


public class PlayScreen implements Screen {
    private NineCircles game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TextureAtlas atlas;

    // Sprites
    private Hero player;
    private Enemy enemy;
    //private ArrayList<Enemy> enemyList;

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


        //enemyList = new ArrayList<Enemy>();

        maploader = new TmxMapLoader();
        map = maploader.load("desert.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / NineCircles.PPM); // This can take in a second value which is scale
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);


        world = new World(new Vector2(0, 0), true); // Here are the gravity values. I don't want gravity
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Hero(world, this);
        
        //enemyList.add(new Enemy(world, this, (float)(Math.random())*1250/NineCircles.PPM, (float)(Math.random())*1250/NineCircles.PPM));
        enemy = new Enemy(world, this, (float)(Math.random())*1250/NineCircles.PPM, (float)(Math.random())*1250/NineCircles.PPM);

        world.setContactListener(new WorldContactListener());

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta){
        // You can apply a force or a linearImpulse
        if(Gdx.input.isKeyPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <= 5){
            player.b2body.applyForce(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && player.b2body.getLinearVelocity().y >= -5){
            player.b2body.applyForce(new Vector2(0, -4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 5){
            player.b2body.applyForce(new Vector2(4f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -5){
            player.b2body.applyForce(new Vector2(-4f, 0), player.b2body.getWorldCenter(), true);
        }

        //if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
        if(Gdx.input.justTouched()){
            player.heroBullet(world, this, player.b2body.getPosition().x, player.b2body.getPosition().y, player.getRotation(), player.radius/NineCircles.PPM);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new PlayScreen(game));
        }

    }


    public void update(float delta) {
        handleInput(delta);

        // This is how many times you want calculations done
        world.step(1 / 60f, 6, 2);

        // This updates the player sprite
        player.update(delta);

        if(enemy.destroyed == false){
            if (enemy.health <= 0){
                enemy.deleteBody();
                enemy.destroyed = true;
                hud.addScore(100);
                enemy = new Enemy(world, this, (float)(Math.random())*1250/NineCircles.PPM, (float)(Math.random())*1250/NineCircles.PPM);
            }
            else{
                enemy.update(delta);
            }
        }



        if (player.bulletList != null){
            for(int i = 0; i < player.bulletList.size(); ++i){
                player.bulletList.get(i).update(delta);
                if (System.nanoTime() - player.bulletList.get(i).creationTime > 2 * 1000000000.0f || player.bulletList.get(i).destroyed) {
                    player.bulletList.get(i).deleteBody();
                    player.bulletList.remove(i);
                }
            }

        }

        hud.update(delta);

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;

        gamecam.update(); // Always update our game came every iteration of our render cycle
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game map
        renderer.render();

        // Render Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        // Here is the code to display the sprite
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch); // Here is the actual Battle Cruiser being drawn

        if (enemy.destroyed == false){
            enemy.draw(game.batch);
        }

        if (player.bulletList != null){
            for(int i = 0; i < player.bulletList.size(); ++i){
                player.bulletList.get(i).draw(game.batch);
            }
        }
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
    }
}
