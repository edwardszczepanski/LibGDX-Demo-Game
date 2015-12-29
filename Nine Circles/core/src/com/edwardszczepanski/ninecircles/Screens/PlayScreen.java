package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Scenes.Hud;
import com.edwardszczepanski.ninecircles.Sprites.Hero;


public class PlayScreen implements Screen {
    private NineCircles game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    // Sprites
    private Hero player;

    // Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(NineCircles game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NineCircles.V_WIDTH / NineCircles.PPM, NineCircles.V_HEIGHT / NineCircles.PPM, gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("desert.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / NineCircles.PPM); // This can take in a second value which is scale
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() /2, 0);


        world = new World(new Vector2(0, 0), true); // Here are the gravity values. I don't want gravity
        b2dr = new Box2DDebugRenderer();

        // This will be cleaned up
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2) / NineCircles.PPM, (rect.getY() + rect.getHeight()/2) / NineCircles.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / NineCircles.PPM, rect.getHeight() / 2 / NineCircles.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        player = new Hero(world);
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -2){
            player.b2body.applyLinearImpulse(new Vector2(0, -4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(4f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-4f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float delta){
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

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


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
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
