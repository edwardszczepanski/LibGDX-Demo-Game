package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edwardszczepanski.ninecircles.NineCircles;


public class MenuScreen implements Screen{
    private NineCircles game;


    public MenuScreen(NineCircles game){
        this.game = game;

    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched()){
            //game.setScreen(new PlayScreen(game, batch));
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



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
