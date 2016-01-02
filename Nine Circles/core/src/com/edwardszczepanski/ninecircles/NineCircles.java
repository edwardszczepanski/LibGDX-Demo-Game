package com.edwardszczepanski.ninecircles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edwardszczepanski.ninecircles.Screens.MenuScreen;

public class NineCircles extends Game {
    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 480;
	public static final float PPM = 50;
	public static final short DEFAULT_BIT = 1;
	public static final short HERO_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short BRICK_BIT = 8;
	public static final short BULLET_BIT = 16;
	public static final short DESTROYED_BIT = 32;

	public SpriteBatch batch;
	private NineCircles game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		game = this;
		setScreen(new MenuScreen(game));
	}

	@Override
	public void render () {
		super.render();
	}
}
