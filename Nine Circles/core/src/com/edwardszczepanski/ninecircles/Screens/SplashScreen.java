package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.edwardszczepanski.ninecircles.NineCircles;
import com.edwardszczepanski.ninecircles.Tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;


public class SplashScreen implements Screen{
    private NineCircles game;
    private Sprite splash;
    private TweenManager tweenManager;

    public SplashScreen(NineCircles game){
        this.game = game;
    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched()){
            game.setScreen(new MenuScreen(game));
        }
    }

    public void update(float delta){

        splash.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        handleInput(delta);
    }

    @Override
    public void show() {

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Texture stamps = new Texture(Gdx.files.internal("stamps.png"));
        splash = new Sprite(stamps);
        splash.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 1.5f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new PlayScreen(game));
            }
        }).start(tweenManager);

    }

    @Override
    public void render(float delta) {
        update(delta);
        tweenManager.update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1); // Color then opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        splash.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        splash.setCenter(width / 2, height / 2);
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
        game.batch.dispose();
        splash.getTexture().dispose();
    }
}
