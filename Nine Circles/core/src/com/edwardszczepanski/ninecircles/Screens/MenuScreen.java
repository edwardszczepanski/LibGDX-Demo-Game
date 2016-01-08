package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.edwardszczepanski.ninecircles.NineCircles;


public class MenuScreen implements Screen{
    private NineCircles game;

    private Sprite splash;

    public MenuScreen(NineCircles game){
        this.game = game;
        Texture stamps = new Texture(Gdx.files.internal("stamps.png"));
        splash = new Sprite(stamps);
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched()){
            game.setScreen(new PlayScreen(game));

        }
    }

    public void update(float delta){
        handleInput(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1); // Color then opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        splash.draw(game.batch);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

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
