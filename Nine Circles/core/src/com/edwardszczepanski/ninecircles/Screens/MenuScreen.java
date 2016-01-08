package com.edwardszczepanski.ninecircles.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.edwardszczepanski.ninecircles.NineCircles;

/**
 * Created by edwardszc on 1/7/16.
 */
public class MenuScreen implements Screen{
    private NineCircles game;
    private Stage stage;
    private Table table;
    private TextButton butonPlay, buttonExit;
    private Label heading;
    private Skin skin;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font12;

    public MenuScreen(NineCircles game){
        this.game = game;
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
        generator = new FreeTypeFontGenerator(Gdx.files.internal("BEBAS.ttf"));
        parameter.size = 12;
        font12 = generator.generateFont(parameter);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 1, 1, 1); // Color then opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        font12.draw(game.batch, "Ayy fuccboi", 100, 100);
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
        generator.dispose();
    }
}
