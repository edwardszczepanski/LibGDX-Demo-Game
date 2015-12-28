package com.edwardszczepanski.ninecircles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edwardszczepanski.ninecircles.Screens.MenuScreen;
import com.edwardszczepanski.ninecircles.Screens.PlayScreen;

public class NineCircles extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
	private SpriteBatch batch;
	Game game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		game = this;
		setScreen(new MenuScreen(game, batch));
	}

	@Override
	public void render () {
		super.render();
	}
}
