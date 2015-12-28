package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Scenes.Hud;

public class PlayScreen implements Screen {
    private Game game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private SpriteBatch batch;
    private Hud hud;

    // Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(Game game, SpriteBatch batch){
        this.game = game;
        this.batch = batch;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NineCircles.V_WIDTH , NineCircles.V_HEIGHT, gamecam);
        hud = new Hud(batch);

        maploader = new TmxMapLoader();
        map = maploader.load("desert.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta){
        if(Gdx.input.isTouched()){
            gamecam.position.y+= 100 * delta;
        }
    }

    public void update(float delta){
        handleInput(delta);
        gamecam.update(); // Always update our game came every iteration of our render cycle
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();


        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


       // batch.setProjectionMatrix(gamecam.combined);



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

    }
}
